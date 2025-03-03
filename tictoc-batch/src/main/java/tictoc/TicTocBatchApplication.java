package tictoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TicTocBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicTocBatchApplication.class, args);
    }
}