package tictoc.user.port;

import tictoc.user.model.UserLoginHistory;
import java.time.LocalDateTime;

public interface UserLoginHistoryRepositoryPort {
    UserLoginHistory save(Long userId, LocalDateTime loginAt, String userIp, String userAgent);
}