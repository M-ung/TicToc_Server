package tictoc.auction.port;

import org.springframework.data.domain.Pageable;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.dto.response.AuctionUseCaseResDTO;
import tictoc.auction.model.Auction;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.model.page.PageCustom;
import java.time.LocalDateTime;

public interface AuctionRepositoryPort {
    Auction saveAuction(Auction auction);
    Auction findAuctionByIdForUpdate(Long auctionId);
    Auction findAuctionById(Long auctionId);
    void validateAuctionTimeRangeForSave(Long userId, LocalDateTime sellStartTime, LocalDateTime sellEndTime);
    void validateAuctionTimeRangeForUpdate(Long userId, Long auctionId, LocalDateTime sellStartTime, LocalDateTime sellEndTime);
    PageCustom<AuctionUseCaseResDTO.Auction> findAuctionsByFilterWithPageable(AuctionUseCaseReqDTO.Filter requestDTO, Pageable pageable);
    AuctionUseCaseResDTO.Detail findDetailById(Long auctionId);
    PageCustom<AuctionUseCaseResDTO.Auction> findMyAuctionsWithPageable(final Long userId, Pageable pageable);
    int updateBidIfHigher(BidUseCaseReqDTO.Bid requestDTO);
}