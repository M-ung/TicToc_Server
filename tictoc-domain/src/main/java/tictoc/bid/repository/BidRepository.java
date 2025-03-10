package tictoc.bid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.bid.model.Bid;
import tictoc.bid.model.type.BidProgress;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long>, BidRepositoryCustom {
    Optional<Bid> findByAuctionIdAndProgress(final Long auctionId, BidProgress progress);
}