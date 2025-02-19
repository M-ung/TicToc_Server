package tictoc.kakao.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import tictoc.kakao.dto.KakaoResDTO;

@FeignClient(name = "${feign.kakao.kakao_get_token_info_name}", url = "${feign.kakao.get-kapi-url}")
public interface KakaoGetTokenInfoFeignApi {
    @GetMapping("/v1/user/access_token_info")
    KakaoResDTO.KakaoTokenInfo getKakaoTokenInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) final String accessTokenWithTokenType);
}