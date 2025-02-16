package tictoc.auction.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.dto.response.AuctionUseCaseResDTO;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.model.type.AuctionType;
import tictoc.model.page.PageCustom;
import tictoc.model.tictoc.TicTocStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static tictoc.auction.model.QAuction.auction;
import static tictoc.auction.model.location.QAuctionLocation.auctionLocation;
import static tictoc.auction.model.location.QLocation.location;

public class AuctionRepositoryCustomImpl implements AuctionRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public AuctionRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public PageCustom<AuctionUseCaseResDTO.Auction> findAuctionsByFilterWithPageable(AuctionUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        var results = queryFilteredAuctions(requestDTO, pageable);
        long total = getTotalCount(auction.status.eq(TicTocStatus.ACTIVE)
                .and(filterPrice(requestDTO.startPrice(), requestDTO.endPrice()))
                .and(filterSellTime(requestDTO.sellStartTime(), requestDTO.sellEndTime()))
                .and(filterProgress(requestDTO.progress()))
                .and(filterType(requestDTO.type()))
                .and(filterLocations(requestDTO.locations())));
        int totalPages = calculateTotalPages(total, pageable.getPageSize());
        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    @Override
    public AuctionUseCaseResDTO.Detail findDetailById(Long auctionId) {
        return queryFactory.select(Projections.constructor(AuctionUseCaseResDTO.Detail.class,
                        auction.id,
                        auction.title,
                        auction.content,
                        auction.startPrice,
                        auction.currentPrice,
                        auction.sellStartTime,
                        auction.sellEndTime,
                        auction.auctionOpenTime,
                        auction.auctionCloseTime,
                        auction.progress,
                        auction.type
                ))
                .from(auction)
                .leftJoin(auctionLocation).on(auction.id.eq(auctionLocation.auctionId))
                .leftJoin(location).on(auctionLocation.locationId.eq(location.id))
                .where(
                        auction.status.eq(TicTocStatus.ACTIVE),
                        auction.id.eq(auctionId)
                )
                .fetchOne();
    }

    @Override
    public PageCustom<AuctionUseCaseResDTO.Auction> findMyAuctionsWithPageable(final Long userId, Pageable pageable) {
        var results = queryMyAuctions(userId, pageable);
        long total = getTotalCount(auction.status.eq(TicTocStatus.ACTIVE).and(auction.auctioneerId.eq(userId)));
        int totalPages = calculateTotalPages(total, pageable.getPageSize());
        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    private List<AuctionUseCaseResDTO.Auction> queryMyAuctions(final Long userId, Pageable pageable) {
        return queryFactory.select(Projections.constructor(AuctionUseCaseResDTO.Auction.class,
                        auction.id,
                        auction.title,
                        auction.startPrice,
                        auction.currentPrice,
                        auction.sellStartTime,
                        auction.sellEndTime,
                        auction.auctionOpenTime,
                        auction.auctionCloseTime,
                        auction.progress,
                        auction.type
                ))
                .from(auction)
                .leftJoin(auctionLocation).on(auction.id.eq(auctionLocation.auctionId))
                .leftJoin(location).on(auctionLocation.locationId.eq(location.id))
                .where(
                        auction.status.eq(TicTocStatus.ACTIVE),
                        auction.auctioneerId.eq(userId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private List<AuctionUseCaseResDTO.Auction> queryFilteredAuctions(AuctionUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return queryFactory.select(Projections.constructor(AuctionUseCaseResDTO.Auction.class,
                        auction.id,
                        auction.title,
                        auction.startPrice,
                        auction.currentPrice,
                        auction.sellStartTime,
                        auction.sellEndTime,
                        auction.auctionOpenTime,
                        auction.auctionCloseTime,
                        auction.progress,
                        auction.type
                ))
                .from(auction)
                .leftJoin(auctionLocation).on(auction.id.eq(auctionLocation.auctionId))
                .leftJoin(location).on(auctionLocation.locationId.eq(location.id))
                .where(
                        auction.status.eq(TicTocStatus.ACTIVE),
                        filterPrice(requestDTO.startPrice(), requestDTO.endPrice()),
                        filterSellTime(requestDTO.sellStartTime(), requestDTO.sellEndTime()),
                        filterProgress(requestDTO.progress()),
                        filterType(requestDTO.type()),
                        filterLocations(requestDTO.locations())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression filterPrice(Integer startPrice, Integer endPrice) {
        return startPrice != null && endPrice != null
                ? auction.currentPrice.between(startPrice, endPrice)
                : null;
    }

    private BooleanExpression filterSellTime(LocalDateTime sellStartTime, LocalDateTime sellEndTime) {
        if (sellStartTime != null && sellEndTime != null) {
            return auction.sellStartTime.goe(sellStartTime).and(auction.sellEndTime.loe(sellEndTime));
        } else if (sellStartTime != null) {
            return auction.sellStartTime.goe(sellStartTime);
        } else if (sellEndTime != null) {
            return auction.sellEndTime.loe(sellEndTime);
        }
        return null;
    }

    private BooleanExpression filterProgress(AuctionProgress progress) {
        return progress != null
                ? auction.progress.eq(progress)
                : null;
    }

    private BooleanExpression filterType(AuctionType type) {
        return type != null
                ? auction.type.eq(type)
                : null;
    }

    private BooleanExpression filterLocations(List<AuctionUseCaseReqDTO.Location> locations) {
        if (locations != null && !locations.isEmpty()) {
            BooleanExpression condition = null;
            for (AuctionUseCaseReqDTO.Location loc : locations) {
                BooleanExpression regionCondition = loc.region() != null ? location.region.eq(loc.region()) : null;
                BooleanExpression cityCondition = loc.city() != null ? location.city.eq(loc.city()) : null;
                BooleanExpression districtCondition = loc.district() != null ? location.district.eq(loc.district()) : null;
                BooleanExpression subDistrictCondition = loc.subDistrict() != null ? location.subDistrict.eq(loc.subDistrict()) : null;
                BooleanExpression locationCondition = combineConditions(regionCondition, cityCondition, districtCondition, subDistrictCondition);
                condition = condition == null ? locationCondition : condition.or(locationCondition);
            }
            return condition;
        }
        return null;
    }

    private BooleanExpression combineConditions(BooleanExpression... conditions) {
        BooleanExpression result = null;
        for (BooleanExpression condition : conditions) {
            if (condition != null) {
                result = result == null ? condition : result.and(condition);
            }
        }
        return result;
    }

    private int calculateTotalPages(long total, int pageSize) {
        return (int) Math.ceil((double) total / pageSize);
    }

    private long getTotalCount(BooleanExpression condition) {
        return Optional.ofNullable(
                queryFactory.select(auction.count())
                        .from(auction)
                        .where(condition)
                        .fetchOne()
        ).orElse(0L);
    }
}