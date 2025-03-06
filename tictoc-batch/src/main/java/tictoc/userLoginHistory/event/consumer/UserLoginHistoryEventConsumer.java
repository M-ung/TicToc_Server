package tictoc.userLoginHistory.event.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tictoc.error.exception.LogFileWriteException;
import tictoc.kafka.evnt.UserLoginHistoryEvent;
import tictoc.kafka.exception.KafkaConsumeException;
import tictoc.user.model.UserLoginHistory;
import tictoc.user.repository.UserLoginHistoryRepository;

import java.io.BufferedWriter;
import java.io.File;
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
    @Transactional
    public void consume(UserLoginHistoryEvent event) {
        try {
            saveUserLoginHistory(UserLoginHistory.of(
                    event.userId(), event.loginAt(), event.ipAddress(), event.device()));
        } catch (Exception e) {
            throw new KafkaConsumeException(KAFKA_CONSUME_ERROR);
        }
    }

    public void saveUserLoginHistory(UserLoginHistory loginHistory) {
        userLoginHistoryRepository.save(loginHistory);
        saveLog(loginHistory);
    }

    private void saveLog(UserLoginHistory loginHistory) {
        String userHome = System.getProperty("user.home");
        String logFilePath = userHome + "/tictoc_logs/user_login_history.log";
        File logFile = new File(logFilePath);
        try {
            logFile.getParentFile().mkdirs();
            synchronized (this) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                    writer.write(String.format("%s - Id: %d, UserId: %d, IPAddress: %s, Device: %s\n",
                            loginHistory.getLoginAt(), loginHistory.getId(), loginHistory.getUserId(), loginHistory.getIpAddress(), loginHistory.getDevice()));
                }
            }
        } catch (IOException e) {
            throw new LogFileWriteException(LOG_FILE_WRITE_ERROR);
        }
    }
}