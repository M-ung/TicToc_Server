package org.tictoc.tictoc.domain.auction.repository.auction;

import org.springframework.data.domain.Pageable;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.response.AuctionResponseDTO;
import org.tictoc.tictoc.global.common.entity.PageCustom;


public interface AuctionRepositoryCustom {
    PageCustom<AuctionResponseDTO.Auctions> findAuctionsByFilterWithPageable(AuctionRequestDTO.Filter requestDTO, Pageable pageable);
}