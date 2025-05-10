package tictoc.user.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tictoc.user.dto.response.UserUseCaseResDTO;
import tictoc.user.port.UserScheduleRepositoryPort;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserQueryServiceTest {
    @InjectMocks
    private UserQueryService userQueryService;

    @Mock
    private UserScheduleRepositoryPort userScheduleRepositoryPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원 스케줄 조회 성공 테스트")
    void 회원_스케줄_조회_성공_테스트() {
        // given
        Long userId = 1L;

        List<UserUseCaseResDTO.Schedules> schedules = List.of(
                UserUseCaseResDTO.Schedules.builder()
                        .scheduleId(1L)
                        .startTime(LocalDateTime.of(2025, 5, 10, 9, 0))
                        .endTime(LocalDateTime.of(2025, 5, 10, 10, 0))
                        .build(),
                UserUseCaseResDTO.Schedules.builder()
                        .scheduleId(2L)
                        .startTime(LocalDateTime.of(2025, 5, 11, 14, 0))
                        .endTime(LocalDateTime.of(2025, 5, 11, 15, 0))
                        .build()
        );

        when(userScheduleRepositoryPort.findSchedulesByUserId(userId)).thenReturn(schedules);

        // when
        List<UserUseCaseResDTO.Schedules> result = userQueryService.getSchedules(userId);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getScheduleId()).isEqualTo(1L);
        assertThat(result.get(1).getStartTime()).isEqualTo(LocalDateTime.of(2025, 5, 11, 14, 0));
        verify(userScheduleRepositoryPort).findSchedulesByUserId(userId);
    }

    @Test
    @DisplayName("회원 스케줄이 없을 경우 빈 리스트 반환 테스트")
    void 회원_스케줄_없음_빈_리스트_반환_테스트() {
        // given
        Long userId = 2L;
        when(userScheduleRepositoryPort.findSchedulesByUserId(userId))
                .thenReturn(List.of());

        // when
        List<UserUseCaseResDTO.Schedules> result = userQueryService.getSchedules(userId);

        // then
        assertThat(result).isEmpty();
        verify(userScheduleRepositoryPort).findSchedulesByUserId(userId);
    }

    @Test
    @DisplayName("회원 스케줄 조회 중 예외 발생 테스트")
    void 회원_스케줄_조회_중_예외_발생_테스트() {
        // given
        Long userId = 3L;
        when(userScheduleRepositoryPort.findSchedulesByUserId(userId))
                .thenThrow(new RuntimeException("DB 조회 실패"));

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userQueryService.getSchedules(userId);
        });

        assertThat(exception).hasMessage("DB 조회 실패");
        verify(userScheduleRepositoryPort).findSchedulesByUserId(userId);
    }
}