package tictoc.userLoginHistory.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import tictoc.annotation.BatchScheduler;

@BatchScheduler
@RequiredArgsConstructor
public class UserLoginHistoryBatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job userLoginHistoryJob;

//    @Scheduled(cron = "0 0 0 * * SUN")
    @Scheduled(fixedDelay = 20000) //TODO 테스트용
    public void runBatchJob() throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(userLoginHistoryJob, jobParameters);
    }
}