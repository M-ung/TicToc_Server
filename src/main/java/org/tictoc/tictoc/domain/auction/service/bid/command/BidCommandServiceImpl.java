package org.tictoc.tictoc.domain.auction.service.bid.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.entity.bid.Bid;
import org.tictoc.tictoc.domain.auction.entity.type.BidStatus;
import org.tictoc.tictoc.domain.auction.exception.auction.AuctionNotFoundException;
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
    private final WinningBidRepository winningBidRepository;

    @Override
    public void bid(final Long userId, BidRequestDTO.Bid requestDTO) {
        Auction findAuction = findAuctionById(requestDTO.auctionId());
        findAuction.validateBid(requestDTO.price());
        findBidByAuctionId(requestDTO.auctionId()).fail();
        bidRepository.save(Bid.of(userId, requestDTO));
        findAuction.increaseBid(requestDTO.price());
    }

    private Auction findAuctionById(final Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(BID_NOT_FOUND));
    }

    private Bid findBidByAuctionId(final Long auctionId) {
        return bidRepository.findByAuctionIdAndStatus(auctionId, BidStatus.PROGRESS)
                .orElseThrow(() -> new AuctionNotFoundException(AUCTION_NOT_FOUND));
    }
}