package tictoc.user.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tictoc.user.dto.response.UserResDTO;
import tictoc.user.dto.response.UserUseCaseResDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-02T14:26:42+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Homebrew)"
)
@Component
public class UserResMapperImpl implements UserResMapper {

    @Override
    public List<UserResDTO.schedules> toSchedules(List<UserUseCaseResDTO.Schedules> responseDTOList) {
        if ( responseDTOList == null ) {
            return null;
        }

        List<UserResDTO.schedules> list = new ArrayList<UserResDTO.schedules>( responseDTOList.size() );
        for ( UserUseCaseResDTO.Schedules schedules : responseDTOList ) {
            list.add( schedulesToschedules( schedules ) );
        }

        return list;
    }

    protected UserResDTO.schedules schedulesToschedules(UserUseCaseResDTO.Schedules schedules) {
        if ( schedules == null ) {
            return null;
        }

        UserResDTO.schedules.schedulesBuilder schedules1 = UserResDTO.schedules.builder();

        schedules1.scheduleId( schedules.getScheduleId() );
        schedules1.startTime( schedules.getStartTime() );
        schedules1.endTime( schedules.getEndTime() );

        return schedules1.build();
    }
}
