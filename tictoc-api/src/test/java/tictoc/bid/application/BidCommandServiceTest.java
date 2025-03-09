package tictoc.bid.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tictoc.TicTocApiApplication;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.model.Auction;
import tictoc.auction.model.type.AuctionType;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.model.Bid;
import tictoc.bid.port.BidCommandUseCase;
import tictoc.bid.port.BidRepositoryPort;
import tictoc.error.ErrorCode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TicTocApiApplication.class)
@ActiveProfiles("test")
public class BidCommandServiceTest {
    @Autowired
    private BidCommandUseCase bidCommandUseCase;
    @Autowired
    private AuctionRepositoryPort auctionRepositoryPort;
    @Autowired
    private BidRepositoryPort bidRepositoryPort;

    private static final Integer BID_PRICE = 1500;
    private static final int NUM_USERS = 5000;
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
        auction = auctionRepositoryPort.saveAuction(Auction.of(9999L, requestDTO));
    }

    @Test
    @DisplayName("입찰 시 동시성 이슈 발생에 대한 테스트")
    public void 입찰_시_동시성_이슈_발생에_대한_테스트() throws InterruptedException {
        CountDownLatch readyLatch = new CountDownLatch(NUM_USERS);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(NUM_USERS);
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_USERS);

        AtomicInteger successUsers = new AtomicInteger(0);
        AtomicInteger failedUsers = new AtomicInteger(0);
        AtomicReference<Long> successUserId = new AtomicReference<>();
        AtomicReference<Integer> successBidPrice = new AtomicReference<>();

        for (int i = 1; i <= NUM_USERS; i++) {
            final Long userId = (long) i;
            executorService.submit(() -> {
                try {
                    readyLatch.countDown();
                    startLatch.await();
                    int bidPrice = (int) (BID_PRICE * userId);
                    bidCommandUseCase.bid(userId, new BidUseCaseReqDTO.Bid(auction.getId(), bidPrice));
                    successUserId.set(userId);
                    successBidPrice.set(bidPrice);
                    successUsers.incrementAndGet();
                } catch (Exception e) {
                    failedUsers.incrementAndGet();
                    assertThat(e.getMessage()).isEqualTo(ErrorCode.BID_FAIL.getMessage());
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        readyLatch.await();
        startLatch.countDown();
        doneLatch.await();
        executorService.shutdown();

        Auction findAuction = auctionRepositoryPort.findAuctionById(auction.getId());
        Bid findBid = bidRepositoryPort.findBidByAuctionId(findAuction.getId());
        assertThat(findAuction.getCurrentPrice()).isEqualTo(successBidPrice.get());
        assertThat(findBid.getBidderId()).isEqualTo(successUserId.get());
        assertThat(successUsers.get()).isEqualTo(1);
        assertThat(failedUsers.get()).isEqualTo(NUM_USERS-1);
        executorService.shutdown();
    }
}