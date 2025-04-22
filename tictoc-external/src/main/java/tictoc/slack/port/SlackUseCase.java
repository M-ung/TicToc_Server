package tictoc.slack.port;

public interface SlackUseCase {
    void send(String message);
}