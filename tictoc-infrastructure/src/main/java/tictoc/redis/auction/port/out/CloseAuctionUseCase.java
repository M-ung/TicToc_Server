package tictoc.redis.auction.port.out;

import java.time.LocalDateTime;

public interface CloseAuctionUseCase {
    void save(Long auctionId, LocalDateTime auctionEndTime);
    void delete(final Long auctionId);
}
