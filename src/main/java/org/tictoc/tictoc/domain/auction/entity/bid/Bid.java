package org.tictoc.tictoc.domain.auction.entity.bid;

import jakarta.persistence.*;
import lombok.*;
import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.type.BidStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auctionId;
    private Long bidderId;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private BidStatus status;
    @Version
    private Integer version;

    public static Bid of(final Long userId, BidRequestDTO.Bid requestDTO) {
        return Bid.builder()
                .auctionId(requestDTO.auctionId())
                .bidderId(userId)
                .price(requestDTO.price())
                .status(BidStatus.PROGRESS)
                .build();
    }

    public void fail() {
        this.status = BidStatus.FAILED;
    }
}