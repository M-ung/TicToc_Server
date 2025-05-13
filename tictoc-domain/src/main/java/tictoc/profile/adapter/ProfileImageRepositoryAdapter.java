package tictoc.profile.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.profile.model.ProfileImage;
import tictoc.profile.port.ProfileImageRepositoryPort;
import tictoc.profile.repository.ProfileImageRepository;

@Component
@RequiredArgsConstructor
public class ProfileImageRepositoryAdapter implements ProfileImageRepositoryPort {
    private final ProfileImageRepository profileImageRepository;

    @Override
    public ProfileImage save(ProfileImage profileImage) {
        return profileImageRepository.save(profileImage);
    }
}