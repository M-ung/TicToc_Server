package tictoc.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tictoc.user.dto.request.UserReqDTO;
import tictoc.user.dto.request.UserUseCaseReqDTO;

@Mapper(componentModel = "spring")
public interface UserReqMapper {

    UserReqMapper INSTANCE = Mappers.getMapper(UserReqMapper.class);

    UserUseCaseReqDTO.Login toUseCaseDTO(UserReqDTO.Login requestDTO);
}