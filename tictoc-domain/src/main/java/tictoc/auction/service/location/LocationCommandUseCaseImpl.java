package tictoc.auction.service.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.model.location.AuctionLocation;
import tictoc.auction.port.in.location.LocationCommandUseCase;
import tictoc.auction.port.out.LocationAdaptor;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationCommandUseCaseImpl implements LocationCommandUseCase {
    private final LocationAdaptor locationAdaptor;

    @Override
    public void saveAuctionLocations(Long auctionId, List<AuctionUseCaseReqDTO.Location> locations) {
        for (AuctionUseCaseReqDTO.Location location : locations) {
            AuctionLocation auctionLocation = AuctionLocation.of(auctionId, locationAdaptor.findLocationIdByFilterOrThrow(location));
            locationAdaptor.saveAuctionLocationEntity(auctionLocation);
        }
    }

    @Override
    public void deleteAuctionLocations(Long auctionId) {
        locationAdaptor.deleteAuctionLocation(locationAdaptor.findAuctionLocationByIdOrThrow(auctionId));
    }
}