package tictoc.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(String kakaoId);
    boolean existsByKakaoId(String kakaoId);
}