package tictoc.user.port;

import tictoc.config.security.jwt.dto.JwtResDTO;

public interface UserCommandUseCase {
    JwtResDTO.Login login(String authenticationCode, String clientIp, String userAgent);
}
