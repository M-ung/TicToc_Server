package org.tictoc.tictoc.domain.auction.service.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.location.AuctionLocation;
import org.tictoc.tictoc.domain.auction.repository.location.AuctionLocationRepository;
import org.tictoc.tictoc.domain.auction.repository.location.LocationRepository;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationCommandServiceImpl implements LocationCommandService {
    private final LocationRepository locationRepository;
    private final AuctionLocationRepository auctionLocationRepository;

    @Override
    public void saveAuctionLocations(Long auctionId, List<AuctionRequestDTO.Location> locations) {
        for (AuctionRequestDTO.Location location : locations) {
            auctionLocationRepository.save(AuctionLocation.of(auctionId, locationRepository.findLocationIdByFilterOrThrow(location)));
        }
    }

    @Override
    public void deleteAuctionLocations(Long auctionId) {
        auctionLocationRepository.delete(auctionLocationRepository.findByAuctionIdOrThrow(auctionId));
    }
}