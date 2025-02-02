package tictoc.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import tictoc.jwt.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    void deleteByUserId(final Long userId);
    RefreshToken findByToken(String token);
}
