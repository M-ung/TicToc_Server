package tictoc.auction.service.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.model.location.AuctionLocation;
import tictoc.auction.port.location.LocationCommandUseCase;
import tictoc.auction.port.location.LocationRepositoryPort;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationCommandService implements LocationCommandUseCase {
    private final LocationRepositoryPort locationRepositoryPort;

    @Override
    public void saveAuctionLocations(Long auctionId, List<AuctionUseCaseReqDTO.Location> locations) {
        for (AuctionUseCaseReqDTO.Location location : locations) {
            AuctionLocation auctionLocation = AuctionLocation.of(auctionId, locationRepositoryPort.findLocationIdByFilterOrThrow(location));
            locationRepositoryPort.saveAuctionLocation(auctionLocation);
        }
    }

    @Override
    public void deleteAuctionLocations(Long auctionId) {
        locationRepositoryPort.deleteAuctionLocation(locationRepositoryPort.findAuctionLocationByIdOrThrow(auctionId));
    }
}