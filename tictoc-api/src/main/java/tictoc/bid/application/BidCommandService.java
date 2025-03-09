package tictoc.bid.application;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.exception.BidException;
import tictoc.bid.model.Bid;
import tictoc.bid.port.BidCommandUseCase;
import tictoc.bid.port.BidRepositoryPort;
import tictoc.constants.RedisConstants;
import java.util.concurrent.TimeUnit;
import static tictoc.error.ErrorCode.BID_FAIL;

@Service
@Transactional
@RequiredArgsConstructor
public class BidCommandService implements BidCommandUseCase {
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final BidRepositoryPort bidRepositoryPort;
    private final RedissonClient redissonClient;

    @Override
    @Transactional
    public void bid(final Long userId, BidUseCaseReqDTO.Bid requestDTO) {
        String lockKey = RedisConstants.LOCK_PREFIX + requestDTO.auctionId();
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (!lock.tryLock(Math.round(RedisConstants.LOCK_WAIT_TIME), RedisConstants.LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
                throw new BidException(BID_FAIL);
            }
            var findAuction = auctionRepositoryPort.findAuctionById(requestDTO.auctionId());

            bidRepositoryPort.checkBeforeBid(findAuction);
            findAuction.startBid(userId);

            Integer beforePrice = findAuction.getCurrentPrice();
            int updatedRows = auctionRepositoryPort.updateBidIfHigher(requestDTO);
            if (updatedRows == 0) {
                throw new BidException(BID_FAIL);
            }
            bidRepositoryPort.saveBid(Bid.of(userId, requestDTO, beforePrice));
            Thread.sleep(RedisConstants.LOCK_LEASE_TIME * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BidException(BID_FAIL);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}