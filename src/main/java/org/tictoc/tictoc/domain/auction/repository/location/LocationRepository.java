package org.tictoc.tictoc.domain.auction.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.location.Location;
import org.tictoc.tictoc.domain.auction.exception.location.LocationIdNotFoundException;
import static org.tictoc.tictoc.global.error.ErrorCode.LOCATION_NOT_FOUND;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {
    default Long findLocationIdByFilterOrThrow(AuctionRequestDTO.Location location) {
        return findLocationIdByFilter(location).orElseThrow(() -> new LocationIdNotFoundException(LOCATION_NOT_FOUND));
    }
}