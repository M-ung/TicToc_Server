package tictoc.config.security.jwt.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.config.security.jwt.dto.JwtResDTO;
import tictoc.error.ErrorCode;
import tictoc.kafka.evnt.UserLoginHistoryEvent;
import tictoc.kafka.evnt.UserLoginHistoryEventPublisher;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtGenerator jwtGenerator;
    private final HttpServletRequest request;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final UserLoginHistoryEventPublisher userLoginHistoryEventPublisher;

    public JwtResDTO.Login createJwt(final Long userId) {
        publishUserLoginHistory(userId);
        return JwtResDTO.Login.of(
                jwtGenerator.generateAccessToken(userId),
                refreshTokenGenerator.generateRefreshToken(userId)
        );
    }

    private void publishUserLoginHistory(Long userId) {
        userLoginHistoryEventPublisher.publish(UserLoginHistoryEvent.of(userId, getClientIp(), getUserAgent()));
    }

    public Long getUserIdFromToken(String token) {
        var subject = jwtGenerator.parseToken(token).getBody().getSubject();
        try {
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.valueOf(ErrorCode.TOKEN_SUBJECT_NOT_NUMERIC_STRING));
        }
    }

    private String getClientIp() {
        var ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private String getUserAgent() {
        return request.getHeader("User-Agent");
    }
}