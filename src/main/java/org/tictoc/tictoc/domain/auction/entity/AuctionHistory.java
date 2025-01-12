package org.tictoc.tictoc.domain.auction.entity;

import jakarta.persistence.*;
import lombok.*;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auctionId;
    private Long buyerId;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private AuctionStatus status;
}
