package tictoc.user.port.in;

import tictoc.database.security.jwt.dto.JwtResDTO;
import tictoc.user.dto.request.UserUseCaseReqDTO;

public interface UserCommandUseCase {
    JwtResDTO.Login login(UserUseCaseReqDTO.Login requestDTO);
}
