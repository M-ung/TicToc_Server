package org.tictoc.tictoc.domain.auction.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.auction.entity.location.Location;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {
}