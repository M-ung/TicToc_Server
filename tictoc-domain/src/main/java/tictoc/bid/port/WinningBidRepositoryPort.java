package tictoc.bid.port;

import org.springframework.data.domain.Pageable;
import tictoc.bid.dto.request.WinningBidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.bid.model.WinningBid;
import tictoc.model.page.PageCustom;

public interface WinningBidRepositoryPort {
    PageCustom<BidUseCaseResDTO.WinningBid> findWinningBidsByFilterWithPageable(WinningBidUseCaseReqDTO.Filter requestDTO, Pageable pageable);
    void saveWinningBid(WinningBid winningBid);
}
