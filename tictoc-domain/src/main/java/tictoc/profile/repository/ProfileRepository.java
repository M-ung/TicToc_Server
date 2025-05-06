package tictoc.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.profile.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsByUserIdAndMoneyGreaterThanEqual(Long userId, Integer price);
}
