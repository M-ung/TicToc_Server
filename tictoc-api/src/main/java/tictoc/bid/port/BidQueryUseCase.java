package tictoc.bid.port;

import org.springframework.data.domain.Pageable;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.dto.request.WinningBidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.model.page.PageCustom;

public interface BidQueryUseCase {
    PageCustom<BidUseCaseResDTO.Bid> getBidsByFilter(final Long userId, BidUseCaseReqDTO.Filter requestDTO, Pageable pageable);
    PageCustom<BidUseCaseResDTO.WinningBid> getWinningBidsByFilter(final Long userId, WinningBidUseCaseReqDTO.Filter requestDTO, Pageable pageable);
}
