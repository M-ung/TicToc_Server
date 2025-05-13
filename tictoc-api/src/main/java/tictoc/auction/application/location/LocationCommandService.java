package tictoc.auction.application.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.model.location.AuctionLocation;
import tictoc.auction.port.location.AuctionLocationRepositoryPort;
import tictoc.auction.port.location.LocationCommandUseCase;
import tictoc.auction.port.location.LocationRepositoryPort;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationCommandService implements LocationCommandUseCase {
    private final LocationRepositoryPort locationRepositoryPort;
    private final AuctionLocationRepositoryPort auctionLocationRepositoryPort;

    @Override
    public void save(Long auctionId, List<AuctionUseCaseReqDTO.Location> locations) {
        for (AuctionUseCaseReqDTO.Location location : locations) {
            AuctionLocation auctionLocation = AuctionLocation.of(auctionId, locationRepositoryPort.findLocationIdByFilter(location));
            auctionLocationRepositoryPort.save(auctionLocation);
        }
    }

    @Override
    public void delete(Long auctionId) {
        auctionLocationRepositoryPort.delete(auctionLocationRepositoryPort.findAuctionLocationById(auctionId));
    }
}