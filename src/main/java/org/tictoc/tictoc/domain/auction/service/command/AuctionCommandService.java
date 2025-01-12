package org.tictoc.tictoc.domain.auction.service.command;

import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;

public interface AuctionCommandService {
    void register(final Long userId, AuctionRequestDTO.Register requestDTO);
    void update(final Long userId, final Long auctionId, AuctionRequestDTO.Update requestDTO);
    void delete(final Long userId, final Long auctionId);
}
