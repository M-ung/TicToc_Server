package tictoc.userLoginHistory.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import tictoc.error.ErrorCode;
import tictoc.error.exception.BatchException;
import tictoc.slack.port.SlackUseCase;
import tictoc.userLoginHistory.archive.LoginHistoryCsvArchiver;
import tictoc.userLoginHistory.dto.UserLoginHistoryDTO;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class UserLoginHistoryBatchConfig {
    private final JobRepository jobRepository;
    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;
    private final LoginHistoryCsvArchiver loginHistoryCsvArchiver;
    private final SlackUseCase slackUseCase;

    @Bean
    public Job userLoginHistoryJob(Step userLoginHistoryStep) {
        return new JobBuilder("userLoginHistoryJob", jobRepository)
                .start(userLoginHistoryStep)
                .build();
    }

    @Bean
    public Step userLoginHistoryStep() {
        return new StepBuilder("userLoginHistoryStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    RetryTemplate retryTemplate = new RetryTemplate();

                    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
                    retryPolicy.setMaxAttempts(3);
                    retryTemplate.setRetryPolicy(retryPolicy);

                    try {
                        retryTemplate.execute(context -> {
                            LocalDateTime endDate = LocalDateTime.now();
                            LocalDateTime startDate = endDate.minusDays(7);

                            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);

                            Map<String, Object> params = Map.of(
                                    "startDate", Timestamp.valueOf(startDate),
                                    "endDate", Timestamp.valueOf(endDate)
                            );

                            var records = jdbc.query(
                                    """
                                    SELECT id, user_id, login_at, ip_address, device
                                    FROM user_login_history
                                    WHERE login_at BETWEEN :startDate AND :endDate
                                    """, params,
                                    (rs, rowNum) -> new UserLoginHistoryDTO(
                                            rs.getLong("id"),
                                            rs.getLong("user_id"),
                                            rs.getTimestamp("login_at").toLocalDateTime(),
                                            rs.getString("ip_address"),
                                            rs.getString("device")
                                    ));

                            if (records.isEmpty()) return null;

                            loginHistoryCsvArchiver.archive(records);

                            var idsToDelete = records.stream().map(UserLoginHistoryDTO::id).toList();
                            jdbc.update("DELETE FROM user_login_history WHERE id IN (:ids)",
                                    Map.of("ids", idsToDelete));

                            return null;
                        });
                    } catch (Exception e) {
                        slackUseCase.send("[배치 실패] 유저 로그인 히스토리 처리 중 예외 발생: " + e.getMessage());
                        throw new BatchException(ErrorCode.BATCH_ERROR, e);
                    }
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}