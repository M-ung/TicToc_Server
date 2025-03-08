package tictoc.auction.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.dto.response.AuctionUseCaseResDTO;
import tictoc.auction.exception.AuctionNotFoundException;
import tictoc.auction.exception.DuplicateAuctionDateException;
import tictoc.auction.model.Auction;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.auction.repository.AuctionRepository;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.model.page.PageCustom;
import tictoc.model.tictoc.TicTocStatus;
import java.time.LocalDateTime;
import static tictoc.error.ErrorCode.AUCTION_NOT_FOUND;
import static tictoc.error.ErrorCode.DUPLICATE_AUCTION_DATE;

@Component
@RequiredArgsConstructor
public class AuctionRepositoryAdapter implements AuctionRepositoryPort {
    private final AuctionRepository auctionRepository;

    @Override
    public Auction saveAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    @Override
    public Auction findAuctionByIdForUpdate(Long auctionId) {
        return auctionRepository.findByIdAndStatusForUpdate(auctionId, TicTocStatus.ACTIVE).orElseThrow(() -> new AuctionNotFoundException(AUCTION_NOT_FOUND));
    }

    @Override
    public Auction findAuctionById(Long auctionId) {
        return auctionRepository.findByIdAndStatus(auctionId, TicTocStatus.ACTIVE).orElseThrow(() -> new AuctionNotFoundException(AUCTION_NOT_FOUND));
    }

    @Override
    public void validateAuctionTimeRangeForSave(Long userId, LocalDateTime sellStartTime, LocalDateTime sellEndTime) {
        if (auctionRepository.existsAuctionInTimeRangeForSave(userId, sellStartTime, sellEndTime, TicTocStatus.ACTIVE)) {
            throw new DuplicateAuctionDateException(DUPLICATE_AUCTION_DATE);
        }
    }

    @Override
    public void  validateAuctionTimeRangeForUpdate(Long userId, Long auctionId, LocalDateTime sellStartTime, LocalDateTime sellEndTime) {
        if (auctionRepository.existsAuctionInTimeRangeForUpdate(userId, auctionId, sellStartTime, sellEndTime, TicTocStatus.ACTIVE)) {
            throw new DuplicateAuctionDateException(DUPLICATE_AUCTION_DATE);
        }
    }

    @Override
    public PageCustom<AuctionUseCaseResDTO.Auction> findAuctionsByFilterWithPageable(AuctionUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return auctionRepository.findAuctionsByFilterWithPageable(requestDTO, pageable);
    }

    @Override
    public AuctionUseCaseResDTO.Detail findDetailById(Long auctionId) {
        return auctionRepository.findDetailById(auctionId);
    }

    @Override
    public PageCustom<AuctionUseCaseResDTO.Auction> findMyAuctionsWithPageable(final Long userId, Pageable pageable) {
        return auctionRepository.findMyAuctionsWithPageable(userId, pageable);
    }

    @Override
    public int updateBidIfHigher(BidUseCaseReqDTO.Bid requestDTO) {
        return auctionRepository.updateBidIfHigher(requestDTO.auctionId(), requestDTO.price());
    }

    @Override
    public int findCurrentIdById(Long auctionId) {
        return auctionRepository.findCurrentPriceById(auctionId);
    }
}