package tictoc.auction.adaptor.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.exception.location.AuctionLocationNotFoundException;
import tictoc.auction.exception.location.LocationIdNotFoundException;
import tictoc.auction.model.location.AuctionLocation;
import tictoc.auction.port.location.LocationRepositoryPort;
import tictoc.auction.repository.location.AuctionLocationRepository;
import tictoc.auction.repository.location.LocationRepository;
import static tictoc.error.ErrorCode.AUCTION_LOCATION_NOT_FOUND;
import static tictoc.error.ErrorCode.LOCATION_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class LocationRepositoryAdaptor implements LocationRepositoryPort {
    private final LocationRepository locationRepository;
    private final AuctionLocationRepository auctionLocationRepository;

    @Override
    public void saveAuctionLocation(AuctionLocation auctionLocation) {
        auctionLocationRepository.save(auctionLocation);
    }

    @Override
    public AuctionLocation findAuctionLocationById(Long auctionId) {
        return auctionLocationRepository.findByAuctionId(auctionId).orElseThrow(() -> new AuctionLocationNotFoundException(AUCTION_LOCATION_NOT_FOUND));
    }

    @Override
    public void deleteAuctionLocation(AuctionLocation auctionLocation) {
        auctionLocationRepository.delete(auctionLocation);
    }

    @Override
    public Long findLocationIdByFilter(AuctionUseCaseReqDTO.Location location) {
        return locationRepository.findLocationIdByFilter(location).orElseThrow(() -> new LocationIdNotFoundException(LOCATION_NOT_FOUND));
    }
}
