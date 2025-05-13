package tictoc.user.adapter;

import lombok.RequiredArgsConstructor;
import tictoc.annotation.Adapter;
import tictoc.user.dto.response.UserUseCaseResDTO;
import tictoc.user.model.UserSchedule;
import tictoc.user.port.UserScheduleRepositoryPort;
import tictoc.user.repository.UserScheduleRepository;
import java.util.List;
import java.util.stream.Collectors;

@Adapter
@RequiredArgsConstructor
public class UserScheduleRepositoryAdapter implements UserScheduleRepositoryPort {
    private final UserScheduleRepository userScheduleRepository;

    @Override
    public void save(UserSchedule userSchedule) {
        userScheduleRepository.save(userSchedule);
    }

    @Override
    public List<UserUseCaseResDTO.Schedules> findSchedulesByUserId(Long userId) {
        return userScheduleRepository.findByUserId(userId).stream()
                .map(schedule -> new UserUseCaseResDTO.Schedules(
                        schedule.getId(),
                        schedule.getStartTime(),
                        schedule.getEndTime()
                ))
                .collect(Collectors.toList());
    }
}