package org.tictoc.tictoc.global.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.tictoc.tictoc.global.auth.jwt.dto.JwtResponseDTO;
import org.tictoc.tictoc.global.auth.jwt.util.JwtGenerator;
import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.infra.redis.refreshtoken.util.RefreshTokenGenerator;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;

    public JwtResponseDTO.Login createJwt(final Long userId) {
        return JwtResponseDTO.Login.of(
                jwtGenerator.generateAccessToken(userId),
                refreshTokenGenerator.generateRefreshToken(userId)
        );
    }

    public Long getUserIdFromToken(String token) {
        var subject = jwtGenerator.parseToken(token).getBody().getSubject();
        try {
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.valueOf(ErrorCode.TOKEN_SUBJECT_NOT_NUMERIC_STRING));
        }
    }
}