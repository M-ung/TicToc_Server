package org.tictoc.tictoc.domain.auction.repository.bid;

import org.springframework.data.domain.Pageable;
import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.bid.response.BidResponseDTO;
import org.tictoc.tictoc.global.common.entity.page.PageCustom;

public interface BidRepositoryCustom {
    PageCustom<BidResponseDTO.Bid> findBidsByFilterWithPageable(final Long userId, BidRequestDTO.Filter requestDTO, Pageable pageable);
}