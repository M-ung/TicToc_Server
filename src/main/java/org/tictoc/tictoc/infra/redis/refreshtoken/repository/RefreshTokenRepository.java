package org.tictoc.tictoc.infra.redis.refreshtoken.repository;

import org.springframework.data.repository.CrudRepository;
import org.tictoc.tictoc.infra.redis.refreshtoken.entitiy.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    void deleteByUserId(final Long userId);
    RefreshToken findByToken(String token);
}
