package tictoc.userLoginHistory.event.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tictoc.annotation.Event;
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
@Event("Consumer")
@RequiredArgsConstructor
public class UserLoginHistoryEventConsumer {
    private final UserLoginHistoryRepository userLoginHistoryRepository;
    private final KafkaTemplate<String, UserLoginHistoryEvent> kafkaTemplate;
    private static final String LOG_FILE_PATH = "/var/log/tictoc/user_login_history.log";
    private static final String LOG_MESSAGE_FORMAT = "%s - Id: %d, UserId: %d, IPAddress: %s, Device: %s%n";

    @KafkaListener(
            topics = "user-login-history-topic",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(UserLoginHistoryEvent event) {
        try {
            log.info("[INFO] 토픽을 소비했습니다.: {}", event);
            saveUserLoginHistory(UserLoginHistory.of(event.userId(), event.loginAt(), event.ipAddress(), event.device()));
        } catch (Exception e) {
            kafkaTemplate.send("user-login-history-topic.DLT", event);
            throw new KafkaConsumeException(KAFKA_CONSUME_ERROR, e);
        }
    }

    public void saveUserLoginHistory(UserLoginHistory loginHistory) {
        userLoginHistoryRepository.save(loginHistory);
        writeLogToFile(loginHistory);
    }

    private void writeLogToFile(UserLoginHistory loginHistory) {
        File logFile = ensureLogFileExists();
        synchronized (this) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(String.format(LOG_MESSAGE_FORMAT,
                        loginHistory.getLoginAt(), loginHistory.getId(), loginHistory.getUserId(), loginHistory.getIpAddress(), loginHistory.getDevice()));
            } catch (IOException e) {
                throw new LogFileWriteException(LOG_FILE_WRITE_ERROR, e);
            }
        }
    }

    private static File ensureLogFileExists() {
        File logFile = new File(LOG_FILE_PATH);
        File directory = logFile.getParentFile();
        if (!directory.exists() && directory.mkdirs()) {
            setFilePermissions(directory, "755");
        }
        if (!logFile.exists()) {
            try {
                if (logFile.createNewFile()) {
                    setFilePermissions(logFile, "666");
                }
            } catch (IOException e) {
                throw new LogFileWriteException(LOG_FILE_CREATION_ERROR, e);
            }
        }
        return logFile;
    }

    private static void setFilePermissions(File file, String permission) {
        try {
            Process process = new ProcessBuilder("chmod", permission, file.getAbsolutePath()).start();
            if (process.waitFor() == 0) {
                log.info("[INFO] chmod {} 권한 설정 완료: {}", permission, file.getAbsolutePath());
            } else {
                log.error("[ERROR] chmod {} 실패: {}", permission, file.getAbsolutePath());
            }
        } catch (Exception e) {
            log.error("[ERROR] chmod {} 실행 중 예외 발생: {}", permission, file.getAbsolutePath(), e);
        }
    }
}