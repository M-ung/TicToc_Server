package tictoc.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tictoc.auction.model.Auction;
import tictoc.bid.model.Bid;
import tictoc.model.baseTime.BaseTimeEntity;
import tictoc.model.tictoc.TicTocStatus;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_schedule")
public class UserSchedule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long winningBidId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    private TicTocStatus status;    //TODO 종료 시 Status를 변경시켜줘야 함.

    public static UserSchedule of(Auction auction, Bid bid) {
        return UserSchedule.builder()
                .userId(bid.getBidderId())
                .winningBidId(1L) //TODO 결제 붙이고 구현할 예정
                .startTime(auction.getSellStartTime())
                .endTime(auction.getSellEndTime())
                .status(TicTocStatus.ACTIVE)
                .build();
    }
}