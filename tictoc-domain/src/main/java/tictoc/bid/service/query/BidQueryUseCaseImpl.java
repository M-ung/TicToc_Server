package tictoc.bid.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.bid.port.in.BidQueryUseCase;
import tictoc.bid.port.out.BidAdaptor;
import tictoc.model.page.PageCustom;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidQueryUseCaseImpl implements BidQueryUseCase {
    private final BidAdaptor bidAdaptor;

    @Override
    public PageCustom<BidUseCaseResDTO.Bid> getBidsByFilter(final Long userId, BidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return bidAdaptor.findBidsByFilterWithPageable(userId, requestDTO, pageable);
    }
}