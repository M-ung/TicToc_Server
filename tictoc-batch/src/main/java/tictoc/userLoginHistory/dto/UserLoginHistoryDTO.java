package tictoc.userLoginHistory.dto;

import java.time.LocalDateTime;

public record UserLoginHistoryDTO(
        Long id,
        Long userId,
        LocalDateTime loginAt,
        String ipAddress,
        String device
) {}