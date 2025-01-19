package org.tictoc.tictoc.domain.auction.repository.location;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import java.util.Optional;

import static org.tictoc.tictoc.domain.auction.entity.location.QLocation.location;

public class LocationRepositoryImpl implements LocationRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public LocationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Long> findLocationIdByFilter(AuctionRequestDTO.Location requestDTO) {
        return Optional.ofNullable(
                queryFactory.select(location.id)
                        .from(location)
                        .where(
                                filterRegion(requestDTO.region()),
                                filterCity(requestDTO.city()),
                                filterDistrict(requestDTO.district()),
                                filterSubDistrict(requestDTO.subDistrict())
                        )
                        .fetchOne()
        );
    }

    private BooleanExpression filterRegion(String region) {
        return region != null ? location.region.eq(region) : null;
    }

    private BooleanExpression filterCity(String city) {
        return city != null ? location.city.eq(city) : null;
    }

    private BooleanExpression filterDistrict(String district) {
        return district != null ? location.district.eq(district) : null;
    }

    private BooleanExpression filterSubDistrict(String subDistrict) {
        return subDistrict != null ? location.subDistrict.eq(subDistrict) : null;
    }
}