package tictoc.user.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.user.exception.UserNotFoundException;
import tictoc.user.model.User;
import tictoc.user.port.UserRepositoryPort;
import tictoc.user.repository.UserRepository;

import java.util.Optional;

import static tictoc.error.ErrorCode.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdaptor implements UserRepositoryPort {
    private final UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public User findUserByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public boolean existsUserByKakaoId(String kakaoId) {
        return userRepository.existsByKakaoId(kakaoId);
    }
}
