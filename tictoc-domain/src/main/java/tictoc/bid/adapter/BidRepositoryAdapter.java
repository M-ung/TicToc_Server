package tictoc.bid.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import tictoc.annotation.Adapter;
import tictoc.auction.model.Auction;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.bid.exception.BidNotFoundException;
import tictoc.bid.model.Bid;
import tictoc.bid.model.type.BidProgress;
import tictoc.bid.port.BidRepositoryPort;
import tictoc.bid.repository.BidRepository;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.model.page.PageCustom;
import static tictoc.error.ErrorCode.BID_NOT_FOUND;

@Adapter
@RequiredArgsConstructor
public class BidRepositoryAdapter implements BidRepositoryPort {
    private final BidRepository bidRepository;

    @Override
    public PageCustom<BidUseCaseResDTO.Bid> findBidsByFilterWithPageable(Long userId, BidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return bidRepository.findBidsByFilterWithPageable(userId, requestDTO, pageable);
    }

    @Override
    public void checkBeforeBid(Auction auction) {
        if (auction.getProgress().equals(AuctionProgress.IN_PROGRESS)) {
            findBidByAuctionId(auction.getId()).fail();
        }
    }

    @Override
    public void save(Bid bid) {
        bidRepository.save(bid);
    }

    @Override
    public Bid findBidByAuctionId(Long auctionId) {
        return bidRepository.findByAuctionIdAndProgress(auctionId, BidProgress.PROGRESS).orElseThrow(() -> new BidNotFoundException(BID_NOT_FOUND));
    }
}