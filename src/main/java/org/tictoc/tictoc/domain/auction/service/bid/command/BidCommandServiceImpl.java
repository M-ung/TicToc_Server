package org.tictoc.tictoc.domain.auction.service.bid.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.bid.Bid;
import org.tictoc.tictoc.domain.auction.entity.type.BidStatus;
import org.tictoc.tictoc.domain.auction.exception.bid.BidNotFoundException;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.domain.auction.repository.bid.BidRepository;
import org.tictoc.tictoc.domain.auction.repository.bid.WinningBidRepository;
import static org.tictoc.tictoc.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BidCommandServiceImpl implements BidCommandService {
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;

    @Override
    public void bid(final Long userId, BidRequestDTO.Bid requestDTO) {
        var findAuction = auctionRepository.findByIdOrThrow(requestDTO.auctionId());
        findAuction.validateAuctionProgress();
        bidRepository.findByAuctionIdAndStatusOrThrow(requestDTO.auctionId()).fail();
        bidRepository.save(Bid.of(userId, requestDTO));
        findAuction.increaseBid(requestDTO.price());
    }
}