package org.tictoc.tictoc.domain.auction.service.auction.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;

public interface AuctionCommandService {
    void register(final Long userId, AuctionRequestDTO.Register requestDTO) throws JsonProcessingException;
    void update(final Long userId, final Long auctionId, AuctionRequestDTO.Update requestDTO);
    void delete(final Long userId, final Long auctionId);
}
