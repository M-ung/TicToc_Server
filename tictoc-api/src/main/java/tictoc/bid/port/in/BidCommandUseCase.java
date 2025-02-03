package tictoc.bid.port.in;


import tictoc.bid.dto.request.BidUseCaseReqDTO;

public interface BidCommandUseCase {
    void bid(final Long userId, BidUseCaseReqDTO.Bid requestDTO);
}
