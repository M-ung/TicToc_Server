package org.tictoc.tictoc.domain.auction.service.auction.query;

import org.springframework.data.domain.Pageable;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.auction.response.AuctionResponseDTO;
import org.tictoc.tictoc.global.common.entity.PageCustom;

public interface AuctionQueryService {
    PageCustom<AuctionResponseDTO.Auction> getAuctionsByFilter(AuctionRequestDTO.Filter requestDTO, Pageable pageable);
}
