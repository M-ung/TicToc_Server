package tictoc.slack.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tictoc.slack.port.SlackUseCase;

@Service
@RequiredArgsConstructor
public class SlackService implements SlackUseCase {
    @Value("${slack.webhook-url}")
    private String slackUrl;
    private final WebClient webClient = WebClient.create();

    @Override
    public void send(String message) {
        webClient.post()
                .uri(slackUrl)
                .header("Content-Type", "application/json")
                .bodyValue("{\"text\":\"" + message + "\"}")
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}