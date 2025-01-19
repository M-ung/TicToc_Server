package org.tictoc.tictoc.domain.auction.service.bid.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.bid.response.BidResponseDTO;
import org.tictoc.tictoc.domain.auction.repository.bid.BidRepository;
import org.tictoc.tictoc.global.common.entity.PageCustom;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidQueryServiceImpl implements BidQueryService {
    private final BidRepository bidRepository;

    @Override
    public PageCustom<BidResponseDTO.Bid> getBidsByFilter(final Long userId, BidRequestDTO.Filter requestDTO, Pageable pageable) {
        return bidRepository.findBidsByFilterWithPageable(userId, requestDTO, pageable);
    }
}