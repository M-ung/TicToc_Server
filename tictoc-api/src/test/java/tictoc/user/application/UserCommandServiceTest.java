package tictoc.user.application;

import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tictoc.config.security.jwt.dto.JwtResDTO;
import tictoc.config.security.jwt.util.JwtProvider;
import tictoc.error.ErrorCode;
import tictoc.error.exception.BadRequestException;
import tictoc.kakao.KakaoFeignProvider;
import tictoc.model.tictoc.TicTocStatus;
import tictoc.profile.port.ProfileRepositoryPort;
import tictoc.user.model.User;
import tictoc.user.model.type.UserRole;
import tictoc.user.port.UserLoginHistoryRepositoryPort;
import tictoc.user.port.UserRepositoryPort;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserCommandServiceTest {
    @InjectMocks
    private UserCommandService userCommandService;

    @Mock
    private UserRepositoryPort userRepositoryPort;
    @Mock private ProfileRepositoryPort profileRepositoryPort;
    @Mock private UserLoginHistoryRepositoryPort userLoginHistoryRepositoryPort;
    @Mock private JwtProvider jwtProvider;
    @Mock private KakaoFeignProvider kakaoFeignProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void 로그인_성공_테스트() {
        // given
        String authCode = "code";
        String kakaoAccessToken = "kakao-access-token";
        String kakaoId = "kakao-id";
        Long userId = 1L;

        JwtResDTO.Login expectedJwt = new JwtResDTO.Login(
                userId,
                "dummy-access-token",
                "dummy-refresh-token",
                9999999999L,
                9999999999L
        );

        when(kakaoFeignProvider.getKakaoAccessToken(authCode)).thenReturn(kakaoAccessToken);
        when(kakaoFeignProvider.getSocialId(kakaoAccessToken)).thenReturn(kakaoId);
        when(userRepositoryPort.findUserByKakaoId(kakaoId)).thenReturn(Optional.of(new User(1L, "kakao-id", "name", UserRole.USER, TicTocStatus.ACTIVE)));
        when(jwtProvider.createJwt(userId)).thenReturn(expectedJwt);

        // when
        JwtResDTO.Login result = userCommandService.login(authCode, "1.1.1.1", "chrome");

        // then
        assertThat(result.accessToken()).isEqualTo("dummy-access-token");
        assertThat(result.refreshToken()).isEqualTo("dummy-refresh-token");
        verify(userLoginHistoryRepositoryPort).save(eq(userId), any(), eq("1.1.1.1"), eq("chrome"));
    }

    @Test
    @DisplayName("잘못된 인증 코드로 로그인 실패 테스트")
    void 잘못된_인증_코드로_로그인_실패_테스트() {
        // given
        String authCode = "invalid-code";
        when(kakaoFeignProvider.getKakaoAccessToken(authCode)).thenThrow(mock(FeignException.BadRequest.class));

        // when & then
        assertThatThrownBy(() -> userCommandService.login(authCode, "0.0.0.0", "Safari")
        ).isInstanceOf(BadRequestException.class)
                .hasMessageContaining(ErrorCode.KAKAO_BAD_REQUEST.getMessage());
    }
}