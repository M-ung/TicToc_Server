package org.tictoc.tictoc.domain.auction.service.auction.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.entity.location.AuctionLocation;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.auction.exception.auction.*;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.domain.auction.repository.location.AuctionLocationRepository;
import org.tictoc.tictoc.domain.auction.repository.location.LocationRepository;
import org.tictoc.tictoc.domain.auction.service.location.LocationCommandService;
import org.tictoc.tictoc.infra.kafka.dto.KafkaAuctionMessageDTO;
import org.tictoc.tictoc.infra.kafka.producer.AuctionCloseProducer;
import org.tictoc.tictoc.infra.redis.dto.RedisAuctionMessageDTO;
import org.tictoc.tictoc.infra.redis.service.RedisAuctionService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import static org.tictoc.tictoc.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionCommandServiceImpl implements AuctionCommandService {
    private final RedisAuctionService redisAuctionService;
    private final LocationCommandService locationCommandService;
    private final AuctionRepository auctionRepository;
    private final AuctionCloseProducer auctionCloseProducer;

    @Override
    public void register(final Long userId, AuctionRequestDTO.Register requestDTO) throws JsonProcessingException {
        auctionRepository.validateAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        var auction = auctionRepository.save(Auction.of(userId, requestDTO));
        var auctionId = auction.getId();
        if (!requestDTO.type().equals(AuctionType.ONLINE)) {
            locationCommandService.saveAuctionLocations(auctionId, requestDTO.locations());
        }
        scheduleAuctionClose(auctionId, auction);
    }

    @Override
    public void update(final Long userId, final Long auctionId, AuctionRequestDTO.Update requestDTO) {
        var findAuction = auctionRepository.findByIdOrThrow(auctionId);
        findAuction.validateAuctionAccess(userId);
        auctionRepository.validateAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        try {
            scheduleAuctionClose(auctionId, updateAuction(findAuction, requestDTO));
        } catch (OptimisticLockingFailureException | JsonProcessingException e) {
            throw new ConflictAuctionUpdateException(CONFLICT_AUCTION_UPDATE);
        }
    }

    @Override
    public void delete(final Long userId, final Long auctionId) {
        Auction findAuction = auctionRepository.findByIdOrThrow(auctionId);
        try {
            findAuction.deactivate(userId);
            redisAuctionService.delete(auctionId);
        } catch (OptimisticLockingFailureException e) {
            throw new ConflictAuctionDeleteException(CONFLICT_AUCTION_DELETE);
        }
    }

    private Auction updateAuction(Auction auction, AuctionRequestDTO.Update requestDTO) throws JsonProcessingException {
        auction.update(requestDTO);
        locationCommandService.deleteAuctionLocations(auction.getId());
        if (!requestDTO.type().equals(AuctionType.ONLINE)) {
            locationCommandService.saveAuctionLocations(auction.getId(), requestDTO.locations());
        }
        redisAuctionService.delete(auction.getId());
        return auction;
    }

    private void scheduleAuctionClose(final Long auctionId, Auction auction) throws JsonProcessingException {
        var delayMillis = ChronoUnit.MILLIS.between(LocalDateTime.now(), auction.getAuctionCloseTime());
        redisAuctionService.save(
                RedisAuctionMessageDTO.auctionClose.of(auctionId, delayMillis, auction)
        );
        auctionCloseProducer.send(
                KafkaAuctionMessageDTO.AuctionClose.of(auctionId, delayMillis)
        );
    }
}