package org.tictoc.tictoc.domain.auction.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.Auction;
import org.tictoc.tictoc.domain.auction.repository.AuctionHistoryRepository;
import org.tictoc.tictoc.domain.auction.repository.AuctionRepository;
import org.tictoc.tictoc.global.exception.auction.DuplicateAuctionDateException;

import static org.tictoc.tictoc.global.exception.ErrorCode.DUPLICATE_AUCTION_DATE;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionCommandServiceImpl implements AuctionCommandService {
    private final AuctionRepository auctionRepository;
    private final AuctionHistoryRepository auctionHistoryRepository;

    @Override
    public void register(final Long userId, AuctionRequestDTO.Register requestDTO) {
        if(auctionRepository.existAuctionInTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime())) {
            throw new DuplicateAuctionDateException(DUPLICATE_AUCTION_DATE);
        }
        auctionRepository.save(Auction.of(userId, requestDTO));
    }

    @Override
    public void update(final Long userId, AuctionRequestDTO.Update requestDTO) {

    }

    @Override
    public void delete(final Long userId, final Long auctionId) {

    }
}
