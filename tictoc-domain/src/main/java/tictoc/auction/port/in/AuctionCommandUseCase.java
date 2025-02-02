package tictoc.auction.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;

public interface AuctionCommandUseCase {
    void register(final Long userId, AuctionUseCaseReqDTO.Register requestDTO) throws JsonProcessingException;
    void update(final Long userId, final Long auctionId, AuctionUseCaseReqDTO.Update requestDTO);
    void delete(final Long userId, final Long auctionId);
}
