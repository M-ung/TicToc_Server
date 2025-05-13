package tictoc.auction.port.location;

import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import java.util.List;

public interface LocationCommandUseCase {
    void save(Long auctionId, List<AuctionUseCaseReqDTO.Location> locations);
    void delete(Long auctionId);
}
