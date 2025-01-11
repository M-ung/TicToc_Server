package org.tictoc.tictoc.domain.auction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private AuctionStatus status;
}
