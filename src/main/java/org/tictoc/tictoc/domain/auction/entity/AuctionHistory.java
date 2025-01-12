package org.tictoc.tictoc.domain.auction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auctionId;
    private Long buyerId;
    @Enumerated(EnumType.STRING)
    private AuctionStatus status;
}
