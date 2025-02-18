package talkPick.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoResDTO {
    public record KakaoTokenInfo(
            Long id,
            Integer expiresInMillis,
            Integer expires_in,
            Integer app_id,
            Integer appId
    ) {}
    public record KakaoAccessToken(
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") Integer expiresIn,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("refresh_token_expires_in") Integer refreshTokenExpiresIn,
            @JsonProperty("scope") String scope
    ) {
    }
}
