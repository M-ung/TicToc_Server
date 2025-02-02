package tictoc.bid.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.port.out.AuctionAdaptor;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.model.Bid;
import tictoc.bid.port.in.BidCommandUseCase;
import tictoc.bid.port.out.BidAdaptor;

@Service
@Transactional
@RequiredArgsConstructor
public class BidCommandUseCaseImpl implements BidCommandUseCase {
    private final AuctionAdaptor auctionAdaptor;
    private final BidAdaptor bidAdaptor;

    @Override
    public void bid(final Long userId, BidUseCaseReqDTO.Bid requestDTO) {
        var findAuction = auctionAdaptor.findAuctionByIdOrThrow(requestDTO.auctionId());
        bidAdaptor.checkBeforeBid(findAuction);
        findAuction.startAuction(userId);
        bidAdaptor.save(Bid.of(userId, requestDTO));
        findAuction.increaseBid(requestDTO.price());
    }
}