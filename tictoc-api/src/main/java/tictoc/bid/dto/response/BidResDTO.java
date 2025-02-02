package tictoc.bid.dto.response;

import tictoc.auction.model.type.AuctionProgress;
import tictoc.bid.model.type.BidStatus;

public class BidResDTO {
    public record Bid(
            Long auctionId,
            String title,
            Integer myPrice,
            Integer currentPrice,
            BidStatus bidStatus,
            AuctionProgress auctionProgress
    ) {}
}