package org.tictoc.tictoc.domain.auction.service.bid.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.entity.bid.Bid;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.domain.auction.repository.bid.BidRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BidCommandServiceImpl implements BidCommandService {
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;

    @Override
    public void bid(final Long userId, BidRequestDTO.Bid requestDTO) {
        var auction = auctionRepository.findByIdOrThrow(requestDTO.auctionId());
        checkBeforeBid(auction);
        auction.startAuction(userId);
        bidRepository.save(Bid.of(userId, requestDTO));
        auction.increaseBid(requestDTO.price());
    }

    private void checkBeforeBid(Auction auction) {
        if (auction.getProgress().equals(AuctionProgress.IN_PROGRESS)) {
            bidRepository.findByAuctionIdAndStatusOrThrow(auction.getId()).fail();
        }
    }
}