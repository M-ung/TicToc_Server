package tictoc.bid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.dto.request.WinningBidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.bid.port.BidQueryUseCase;
import tictoc.bid.port.BidRepositoryPort;
import tictoc.model.page.PageCustom;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidQueryService implements BidQueryUseCase {
    private final BidRepositoryPort bidRepositoryPort;

    @Override
    public PageCustom<BidUseCaseResDTO.Bid> getBidsByFilter(final Long userId, BidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return bidRepositoryPort.findBidsByFilterWithPageable(userId, requestDTO, pageable);
    }

    @Override
    public PageCustom<BidUseCaseResDTO.WinningBid> getWinningBidsByFilter(Long userId, WinningBidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return bidRepositoryPort.findWinningBidsByFilterWithPageable(requestDTO, pageable);
    }
}