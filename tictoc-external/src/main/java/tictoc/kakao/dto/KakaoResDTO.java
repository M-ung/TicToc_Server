package tictoc.kakao.dto;

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
    ) {}

    public record KakaoProfile(
            @JsonProperty("id") Long id,
            @JsonProperty("kakao_account") KakaoAccount kakaoAccount
    ) {}

    public record KakaoAccount(
            @JsonProperty("profile") KakaoProfileInfo profile,
            @JsonProperty("name") String name
    ) {}

    public record KakaoProfileInfo(
            @JsonProperty("nickname") String nickname,
            @JsonProperty("thumbnail_image_url") String thumbnailImageUrl,
            @JsonProperty("profile_image_url") String profileImageUrl
    ) {}
}