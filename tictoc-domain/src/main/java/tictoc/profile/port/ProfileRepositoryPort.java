package tictoc.profile.port;

import tictoc.profile.model.Profile;
import tictoc.profile.model.ProfileImage;

public interface ProfileRepositoryPort {
    Profile saveProfile(Profile profile);
    ProfileImage saveProfileImage(ProfileImage profileImage);
}
