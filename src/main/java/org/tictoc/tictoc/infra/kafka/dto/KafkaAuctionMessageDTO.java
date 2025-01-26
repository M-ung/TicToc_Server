package org.tictoc.tictoc.infra.kafka.dto;

public class KafkaAuctionMessageDTO {
    public record auctionClose (
            Long auctionId,
            long delayMillis ) {
        public static auctionClose of(Long auctionId, long delayMillis) {
            return new auctionClose(auctionId, delayMillis);
        }
    }
}