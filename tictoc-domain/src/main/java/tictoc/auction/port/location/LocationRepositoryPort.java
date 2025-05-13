package tictoc.auction.port.location;

import tictoc.auction.dto.request.AuctionUseCaseReqDTO;

public interface LocationRepositoryPort {
    Long findLocationIdByFilter(AuctionUseCaseReqDTO.Location location);
}