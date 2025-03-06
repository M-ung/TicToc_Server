package tictoc.kafka.evnt.publisher;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import tictoc.kafka.evnt.UserLoginHistoryEvent;

@Component
@RequiredArgsConstructor
public class LoginHistoryPublisher {
    private final HttpServletRequest request;
    private final UserLoginHistoryEventPublisher userLoginHistoryEventPublisher;

    @Async
    public void publish(Long userId) {
        userLoginHistoryEventPublisher.publish(
                UserLoginHistoryEvent.of(userId, getClientIp(), getUserAgent())
        );
    }

    private String getClientIp() {
        var ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        return ip;
    }

    private String getUserAgent() {
        return request.getHeader("User-Agent");
    }
}