package tictoc.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.user.model.UserLoginHistory;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long> {
}