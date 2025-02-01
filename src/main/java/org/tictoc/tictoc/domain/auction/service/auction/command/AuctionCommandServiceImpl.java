package org.tictoc.tictoc.domain.auction.service.auction.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.auction.exception.auction.*;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.domain.auction.service.location.LocationCommandService;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;
import org.tictoc.tictoc.infra.redis.dto.RedisAuctionMessageDTO;
import org.tictoc.tictoc.infra.redis.service.RedisAuctionService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import static org.tictoc.tictoc.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionCommandServiceImpl implements AuctionCommandService {
    private final RedisAuctionService redisAuctionService;
    private final LocationCommandService locationCommandService;
    private final AuctionRepository auctionRepository;

    @Override
    public void register(final Long userId, AuctionRequestDTO.Register requestDTO) throws JsonProcessingException {
        validateAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
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
        validateAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        try {
            scheduleAuctionClose(auctionId, updateAuction(findAuction, requestDTO));
        } catch (OptimisticLockingFailureException | JsonProcessingException e) {
            throw new ConflictAuctionUpdateException(CONFLICT_AUCTION_UPDATE);
        }
    }

    @Override
    public void delete(final Long userId, final Long auctionId) {
        var findAuction = auctionRepository.findByIdOrThrow(auctionId);
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

    private void validateAuctionTimeRange(Long userId, LocalDateTime sellStartTime, LocalDateTime sellEndTime) {
        if (auctionRepository.existsAuctionInTimeRange(userId, sellStartTime, sellEndTime, TicTocStatus.ACTIVE)) {
            throw new DuplicateAuctionDateException(DUPLICATE_AUCTION_DATE);
        }
    }

    private void scheduleAuctionClose(final Long auctionId, Auction auction) throws JsonProcessingException {
        var delayMillis = ChronoUnit.MILLIS.between(LocalDateTime.now(), auction.getAuctionCloseTime());
        redisAuctionService.save(
                RedisAuctionMessageDTO.auctionClose.of(auctionId, delayMillis, auction)
        );
//        auctionCloseProducer.send(
//                KafkaAuctionMessageDTO.AuctionClose.of(auctionId, delayMillis)
//        );
//        public void process(KafkaAuctionMessageDTO.AuctionClose message) {
//            var auctionId = message.auctionId();
//            var isInRedis = redisAuctionService.exists(auctionId);
//            var auction = isInRedis ? redisAuctionService.find(auctionId) : auctionRepository.findByIdOrThrow(auctionId);
//            close(auction, isInRedis);
//        }
//
//        private void close(Auction auction, boolean isInRedis) {
//            if(auction.getProgress().equals(AuctionProgress.IN_PROGRESS)) {
//                auction.bid();
//                var bid = bidRepository.findByAuctionIdAndStatusOrThrow(auction.getId());
//                bid.win();
//                winningBidRepository.save(WinningBid.of(auction, bid));
//            } else {
//                auction.notBid();
//            }
//            if (isInRedis) redisAuctionService.delete(auction.getId());
//            auctionRepository.save(auction);
//        }
    }
}