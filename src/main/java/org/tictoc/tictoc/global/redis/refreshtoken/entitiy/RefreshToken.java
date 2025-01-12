package org.tictoc.tictoc.global.redis.refreshtoken.entitiy;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "refresh_token", timeToLive = 3600 * 24 * 7)
public class RefreshToken {
    @Id
    @NotNull
    @Column(name = "refresh_token_id", nullable = false)
    private String token;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    public static RefreshToken of(final String token, final Long userId, LocalDateTime expiredAt) {
        return RefreshToken.builder()
                .token(token)
                .userId(userId)
                .expiredAt(expiredAt)
                .build();
    }
}
