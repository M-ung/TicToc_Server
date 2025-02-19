package tictoc.kakao.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import tictoc.kakao.dto.KakaoResDTO;

@FeignClient(name = "${feign.kakao.kakao_get_profile_name}", url = "${feign.kakao.get-kapi-url}")
public interface KakaoGetProfileFeignApi {
    @GetMapping("/v2/user/me")
    KakaoResDTO.KakaoProfile getKakaoProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) final String accessToken);
}