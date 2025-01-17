package org.tictoc.tictoc.domain.auction.entity.location;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auctionId;
    private Long locationId;

    public static AuctionLocation of(Long auctionId, Long locationId) {
        return AuctionLocation.builder()
                .auctionId(auctionId)
                .locationId(locationId)
                .build();
    }
}