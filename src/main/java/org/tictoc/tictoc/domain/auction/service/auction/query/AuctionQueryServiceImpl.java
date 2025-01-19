package org.tictoc.tictoc.domain.auction.service.auction.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.auction.response.AuctionResponseDTO;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.global.common.entity.PageCustom;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionQueryServiceImpl implements AuctionQueryService {
    private final AuctionRepository auctionRepository;

    @Override
    public PageCustom<AuctionResponseDTO.Auction> getAuctionsByFilter(AuctionRequestDTO.Filter requestDTO, Pageable pageable) {
        return auctionRepository.findAuctionsByFilterWithPageable(requestDTO, pageable);
    }
}