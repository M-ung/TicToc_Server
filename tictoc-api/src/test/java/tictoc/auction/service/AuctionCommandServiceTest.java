package tictoc.auction.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import tictoc.TicTocApiApplication;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.exception.ConflictAuctionDeleteException;
import tictoc.auction.exception.ConflictAuctionUpdateException;
import tictoc.auction.model.Auction;
import tictoc.auction.model.type.AuctionType;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.port.BidRepositoryPort;
import tictoc.bid.service.BidCommandService;
import tictoc.error.ErrorCode;
import tictoc.model.tictoc.TicTocStatus;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ContextConfiguration(classes = TicTocApiApplication.class)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
class AuctionCommandServiceTest {
    @Autowired
    private AuctionCommandService auctionCommandService;

    @Autowired
    private BidCommandService bidCommandService;

    @Autowired
    private AuctionRepositoryPort auctionRepositoryPort;

    @Autowired
    private BidRepositoryPort bidRepositoryPort;

    private static final Integer BID_PRICE = 1500;
    private static final int NUM_USERS = 10;
    private Auction auction;

    @BeforeEach
    public void setup() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sellStartTime = now.plusMinutes(10);
        LocalDateTime sellEndTime = now.plusHours(2);
        LocalDateTime auctionCloseTime = now.plusHours(3);

        AuctionUseCaseReqDTO.Register requestDTO = new AuctionUseCaseReqDTO.Register(
                "Test Auction",
                "This is a test auction.",
                1000,
                sellStartTime,
                sellEndTime,
                auctionCloseTime,
                Collections.emptyList(),
                AuctionType.ONLINE
        );
        auction = auctionRepositoryPort.saveAuction(Auction.of(1L, requestDTO));
    }

    @Test
    @DisplayName("경매 수정 시 동시성 이슈 발생에 대한 테스트")
    public void 경매_수정_시_동시성_이슈_발생에_대한_테스트() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            try {
                AuctionUseCaseReqDTO.Update updateDTO = new AuctionUseCaseReqDTO.Update(
                        "Updated Auction Title",
                        "Updated Auction Description",
                        1200,
                        LocalDateTime.now().plusMinutes(15),
                        LocalDateTime.now().plusHours(1),
                        LocalDateTime.now().plusHours(2),
                        Collections.emptyList(),
                        AuctionType.ONLINE
                );
                auctionCommandService.update(1L, auction.getId(), updateDTO);
            } catch (Exception e) {
                assertThat(e).isInstanceOf(ConflictAuctionUpdateException.class);
            } finally {
                latch.countDown();
            }
        });

        executorService.submit(() -> {
            try {
                bidCommandService.bid(2L, new BidUseCaseReqDTO.Bid(auction.getId(), BID_PRICE));
            } catch (Exception e) {
                assertThat(e.getMessage()).isEqualTo(ErrorCode.BID_FAIL.getMessage());
            } finally {
                latch.countDown();
            }
        });

        latch.await();

        Auction updatedAuction = auctionRepositoryPort.findAuctionById(auction.getId());
        assertThat(updatedAuction.getTitle()).isEqualTo("Test Auction");
    }

    @Test
    @DisplayName("경매 삭제 시 동시성 이슈 발생에 대한 테스트")
    public void 경매_삭제_시_동시성_이슈_발생에_대한_테스트() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            try {
                auctionCommandService.delete(1L, auction.getId());
            } catch (Exception e) {
                assertThat(e).isInstanceOf(ConflictAuctionDeleteException.class);
            } finally {
                latch.countDown();
            }
        });

        executorService.submit(() -> {
            try {
                bidCommandService.bid(2L, new BidUseCaseReqDTO.Bid(auction.getId(), BID_PRICE));
            } catch (Exception e) {
                assertThat(e.getMessage()).isEqualTo(ErrorCode.BID_FAIL.getMessage());
            } finally {
                latch.countDown();
            }
        });

        latch.await();

        Auction updatedAuction = auctionRepositoryPort.findAuctionById(auction.getId());
        assertThat(updatedAuction.getStatus()).isEqualTo(TicTocStatus.ACTIVE);
    }
}