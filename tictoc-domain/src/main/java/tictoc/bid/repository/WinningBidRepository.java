package tictoc.bid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.bid.model.WinningBid;

public interface WinningBidRepository extends JpaRepository<WinningBid, Integer>, WinningBidRepositoryCustom {
}
