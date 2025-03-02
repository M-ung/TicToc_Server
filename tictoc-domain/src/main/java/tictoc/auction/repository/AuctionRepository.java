package tictoc.auction.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tictoc.auction.model.Auction;
import tictoc.model.tictoc.TicTocStatus;
import java.time.LocalDateTime;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionRepositoryCustom {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Auction a WHERE a.id = :auctionId and a.status = :status")
    Optional<Auction> findByIdAndStatusForUpdate(@Param("auctionId") Long auctionId, @Param("status") TicTocStatus status);
    Optional<Auction> findByIdAndStatus(Long auctionId, TicTocStatus status);
    @Query("SELECT COUNT(a) > 0 FROM Auction a " +
            "WHERE a.auctioneerId = :userId " +
            "AND a.status = :status " +
            "AND a.sellStartTime < :sellEndTime " +
            "AND a.sellEndTime > :sellStartTime")
    boolean existsAuctionInTimeRangeForSave(@Param("userId") Long userId, @Param("sellStartTime") LocalDateTime sellStartTime, @Param("sellEndTime") LocalDateTime sellEndTime, @Param("status") TicTocStatus status);

    @Query("SELECT COUNT(a) > 0 FROM Auction a " +
            "WHERE a.auctioneerId = :userId " +
            "AND a.status = :status " +
            "AND a.id <> :auctionId " +
            "AND a.sellStartTime < :sellEndTime " +
            "AND a.sellEndTime > :sellStartTime")
    boolean existsAuctionInTimeRangeForUpdate(@Param("userId") Long userId, @Param("auctionId") Long auctionId, @Param("sellStartTime") LocalDateTime sellStartTime, @Param("sellEndTime") LocalDateTime sellEndTime, @Param("status") TicTocStatus status);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Auction a SET a.currentPrice = :price WHERE a.id = :auctionId AND a.currentPrice < :price")
    int updateBidIfHigher(@Param("auctionId") Long auctionId, @Param("price") Integer price);
}