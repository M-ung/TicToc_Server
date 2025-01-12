package org.tictoc.tictoc.domain.user.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.user.dto.request.UserRequestDTO;
import org.tictoc.tictoc.domain.user.entity.User;
import org.tictoc.tictoc.domain.user.repository.UserRepository;
import org.tictoc.tictoc.global.auth.jwt.JwtProvider;
import org.tictoc.tictoc.global.auth.jwt.dto.JwtResponseDTO;
import org.tictoc.tictoc.global.exception.ErrorCode;
import org.tictoc.tictoc.global.exception.user.UserNotFoundException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public JwtResponseDTO.Login login(UserRequestDTO.Login requestDTO) {
        final Long userId = requestDTO.userId();
        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return jwtProvider.createJwt(userId);

    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
