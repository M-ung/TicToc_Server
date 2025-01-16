package org.tictoc.tictoc.domain.auction.dto.response;

import org.tictoc.tictoc.domain.auction.entity.Zone;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;

import java.time.LocalDateTime;
import java.util.List;

public class AuctionResponseDTO {
    public record Auctions(
            Long auctionId,
            String title,
            Integer startPrice,
            Integer currentPrice,
            LocalDateTime sellStartTime,
            LocalDateTime sellEndTime,
            LocalDateTime auctionOpenTime,
            LocalDateTime auctionCloseTime,
            List<Zone> zones,
            AuctionProgress progress,
            AuctionType type
    ){}
}
