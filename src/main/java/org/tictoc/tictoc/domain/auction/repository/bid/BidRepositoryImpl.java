package org.tictoc.tictoc.domain.auction.repository.bid;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.bid.response.BidResponseDTO;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.BidStatus;
import org.tictoc.tictoc.global.common.entity.page.PageCustom;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;
import java.util.List;
import java.util.Optional;
import static org.tictoc.tictoc.domain.auction.entity.auction.QAuction.auction;
import static org.tictoc.tictoc.domain.auction.entity.bid.QBid.bid;

public class BidRepositoryImpl implements BidRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public BidRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public PageCustom<BidResponseDTO.Bid> findBidsByFilterWithPageable(final Long userId, BidRequestDTO.Filter requestDTO, Pageable pageable) {
        var results = queryFilteredBids(userId, requestDTO, pageable);
        long total = getTotalByFilter(userId, requestDTO);
        int totalPages = calculateTotalPages(total, pageable.getPageSize());
        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    private List<BidResponseDTO.Bid> queryFilteredBids(Long userId, BidRequestDTO.Filter requestDTO, Pageable pageable) {
        return queryFactory.select(Projections.constructor(BidResponseDTO.Bid.class,
                        auction.id,
                        auction.title,
                        bid.price,
                        auction.currentPrice,
                        bid.status,
                        auction.progress
                ))
                .from(bid)
                .leftJoin(auction).on(bid.auctionId.eq(auction.id))
                .where(
                        auction.status.eq(TicTocStatus.ACTIVE),
                        bid.bidderId.eq(userId),
                        filterBidStatus(requestDTO.bidStatus()),
                        filterAuctionProgress(requestDTO.auctionProgress())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression filterBidStatus(BidStatus status) {
        return status != null
                ? bid.status.eq(status)
                : null;
    }

    private BooleanExpression filterAuctionProgress(AuctionProgress progress) {
        return progress != null
                ? auction.progress.eq(progress)
                : null;
    }

    private int calculateTotalPages(long total, int pageSize) {
        return (int) Math.ceil((double) total / pageSize);
    }

    private long getTotalByFilter(Long userId, BidRequestDTO.Filter requestDTO) {
        return Optional.ofNullable(
                queryFactory.select(bid.count())
                        .from(bid)
                        .leftJoin(auction).on(bid.auctionId.eq(auction.id))
                        .where(
                                auction.status.eq(TicTocStatus.ACTIVE),
                                bid.bidderId.eq(userId),
                                filterBidStatus(requestDTO.bidStatus()),
                                filterAuctionProgress(requestDTO.auctionProgress())
                        )
                        .fetchOne()
        ).orElse(0L);
    }
}
