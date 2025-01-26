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
import org.tictoc.tictoc.domain.auction.exception.location.AuctionLocationNotFoundException;
import org.tictoc.tictoc.domain.auction.exception.location.LocationIdNotFoundException;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.domain.auction.repository.location.AuctionLocationRepository;
import org.tictoc.tictoc.domain.auction.repository.location.LocationRepository;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;
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
    private final AuctionCloseProducer auctionCloseProducer;
    private final RedisAuctionService redisAuctionService;
    private final AuctionRepository auctionRepository;
    private final AuctionLocationRepository auctionLocationRepository;
    private final LocationRepository locationRepository;

    @Override
    public void register(final Long userId, AuctionRequestDTO.Register requestDTO) throws JsonProcessingException {
        checkAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        var auction = saveAuction(userId, requestDTO);
        var auctionId = auction.getId();
        if (!requestDTO.type().equals(AuctionType.ONLINE)) {
            saveAuctionLocations(auctionId, requestDTO.locations());
        }
        scheduleAuctionClose(auctionId, auction);
    }

    @Override
    public void update(final Long userId, final Long auctionId, AuctionRequestDTO.Update requestDTO) {
        validateAuctionAccess(userId, auctionId);
        checkAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        try {
            var auction = updateAuction(auctionId, requestDTO);
            scheduleAuctionClose(auctionId, auction);
        } catch (OptimisticLockingFailureException | JsonProcessingException e) {
            throw new ConflictAuctionUpdateException(CONFLICT_AUCTION_UPDATE);
        }
    }

    @Override
    public void delete(final Long userId, final Long auctionId) {
        validateAuctionAccess(userId, auctionId);
        try {
            findAuctionById(auctionId).deactivate();
            redisAuctionService.delete(auctionId);
        } catch (OptimisticLockingFailureException e) {
            throw new ConflictAuctionDeleteException(CONFLICT_AUCTION_DELETE);
        }
    }

    private Auction saveAuction(final Long userId, AuctionRequestDTO.Register requestDTO) {
        return auctionRepository.save(Auction.of(userId, requestDTO));
    }

    private void saveAuctionLocations(Long auctionId, List<AuctionRequestDTO.Location> locations) {
        for (AuctionRequestDTO.Location location : locations) {
            auctionLocationRepository.save(AuctionLocation.of(auctionId, findLocationIdByLocation(location)));
        }
    }

    private Auction updateAuction(final Long auctionId, AuctionRequestDTO.Update requestDTO) throws JsonProcessingException {
        var auction = findAuctionById(auctionId);
        auction.update(requestDTO);
        deleteAuctionLocationByAuctionId(auctionId);
        if (!requestDTO.type().equals(AuctionType.ONLINE)) {
            saveAuctionLocations(auctionId, requestDTO.locations());
        }
        redisAuctionService.delete(auctionId);
        return auction;
    }

    private void deleteAuctionLocationByAuctionId(final Long auctionId) {
        auctionLocationRepository.delete(findAuctionLocationByAuctionId(auctionId));
    }

    private Auction findAuctionById(final Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(AUCTION_NOT_FOUND));
    }

    private AuctionLocation findAuctionLocationByAuctionId(final Long auctionId) {
        return auctionLocationRepository.findByAuctionId(auctionId)
                .orElseThrow(() -> new AuctionLocationNotFoundException(AUCTION_LOCATION_NOT_FOUND));
    }

    private Long findLocationIdByLocation(AuctionRequestDTO.Location location) {
        return locationRepository.findLocationIdByFilter(location)
                .orElseThrow(() -> new LocationIdNotFoundException(LOCATION_NOT_FOUND));
    }

    private void validateAuctionAccess(final Long userId, final Long auctioneerId) {
        if(!userId.equals(auctioneerId)) {
            throw new AuctionNoAccessException(AUCTION_NO_ACCESS);
        }
    }

    private void checkAuctionTimeRange(Long userId, LocalDateTime sellStartTime, LocalDateTime sellEndTime) {
        if(auctionRepository.existsAuctionInTimeRange(userId, sellStartTime, sellEndTime)) {
            throw new DuplicateAuctionDateException(DUPLICATE_AUCTION_DATE);
        }
    }

    private void scheduleAuctionClose(final Long auctionId, Auction auction) throws JsonProcessingException {
        var delayMillis = ChronoUnit.MILLIS.between(LocalDateTime.now(), auction.getAuctionCloseTime());
        redisAuctionService.save(
                RedisAuctionMessageDTO.auctionClose.of(auctionId, delayMillis, auction)
        );
        auctionCloseProducer.send(
                KafkaAuctionMessageDTO.auctionClose.of(auctionId, delayMillis)
        );
    }
}