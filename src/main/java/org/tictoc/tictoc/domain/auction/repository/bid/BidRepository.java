package org.tictoc.tictoc.domain.auction.repository.bid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.auction.entity.bid.Bid;
import org.tictoc.tictoc.domain.auction.entity.type.BidStatus;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long>, BidRepositoryCustom {
    Optional<Bid> findByAuctionIdAndStatus(final Long auctionId, BidStatus status);
}