package org.tictoc.tictoc.domain.auction.service.location;

import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import java.util.List;

public interface LocationCommandService {
    void saveAuctionLocations(Long auctionId, List<AuctionRequestDTO.Location> locations);
    void deleteAuctionLocations(Long auctionId);
}
