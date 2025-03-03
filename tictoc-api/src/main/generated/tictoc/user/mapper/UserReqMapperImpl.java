package tictoc.user.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tictoc.user.dto.request.UserReqDTO;
import tictoc.user.dto.request.UserUseCaseReqDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T00:55:06+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Homebrew)"
)
@Component
public class UserReqMapperImpl implements UserReqMapper {

    @Override
    public UserUseCaseReqDTO.Login toUseCaseDTO(UserReqDTO.Login requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Long userId = null;

        userId = requestDTO.userId();

        UserUseCaseReqDTO.Login login = new UserUseCaseReqDTO.Login( userId );

        return login;
    }
}
