package org.tictoc.tictoc.domain.auction.dto.request;

import jakarta.validation.constraints.NotNull;
import org.tictoc.tictoc.domain.auction.entity.Zone;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;

import java.time.LocalDateTime;
import java.util.List;

public class AuctionRequestDTO {
    public record Register(
            @NotNull String title,
            @NotNull String content,
            @NotNull Integer startPrice,
            @NotNull LocalDateTime sellStartTime,
            @NotNull LocalDateTime sellEndTime,
            @NotNull LocalDateTime auctionCloseTime,
            @NotNull List<Zone> zones,
            @NotNull AuctionType type
    ) {}
    public record Update(
            @NotNull String title,
            @NotNull String content,
            @NotNull Integer startPrice,
            @NotNull LocalDateTime sellStartTime,
            @NotNull LocalDateTime sellEndTime,
            @NotNull LocalDateTime auctionCloseTime,
            @NotNull List<Zone> zones,
            @NotNull AuctionType type
    ) {}
}
