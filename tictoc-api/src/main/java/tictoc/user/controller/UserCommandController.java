package tictoc.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tictoc.config.security.jwt.dto.JwtResDTO;
import tictoc.user.dto.request.UserReqDTO;
import tictoc.user.mapper.UserReqMapper;
import tictoc.user.port.in.UserCommandUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "User", description = "회원 관련 API")
public class UserCommandController {
    private final UserReqMapper userReqMapper;
    private final UserCommandUseCase userCommandUseCase;

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인 API 입니다.")
    public ResponseEntity<JwtResDTO.Login> login (@RequestBody UserReqDTO.Login requestDTO) {
        return ResponseEntity.ok(userCommandUseCase.login(userReqMapper.toUseCaseDTO(requestDTO)));
    }
}
