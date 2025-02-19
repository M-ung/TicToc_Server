package tictoc.profile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tictoc.model.baseTime.BaseTimeEntity;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profile")
public class Profile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String nickname;
    private Integer money;

    public static Profile of(Long userId, String nickname) {
        return Profile.builder()
                .userId(userId)
                .nickname(nickname)
                .money(0)
                .build();
    }
}