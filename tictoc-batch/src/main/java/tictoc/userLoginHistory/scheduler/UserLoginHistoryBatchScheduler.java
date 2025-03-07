package tictoc.userLoginHistory.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLoginHistoryBatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job userLoginHistoryJob;

    @Scheduled(cron = "*/50 * * * * *")  //TODO 30초에 한 번 (테스트용)
//    @Scheduled(cron = "0 0 1 * * *") //TODO 아침 10시에 한 번 (배포용)
    public void runBatchJob() throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(userLoginHistoryJob, jobParameters);
    }
}