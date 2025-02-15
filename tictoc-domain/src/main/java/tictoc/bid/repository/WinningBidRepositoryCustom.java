package tictoc.bid.repository;

import org.springframework.data.domain.Pageable;
import tictoc.bid.dto.request.WinningBidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.model.page.PageCustom;

public interface WinningBidRepositoryCustom {
    PageCustom<BidUseCaseResDTO.WinningBid> findWinningBidsByFilterWithPageable(WinningBidUseCaseReqDTO.Filter requestDTO, Pageable pageable);
}
