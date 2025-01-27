package org.tictoc.tictoc.domain.auction.dto.auction.request;

import java.time.LocalDateTime;

public interface AuctionTimesDTO {
    LocalDateTime sellStartTime();
    LocalDateTime sellEndTime();
    LocalDateTime auctionCloseTime();
}