package org.tictoc.tictoc.domain.auction.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.Auction;
import org.tictoc.tictoc.domain.auction.entity.location.AuctionLocation;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.auction.exception.*;
import org.tictoc.tictoc.domain.auction.repository.history.AuctionHistoryRepository;
import org.tictoc.tictoc.domain.auction.repository.AuctionRepository;
import org.tictoc.tictoc.domain.auction.repository.location.AuctionLocationRepository;
import org.tictoc.tictoc.domain.auction.repository.location.LocationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.tictoc.tictoc.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionCommandServiceImpl implements AuctionCommandService {
    private final AuctionRepository auctionRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;
    private final AuctionLocationRepository auctionLocationRepository;
    private final LocationRepository locationRepository;

    @Override
    public void register(final Long userId, AuctionRequestDTO.Register requestDTO) {
        checkAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        Auction auction = auctionRepository.save(Auction.of(userId, requestDTO));
        Long auctionId = auction.getId();
        if (!requestDTO.type().equals(AuctionType.ONLINE)) {
            saveAuctionLocations(auctionId, requestDTO.locations());
        }
    }

    @Override
    public void update(final Long userId, final Long auctionId, AuctionRequestDTO.Update requestDTO) {
        validateAuctionAccess(userId, auctionId);
        checkAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        Auction findAuction = findAuctionById(auctionId);
        try {
            findAuction.update(requestDTO);
            if(!requestDTO.type().equals(AuctionType.ONLINE)) {
                //TODO 위치 저장이 아닌 수정을 해야 함
                saveAuctionLocations(auctionId, requestDTO.locations());
            }
        } catch (OptimisticLockingFailureException e) {
            throw new ConflictAuctionUpdateException(CONFLICT_AUCTION_UPDATE);
        }
    }

    @Override
    public void delete(final Long userId, final Long auctionId) {
        validateAuctionAccess(userId, auctionId);
        try {
            findAuctionById(auctionId).deactivate();
        } catch (OptimisticLockingFailureException e) {
            throw new ConflictAuctionDeleteException(CONFLICT_AUCTION_DELETE);
        }
    }

    private void saveAuctionLocations(Long auctionId, List<AuctionRequestDTO.Location> locations) {
        for (AuctionRequestDTO.Location location : locations) {
            Optional<Long> optionalLocationId = locationRepository.findLocationIdByFilter(location);
            if (optionalLocationId.isPresent()) {
                Long locationId = optionalLocationId.get();
                auctionLocationRepository.save(AuctionLocation.of(auctionId, locationId));
            }
        }
    }

    private Auction findAuctionById(final Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(AUCTION_NOT_FOUND));
    }

    private void validateAuctionAccess(final Long userId, final Long auctioneerId) {
        if(!userId.equals(auctioneerId)) {
            throw new AuctionNoAccessException(AUCTION_NO_ACCESS);
        }
    }

    private void checkAuctionTimeRange(Long userId, LocalDateTime sellStartTime, LocalDateTime sellEndTime) {
        if(auctionRepository.existsAuctionInTimeRange(userId, sellStartTime, sellEndTime)) {
            throw new DuplicateAuctionDateException(DUPLICATE_AUCTION_DATE);
        }
    }
}
