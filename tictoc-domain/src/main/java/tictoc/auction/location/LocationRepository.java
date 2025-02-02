package tictoc.auction.location;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.auction.model.location.Location;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {
}