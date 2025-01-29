package org.tictoc.tictoc.domain.auction.repository.bid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.auction.entity.bid.WinningBid;

public interface WinningBidRepository extends JpaRepository<WinningBid, Integer> {
}
