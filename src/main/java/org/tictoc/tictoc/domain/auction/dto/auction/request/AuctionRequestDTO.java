package org.tictoc.tictoc.domain.auction.dto.auction.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.auction.exception.auction.ValidateAuctionTimesException;
import org.tictoc.tictoc.global.error.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;

import static org.tictoc.tictoc.global.error.ErrorCode.INVALID_AUCTION_TIME_RANGE;

public class AuctionRequestDTO {
    public record Register(
            @NotNull String title,
            @NotNull String content,
            @NotNull Integer startPrice,
            @NotNull @Future(message = "sellStartTime 현재 시간보다 미래여야 합니다.") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            LocalDateTime sellStartTime,
            @NotNull @Future(message = "sellEndTime 현재 시간보다 미래여야 합니다.") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            LocalDateTime sellEndTime,
            @NotNull @Future(message = "auctionCloseTime 현재 시간보다 미래여야 합니다.") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            LocalDateTime auctionCloseTime,
            @Nullable List<AuctionRequestDTO.Location> locations,
            @NotNull AuctionType type
    ) implements AuctionTimesDTO {
        public Register {
            validateIsBefore(sellStartTime, sellEndTime);
        }
    }

    public record Update(
            @NotNull String title,
            @NotNull String content,
            @NotNull Integer startPrice,
            @NotNull @Future(message = "sellStartTime 현재 시간보다 미래여야 합니다.") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            LocalDateTime sellStartTime,
            @NotNull @Future(message = "sellEndTime 현재 시간보다 미래여야 합니다.") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            LocalDateTime sellEndTime,
            @NotNull @Future(message = "auctionCloseTime 현재 시간보다 미래여야 합니다.") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            LocalDateTime auctionCloseTime,
            @NotNull List<AuctionRequestDTO.Location> locations,
            @NotNull AuctionType type
    ) implements AuctionTimesDTO {
        public Update {
            validateIsBefore(sellStartTime, sellEndTime);
        }
    }

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
    ) {}

    private static void validateIsBefore(LocalDateTime sellStartTime, LocalDateTime sellEndTime) {
        if (!sellStartTime.isBefore(sellEndTime)) {
            throw new ValidateAuctionTimesException(INVALID_AUCTION_TIME_RANGE);
        }
    }
}