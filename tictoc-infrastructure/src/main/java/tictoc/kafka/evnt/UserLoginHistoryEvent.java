package tictoc.kafka.evnt;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record UserLoginHistoryEvent (
        Long userId,
        LocalDateTime loginAt,
        String ipAddress,
        String device
) {
    public static UserLoginHistoryEvent of(Long userId, String ipAddress, String device) {
        return UserLoginHistoryEvent.builder()
                .userId(userId)
                .loginAt(LocalDateTime.now())
                .ipAddress(ipAddress)
                .device(device)
                .build();
    }
}
