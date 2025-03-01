package tictoc.user.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.user.model.UserLoginHistory;
import tictoc.user.port.UserLoginHistoryRepositoryPort;
import tictoc.user.repository.UserLoginHistoryRepository;

@Component
@RequiredArgsConstructor
public class UserLoginHistoryRepositoryAdaptor implements UserLoginHistoryRepositoryPort {
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    public void saveUserLoginHistory(UserLoginHistory userLoginHistory) {
        userLoginHistoryRepository.save(userLoginHistory);
    }
}
