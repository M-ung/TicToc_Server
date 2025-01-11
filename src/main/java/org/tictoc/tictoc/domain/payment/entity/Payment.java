package org.tictoc.tictoc.domain.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private PaymentStatus status;
}
