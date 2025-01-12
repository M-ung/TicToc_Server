package org.tictoc.tictoc.domain.user.controller.command;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tictoc.tictoc.domain.user.dto.request.UserRequestDTO;
import org.tictoc.tictoc.domain.user.service.command.UserCommandService;
import org.tictoc.tictoc.global.auth.jwt.dto.JwtResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "User", description = "회원 관련 API")
public class UserCommandController {
    private final UserCommandService userCommandService;

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인 API 입니다.")
    public ResponseEntity<JwtResponseDTO.Login> login (@RequestBody UserRequestDTO.Login requestDTO) {
        return ResponseEntity.ok(userCommandService.login(requestDTO));
    }
}
