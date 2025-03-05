package tictoc.userLoginHistory.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
@EnableBatchProcessing
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
                .<Long, List<Long>>chunk(100, transactionManager)
                .reader(userLoginHistoryReader())
                .writer(userLoginHistoryItemWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Long> userLoginHistoryReader() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minus(1, ChronoUnit.WEEKS);
        return new JdbcCursorItemReaderBuilder<Long>()
                .dataSource(dataSource)
                .name("userLoginHistoryReader")
                .sql("SELECT id FROM user_login_history WHERE login_at < ?")
                .queryArguments(oneWeekAgo)
                .rowMapper((rs, rowNum) -> rs.getLong("id"))
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<List<Long>> userLoginHistoryItemWriter() {
        JdbcBatchItemWriter<List<Long>> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("DELETE FROM user_login_history WHERE id IN (:ids)");
        writer.setItemPreparedStatementSetter((item, ps) -> {
            for (int i = 0; i < item.size(); i++) {
                ps.setLong(i + 1, item.get(i));
            }
        });
        return writer;
    }
}