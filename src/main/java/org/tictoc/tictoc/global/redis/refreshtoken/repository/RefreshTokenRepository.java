package org.tictoc.tictoc.global.redis.refreshtoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.global.redis.refreshtoken.entitiy.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    void deleteByUserId(Long userId);
    Optional<RefreshToken> findRefreshTokenByToken(String token);
    void deleteRefreshTokenByUserId(final Long userId);
}
