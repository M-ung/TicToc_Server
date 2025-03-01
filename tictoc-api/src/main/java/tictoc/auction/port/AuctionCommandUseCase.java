package tictoc.auction.port;

import tictoc.auction.dto.request.AuctionUseCaseReqDTO;

public interface AuctionCommandUseCase {
    void register(final Long userId, AuctionUseCaseReqDTO.Register requestDTO);
    void update(final Long userId, final Long auctionId, AuctionUseCaseReqDTO.Update requestDTO);
    void delete(final Long userId, final Long auctionId);
}
