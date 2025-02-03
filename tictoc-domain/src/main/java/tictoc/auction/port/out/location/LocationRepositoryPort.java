package tictoc.auction.port.out.location;

import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.model.location.AuctionLocation;

public interface LocationRepositoryPort {
    void saveAuctionLocation(AuctionLocation auctionLocation);
    AuctionLocation findAuctionLocationByIdOrThrow(Long auctionId);
    void deleteAuctionLocation(AuctionLocation auctionLocation);
    Long findLocationIdByFilterOrThrow(AuctionUseCaseReqDTO.Location location);
}