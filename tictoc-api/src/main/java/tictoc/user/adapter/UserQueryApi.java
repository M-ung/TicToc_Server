package tictoc.user.adapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import tictoc.annotation.UserId;
import tictoc.user.dto.response.UserResDTO;
import java.util.List;

@Tag(name = "User", description = "사용자 관련 API")
public interface UserQueryApi {
    @Operation(summary = "사용자 스케줄 조회 API", description = "사용자 스케줄 조회 API 입니다.")
    ResponseEntity<List<UserResDTO.schedules>> getSchedules(@UserId final Long userId);
}