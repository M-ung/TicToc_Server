package tictoc.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.config.security.jwt.model.Token;
import tictoc.kakao.KakaoFeignProvider;
import tictoc.error.ErrorCode;
import tictoc.config.security.jwt.dto.JwtResDTO;
import tictoc.config.security.jwt.util.JwtProvider;
import tictoc.kakao.dto.KakaoResDTO;
import tictoc.profile.adaptor.ProfileRepositoryAdaptor;
import tictoc.profile.model.Profile;
import tictoc.profile.model.ProfileImage;
import tictoc.profile.port.ProfileRepositoryPort;
import tictoc.user.dto.request.UserUseCaseReqDTO;
import tictoc.user.model.User;
import tictoc.user.port.UserCommandUseCase;
import tictoc.user.adaptor.UserRepositoryAdaptor;
import tictoc.user.exception.UserNotFoundException;
import tictoc.user.port.UserRepositoryPort;
import tictoc.user.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService implements UserCommandUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final ProfileRepositoryPort profileRepositoryPort;
    private final JwtProvider jwtProvider;
    private final KakaoFeignProvider kakaoFeignProvider;

    @Override
    public JwtResDTO.Login login(UserUseCaseReqDTO.Login requestDTO) {
        final var userId = requestDTO.userId();
        var findUser = userRepositoryPort.findUserById(userId);
        return jwtProvider.createJwt(findUser.getId());

    }

    @Override
    public JwtResDTO.Login login(String authenticationCode) {
        final var kakaoId = kakaoFeignProvider.login(authenticationCode);
        if(userRepositoryPort.existsUserByKakaoId(kakaoId)) {
            return createJwt(userRepositoryPort.findUserByKakaoId(kakaoId).getId());
        } else {
            return createUser(kakaoId, authenticationCode);
        }
    }

    private JwtResDTO.Login createUser(String kakaoId, String authenticationCode) {
        KakaoResDTO.KakaoProfile profile = kakaoFeignProvider.getKakaoProfile(authenticationCode);
        KakaoResDTO.KakaoAccount account = profile.kakaoAccount();
        KakaoResDTO.KakaoProfileInfo profileInfo = account.profile();

        User user = saveUser(kakaoId, account.name());
        Profile profileEntity = saveProfile(user.getId(), profileInfo.nickname());
        saveProfileImage(profileEntity.getId(), profileInfo.profileImageUrl());

        return createJwt(user.getId());
    }

    private JwtResDTO.Login createJwt(final Long userId) {
        return jwtProvider.createJwt(userId);
    }

    private User saveUser(String kakaoId, String name) {
        return userRepositoryPort.saveUser(User.of(kakaoId, name));
    }

    private Profile saveProfile(Long userId, String nickname) {
        return profileRepositoryPort.saveProfile(Profile.of(userId, nickname));
    }

    private void saveProfileImage(Long profileId, String profileImageUrl) {
        profileRepositoryPort.saveProfileImage(ProfileImage.of(profileId, profileImageUrl));
    }
}
