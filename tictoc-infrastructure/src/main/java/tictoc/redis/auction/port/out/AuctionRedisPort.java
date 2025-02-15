package tictoc.redis.auction.port.out;

import java.time.LocalDateTime;

public interface AuctionRedisPort {
    void saveAuctionClose(Long auctionId, LocalDateTime sellEndTime);
    Long getAuctionClose(Long auctionId);
    void deleteAuctionClose(Long auctionId);
}
