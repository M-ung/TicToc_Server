package tictoc.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_login_history")
public class UserLoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @LastModifiedDate
    private LocalDateTime loginAt;

    public static UserLoginHistory of(Long userId) {
        return UserLoginHistory.builder()
                .userId(userId)
                .loginAt(LocalDateTime.now())
                .build();
    }
}