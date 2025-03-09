package tictoc.kafka.evnt.producer;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import tictoc.annotation.Event;
import tictoc.error.ErrorCode;
import tictoc.kafka.evnt.UserLoginHistoryEvent;
import tictoc.kafka.evnt.constants.UserLoginHistoryConstants;
import tictoc.kafka.exception.KafkaPublishException;

@Slf4j
@Event("Producer")
@RequiredArgsConstructor
public class UserLoginHistoryEventProducer {
    private final HttpServletRequest request;
    private final KafkaTemplate<String, UserLoginHistoryEvent> kafkaTemplate;

    @Async
    public void produce(Long userId) {
        try {
            UserLoginHistoryEvent event = UserLoginHistoryEvent.of(userId, getClientIp(), getUserAgent());
            log.info("[INFO] 토픽을 발행했습니다.: {}", event);
            kafkaTemplate.send("user-login-history-topic", event);
        } catch (Exception e) {
            throw new KafkaPublishException(ErrorCode.KAFKA_PUBLISH_ERROR);
        }
    }

    private String getClientIp() {
        var ip = request.getHeader(UserLoginHistoryConstants.X_FORWARDED_FOR);
        if (ip == null || ip.isEmpty() ||UserLoginHistoryConstants.UNKNOWN.equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        return ip;
    }

    private String getUserAgent() {
        return request.getHeader(UserLoginHistoryConstants.USER_AGENT);
    }
}