package tictoc.bid.port;


import tictoc.bid.dto.request.BidUseCaseReqDTO;

public interface BidCommandUseCase {
    void bid(final Long userId, BidUseCaseReqDTO.Bid requestDTO);
}
