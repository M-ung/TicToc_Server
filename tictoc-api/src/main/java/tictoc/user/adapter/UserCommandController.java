package tictoc.user.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tictoc.config.security.jwt.dto.JwtResDTO;
import tictoc.user.port.UserCommandUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class UserCommandController implements UserCommandApi {
    private final UserCommandUseCase userCommandUseCase;

    @PostMapping("/login")
    public ResponseEntity<JwtResDTO.Login> login(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authenticationCode) {
        return ResponseEntity.ok(userCommandUseCase.login(authenticationCode));
    }
}