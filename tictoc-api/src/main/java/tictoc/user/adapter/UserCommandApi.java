package tictoc.user.adapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import tictoc.config.security.jwt.dto.JwtResDTO;

@Tag(name = "User", description = "사용자 관련 API")
public interface UserCommandApi {
    @Operation(summary = "사용자 로그인 API", description = "사용자 로그인 API 입니다.")
    ResponseEntity<JwtResDTO.Login> login(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authenticationCode);
}