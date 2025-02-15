package tictoc.bid.dto.request;

import jakarta.annotation.Nullable;
import java.time.LocalDateTime;

public class WinningBidReqDTO {
    public record Filter (
            @Nullable Integer price,
            @Nullable LocalDateTime winningBidDate,
            @Nullable LocalDateTime sellStartTime,
            @Nullable LocalDateTime sellEndTime
    ) {}
}