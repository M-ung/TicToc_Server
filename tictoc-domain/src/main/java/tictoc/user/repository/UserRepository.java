package tictoc.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
