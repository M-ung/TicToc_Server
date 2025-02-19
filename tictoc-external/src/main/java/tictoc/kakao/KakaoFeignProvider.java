package tictoc.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.kakao.api.KakaoGetProfileFeignApi;
import tictoc.kakao.api.KakaoGetTokenFeignApi;
import tictoc.kakao.api.KakaoGetTokenInfoFeignApi;
import tictoc.kakao.constants.KakaoConstants;
import tictoc.kakao.dto.KakaoResDTO;

@Component
@RequiredArgsConstructor
public class KakaoFeignProvider {
    private final KakaoProperties kakaoProperties;
    private final KakaoGetTokenFeignApi kakaoGetTokenFeignApi;
    private final KakaoGetTokenInfoFeignApi kakaoGetTokenInfoFeignApi;
    private final KakaoGetProfileFeignApi kakaoGetProfileFeignApi;
//
//    public String login(final String authorizationCode) {
//        try {
//            final String accessToken = getKakaoAccessToken(authorizationCode);
//            return getSocialId(accessToken);
//        } catch (FeignException e) {
//            throw new BadRequestException(ErrorCode.KAKAO_BAD_REQUEST);
//        }
//    }

    public KakaoResDTO.KakaoUserInfo getKakaoProfile(final String accessToken) {
        return kakaoGetProfileFeignApi.getKakaoProfile(accessToken);
    }

    public String getKakaoAccessToken(final String authorizationCode) {
        KakaoResDTO.KakaoAccessToken kakaoAccessTokenRes = kakaoGetTokenFeignApi.getKakaoAccessToken(
                KakaoConstants.AUTH_CODE,
                kakaoProperties.getRestApiKey(),
                kakaoProperties.getRedirect(),
                authorizationCode
        );
        return KakaoConstants.BEARER + kakaoAccessTokenRes.accessToken();
    }

    public String getSocialId(final String accessToken) {
        KakaoResDTO.KakaoTokenInfo kakaoAccessTokenInfoRes = kakaoGetTokenInfoFeignApi.getKakaoTokenInfo(accessToken);
        return String.valueOf(kakaoAccessTokenInfoRes.id());
    }
}