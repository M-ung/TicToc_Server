package tictoc.user.port;

import tictoc.user.model.User;

public interface UserRepositoryPort {
    User saveUser(User user);
    User findUserById(Long userId);
    User findUserByKakaoId(String kakaoId);
    boolean existsUserByKakaoId(String kakaoId);
}
