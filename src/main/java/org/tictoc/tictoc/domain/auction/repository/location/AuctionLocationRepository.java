package org.tictoc.tictoc.domain.auction.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.auction.entity.location.AuctionLocation;

import java.util.Optional;

public interface AuctionLocationRepository extends JpaRepository<AuctionLocation, Long> {
    Optional<AuctionLocation> findByAuctionId(Long auctionId);
}
