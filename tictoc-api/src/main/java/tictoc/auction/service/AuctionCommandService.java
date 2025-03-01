package tictoc.auction.service;

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
import tictoc.redis.auction.port.out.AuctionRedisPort;
import static tictoc.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionCommandService implements AuctionCommandUseCase {
    private final LocationCommandUseCase locationCommandUseCase;
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final AuctionRedisPort auctionRedisPort;

    @Override
    public void register(final Long userId, AuctionUseCaseReqDTO.Register requestDTO) {
        auctionRepositoryPort.validateAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        var auction = auctionRepositoryPort.saveAuction(Auction.of(userId, requestDTO));
        var auctionId = auction.getId();
        if (!requestDTO.type().equals(AuctionType.ONLINE)) {
            locationCommandUseCase.saveAuctionLocations(auctionId, requestDTO.locations());
        }
        auctionRedisPort.save(auctionId, auction.getAuctionCloseTime());
    }

    @Override
    public void update(final Long userId, final Long auctionId, AuctionUseCaseReqDTO.Update requestDTO) {
        var findAuction = auctionRepositoryPort.findAuctionByIdForUpdateOrThrow(auctionId);
        findAuction.validateAuctionAccess(userId);
        auctionRepositoryPort.validateAuctionTimeRange(userId, requestDTO.sellStartTime(), requestDTO.sellEndTime());
        try {
            findAuction.update(requestDTO);
            locationCommandUseCase.deleteAuctionLocations(auctionId);
            if (!requestDTO.type().equals(AuctionType.ONLINE)) {
                locationCommandUseCase.saveAuctionLocations(auctionId, requestDTO.locations());
            }
            auctionRedisPort.delete(auctionId);
            auctionRedisPort.save(auctionId, findAuction.getAuctionCloseTime());
        } catch (OptimisticLockingFailureException e) {
            throw new ConflictAuctionUpdateException(CONFLICT_AUCTION_UPDATE);
        }
    }

    @Override
    public void delete(final Long userId, final Long auctionId) {
        var findAuction = auctionRepositoryPort.findAuctionByIdForUpdateOrThrow(auctionId);
        try {
            findAuction.deactivate(userId);
            auctionRedisPort.delete(auctionId);
        } catch (OptimisticLockingFailureException e) {
            throw new ConflictAuctionDeleteException(CONFLICT_AUCTION_DELETE);
        }
    }
}