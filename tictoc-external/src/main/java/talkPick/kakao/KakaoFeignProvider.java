package talkPick.kakao;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.kakao.api.KakaoGetTokenFeignApi;
import talkPick.kakao.api.KakaoGetTokenInfoFeignApi;
import talkPick.kakao.constants.KakaoConstants;
import talkPick.kakao.dto.KakaoResDTO;
import tictoc.error.ErrorCode;
import tictoc.error.exception.BadRequestException;

@Component
@RequiredArgsConstructor
public class KakaoFeignProvider {
    private final KakaoProperties kakaoProperties;
    private final KakaoGetTokenFeignApi kakaoGetTokenFeignApi;
    private final KakaoGetTokenInfoFeignApi kakaoGetTokenInfoFeignApi;

    public String login(final String authorizationCode) {
        try {
            final String accessTokenWithBearer = getKakaoAccessToken(authorizationCode);
            return getSocialId(accessTokenWithBearer);
        } catch (FeignException e) {
            throw new BadRequestException(ErrorCode.KAKAO_BAD_REQUEST);
        }
    }

    //카카오 액세스 토큰 받아서 Bearer 붙여서 리턴
    private String getKakaoAccessToken(final String authorizationCode) {
        KakaoResDTO.KakaoAccessToken kakaoAccessTokenRes = kakaoGetTokenFeignApi.getKakaoAccessToken(
                KakaoConstants.AUTH_CODE,
                kakaoProperties.getRestApiKey(),
                kakaoProperties.getRedirect(),
                authorizationCode
        );
        return KakaoConstants.BEARER + kakaoAccessTokenRes.accessToken();
    }

    // 카카오 액세스 토큰으로 카카오의 userID 가져오기
    private String getSocialId(final String accessToken) {
        KakaoResDTO.KakaoTokenInfo kakaoAccessTokenInfoRes = kakaoGetTokenInfoFeignApi.getKakaoSocialId(accessToken);
        return String.valueOf(kakaoAccessTokenInfoRes.id());
    }
}