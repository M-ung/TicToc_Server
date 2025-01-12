package org.tictoc.tictoc.global.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.tictoc.tictoc.global.auth.jwt.dto.JwtResponseDTO;
import org.tictoc.tictoc.global.auth.jwt.util.JwtGenerator;
import org.tictoc.tictoc.global.exception.ErrorCode;
import org.tictoc.tictoc.global.redis.refreshtoken.util.RefreshTokenGenerator;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;

    public JwtResponseDTO.Login createJwt(final long userId) {
        return JwtResponseDTO.Login.of(
                jwtGenerator.generateAccessToken(userId),
                refreshTokenGenerator.generateRefreshToken(userId)
        );
    }

    public long getUserIdFromSubject(String token) {
        Jws<Claims> jws = jwtGenerator.parseToken(token);
        String subject = jws.getBody().getSubject();

        try {
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.valueOf(ErrorCode.TOKEN_SUBJECT_NOT_NUMERIC_STRING));
        }
    }
}
