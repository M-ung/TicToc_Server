package tictoc.bid.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tictoc.TicTocApiApplication;
import tictoc.auction.model.Auction;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.port.BidRepositoryPort;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TicTocApiApplication.class)
@ActiveProfiles("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidCommandServiceTest {
    @Autowired
    private BidCommandService bidCommandService;
    @Autowired
    private AuctionRepositoryPort auctionRepositoryPort;
    @Autowired
    private BidRepositoryPort bidRepositoryPort;

    private static final Integer BID_PRICE = 1500;
    private static final int NUM_USERS = 100;
    private Auction auction;

//    @BeforeEach
//    public void setup() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime sellStartTime = now.plusMinutes(10);
//        LocalDateTime sellEndTime = now.plusHours(2);
//        LocalDateTime auctionCloseTime = now.plusHours(3);
//
//        AuctionUseCaseReqDTO.Register requestDTO = new AuctionUseCaseReqDTO.Register(
//                "Test Auction",
//                "This is a test auction.",
//                1000,
//                sellStartTime,
//                sellEndTime,
//                auctionCloseTime,
//                Collections.emptyList(),
//                AuctionType.ONLINE
//        );
//        auction = auctionRepositoryPort.saveAuction(Auction.of(1L, requestDTO));
//    }
//
//    @Test
//    @DisplayName("입찰 시 동시성 이슈 발생에 대한 테스트")
//    public void 입찰_시_동시성_이슈_발생에_대한_테스트() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(NUM_USERS);
//        ExecutorService executorService = Executors.newFixedThreadPool(NUM_USERS);
//
//        AtomicInteger successUsers = new AtomicInteger(0);
//        AtomicInteger failedUsers = new AtomicInteger(0);
//
//        for (int i = 1; i <= NUM_USERS; i++) {
//            final Long userId = (long) i;
//            executorService.submit(() -> {
//                try {
//                    bidCommandService.bid(userId, new BidUseCaseReqDTO.Bid(auction.getId(), BID_PRICE));
//                    successUsers.incrementAndGet();
//                } catch (BidException e) {
//                    failedUsers.incrementAndGet();
//                    assertThat(e.getMessage()).isEqualTo(ErrorCode.BID_FAIL.getMessage());
//                } finally {
//                    latch.countDown();
//                }
//            });
//        }
//
//        latch.await();
//
//        Auction updatedAuction = auctionRepositoryPort.findAuctionById(auction.getId());
//        assertThat(updatedAuction.getCurrentPrice()).isEqualTo(BID_PRICE);
//
//        assertThat(successUsers.get()).isEqualTo(1);
//        assertThat(failedUsers.get()).isEqualTo(NUM_USERS-1);
//        executorService.shutdown();
//    }
}