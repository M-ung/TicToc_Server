package tictoc.userLoginHistory.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import tictoc.userLoginHistory.archive.LoginHistoryCsvArchiver;
import tictoc.userLoginHistory.dto.UserLoginHistoryDTO;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class UserLoginHistoryBatchConfig {
    private final JobRepository jobRepository;
    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;
    private final LoginHistoryCsvArchiver loginHistoryCsvArchiver;

    @Bean
    public Job userLoginHistoryJob(Step step) {
        return new JobBuilder("userLoginHistoryJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step userLoginHistoryStep() {
        return new StepBuilder("userLoginHistoryStep", jobRepository)
                .<UserLoginHistoryDTO, UserLoginHistoryDTO>chunk(100, transactionManager)
                .reader(userLoginHistoryReader())
                .writer(userLoginHistoryItemWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<UserLoginHistoryDTO> userLoginHistoryReader() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);

        return new JdbcCursorItemReaderBuilder<UserLoginHistoryDTO>()
                .dataSource(dataSource)
                .name("userLoginHistoryReader")
                .sql("""
                    SELECT id, user_id, login_at, ip_address, device 
                    FROM user_login_history 
                    WHERE login_at BETWEEN ? AND ?
                    """)
                .queryArguments(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate))
                .rowMapper((rs, rowNum) -> new UserLoginHistoryDTO(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getTimestamp("login_at").toLocalDateTime(),
                        rs.getString("ip_address"),
                        rs.getString("device")
                ))
                .build();
    }


    @Bean
    public ItemWriter<UserLoginHistoryDTO> userLoginHistoryItemWriter() {
        return chunk -> {
            List<? extends UserLoginHistoryDTO> items = chunk.getItems();
            if (items.isEmpty()) return;
            loginHistoryCsvArchiver.archive(items);
            List<Long> idsToDelete = new ArrayList<>();
            for (UserLoginHistoryDTO dto : items) {
                idsToDelete.add(dto.id());
            }
            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
            String sql = "DELETE FROM user_login_history WHERE id IN (:ids)";
            Map<String, Object> params = Map.of("ids", idsToDelete);
            jdbc.update(sql, params);
        };
    }
}