package tictoc.user.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.user.model.UserSchedule;
import tictoc.user.port.UserScheduleRepositoryPort;
import tictoc.user.repository.UserScheduleRepository;

@Component
@RequiredArgsConstructor
public class UserScheduleRepositoryAdaptor implements UserScheduleRepositoryPort {
    private final UserScheduleRepository userScheduleRepository;

    @Override
    public void saveUserSchedule(UserSchedule userSchedule) {
        userScheduleRepository.save(userSchedule);
    }
}