package tictoc.auction.application;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.model.Auction;
import tictoc.auction.port.AuctionCommandUseCase;
import tictoc.auction.model.type.AuctionType;
import tictoc.auction.exception.ConflictAuctionDeleteException;
import tictoc.auction.exception.ConflictAuctionUpdateException;
import tictoc.auction.port.location.LocationCommandUseCase;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.error.ErrorCode;
import tictoc.redis.auction.port.out.CloseAuctionUseCase;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionCommandService implements AuctionCommandUseCase {
    private final LocationCommandUseCase locationCommandUseCase;
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final CloseAuctionUseCase closeAuctionUseCase;

    @Override
    public void register(final Long userId, AuctionUseCaseReqDTO.Register requestDTO) {
        auctionRepositoryPort.validateAuctionTimeRangeForSave(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        var auction = auctionRepositoryPort.saveAuction(Auction.of(userId, requestDTO));
        var auctionId = auction.getId();
        if (!requestDTO.type().equals(AuctionType.ONLINE)) {
            locationCommandUseCase.saveAuctionLocations(auctionId, requestDTO.locations());
        }
        closeAuctionUseCase.save(auctionId, auction.getAuctionCloseTime());
    }

    @Override
    public void update(final Long userId, final Long auctionId, AuctionUseCaseReqDTO.Update requestDTO) {
        try {
            var findAuction = auctionRepositoryPort.findAuctionByIdForUpdate(auctionId);
            findAuction.validateAuctionAccess(userId);
            auctionRepositoryPort.validateAuctionTimeRangeForUpdate(userId, auctionId, findAuction.getSellStartTime(), findAuction.getSellEndTime());
            findAuction.update(requestDTO);
            if(!findAuction.getType().equals(AuctionType.ONLINE)) {
                locationCommandUseCase.deleteAuctionLocations(auctionId);
            }
            if (!requestDTO.type().equals(AuctionType.ONLINE)) {
                locationCommandUseCase.saveAuctionLocations(auctionId, requestDTO.locations());
            }
            closeAuctionUseCase.delete(auctionId);
            closeAuctionUseCase.save(auctionId, findAuction.getAuctionCloseTime());
        } catch (OptimisticLockingFailureException e) {
            throw new ConflictAuctionUpdateException(ErrorCode.CONFLICT_AUCTION_UPDATE);
        }
    }

    @Override
    public void delete(final Long userId, final Long auctionId) {
        try {
            var findAuction = auctionRepositoryPort.findAuctionByIdForUpdate(auctionId);
            findAuction.deactivate(userId);
            closeAuctionUseCase.delete(auctionId);
        } catch (OptimisticLockingFailureException e) {
            throw new ConflictAuctionUpdateException(ErrorCode.CONFLICT_AUCTION_UPDATE);
        }
    }
}