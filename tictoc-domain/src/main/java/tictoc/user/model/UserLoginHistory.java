package tictoc.user.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user_login_history")
public class UserLoginHistory {
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private Long userId;
    @Field(type = FieldType.Keyword)
    private LocalDateTime loginAt;
    @Field(type = FieldType.Keyword)
    private String ipAddress;
    @Field(type = FieldType.Keyword)
    private String device;

    public static UserLoginHistory of(Long userId, String ipAddress, String device) {
        log.info("[Login History] UserId: {}, IPAddress: {}, Device: {}", userId, ipAddress, device);
        return UserLoginHistory.builder()
                .userId(userId)
                .loginAt(LocalDateTime.now())
                .ipAddress(ipAddress)
                .device(device)
                .build();
    }
}