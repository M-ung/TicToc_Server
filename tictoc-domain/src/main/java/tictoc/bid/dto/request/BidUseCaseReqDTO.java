package tictoc.bid.dto.request;

import tictoc.auction.model.type.AuctionProgress;
import tictoc.bid.model.type.BidStatus;

public class BidUseCaseReqDTO {
    public record Bid(
            Long auctionId,
            Integer price
    ) {}
    public record Filter (
            BidStatus bidStatus,
            AuctionProgress auctionProgress
    ) {}
}
