package org.tictoc.tictoc.infra.kafka.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record AuctionCloseMessageDTO (
        Long auctionId,
        long delayMillis
){
    public static AuctionCloseMessageDTO of(Long auctionId, LocalDateTime auctionCloseTime) {
        long delayMillis = ChronoUnit.MILLIS.between(LocalDateTime.now(), auctionCloseTime);
        return new AuctionCloseMessageDTO(auctionId, delayMillis);
    }
}