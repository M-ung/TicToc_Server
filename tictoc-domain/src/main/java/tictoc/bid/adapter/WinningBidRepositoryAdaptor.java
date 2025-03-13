package tictoc.bid.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import tictoc.annotation.Adapter;
import tictoc.bid.dto.request.WinningBidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.bid.model.WinningBid;
import tictoc.bid.port.WinningBidRepositoryPort;
import tictoc.bid.repository.WinningBidRepository;
import tictoc.model.page.PageCustom;

@Adapter
@RequiredArgsConstructor
public class WinningBidRepositoryAdaptor implements WinningBidRepositoryPort {
    private final WinningBidRepository winningBidRepository;

    @Override
    public PageCustom<BidUseCaseResDTO.WinningBid> findWinningBidsByFilterWithPageable(WinningBidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return winningBidRepository.findWinningBidsByFilterWithPageable(requestDTO, pageable);
    }

    @Override
    public void saveWinningBid(WinningBid winningBid) {
        winningBidRepository.save(winningBid);
    }
}
