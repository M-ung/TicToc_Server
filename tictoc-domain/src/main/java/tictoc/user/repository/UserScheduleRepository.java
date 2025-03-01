package tictoc.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.user.model.UserSchedule;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {
}