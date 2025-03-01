package tictoc.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tictoc.user.dto.response.UserResDTO;
import tictoc.user.dto.response.UserUseCaseResDTO;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserResMapper {
    UserResMapper INSTANCE = Mappers.getMapper(UserResMapper.class);

    List<UserResDTO.schedules> toSchedules(List<UserUseCaseResDTO.Schedules> responseDTOList);
}
