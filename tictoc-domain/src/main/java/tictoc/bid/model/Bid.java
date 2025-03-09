package tictoc.bid.model;

import jakarta.persistence.*;
import lombok.*;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.model.type.BidStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bid", uniqueConstraints = @UniqueConstraint(columnNames = {"auctionId", "beforePrice"}))
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auctionId;
    private Long bidderId;
    private Integer beforePrice;
    private Integer bidPrice;
    @Enumerated(EnumType.STRING)
    private BidStatus status;

    public static Bid of(final Long userId, BidUseCaseReqDTO.Bid requestDTO, Integer currentPrice) {
        return Bid.builder()
                .auctionId(requestDTO.auctionId())
                .bidderId(userId)
                .beforePrice(currentPrice)
                .bidPrice(requestDTO.price())
                .status(BidStatus.PROGRESS)
                .build();
    }

    public void fail() {
        this.status = BidStatus.FAILED;
    }

    public void win() {
        this.status = BidStatus.WIN;
    }
}