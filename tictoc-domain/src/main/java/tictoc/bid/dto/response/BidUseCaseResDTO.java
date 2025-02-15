package tictoc.bid.dto.response;

import tictoc.auction.model.type.AuctionProgress;
import tictoc.bid.model.type.BidStatus;
import java.time.LocalDateTime;

public class BidUseCaseResDTO {
    public record Bid(
            Long auctionId,
            String title,
            Integer myPrice,
            Integer currentPrice,
            BidStatus bidStatus,
            AuctionProgress auctionProgress
    ) {}
    public record WinningBid(
            Integer price,
            LocalDateTime winningBidDate,
            LocalDateTime sellStartTime,
            LocalDateTime sellEndTime
    ) {}
}
