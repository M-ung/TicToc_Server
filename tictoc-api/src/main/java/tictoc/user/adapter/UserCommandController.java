package tictoc.user.adapter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tictoc.config.security.jwt.dto.JwtResDTO;
import tictoc.constants.UserLoginHistoryConstants;
import tictoc.user.port.UserCommandUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class UserCommandController implements UserCommandApi {
    private final HttpServletRequest request;
    private final UserCommandUseCase userCommandUseCase;

    @PostMapping("/login")
    public ResponseEntity<JwtResDTO.Login> login(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authenticationCode) {
        return ResponseEntity.ok(userCommandUseCase.login(authenticationCode, getUserIp(), getUserAgent()));
    }

    private String getUserIp() {
        var ip = request.getHeader(UserLoginHistoryConstants.X_FORWARDED_FOR);
        if (ip == null || ip.isEmpty() ||UserLoginHistoryConstants.UNKNOWN.equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        return ip;
    }

    private String getUserAgent() {
        return request.getHeader(UserLoginHistoryConstants.USER_AGENT);
    }
}