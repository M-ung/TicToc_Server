package tictoc.notification.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tictoc.model.baseTime.BaseTimeEntity;
import tictoc.notification.model.type.NotificationStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification")
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String message;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
    private Boolean isRead = false;
}
