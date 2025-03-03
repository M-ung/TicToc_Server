package tictoc.user.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.error.exception.LogFileWriteException;
import tictoc.user.model.UserLoginHistory;
import tictoc.user.port.UserLoginHistoryRepositoryPort;
import tictoc.user.repository.UserLoginHistoryRepository;
import java.io.FileWriter;
import java.io.IOException;
import static tictoc.error.ErrorCode.LOG_FILE_WRITE_BAD_REQUEST;

@Component
@RequiredArgsConstructor
public class UserLoginHistoryRepositoryAdaptor implements UserLoginHistoryRepositoryPort {
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    public void saveUserLoginHistory(UserLoginHistory loginHistory) {
        userLoginHistoryRepository.save(loginHistory);
        saveLog(loginHistory);
    }

    private void saveLog(UserLoginHistory loginHistory) {
        try (FileWriter writer = new FileWriter("/var/log/user_login_history.log", true)) {
            writer.write(String.format("%s - UserId: %d, IPAddress: %s, Device: %s\n",
                    loginHistory.getLoginAt(), loginHistory.getUserId(),
                    loginHistory.getIpAddress(), loginHistory.getDevice()));
        } catch (IOException e) {
            throw new LogFileWriteException(LOG_FILE_WRITE_BAD_REQUEST);
        }
    }
}