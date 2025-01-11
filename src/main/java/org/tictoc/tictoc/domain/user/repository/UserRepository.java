package org.tictoc.tictoc.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
