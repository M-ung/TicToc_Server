package tictoc.userLoginHistory.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tictoc.error.exception.LogFileWriteException;
import tictoc.user.event.UserLoginHistoryEvent;
import tictoc.user.model.UserLoginHistory;
import tictoc.user.repository.UserLoginHistoryRepository;
import java.io.FileWriter;
import java.io.IOException;
import static tictoc.error.ErrorCode.LOG_FILE_WRITE_BAD_REQUEST;

@Component
@RequiredArgsConstructor
public class UserLoginHistoryEventConsumer {
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    @KafkaListener(topics = "user-login-history-topic", groupId = "batch-group")
    public void consume(UserLoginHistoryEvent event) {
        UserLoginHistory userLoginHistory = saveUserLoginHistory(UserLoginHistory.of(
                event.userId(), event.loginAt(), event.ipAddress(), event.device()));
        saveLog(userLoginHistory);
    }

    public UserLoginHistory saveUserLoginHistory(UserLoginHistory loginHistory) {
        userLoginHistoryRepository.save(loginHistory);
        saveLog(loginHistory);
        return loginHistory;
    }

    private void saveLog(UserLoginHistory loginHistory) {
        try (FileWriter writer = new FileWriter("/log/user_login_history.log", true)) {
            writer.write(String.format("%s - UserId: %d, IPAddress: %s, Device: %s\n",
                    loginHistory.getLoginAt(), loginHistory.getUserId(),
                    loginHistory.getIpAddress(), loginHistory.getDevice()));
        } catch (IOException e) {
            throw new LogFileWriteException(LOG_FILE_WRITE_BAD_REQUEST);
        }
    }
}