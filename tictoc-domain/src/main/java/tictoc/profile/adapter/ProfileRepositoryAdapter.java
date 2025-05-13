package tictoc.profile.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.exception.CloseAuctionException;
import tictoc.profile.model.Profile;
import tictoc.profile.port.ProfileRepositoryPort;
import tictoc.profile.repository.ProfileRepository;
import static tictoc.error.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class ProfileRepositoryAdapter implements ProfileRepositoryPort {
    private final ProfileRepository profileRepository;

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public boolean checkMoney(Long userId, Integer price) {
        return profileRepository.existsByUserIdAndMoneyGreaterThanEqual(userId, price);
    }

    @Override
    @Transactional
    public void subtractMoney(Long userId, Integer price) {
        int updatedCount = profileRepository.subtractMoney(userId, price);
        if (updatedCount == 0) {
            throw new CloseAuctionException(CLOSE_AUCTION_ERROR_FROM_BIDDER);
        }
    }

    @Override
    @Transactional
    public void addMoney(Long userId, Integer price) {
        int updatedCount = profileRepository.addMoney(userId, price);
        if (updatedCount == 0) {
            throw new CloseAuctionException(CLOSE_AUCTION_ERROR_FROM_AUCTIONEER);
        }
    }
}