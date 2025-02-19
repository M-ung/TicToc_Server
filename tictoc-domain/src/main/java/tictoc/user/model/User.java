package tictoc.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.model.Auction;
import tictoc.model.baseTime.BaseTimeEntity;
import tictoc.model.tictoc.TicTocStatus;
import tictoc.user.model.type.UserRole;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kakaoId;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private TicTocStatus status;

    public static User of(String kakaoId, String name) {
        return User.builder()
                .kakaoId(kakaoId)
                .name(name)
                .role(UserRole.USER)
                .status(TicTocStatus.ACTIVE)
                .build();
    }
}