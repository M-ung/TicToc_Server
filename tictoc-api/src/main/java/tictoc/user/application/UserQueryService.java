package tictoc.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.user.dto.response.UserUseCaseResDTO;
import tictoc.user.port.UserQueryUseCase;
import tictoc.user.port.UserRepositoryPort;
import tictoc.user.port.UserScheduleRepositoryPort;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService implements UserQueryUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final UserScheduleRepositoryPort userScheduleRepositoryPort;

    @Override
    public List<UserUseCaseResDTO.Schedules> getSchedules(final Long userId) {
        return userScheduleRepositoryPort.findSchedulesByUserId(userId);
    }
}
