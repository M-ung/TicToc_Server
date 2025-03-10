package tictoc.bid.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.bid.model.type.BidProgress;
import tictoc.model.page.PageCustom;
import tictoc.model.tictoc.TicTocStatus;
import java.util.List;
import java.util.Optional;
import static tictoc.auction.model.QAuction.auction;
import static tictoc.bid.model.QBid.bid;

public class BidRepositoryImpl implements BidRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public BidRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public PageCustom<BidUseCaseResDTO.Bid> findBidsByFilterWithPageable(final Long userId, BidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        var results = queryFilteredBids(userId, requestDTO, pageable);
        long total = getTotalByFilter(userId, requestDTO);
        int totalPages = calculateTotalPages(total, pageable.getPageSize());
        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    private List<BidUseCaseResDTO.Bid> queryFilteredBids(Long userId, BidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return queryFactory.select(Projections.constructor(BidUseCaseResDTO.Bid.class,
                        auction.id,
                        auction.title,
                        bid.bidPrice,
                        auction.currentPrice,
                        bid.progress,
                        auction.progress
                ))
                .from(bid)
                .leftJoin(auction).on(bid.auctionId.eq(auction.id))
                .where(
                        auction.status.eq(TicTocStatus.ACTIVE),
                        bid.bidderId.eq(userId),
                        filterBidStatus(requestDTO.bidProgress()),
                        filterAuctionProgress(requestDTO.auctionProgress())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression filterBidStatus(BidProgress status) {
        return status != null
                ? bid.progress.eq(status)
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

    private long getTotalByFilter(Long userId, BidUseCaseReqDTO.Filter requestDTO) {
        return Optional.ofNullable(
                queryFactory.select(bid.count())
                        .from(bid)
                        .leftJoin(auction).on(bid.auctionId.eq(auction.id))
                        .where(
                                auction.status.eq(TicTocStatus.ACTIVE),
                                bid.bidderId.eq(userId),
                                filterBidStatus(requestDTO.bidProgress()),
                                filterAuctionProgress(requestDTO.auctionProgress())
                        )
                        .fetchOne()
        ).orElse(0L);
    }
}
