package tictoc.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLoginHistoryEventPublisher {
    private final KafkaTemplate<String, UserLoginHistoryEvent> kafkaTemplate;

    public void publish(UserLoginHistoryEvent event) {
        kafkaTemplate.send("user-login-history-topic", event);
    }
}
