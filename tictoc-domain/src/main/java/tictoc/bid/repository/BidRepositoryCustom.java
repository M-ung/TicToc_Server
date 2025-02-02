package tictoc.bid.repository;

import org.springframework.data.domain.Pageable;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.model.page.PageCustom;

public interface BidRepositoryCustom {
    PageCustom<BidUseCaseResDTO.Bid> findBidsByFilterWithPageable(final Long userId, BidUseCaseReqDTO.Filter requestDTO, Pageable pageable);
}