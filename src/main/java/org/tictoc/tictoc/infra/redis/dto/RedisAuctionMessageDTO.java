package org.tictoc.tictoc.infra.redis.dto;

import org.tictoc.tictoc.domain.auction.entity.auction.Auction;

public class RedisAuctionMessageDTO {
    public record auctionClose (
            Long auctionId,
            long delayMillis,
            Auction auction
    ) {
        public static auctionClose of(Long auctionId, long delayMillis, Auction auction) {
            return new auctionClose(auctionId, delayMillis, auction);
        }
    }
}