package tictoc.user.dto.request;

public class UserReqDTO {
    public record Login(
            Long userId
    ) {
    }
}
