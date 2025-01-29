package org.tictoc.tictoc.domain.auction.entity.bid;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.global.common.entity.base.BaseTimeEntity;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WinningBid extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auctioneerId;
    private Long winningBidderId;
    private Long auctionId;
    private Integer price;

    public static WinningBid of(Auction auction, Bid bid) {
        return WinningBid.builder()
                .auctioneerId(auction.getAuctioneerId())
                .winningBidderId(bid.getBidderId())
                .auctionId(bid.getAuctionId())
                .build();
    }
}