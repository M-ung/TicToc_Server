package org.tictoc.tictoc.domain.auction.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.auction.entity.location.AuctionLocation;
import org.tictoc.tictoc.domain.auction.exception.location.AuctionLocationNotFoundException;

import java.util.Optional;

import static org.tictoc.tictoc.global.error.ErrorCode.AUCTION_LOCATION_NOT_FOUND;

public interface AuctionLocationRepository extends JpaRepository<AuctionLocation, Long> {
    Optional<AuctionLocation> findByAuctionId(Long auctionId);
    default AuctionLocation findByAuctionIdOrThrow(Long auctionId) {
        return findByAuctionId(auctionId).orElseThrow(() -> new AuctionLocationNotFoundException(AUCTION_LOCATION_NOT_FOUND));
    }
}
