package tictoc.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tictoc.annotation.UserId;
import tictoc.user.dto.response.UserResDTO;
import tictoc.user.mapper.UserResMapper;
import tictoc.user.port.UserQueryUseCase;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "User", description = "회원 관련 API")
public class UserQueryController {
    private final UserResMapper userResMapper;
    private final UserQueryUseCase userQueryUseCase;

    @GetMapping("/schedule")
    public ResponseEntity<List<UserResDTO.schedules>> getSchedules(@UserId final Long userId) {
        return ResponseEntity.ok(userResMapper.toSchedules(userQueryUseCase.getSchedules(userId)));
    }
}