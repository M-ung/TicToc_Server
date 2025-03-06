package tictoc.userLoginHistory.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tictoc.error.exception.LogFileWriteException;
import tictoc.kafka.evnt.UserLoginHistoryEvent;
import tictoc.kafka.exception.KafkaConsumeException;
import tictoc.user.model.UserLoginHistory;
import tictoc.user.repository.UserLoginHistoryRepository;
import java.io.FileWriter;
import java.io.IOException;
import static tictoc.error.ErrorCode.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserLoginHistoryEventConsumer {
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    @KafkaListener(
            topics = "user-login-history-topic",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(UserLoginHistoryEvent event) {
        try {
            UserLoginHistory userLoginHistory = saveUserLoginHistory(UserLoginHistory.of(
                    event.userId(), event.loginAt(), event.ipAddress(), event.device()));
            saveLog(userLoginHistory);
        } catch (Exception e) {
            throw new KafkaConsumeException(KAFKA_CONSUME_ERROR);
        }
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