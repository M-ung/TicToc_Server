package org.tictoc.tictoc.infra.kafka.dto;

public class KafkaAuctionMessageDTO {
    public record AuctionClose(
            Long auctionId,
            long delayMillis ) {
        public static AuctionClose of(Long auctionId, long delayMillis) {
            return new AuctionClose(auctionId, delayMillis);
        }
    }
}