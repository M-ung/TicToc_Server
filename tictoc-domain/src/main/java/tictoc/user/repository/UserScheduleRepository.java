package tictoc.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.user.model.UserSchedule;
import java.util.List;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {
    List<UserSchedule> findByUserId(Long userId);
}