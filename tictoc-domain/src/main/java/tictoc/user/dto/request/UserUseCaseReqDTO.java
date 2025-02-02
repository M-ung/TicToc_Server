package tictoc.user.dto.request;

public class UserUseCaseReqDTO {
    public record Login(
            Long userId
    ) {
    }
}
