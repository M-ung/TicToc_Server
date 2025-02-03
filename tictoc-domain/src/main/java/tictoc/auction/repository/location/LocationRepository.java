package tictoc.auction.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.auction.model.location.Location;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {
}