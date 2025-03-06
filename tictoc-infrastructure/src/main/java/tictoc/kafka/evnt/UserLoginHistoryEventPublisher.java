package tictoc.kafka.evnt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tictoc.error.ErrorCode;
import tictoc.kafka.exception.KafkaPublishException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserLoginHistoryEventPublisher {
    private final KafkaTemplate<String, UserLoginHistoryEvent> kafkaTemplate;

    public void publish(UserLoginHistoryEvent event) {
        try {
            log.info("UserLoginHistory 토픽을 발행했습니다.: {}", event);
            kafkaTemplate.send("user-login-history-topic", event);
        } catch (Exception e) {
            throw new KafkaPublishException(ErrorCode.KAFKA_PUBLISH_ERROR);
        }
    }
}
