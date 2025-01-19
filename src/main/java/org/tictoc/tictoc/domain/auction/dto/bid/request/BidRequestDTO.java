package org.tictoc.tictoc.domain.auction.dto.bid.request;

import jakarta.annotation.Nullable;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.BidStatus;

public class BidRequestDTO {
    public record Register(
    ) {}
    public record Filter (
            @Nullable BidStatus bidStatus,
            @Nullable AuctionProgress auctionProgress
    ) {}
}