package tictoc.auction.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.model.type.AuctionType;
import tictoc.auction.exception.InvalidAuctionTimesException;
import java.time.LocalDateTime;
import java.util.List;
import static tictoc.error.ErrorCode.INVALID_AUCTION_TIME_RANGE;

public class AuctionReqDTO {
    public record Register(
            @NotNull String title,
            @NotNull String content,
            @NotNull Integer startPrice,
            @NotNull @Future(message = "sellStartTime 현재 시간보다 미래여야 합니다.")
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime sellStartTime,
            @NotNull @Future(message = "sellEndTime 현재 시간보다 미래여야 합니다.")
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime sellEndTime,
            @NotNull @Future(message = "auctionCloseTime 현재 시간보다 미래여야 합니다.")
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime auctionCloseTime,
            @Nullable List<Location> locations,
            @NotNull AuctionType type
    ) {
        public Register {
            validateIsBefore(sellStartTime, sellEndTime);
        }
    }

    public record Update(
            @NotNull String title,
            @NotNull String content,
            @NotNull Integer startPrice,
            @NotNull @Future(message = "sellStartTime 현재 시간보다 미래여야 합니다.")
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime sellStartTime,
            @NotNull @Future(message = "sellEndTime 현재 시간보다 미래여야 합니다.")
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime sellEndTime,
            @NotNull @Future(message = "auctionCloseTime 현재 시간보다 미래여야 합니다.")
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime auctionCloseTime,
            @NotNull List<Location> locations,
            @NotNull AuctionType type
    ) {
        public Update {
            validateIsBefore(sellStartTime, sellEndTime);
        }
    }

    public record Filter(
            @Nullable Integer startPrice,
            @Nullable Integer endPrice,
            @Nullable LocalDateTime sellStartTime,
            @Nullable LocalDateTime sellEndTime,
            @Nullable List<Location> locations,
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
            throw new InvalidAuctionTimesException(INVALID_AUCTION_TIME_RANGE);
        }
    }
}