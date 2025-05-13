package tictoc.user.port;

import tictoc.user.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findUserByKakaoId(String kakaoId);
}
