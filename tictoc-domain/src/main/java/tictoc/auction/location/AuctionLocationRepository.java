package tictoc.auction.location;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.auction.model.location.AuctionLocation;

import java.util.Optional;

public interface AuctionLocationRepository extends JpaRepository<AuctionLocation, Long> {
    Optional<AuctionLocation> findByAuctionId(Long auctionId);
}
