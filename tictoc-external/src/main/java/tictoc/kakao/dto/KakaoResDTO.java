package tictoc.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoResDTO {
    public record KakaoTokenInfo(
            Long id,
            Integer expiresInMillis,
            Integer expires_in,
            Integer app_id
    ) {}

    public record KakaoAccessToken(
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") Integer expiresIn,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("refresh_token_expires_in") Integer refreshTokenExpiresIn
    ) {}

    public record KakaoUserInfo(
            @JsonProperty("kakao_account") KakaoAccount kakaoAccount
    ) {
        public record KakaoAccount(
                @JsonProperty("email") String email,
                @JsonProperty("profile") Profile profile
        ) {
            public record Profile(
                    @JsonProperty("nickname") String nickname,
                    @JsonProperty("thumbnail_image_url") String profileImgUrl
            ) {}
        }
    }
}