package tictoc.config.security.jwt.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.config.security.jwt.dto.JwtResDTO;
import tictoc.error.ErrorCode;
import tictoc.user.model.UserLoginHistory;
import tictoc.user.port.UserLoginHistoryRepositoryPort;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtGenerator jwtGenerator;
    private final HttpServletRequest request;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final UserLoginHistoryRepositoryPort userLoginHistoryRepositoryPort;

    public JwtResDTO.Login createJwt(final Long userId) {
        saveUserLoginHistory(userId);
        return JwtResDTO.Login.of(
                jwtGenerator.generateAccessToken(userId),
                refreshTokenGenerator.generateRefreshToken(userId)
        );
    }

    private void saveUserLoginHistory(Long userId) {
        userLoginHistoryRepositoryPort.saveUserLoginHistory(UserLoginHistory.of(userId, getClientIp(), getUserAgent()));
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
        String ip = request.getHeader("X-Forwarded-For");
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