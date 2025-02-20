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
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auctionId;
    private Long bidderId;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private BidStatus status;

    public static Bid of(final Long userId, BidUseCaseReqDTO.Bid requestDTO) {
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

    public void win() {
        this.status = BidStatus.WIN;
    }
}