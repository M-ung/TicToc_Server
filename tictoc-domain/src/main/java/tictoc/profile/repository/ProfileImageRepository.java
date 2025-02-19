package tictoc.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.profile.model.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
