package tictoc.bid.model;

import jakarta.persistence.*;
import lombok.*;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.model.type.BidProgress;
import tictoc.model.baseTime.BaseTimeEntity;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bid", uniqueConstraints = @UniqueConstraint(columnNames = {"auctionId", "beforePrice"}))
public class Bid extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auctionId;
    private Long bidderId;
    private Integer beforePrice;
    private Integer bidPrice;
    @Enumerated(EnumType.STRING)
    private BidProgress progress;

    public static Bid of(final Long userId, BidUseCaseReqDTO.Bid requestDTO, Integer currentPrice) {
        return Bid.builder()
                .auctionId(requestDTO.auctionId())
                .bidderId(userId)
                .beforePrice(currentPrice)
                .bidPrice(requestDTO.price())
                .progress(BidProgress.PROGRESS)
                .build();
    }

    public void fail() {
        this.progress = BidProgress.FAILED;
    }

    public void win() {
        this.progress = BidProgress.WIN;
    }
}