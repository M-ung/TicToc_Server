package tictoc.auction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.model.Auction;
import tictoc.auction.port.in.AuctionCommandUseCase;
import tictoc.auction.port.out.AuctionAdaptor;
import tictoc.auction.model.type.AuctionType;
import tictoc.auction.exception.ConflictAuctionDeleteException;
import tictoc.auction.exception.ConflictAuctionUpdateException;
import tictoc.auction.port.in.location.LocationCommandUseCase;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import static tictoc.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionCommandService implements AuctionCommandUseCase {
    private final LocationCommandUseCase locationCommandUseCase;
    private final AuctionAdaptor auctionAdaptor;

    @Override
    public void register(final Long userId, AuctionUseCaseReqDTO.Register requestDTO) throws JsonProcessingException {
        auctionAdaptor.validateAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        var auction = auctionAdaptor.saveAuctionEntity(Auction.of(userId, requestDTO));
        var auctionId = auction.getId();
        if (!requestDTO.type().equals(AuctionType.ONLINE)) {
            locationCommandUseCase.saveAuctionLocations(auctionId, requestDTO.locations());
        }
        scheduleAuctionClose(auctionId, auction);
    }

    @Override
    public void update(final Long userId, final Long auctionId, AuctionUseCaseReqDTO.Update requestDTO) {
        var findAuction = auctionAdaptor.findAuctionByIdOrThrow(auctionId);
        findAuction.validateAuctionAccess(userId);
        auctionAdaptor.validateAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        try {
            scheduleAuctionClose(auctionId, updateAuction(findAuction, requestDTO));
        } catch (OptimisticLockingFailureException | JsonProcessingException e) {
            throw new ConflictAuctionUpdateException(CONFLICT_AUCTION_UPDATE);
        }
    }

    @Override
    public void delete(final Long userId, final Long auctionId) {
        var findAuction = auctionAdaptor.findAuctionByIdOrThrow(auctionId);
        try {
            findAuction.deactivate(userId);
//            redisAuctionService.delete(auctionId);
        } catch (OptimisticLockingFailureException e) {
            throw new ConflictAuctionDeleteException(CONFLICT_AUCTION_DELETE);
        }
    }

    private Auction updateAuction(Auction auction, AuctionUseCaseReqDTO.Update requestDTO) throws JsonProcessingException {
        auction.update(requestDTO);
        locationCommandUseCase.deleteAuctionLocations(auction.getId());
        if (!requestDTO.type().equals(AuctionType.ONLINE)) {
            locationCommandUseCase.saveAuctionLocations(auction.getId(), requestDTO.locations());
        }
//        redisAuctionService.delete(auctionEntity.getId());
        return auction;
    }

    private void scheduleAuctionClose(final Long auctionId, Auction auctionDomain) throws JsonProcessingException {
        var delayMillis = ChronoUnit.MILLIS.between(LocalDateTime.now(), auctionDomain.getAuctionCloseTime());
//        redisAuctionService.save(
//                RedisAuctionMessageDTO.auctionClose.of(auctionId, delayMillis, auctionEntity)
//        );
//        auctionCloseProducer.send(
//                KafkaAuctionMessageDTO.AuctionClose.of(auctionId, delayMillis)
//        );
//        public void process(KafkaAuctionMessageDTO.AuctionClose message) {
//            var auctionId = message.auctionId();
//            var isInRedis = redisAuctionService.exists(auctionId);
//            var auctionEntity = isInRedis ? redisAuctionService.find(auctionId) : auctionRepository.findByIdOrThrow(auctionId);
//            close(auctionEntity, isInRedis);
//        }
//
//        private void close(Auction auctionEntity, boolean isInRedis) {
//            if(auctionEntity.getProgress().equals(AuctionProgress.IN_PROGRESS)) {
//                auctionEntity.bid();
//                var bid = bidRepository.findByAuctionIdAndStatusOrThrow(auctionEntity.getId());
//                bid.win();
//                winningBidRepository.save(WinningBid.of(auctionEntity, bid));
//            } else {
//                auctionEntity.notBid();
//            }
//            if (isInRedis) redisAuctionService.delete(auctionEntity.getId());
//            auctionRepository.save(auctionEntity);
//        }
    }
}