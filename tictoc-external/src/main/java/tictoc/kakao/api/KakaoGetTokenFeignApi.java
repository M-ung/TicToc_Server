package tictoc.kakao.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tictoc.kakao.dto.KakaoResDTO;

@FeignClient(name = "${feign.kakao.name}", url = "${feign.kakao.get-token-url}")
public interface KakaoGetTokenFeignApi {
    @PostMapping(value = "/oauth/token")
    KakaoResDTO.KakaoAccessToken getKakaoAccessToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code
    );
}