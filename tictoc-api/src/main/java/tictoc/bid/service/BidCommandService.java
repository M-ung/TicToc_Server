package tictoc.bid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.exception.BidException;
import tictoc.bid.model.Bid;
import tictoc.bid.port.BidCommandUseCase;
import tictoc.bid.port.BidRepositoryPort;

import static tictoc.error.ErrorCode.BID_FAIL;

@Service
@Transactional
@RequiredArgsConstructor
public class BidCommandService implements BidCommandUseCase {
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final BidRepositoryPort bidRepositoryPort;

    @Override
    public void bid(final Long userId, BidUseCaseReqDTO.Bid requestDTO) {
        /** 동시성 문제를 예방하기 위해 입찰가를 먼저 UPDATE 한다. **/
        if(auctionRepositoryPort.updateBidIfHigher(requestDTO) == 0) {
            throw new BidException(BID_FAIL);
        }
        var findAuction = auctionRepositoryPort.findAuctionByIdOrThrow(requestDTO.auctionId());
        bidRepositoryPort.checkBeforeBid(findAuction);
        findAuction.startAuction(userId);
        bidRepositoryPort.saveBid(Bid.of(userId, requestDTO));
    }
}