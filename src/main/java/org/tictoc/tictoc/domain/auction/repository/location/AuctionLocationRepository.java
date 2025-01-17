package org.tictoc.tictoc.domain.auction.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.auction.entity.location.AuctionLocation;

public interface AuctionLocationRepository extends JpaRepository<AuctionLocation, Long> {
}
