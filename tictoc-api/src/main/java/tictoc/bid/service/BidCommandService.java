package tictoc.bid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.port.out.AuctionRepositoryPort;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.model.Bid;
import tictoc.bid.port.in.BidCommandUseCase;
import tictoc.bid.port.out.BidRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class BidCommandService implements BidCommandUseCase {
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final BidRepositoryPort bidRepositoryPort;

    @Override
    public void bid(final Long userId, BidUseCaseReqDTO.Bid requestDTO) {
        var findAuction = auctionRepositoryPort.findAuctionByIdOrThrow(requestDTO.auctionId());
        bidRepositoryPort.checkBeforeBid(findAuction);
        findAuction.startAuction(userId);
        bidRepositoryPort.saveBid(Bid.of(userId, requestDTO));
        findAuction.increaseBid(requestDTO.price());
    }
}