package tictoc.redis.auction.port.out;

import java.time.LocalDateTime;

public interface AuctionRedisPort {
    void saveClosedAuction(Long auctionId, LocalDateTime auctionEndTime);
    void deleteClosedAuction(final Long auctionId);
}
