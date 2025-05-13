package tictoc.auction.adapter.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.exception.location.LocationIdNotFoundException;
import tictoc.auction.port.location.LocationRepositoryPort;
import tictoc.auction.repository.location.LocationRepository;
import static tictoc.error.ErrorCode.LOCATION_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class LocationRepositoryAdapter implements LocationRepositoryPort {
    private final LocationRepository locationRepository;

    @Override
    public Long findLocationIdByFilter(AuctionUseCaseReqDTO.Location location) {
        return locationRepository.findLocationIdByFilter(location).orElseThrow(() -> new LocationIdNotFoundException(LOCATION_NOT_FOUND));
    }
}
