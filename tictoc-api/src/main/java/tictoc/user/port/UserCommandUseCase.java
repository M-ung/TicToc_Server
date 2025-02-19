package tictoc.user.port;

import tictoc.config.security.jwt.dto.JwtResDTO;
import tictoc.user.dto.request.UserUseCaseReqDTO;

public interface UserCommandUseCase {
    JwtResDTO.Login login(UserUseCaseReqDTO.Login requestDTO);
    JwtResDTO.Login login(String authenticationCode);
}
