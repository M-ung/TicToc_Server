package tictoc.profile.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.profile.model.Profile;
import tictoc.profile.model.ProfileImage;
import tictoc.profile.port.ProfileRepositoryPort;
import tictoc.profile.repository.MoneyHistoryRepository;
import tictoc.profile.repository.ProfileImageRepository;
import tictoc.profile.repository.ProfileRepository;

@Component
@RequiredArgsConstructor
public class ProfileRepositoryAdaptor implements ProfileRepositoryPort {
    private final ProfileRepository profileRepository;
    private final ProfileImageRepository profileImageRepository;
    private final MoneyHistoryRepository moneyHistoryRepository;

    @Override
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public ProfileImage saveProfileImage(ProfileImage profileImage) {
        return profileImageRepository.save(profileImage);
    }
}
