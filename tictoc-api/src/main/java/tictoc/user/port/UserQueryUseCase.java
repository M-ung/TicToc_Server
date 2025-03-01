package tictoc.user.port;

import tictoc.user.dto.response.UserUseCaseResDTO;
import java.util.List;

public interface UserQueryUseCase {
    List<UserUseCaseResDTO.Schedules> getSchedules(final Long userId);
}
