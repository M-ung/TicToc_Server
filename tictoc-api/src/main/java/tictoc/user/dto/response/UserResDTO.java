package tictoc.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class UserResDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class schedules {
        private Long scheduleId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
}
