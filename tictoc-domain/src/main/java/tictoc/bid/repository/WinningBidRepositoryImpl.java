package tictoc.bid.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import tictoc.bid.dto.request.WinningBidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.model.page.PageCustom;
import tictoc.model.tictoc.TicTocStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static tictoc.auction.model.QAuction.auction;
import static tictoc.bid.model.QWinningBid.winningBid;

public class WinningBidRepositoryImpl implements WinningBidRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public WinningBidRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public PageCustom<BidUseCaseResDTO.WinningBid> findWinningBidsByFilterWithPageable(WinningBidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        var results = queryFilteredWinningBids(requestDTO, pageable);
        BooleanExpression[] conditions = new BooleanExpression[]{
                auction.status.eq(TicTocStatus.ACTIVE),
                filterPrice(requestDTO.price()),
                filterBidDate(requestDTO.winningBidDate()),
                filterSellTime(requestDTO)
        };

        long total = pageable.getPageNumber() == 0 ? getTotal(conditions) : -1;
        int totalPages = total >= 0 ? (int) Math.ceil((double) total / pageable.getPageSize()) : -1;
        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    private List<BidUseCaseResDTO.WinningBid> queryFilteredWinningBids(WinningBidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return  queryFactory.select(Projections.constructor(BidUseCaseResDTO.WinningBid.class,
                        winningBid.price,
                        winningBid.updatedAt,
                        auction.sellStartTime,
                        auction.sellEndTime
                ))
                .from(winningBid)
                .leftJoin(auction).on(winningBid.auctionId.eq(auction.id))
                .where(
                        auction.status.eq(TicTocStatus.ACTIVE),
                        filterPrice(requestDTO.price()),
                        filterBidDate(requestDTO.winningBidDate()),
                        filterSellTime(requestDTO)
                )
                .orderBy(winningBid.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression filterSellTime(WinningBidUseCaseReqDTO.Filter requestDTO) {
        return requestDTO.sellStartTime() != null && requestDTO.sellEndTime() == null
                ? auction.sellStartTime.goe(requestDTO.sellStartTime())
                : requestDTO.sellStartTime() == null && requestDTO.sellEndTime() != null
                ? auction.sellEndTime.loe(requestDTO.sellEndTime())
                : requestDTO.sellStartTime() != null && requestDTO.sellEndTime() != null
                ? auction.sellStartTime.goe(requestDTO.sellStartTime())
                .and(auction.sellEndTime.loe(requestDTO.sellEndTime()))
                : null;
    }

    private BooleanExpression filterPrice(Integer price) {
        return price != null ? winningBid.price.eq(price) : null;
    }

    private BooleanExpression filterBidDate(LocalDateTime localDateTime) {
        return localDateTime != null ? winningBid.updatedAt.eq(localDateTime) : null;
    }

    private long getTotal(BooleanExpression... conditions) {
        return Optional.ofNullable(
                queryFactory.select(winningBid.count())
                        .from(winningBid)
                        .leftJoin(auction).on(winningBid.auctionId.eq(auction.id))
                        .where(conditions)
                        .fetchOne()
        ).orElse(0L);
    }
}