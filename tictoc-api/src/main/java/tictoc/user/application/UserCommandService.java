package tictoc.user.application;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.error.ErrorCode;
import tictoc.error.exception.BadRequestException;
import tictoc.kakao.KakaoFeignProvider;
import tictoc.config.security.jwt.dto.JwtResDTO;
import tictoc.config.security.jwt.util.JwtProvider;
import tictoc.kakao.dto.KakaoResDTO;
import tictoc.profile.model.Profile;
import tictoc.profile.model.ProfileImage;
import tictoc.profile.port.ProfileImageRepositoryPort;
import tictoc.profile.port.ProfileRepositoryPort;
import tictoc.user.model.User;
import tictoc.user.port.UserCommandUseCase;
import tictoc.user.port.UserLoginHistoryRepositoryPort;
import tictoc.user.port.UserRepositoryPort;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService implements UserCommandUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final ProfileRepositoryPort profileRepositoryPort;
    private final UserLoginHistoryRepositoryPort userLoginHistoryRepositoryPort;
    private final JwtProvider jwtProvider;
    private final KakaoFeignProvider kakaoFeignProvider;
    private final ProfileImageRepositoryPort profileImageRepositoryPort;

    @Override
    public JwtResDTO.Login login(String authenticationCode, String userIp, String userAgent) {
        try {
            final var accessToken = kakaoFeignProvider.getKakaoAccessToken(authenticationCode);
            final var kakaoId = kakaoFeignProvider.getSocialId(accessToken);
            final Long userId = userRepositoryPort.findUserByKakaoId(kakaoId)
                    .map(User::getId)
                    .orElseGet(() -> createUser(kakaoId, accessToken));
            userLoginHistoryRepositoryPort.save(userId, LocalDateTime.now(), userIp, userAgent);
            return createJwt(userId);
        } catch (FeignException e) {
            throw new BadRequestException(ErrorCode.KAKAO_BAD_REQUEST);
        }
    }

    //TODO 카카오 소셜 로그아웃 기능 구현해야 함.

    private Long createUser(String kakaoId, String accessToken) {
        KakaoResDTO.KakaoUserInfo kakaoUserInfo = kakaoFeignProvider.getKakaoProfile(accessToken);

        User user = saveUser(kakaoId, kakaoUserInfo.kakaoAccount().profile().nickname());
        Profile profile = saveProfile(user.getId(), kakaoUserInfo.kakaoAccount().profile().nickname());
        saveProfileImage(profile.getId(), kakaoUserInfo.kakaoAccount().profile().profileImgUrl());
        return user.getId();
    }

    private JwtResDTO.Login createJwt(final Long userId) {
        return jwtProvider.createJwt(userId);
    }

    private User saveUser(String kakaoId, String name) {
        return userRepositoryPort.save(User.of(kakaoId, name));
    }

    private Profile saveProfile(Long userId, String nickname) {
        return profileRepositoryPort.save(Profile.of(userId, nickname));
    }

    private void saveProfileImage(Long profileId, String profileImageUrl) {
        profileImageRepositoryPort.save(ProfileImage.of(profileId, profileImageUrl));
    }
}