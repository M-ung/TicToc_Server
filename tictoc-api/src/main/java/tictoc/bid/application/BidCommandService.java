package tictoc.bid.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.annotation.DistributedLock;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.constants.RedisConstants;
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
    @DistributedLock(key = "#requestDTO.auctionId", delayTime = RedisConstants.LOCK_LEASE_TIME)
    public void bid(final Long userId, BidUseCaseReqDTO.Bid requestDTO) {
        var findAuction = auctionRepositoryPort.findAuctionById(requestDTO.auctionId());
        bidRepositoryPort.checkBeforeBid(findAuction);
        findAuction.startBid(userId);
        Integer beforePrice = findAuction.getCurrentPrice();
        executeAtomicBidUpdate(requestDTO);
        bidRepositoryPort.saveBid(Bid.of(userId, requestDTO, beforePrice));
    }

    private void executeAtomicBidUpdate(BidUseCaseReqDTO.Bid requestDTO) {
        int updatedRows = auctionRepositoryPort.updateBidIfHigher(requestDTO);
        if (updatedRows == 0) {
            throw new BidException(BID_FAIL);
        }
    }
}