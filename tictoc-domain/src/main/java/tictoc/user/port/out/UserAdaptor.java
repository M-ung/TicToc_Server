package tictoc.user.port.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.user.model.User;
import tictoc.user.repository.UserRepository;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAdaptor {
    private final UserRepository userRepository;

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
