package tictoc.kafka.evnt;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record UserLoginHistoryEvent (
        Long userId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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