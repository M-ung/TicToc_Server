package tictoc.auction.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tictoc.auction.model.Auction;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.redis.constants.AuctionRedisConstants;
import java.time.*;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuctionExpirationWorker {
    private final StringRedisTemplate redisTemplate;
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final String auctionCloseKey = AuctionRedisConstants.AUCTION_CLOSE_KEY;

    @Scheduled(fixedRate = 5000)
    public void checkExpiredAuctions() {
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        Set<String> closedAuctionIds = redisTemplate.opsForZSet().rangeByScore(auctionCloseKey, 0, now);
        for (String auctionId : closedAuctionIds) {
            Auction findAuction = auctionRepositoryPort.findAuctionByIdOrThrow(Long.parseLong(auctionId));
            if (findAuction.getProgress().equals(AuctionProgress.NOT_STARTED)) {
                findAuction.notBid();
            } else {
                findAuction.bid();
            }
            auctionRepositoryPort.saveAuction(findAuction);
            redisTemplate.opsForZSet().remove(auctionCloseKey, auctionId);
        }
    }
}