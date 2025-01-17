package org.tictoc.tictoc.domain.auction.repository.location;

import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;

import java.util.Optional;

public interface LocationRepositoryCustom {
    Optional<Long> findLocationIdByFilter(AuctionRequestDTO.Location requestDTO);
}
