package tictoc.auction.port.location;

import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.model.location.AuctionLocation;

public interface LocationRepositoryPort {
    void saveAuctionLocation(AuctionLocation auctionLocation);
    AuctionLocation findAuctionLocationByIdOrThrow(Long auctionId);
    void deleteAuctionLocation(AuctionLocation auctionLocation);
    Long findLocationIdByFilterOrThrow(AuctionUseCaseReqDTO.Location location);
}