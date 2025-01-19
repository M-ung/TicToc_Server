package org.tictoc.tictoc.domain.auction.service.auction.query;

import org.springframework.data.domain.Pageable;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.response.AuctionResponseDTO;
import org.tictoc.tictoc.global.common.entity.PageCustom;

public interface AuctionQueryService {
    PageCustom<AuctionResponseDTO.Auctions> getAuctionsByFilter(AuctionRequestDTO.Filter requestDTO, Pageable pageable);
}
