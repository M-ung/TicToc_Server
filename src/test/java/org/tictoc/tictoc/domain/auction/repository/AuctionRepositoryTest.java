package org.tictoc.tictoc.domain.auction.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.response.AuctionResponseDTO;
import org.tictoc.tictoc.domain.auction.entity.Auction;
import org.tictoc.tictoc.domain.auction.entity.location.Location;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.user.entity.User;
import org.tictoc.tictoc.domain.user.entity.type.UserRole;
import org.tictoc.tictoc.domain.user.repository.UserRepository;
import org.tictoc.tictoc.global.common.entity.PageCustom;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
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
                    .progress(AuctionProgress.NOT_PROGRESS)
                    .type(AuctionType.OFFLINE)
                    .status(TicTocStatus.ACTIVE)
                    .version(0)
                    .build();

            auctionRepository.save(auction);
        }
    }

    @Test
    @DisplayName("해당 시간 안에 경매가 존재하는지 확인하는 테스트")
    void 해당_시간_안에_경매가_존재하는지_확인하는_테스트() throws Exception {
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
    @DisplayName("경매 종료 시간 전에 존재하는 경매 확인 테스트")
    void 경매_종료_시간_전에_존재하는_경매_확인_테스트() throws Exception {
        // given
        LocalDateTime checkTime = LocalDateTime.of(2024, 12, 15, 20, 0, 0);

        // when
        List<Auction> auctions = auctionRepository.findByProgressNotAndAuctionCloseTime(AuctionProgress.FINISHED, checkTime);

        // then
        assertEquals(3, auctions.size());
    }

    @Test
    @DisplayName("필터 조건에 따라 경매 목록을 페이징 조회하는 테스트")
    void 필터_조건에_따라_경매_목록을_페이징_조회하는_테스트() {
        // given
        AuctionRequestDTO.Filter filter = new AuctionRequestDTO.Filter(
                1000, 5000,
                null,
                null,
                null,
                AuctionProgress.NOT_PROGRESS,
                AuctionType.OFFLINE
        );

        Pageable pageable = PageRequest.of(0, 10);

        // when
        PageCustom<AuctionResponseDTO.Auctions> result = auctionRepository.findAuctionsByFilterWithPageable(filter, pageable);

        // then
        assertNotNull(result);
        assertEquals(3, result.getContent().size());
        assertEquals(0, result.getNumber());
        assertEquals(1000, result.getContent().get(0).getCurrentPrice());
    }
}