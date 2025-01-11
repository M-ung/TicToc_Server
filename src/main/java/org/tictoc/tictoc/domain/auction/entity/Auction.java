package org.tictoc.tictoc.domain.auction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tictoc.tictoc.global.common.entity.BaseTimeEntity;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auction extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auctioneerId;
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    private Integer startPrice;
    private Integer currentPrice;
    private Integer finalPrice;
    private LocalDateTime sellStartTime;
    private LocalDateTime sellEndTime;
    private LocalDateTime auctionOpenTime;
    private LocalDateTime auctionCloseTime;
    private TicTocStatus status;
}
