package tictoc.auction.port.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.exception.location.AuctionLocationNotFoundException;
import tictoc.auction.exception.location.LocationIdNotFoundException;
import tictoc.auction.model.location.AuctionLocation;
import tictoc.auction.location.AuctionLocationRepository;
import tictoc.auction.location.LocationRepository;
import static tictoc.error.ErrorCode.AUCTION_LOCATION_NOT_FOUND;
import static tictoc.error.ErrorCode.LOCATION_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class LocationAdaptor {
    private final LocationRepository locationRepository;
    private final AuctionLocationRepository auctionLocationRepository;

    public void saveAuctionLocationEntity(AuctionLocation auctionLocation) {
        auctionLocationRepository.save(auctionLocation);
    }

    public Long findLocationIdByFilterOrThrow(AuctionUseCaseReqDTO.Location location) {
        return locationRepository.findLocationIdByFilter(location).orElseThrow(() -> new LocationIdNotFoundException(LOCATION_NOT_FOUND));
    }

    public AuctionLocation findAuctionLocationByIdOrThrow(Long auctionId) {
        return auctionLocationRepository.findByAuctionId(auctionId).orElseThrow(() -> new AuctionLocationNotFoundException(AUCTION_LOCATION_NOT_FOUND));
    }

    public void deleteAuctionLocation(AuctionLocation auctionLocation) {
        auctionLocationRepository.delete(auctionLocation);
    }
}
