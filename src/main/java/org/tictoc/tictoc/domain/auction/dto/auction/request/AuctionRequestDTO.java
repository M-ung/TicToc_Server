package org.tictoc.tictoc.domain.auction.dto.auction.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import java.time.LocalDateTime;
import java.util.List;

public class AuctionRequestDTO {
    public record Register(
            @NotNull String title,
            @NotNull String content,
            @NotNull Integer startPrice,
            @NotNull
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
            LocalDateTime sellStartTime,
            @NotNull
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
            LocalDateTime sellEndTime,
            @NotNull
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
            LocalDateTime auctionCloseTime,
            @Nullable List<AuctionRequestDTO.Location> locations,
            @NotNull AuctionType type
    ) {}
    public record Update(
            @NotNull String title,
            @NotNull String content,
            @NotNull Integer startPrice,
            @NotNull
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
            LocalDateTime sellStartTime,
            @NotNull
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
            LocalDateTime sellEndTime,
            @NotNull
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
            LocalDateTime auctionCloseTime,
            @NotNull List<AuctionRequestDTO.Location> locations,
            @NotNull AuctionType type
    ) {}
    public record Filter(
            @Nullable Integer startPrice,
            @Nullable Integer endPrice,
            @Nullable LocalDateTime sellStartTime,
            @Nullable LocalDateTime sellEndTime,
            @Nullable List<AuctionRequestDTO.Location> locations,
            @Nullable AuctionProgress progress,
            @Nullable AuctionType type
    ) {}
    public record Location(
            @Nullable String region,
            @Nullable String city,
            @Nullable String district,
            @Nullable String subDistrict
    ){}
}
