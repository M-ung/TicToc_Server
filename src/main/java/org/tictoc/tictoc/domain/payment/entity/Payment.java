package org.tictoc.tictoc.domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tictoc.tictoc.domain.payment.entity.type.PaymentStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tradeId;
    private Long auctioneerId;
    private Long bidderId;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
