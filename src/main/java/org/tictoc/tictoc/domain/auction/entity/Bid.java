package org.tictoc.tictoc.domain.auction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tictoc.tictoc.global.common.entity.BaseTimeEntity;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bid extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sellerId;
    private Long bidderId;
    private Long auctionId;
    private Integer price;
}
