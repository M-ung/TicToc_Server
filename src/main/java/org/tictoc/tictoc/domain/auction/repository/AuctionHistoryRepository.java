package org.tictoc.tictoc.domain.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.auction.entity.AuctionHistory;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long> {
}
