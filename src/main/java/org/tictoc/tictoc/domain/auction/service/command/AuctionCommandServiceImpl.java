package org.tictoc.tictoc.domain.auction.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.Auction;
import org.tictoc.tictoc.domain.auction.repository.AuctionHistoryRepository;
import org.tictoc.tictoc.domain.auction.repository.AuctionRepository;
import org.tictoc.tictoc.global.exception.auction.AuctionNoAccessException;
import org.tictoc.tictoc.global.exception.auction.AuctionNotFoundException;
import org.tictoc.tictoc.global.exception.auction.ConflictAuctionUpdateException;
import org.tictoc.tictoc.global.exception.auction.DuplicateAuctionDateException;

import static org.tictoc.tictoc.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionCommandServiceImpl implements AuctionCommandService {
    private final AuctionRepository auctionRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;

    @Override
    public void register(final Long userId, AuctionRequestDTO.Register requestDTO) {
        if(auctionRepository.existsAuctionInTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime())) {
            throw new DuplicateAuctionDateException(DUPLICATE_AUCTION_DATE);
        }
        auctionRepository.save(Auction.of(userId, requestDTO));
    }

    @Override
    public void update(final Long userId, final Long auctionId, AuctionRequestDTO.Update requestDTO) {
        validateAuctionAccess(userId, auctionId);
        auctionRepository.existsByAuctioneerIdAndId(userId, auctionId);
        try {
            findAuctionById(auctionId).update(requestDTO);
        } catch (OptimisticLockingFailureException e) {
            throw new ConflictAuctionUpdateException(CONFLICT_AUCTION_UPDATE);
        }
    }

    @Override
    public void delete(final Long userId, final Long auctionId) {

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
}
