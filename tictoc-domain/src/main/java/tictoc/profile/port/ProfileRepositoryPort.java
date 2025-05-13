package tictoc.profile.port;

import tictoc.profile.model.Profile;
import tictoc.profile.model.ProfileImage;

public interface ProfileRepositoryPort {
    Profile save(Profile profile);
    boolean checkMoney(Long userId, Integer price);
    void subtractMoney(Long userId, Integer price);
    void addMoney(Long auctioneerId, Integer bidPrice);
}
