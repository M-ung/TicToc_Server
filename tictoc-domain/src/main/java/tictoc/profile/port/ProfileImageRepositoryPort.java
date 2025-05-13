package tictoc.profile.port;

import tictoc.profile.model.ProfileImage;

public interface ProfileImageRepositoryPort {
    ProfileImage save(ProfileImage profileImage);
}
