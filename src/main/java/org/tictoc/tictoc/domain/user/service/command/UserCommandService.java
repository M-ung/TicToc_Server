package org.tictoc.tictoc.domain.user.service.command;

import org.tictoc.tictoc.domain.user.dto.request.UserRequestDTO;
import org.tictoc.tictoc.global.auth.jwt.dto.JwtResponseDTO;

public interface UserCommandService {
    JwtResponseDTO.Login login(UserRequestDTO.Login requestDTO);
}
