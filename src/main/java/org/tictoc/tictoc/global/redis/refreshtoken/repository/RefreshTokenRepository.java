package org.tictoc.tictoc.global.redis.refreshtoken.repository;

import org.springframework.data.repository.CrudRepository;
import org.tictoc.tictoc.global.redis.refreshtoken.entitiy.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    void deleteByUserId(final Long userId);
    RefreshToken findByToken(String token);
}
