package tictoc.userLoginHistory.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
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
import java.util.HashMap;
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
    public Job userLoginHistoryJob(Step userLoginHistoryStep) {
        return new JobBuilder("userLoginHistoryJob", jobRepository)
                .start(userLoginHistoryStep)
                .build();
    }

    @Bean
    public Step userLoginHistoryStep() throws Exception {
        return new StepBuilder("userLoginHistoryStep", jobRepository)
                .<UserLoginHistoryDTO, UserLoginHistoryDTO>chunk(100, transactionManager)
                .reader(userLoginHistoryReader())
                .writer(userLoginHistoryItemWriter())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<UserLoginHistoryDTO> userLoginHistoryReader() {
        try {
            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate = endDate.minusDays(7);
            Map<String, Object> params = getDateParams(startDate, endDate);
            JdbcPagingItemReader<UserLoginHistoryDTO> reader = new JdbcPagingItemReader<>();
            reader.setDataSource(dataSource);
            reader.setQueryProvider(createQueryProvider());
            reader.setParameterValues(params);
            reader.setPageSize(100);
            reader.setRowMapper((rs, rowNum) -> new UserLoginHistoryDTO(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getTimestamp("login_at").toLocalDateTime(),
                    rs.getString("ip_address"),
                    rs.getString("device")
            ));
            reader.afterPropertiesSet();
            return reader;

        } catch (Exception e) {
            throw new IllegalStateException("JdbcPagingItemReader 생성 실패", e);
        }
    }

    private Map<String, Object> getDateParams(LocalDateTime start, LocalDateTime end) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", Timestamp.valueOf(start));
        params.put("endDate", Timestamp.valueOf(end));
        return params;
    }

    private PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean providerFactory = new SqlPagingQueryProviderFactoryBean();
        providerFactory.setDataSource(dataSource);
        providerFactory.setSelectClause("SELECT id, user_id, login_at, ip_address, device");
        providerFactory.setFromClause("FROM user_login_history");
        providerFactory.setWhereClause("WHERE login_at BETWEEN :startDate AND :endDate");
        providerFactory.setSortKey("id");
        return providerFactory.getObject();
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