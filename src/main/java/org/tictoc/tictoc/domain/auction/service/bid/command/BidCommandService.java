package org.tictoc.tictoc.domain.auction.service.bid.command;

import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;

public interface BidCommandService {
    //TODO 입찰하기 -> 입찰금 변경
    void bid(final Long userId, BidRequestDTO.Bid requestDTO);
}
