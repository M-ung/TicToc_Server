package tictoc.auction.adapter.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.auction.exception.location.AuctionLocationNotFoundException;
import tictoc.auction.model.location.AuctionLocation;
import tictoc.auction.port.location.AuctionLocationRepositoryPort;
import tictoc.auction.repository.location.AuctionLocationRepository;
import static tictoc.error.ErrorCode.AUCTION_LOCATION_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class AuctionLocationRepositoryAdapter implements AuctionLocationRepositoryPort {
    private final AuctionLocationRepository auctionLocationRepository;

    @Override
    public void save(AuctionLocation auctionLocation) {
        auctionLocationRepository.save(auctionLocation);
    }

    @Override
    public AuctionLocation findAuctionLocationById(Long auctionId) {
        return auctionLocationRepository.findByAuctionId(auctionId).orElseThrow(() -> new AuctionLocationNotFoundException(AUCTION_LOCATION_NOT_FOUND));
    }

    @Override
    public void delete(AuctionLocation auctionLocation) {
        auctionLocationRepository.delete(auctionLocation);
    }
}
