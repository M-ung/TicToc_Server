package tictoc.userLoginHistory.event.consumer;

import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class UserLoginHistoryEventConsumer {
    private final UserLoginHistoryRepository userLoginHistoryRepository;
    private static final String LOG_FILE_PATH = "/var/log/tictoc/user_login_history.log";
    private static final String LOG_MESSAGE_FORMAT = "%s - Id: %d, UserId: %d, IPAddress: %s, Device: %s%n";

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
        File logFile = createDirectoryAndFile();
        writeLog(loginHistory, logFile);
    }

    private void writeLog(UserLoginHistory loginHistory, File logFile) {
        synchronized (this) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(String.format(LOG_MESSAGE_FORMAT,
                        loginHistory.getLoginAt(), loginHistory.getId(), loginHistory.getUserId(), loginHistory.getIpAddress(), loginHistory.getDevice()));
            } catch (IOException e) {
                throw new LogFileWriteException(LOG_FILE_WRITE_ERROR);
            }
        }
    }

    private static File createDirectoryAndFile() {
        File logFile = new File(LOG_FILE_PATH);
        File directory = logFile.getParentFile();
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new LogFileWriteException(LOG_DIRECTORY_CREATION_ERROR);
            }
        }
        try {
            if (!logFile.exists()) {
                boolean created = logFile.createNewFile();
                if (!created) {
                    throw new LogFileWriteException(LOG_FILE_CREATION_ERROR);
                }
            }
        } catch (IOException e) {
            throw new LogFileWriteException(LOG_FILE_CREATION_ERROR, e);
        }
        return logFile;
    }
}