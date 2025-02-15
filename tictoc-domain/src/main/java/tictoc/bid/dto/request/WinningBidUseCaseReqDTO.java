package tictoc.bid.dto.request;

import java.time.LocalDateTime;

public class WinningBidUseCaseReqDTO {
    public record Filter (
            Integer price,
            LocalDateTime winningBidDate,
            LocalDateTime sellStartTime,
            LocalDateTime sellEndTime
    ) {}
}