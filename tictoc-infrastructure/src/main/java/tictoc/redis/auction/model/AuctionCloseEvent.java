package tictoc.redis.auction.model;

public record AuctionCloseEvent (
        Long auctionId,
        long auctionCloseMillis
) {
    public static AuctionCloseEvent of(Long auctionId, long auctionCloseMillis) {
        return new AuctionCloseEvent(auctionId, auctionCloseMillis);
    }
}