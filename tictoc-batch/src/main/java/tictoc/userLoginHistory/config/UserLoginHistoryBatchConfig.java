package tictoc.userLoginHistory.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
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
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class UserLoginHistoryBatchConfig {
    private final JobRepository jobRepository;
    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job userLoginHistoryJob(Step step) {
        return new JobBuilder("userLoginHistoryJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step userLoginHistoryStep() {
        return new StepBuilder("userLoginHistoryStep", jobRepository)
                .<Long, Long>chunk(100, transactionManager)
                .reader(userLoginHistoryReader())
                .writer(userLoginHistoryItemWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Long> userLoginHistoryReader() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);

        return new JdbcCursorItemReaderBuilder<Long>()
                .dataSource(dataSource)
                .name("userLoginHistoryReader")
                .sql("SELECT id FROM user_login_history WHERE login_at BETWEEN ? AND ?")
                .queryArguments(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate))
                .rowMapper((rs, rowNum) -> rs.getLong("id"))
                .build();
    }

    @Bean
    public ItemWriter<Long> userLoginHistoryItemWriter() {
        return items -> {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
            if (items.isEmpty()) return;
            String sql = "DELETE FROM user_login_history WHERE id IN (:ids)";
            Map<String, Object> params = new HashMap<>();
            params.put("ids", items);
            namedParameterJdbcTemplate.update(sql, params);
        };
    }
}