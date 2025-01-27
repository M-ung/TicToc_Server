package org.tictoc.tictoc.domain.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tictoc.tictoc.domain.profile.entity.type.MoneyHistoryStatus;
import org.tictoc.tictoc.global.common.entity.base.BaseTimeEntity;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
