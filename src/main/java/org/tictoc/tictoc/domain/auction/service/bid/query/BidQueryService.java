package org.tictoc.tictoc.domain.auction.service.bid.query;

import org.springframework.data.domain.Pageable;
import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.bid.response.BidResponseDTO;
import org.tictoc.tictoc.global.common.entity.PageCustom;

public interface BidQueryService {
    PageCustom<BidResponseDTO.Bid> getBidsByFilter(final Long userId, BidRequestDTO.Filter requestDTO, Pageable pageable);
}
