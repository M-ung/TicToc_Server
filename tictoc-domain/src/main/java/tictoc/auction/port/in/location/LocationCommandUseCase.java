package tictoc.auction.port.in.location;

import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import java.util.List;

public interface LocationCommandUseCase {
    void saveAuctionLocations(Long auctionId, List<AuctionUseCaseReqDTO.Location> locations);
    void deleteAuctionLocations(Long auctionId);
}
