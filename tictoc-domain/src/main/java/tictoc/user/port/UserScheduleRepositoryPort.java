package tictoc.user.port;

import tictoc.user.dto.response.UserUseCaseResDTO;
import tictoc.user.model.UserSchedule;
import java.util.List;

public interface UserScheduleRepositoryPort {
    void saveUserSchedule(UserSchedule userSchedule);
    List<UserUseCaseResDTO.Schedules> findSchedulesByUserId(Long userId);
}
