package tictoc.bid.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.bid.model.type.BidStatus;

public class BidReqDTO {
    public record Bid(
            @NotNull Long auctionId,
            @NotNull Integer price
    ) {}
    public record Filter (
            @Nullable BidStatus bidStatus,
            @Nullable AuctionProgress auctionProgress
    ) {}
}