package org.tictoc.tictoc.domain.auction.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.entity.Auction;
import org.tictoc.tictoc.domain.auction.entity.Zone;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.user.entity.User;
import org.tictoc.tictoc.domain.user.entity.type.UserRole;
import org.tictoc.tictoc.domain.user.repository.UserRepository;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuctionRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @BeforeEach
    @DisplayName("[setup] User")
    void 테스트_전_사용자_세팅() {
        for (int i = 0; i < 3; i++) {
            User user = User.builder()
                    .id((long) i)
                    .kakaoId("kakao" + i)
                    .name("name" + i)
                    .role(UserRole.USER)
                    .status(TicTocStatus.ACTIVE)
                    .build();
            userRepository.save(user);
        }
    }

    @BeforeEach
    @DisplayName("[setup] Auction")
    void 테스트_전_경매_세팅() {
        for (int i = 1; i <= 3; i++) {
            Auction auction = Auction.builder()
                    .id((long) i)
                    .auctioneerId((long) i)
                    .title("title" + i)
                    .content("content" + i)
                    .startPrice(1000)
                    .currentPrice(1000)
                    .finalPrice(1000)
                    .sellStartTime(LocalDateTime.now().plusHours(1))
                    .sellEndTime(LocalDateTime.now().plusHours(2))
                    .auctionOpenTime(LocalDateTime.of(2024, 12, 15, 12, 0, 0))
                    .auctionCloseTime(LocalDateTime.of(2024, 12, 15, 20, 0, 0))
                    .zones(List.of(Zone.builder().city("City").localNameOfCity("Local").province("Province").build()))
                    .progress(AuctionProgress.NOT_PROGRESS)
                    .type(AuctionType.OFFLINE)
                    .status(TicTocStatus.ACTIVE)
                    .version(0)
                    .build();

            auctionRepository.save(auction);
        }
    }

    @Test
    @DisplayName("해당_시간_안에_경매가_존재하는지_확인하는_테스트")
    void 해당_시간_안에_경매가_존재하는지_확인하는_테스트() {
        // given
        Long userId = 1L;
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);  // 경매 시작 시간
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);    // 경매 종료 시간

        // when
        boolean exists = auctionRepository.existsAuctionInTimeRange(userId, startTime, endTime);

        // then
        assertTrue(exists);
    }

    @Test
    @DisplayName("경매_종료_시간_전에_존재하는_경매_확인_테스트")
    void 경매_종료_시간_전에_존재하는_경매_확인_테스트() {
        // given
        LocalDateTime checkTime = LocalDateTime.of(2024, 12, 15, 20, 0, 0);

        // when
        List<Auction> auctions = auctionRepository.findByProgressNotAndAuctionCloseTime(AuctionProgress.FINISHED, checkTime);

        // then
        assertEquals(3, auctions.size());
    }
}