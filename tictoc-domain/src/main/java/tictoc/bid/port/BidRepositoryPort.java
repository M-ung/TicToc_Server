package tictoc.bid.port;

import org.springframework.data.domain.Pageable;
import tictoc.auction.model.Auction;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.bid.model.Bid;
import tictoc.model.page.PageCustom;

public interface BidRepositoryPort {
    PageCustom<BidUseCaseResDTO.Bid> findBidsByFilterWithPageable(Long userId, BidUseCaseReqDTO.Filter requestDTO, Pageable pageable);
    void checkBeforeBid(Auction auction);
    void save(Bid bid);
    Bid findBidByAuctionId(Long auctionId);
}