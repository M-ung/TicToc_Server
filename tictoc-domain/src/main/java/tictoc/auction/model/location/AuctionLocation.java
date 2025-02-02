package tictoc.auction.model.location;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auction_location")
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