package tictoc.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.error.ErrorCode;
import tictoc.jwt.dto.JwtResDTO;
import tictoc.jwt.util.JwtProvider;
import tictoc.user.dto.request.UserUseCaseReqDTO;
import tictoc.user.port.in.UserCommandUseCase;
import tictoc.user.port.out.UserAdaptor;
import tictoc.user.exception.UserNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService implements UserCommandUseCase {
    private final UserAdaptor userAdaptor;
    private final JwtProvider jwtProvider;

    @Override
    public JwtResDTO.Login login(UserUseCaseReqDTO.Login requestDTO) {
        final var userId = requestDTO.userId();
        var findUser = userAdaptor.findUserById(userId);
        if(findUser.isEmpty()) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return jwtProvider.createJwt(userId);

    }
}
