package org.tictoc.tictoc.domain.auction.service.bid.command;

import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;

public interface BidCommandService {
    void bid(final Long userId, BidRequestDTO.Bid requestDTO);
}
