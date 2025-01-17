package org.tictoc.tictoc.domain.auction.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.auction.entity.history.AuctionHistory;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long> {
}
