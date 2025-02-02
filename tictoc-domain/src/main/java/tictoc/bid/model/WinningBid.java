package tictoc.bid.model;

import jakarta.persistence.*;
import lombok.*;
import tictoc.auction.model.Auction;
import tictoc.model.baseTime.BaseTimeEntity;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "winning_bid")
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