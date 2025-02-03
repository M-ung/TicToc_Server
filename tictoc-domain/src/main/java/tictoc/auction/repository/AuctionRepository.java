package tictoc.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tictoc.auction.model.Auction;
import tictoc.model.tictoc.TicTocStatus;
import java.time.LocalDateTime;

public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionRepositoryCustom {
    @Query("SELECT COUNT(a) > 0 FROM Auction a " +
            "WHERE a.auctioneerId = :userId " +
            "AND a.status = :status " +
            "AND a.sellStartTime < :sellEndTime " +
            "AND a.sellEndTime > :sellStartTime")
    boolean existsAuctionInTimeRange(@Param("userId") Long userId, @Param("sellStartTime") LocalDateTime sellStartTime, @Param("sellEndTime") LocalDateTime sellEndTime, @Param("status") TicTocStatus status);
}