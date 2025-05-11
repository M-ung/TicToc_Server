package tictoc.bid.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tictoc.auction.model.Auction;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.exception.BidException;
import tictoc.bid.model.Bid;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.error.ErrorCode;
import tictoc.model.tictoc.TicTocStatus;
import tictoc.profile.port.ProfileRepositoryPort;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.port.BidRepositoryPort;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class BidCommandServiceTest {
    @InjectMocks private BidCommandService bidCommandService;

    @Mock private ProfileRepositoryPort profileRepositoryPort;
    @Mock private AuctionRepositoryPort auctionRepositoryPort;
    @Mock private BidRepositoryPort bidRepositoryPort;

    private Auction auction;
    private BidUseCaseReqDTO.Bid bidDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        auction = Auction.builder()
                .id(1L)
                .auctioneerId(100L)
                .currentPrice(1000)
                .finalPrice(1000)
                .progress(AuctionProgress.NOT_STARTED)
                .auctionCloseTime(LocalDateTime.now().plusHours(1))
                .status(TicTocStatus.ACTIVE)
                .build();

        bidDTO = new BidUseCaseReqDTO.Bid(1L, 1500);
    }

    @Test
    @DisplayName("입찰 성공 테스트")
    void 입찰_성공_테스트() {
        // given
        when(auctionRepositoryPort.findAuctionById(1L)).thenReturn(auction);
        when(profileRepositoryPort.checkMoney(200L, 1500)).thenReturn(true);
        doNothing().when(bidRepositoryPort).checkBeforeBid(any());
        when(auctionRepositoryPort.updateBidIfHigher(bidDTO)).thenReturn(1);

        // when
        bidCommandService.bid(200L, bidDTO);

        // then
        verify(bidRepositoryPort).saveBid(any(Bid.class));
        verify(auctionRepositoryPort).updateBidIfHigher(bidDTO);
    }

    @Test
    @DisplayName("잔액 부족으로 입찰 실패 테스트")
    void 잔액_부족으로_입찰_실패_테스트() {
        // given
        when(auctionRepositoryPort.findAuctionById(1L)).thenReturn(auction);
        when(profileRepositoryPort.checkMoney(200L, 1500)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> bidCommandService.bid(200L, bidDTO))
                .isInstanceOf(BidException.class)
                .hasMessageContaining(ErrorCode.INVALID_PROFILE_MONEY.getMessage());

        verify(bidRepositoryPort, never()).saveBid(any());
    }

    @Test
    @DisplayName("입찰 조건 불충족으로 입찰 실패 테스트")
    void 입찰_조건_불충족으로_입찰_실패_테스트() {
        // given
        when(auctionRepositoryPort.findAuctionById(1L)).thenReturn(auction);
        when(profileRepositoryPort.checkMoney(200L, 1500)).thenReturn(true);
        doThrow(new BidException(ErrorCode.BID_FAIL)).when(bidRepositoryPort).checkBeforeBid(auction);

        // when & then
        assertThatThrownBy(() -> bidCommandService.bid(200L, bidDTO))
                .isInstanceOf(BidException.class)
                .hasMessageContaining(ErrorCode.BID_FAIL.getMessage());

        verify(bidRepositoryPort, never()).saveBid(any());
    }

    @Test
    @DisplayName("현재 입찰가보다 낮은 금액으로 입찰 시 실패 테스트")
    void 낮은_금액으로_입찰_실패_테스트() {
        // given
        BidUseCaseReqDTO.Bid lowBidDTO = new BidUseCaseReqDTO.Bid(1L, 900);
        when(auctionRepositoryPort.findAuctionById(1L)).thenReturn(auction);
        when(profileRepositoryPort.checkMoney(200L, 900)).thenReturn(true);
        doNothing().when(bidRepositoryPort).checkBeforeBid(any());
        when(auctionRepositoryPort.updateBidIfHigher(lowBidDTO)).thenReturn(0);

        // when & then
        assertThatThrownBy(() -> bidCommandService.bid(200L, lowBidDTO))
                .isInstanceOf(BidException.class)
                .hasMessageContaining(ErrorCode.BID_FAIL.getMessage());

        verify(bidRepositoryPort, never()).saveBid(any());
    }
}