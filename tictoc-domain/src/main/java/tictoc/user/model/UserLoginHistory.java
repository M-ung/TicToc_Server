package tictoc.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_login_history")
public class UserLoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Column(columnDefinition = "DATETIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginAt;
    private String ipAddress;
    private String device;

    public static UserLoginHistory of(Long userId, LocalDateTime loginAt, String ipAddress, String device) {
        return UserLoginHistory.builder()
                .userId(userId)
                .loginAt(loginAt)
                .ipAddress(ipAddress)
                .device(device)
                .build();
    }
}