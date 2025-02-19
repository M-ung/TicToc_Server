package tictoc.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tictoc.config.security.jwt.dto.JwtResDTO;
import tictoc.user.mapper.UserReqMapper;
import tictoc.user.port.UserCommandUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "User", description = "회원 관련 API")
public class UserCommandController {
    private final UserReqMapper userReqMapper;
    private final UserCommandUseCase userCommandUseCase;

    @PostMapping("/login")
    public ResponseEntity<JwtResDTO.Login> login(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authenticationCode) {
        return ResponseEntity.ok(userCommandUseCase.login(authenticationCode));
    }
}