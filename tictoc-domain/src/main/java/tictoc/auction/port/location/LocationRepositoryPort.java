package tictoc.auction.port.location;

import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.model.location.AuctionLocation;

public interface LocationRepositoryPort {
    void saveAuctionLocation(AuctionLocation auctionLocation);
    AuctionLocation findAuctionLocationById(Long auctionId);
    void deleteAuctionLocation(AuctionLocation auctionLocation);
    Long findLocationIdByFilter(AuctionUseCaseReqDTO.Location location);
}