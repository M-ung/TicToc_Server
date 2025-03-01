package tictoc.bid.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import tictoc.auction.model.Auction;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.dto.request.WinningBidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.bid.exception.BidNotFoundException;
import tictoc.bid.model.Bid;
import tictoc.bid.model.type.BidStatus;
import tictoc.bid.port.BidRepositoryPort;
import tictoc.bid.repository.BidRepository;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.bid.repository.WinningBidRepository;
import tictoc.model.page.PageCustom;
import static tictoc.error.ErrorCode.BID_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class BidRepositoryAdaptor implements BidRepositoryPort {
    private final BidRepository bidRepository;
    private final WinningBidRepository winningBidRepository;

    @Override
    public PageCustom<BidUseCaseResDTO.Bid> findBidsByFilterWithPageable(Long userId, BidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return bidRepository.findBidsByFilterWithPageable(userId, requestDTO, pageable);
    }

    @Override
    public void checkBeforeBid(Auction auction) {
        if (auction.getProgress().equals(AuctionProgress.IN_PROGRESS)) {
            bidRepository.findByAuctionIdAndStatus(auction.getId(), BidStatus.PROGRESS).orElseThrow(() -> new BidNotFoundException(BID_NOT_FOUND)).fail();
        }
    }

    @Override
    public void saveBid(Bid bid) {
        bidRepository.save(bid);
    }

    @Override
    public PageCustom<BidUseCaseResDTO.WinningBid> getWinningBidsByFilterWithPageable(WinningBidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return winningBidRepository.findWinningBidsByFilterWithPageable(requestDTO, pageable);
    }
}