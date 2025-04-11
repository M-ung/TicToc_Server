package tictoc.user.adapter;

import lombok.RequiredArgsConstructor;
import tictoc.annotation.Adapter;
import tictoc.user.model.UserLoginHistory;
import tictoc.user.port.UserLoginHistoryRepositoryPort;
import tictoc.user.repository.UserLoginHistoryRepository;
import java.time.LocalDateTime;

@Adapter
@RequiredArgsConstructor
public class UserLoginHistoryRepositoryAdapter implements UserLoginHistoryRepositoryPort {
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    @Override
    public UserLoginHistory save(Long userId, LocalDateTime loginAt, String userIp, String userAgent) {
        return userLoginHistoryRepository.save(
                UserLoginHistory.of(userId, loginAt, userIp, userAgent)
        );
    }
}
