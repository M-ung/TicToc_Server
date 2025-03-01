package tictoc.user.port;

import tictoc.user.model.UserLoginHistory;

public interface UserLoginHistoryRepositoryPort {
    void saveUserLoginHistory(UserLoginHistory loginHistory);
}