package org.tictoc.tictoc.domain.auction.repository.bid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.auction.entity.bid.Bid;

public interface BidRepository extends JpaRepository<Bid, Long>, BidRepositoryCustom {
}