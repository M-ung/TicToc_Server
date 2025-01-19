package org.tictoc.tictoc.domain.auction.dto.bid.response;

import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.BidStatus;

public class BidResponseDTO {
    public record Bid(
            Long auctionId,
            String title,
            Integer myPrice,
            Integer currentPrice,
            BidStatus bidStatus,
            AuctionProgress auctionProgress
    ) {}
}