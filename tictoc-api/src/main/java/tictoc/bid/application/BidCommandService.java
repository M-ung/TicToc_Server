package tictoc.bid.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.annotation.DistributedLock;
import tictoc.auction.model.Auction;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.constants.RedisConstants;
import tictoc.bid.exception.BidException;
import tictoc.bid.model.Bid;
import tictoc.bid.port.BidCommandUseCase;
import tictoc.bid.port.BidRepositoryPort;
import tictoc.profile.port.ProfileRepositoryPort;
import static tictoc.error.ErrorCode.BID_FAIL;
import static tictoc.error.ErrorCode.INVALID_PROFILE_MONEY;

@Service
@Transactional
@RequiredArgsConstructor
public class BidCommandService implements BidCommandUseCase {
    private final ProfileRepositoryPort profileRepositoryPort;
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final BidRepositoryPort bidRepositoryPort;

    @Override
    @DistributedLock(key = "#requestDTO.auctionId", delayTime = RedisConstants.LOCK_LEASE_TIME)
    public void bid(final Long userId, BidUseCaseReqDTO.Bid requestDTO) {
        var findAuction = auctionRepositoryPort.findAuctionById(requestDTO.auctionId());
        validateBid(userId, requestDTO.price(), findAuction);
        executeAtomicBidUpdate(requestDTO);
        bidRepositoryPort.save(Bid.of(userId, requestDTO, findAuction.getCurrentPrice()));
    }

    private void validateBid(Long userId, Integer price, Auction auction) {
        if (!profileRepositoryPort.checkMoney(userId, price)) {
            throw new BidException(INVALID_PROFILE_MONEY);
        }
        bidRepositoryPort.checkBeforeBid(auction);
        auction.validateBeforeBid(userId);
    }

    private void executeAtomicBidUpdate(BidUseCaseReqDTO.Bid requestDTO) {
        int updatedRows = auctionRepositoryPort.updateBidIfHigher(requestDTO);
        if (updatedRows == 0) {
            throw new BidException(BID_FAIL);
        }
    }
}