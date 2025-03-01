package tictoc.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class UserUseCaseResDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Schedules {
        private Long scheduleId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
}
