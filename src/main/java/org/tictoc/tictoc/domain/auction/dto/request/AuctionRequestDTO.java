package org.tictoc.tictoc.domain.auction.dto.request;

import org.tictoc.tictoc.domain.auction.entity.Zone;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;

import java.time.LocalDateTime;
import java.util.List;

public class AuctionRequestDTO {
    public record Register(
            String title,
            String content,
            Integer startPrice,
            LocalDateTime sellStartTime,
            LocalDateTime sellEndTime,
            LocalDateTime auctionCloseTime,
            List<Zone> zones,
            AuctionType type
    ) {}
    public record Update(
            String title,
            String content,
            Integer startPrice,
            LocalDateTime sellStartTime,
            LocalDateTime sellEndTime,
            LocalDateTime auctionCloseTime,
            List<Zone> zones,
            AuctionType type
    ) {}
}
