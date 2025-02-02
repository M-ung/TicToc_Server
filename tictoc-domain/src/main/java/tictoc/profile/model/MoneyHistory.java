package tictoc.profile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tictoc.model.baseTime.BaseTimeEntity;
import tictoc.profile.model.type.MoneyHistoryStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "money_history")
public class MoneyHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Integer useMoney;
    private Integer finalMoney;
    @Enumerated(EnumType.STRING)
    private MoneyHistoryStatus status;
}
