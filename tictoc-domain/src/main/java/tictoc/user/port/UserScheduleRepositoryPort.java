package tictoc.user.port;

import tictoc.user.model.UserSchedule;

public interface UserScheduleRepositoryPort {
    void saveUserSchedule(UserSchedule userSchedule);
}
