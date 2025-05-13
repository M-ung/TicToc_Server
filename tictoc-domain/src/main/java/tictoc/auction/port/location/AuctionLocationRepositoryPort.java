package tictoc.auction.port.location;
import tictoc.auction.model.location.AuctionLocation;

public interface AuctionLocationRepositoryPort {
    void save(AuctionLocation auctionLocation);
    AuctionLocation findAuctionLocationById(Long auctionId);
    void delete(AuctionLocation auctionLocation);
}