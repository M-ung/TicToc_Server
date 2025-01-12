package org.tictoc.tictoc.domain.user.dto.request;

public class UserRequestDTO {
    public record Login(
            Long userId
    ) {
    }
}
