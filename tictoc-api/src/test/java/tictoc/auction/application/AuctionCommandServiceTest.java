package tictoc.auction.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.exception.ConflictAuctionUpdateException;
import tictoc.auction.model.Auction;
import tictoc.auction.model.type.AuctionType;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.error.ErrorCode;
import tictoc.model.tictoc.TicTocStatus;
import tictoc.redis.auction.port.out.CloseAuctionUseCase;
import java.time.LocalDateTime;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuctionCommandServiceTest {
    @InjectMocks private AuctionCommandService auctionCommandService;

    @Mock private AuctionRepositoryPort auctionRepositoryPort;
    @Mock private CloseAuctionUseCase closeAuctionUseCase;

    private AuctionUseCaseReqDTO.Register registerDTO;
    private AuctionUseCaseReqDTO.Update updateDTO;
    private Auction auction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        LocalDateTime now = LocalDateTime.now();
        registerDTO = new AuctionUseCaseReqDTO.Register(
                "Valid Title",
                "Valid Content",
                1000,
                now.plusHours(1),
                now.plusHours(3),
                now.plusMinutes(30),
                Collections.emptyList(),
                AuctionType.ONLINE
        );

        updateDTO = new AuctionUseCaseReqDTO.Update(
                "Updated Title",
                "Updated Content",
                2000,
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2),
                LocalDateTime.now().plusHours(3),
                Collections.emptyList(),
                AuctionType.ONLINE
        );

        auction = Auction.of(1L, registerDTO);
    }

    @Test
    @DisplayName("경매 등록 성공 테스트")
    void 경매_등록_성공_테스트() {
        // given
        Auction mockAuction = mock(Auction.class);
        when(mockAuction.getId()).thenReturn(1L);
        when(auctionRepositoryPort.saveAuction(any())).thenReturn(mockAuction);

        // when
        auctionCommandService.register(1L, registerDTO);

        // then
        verify(auctionRepositoryPort).validateAuctionTimeRangeForSave(anyLong(), any(), any());
        verify(auctionRepositoryPort).saveAuction(any());
        verify(closeAuctionUseCase).save(eq(1L), any());
    }

    @Test
    @DisplayName("잘못된 시간 검증으로 경매 등록 실패 테스트")
    void 잘못된_시간_검증으로_경매_등록_실패_테스트() {
        // given
        doThrow(new IllegalArgumentException("시간 범위 오류"))
                .when(auctionRepositoryPort).validateAuctionTimeRangeForSave(anyLong(), any(), any());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> auctionCommandService.register(1L, registerDTO));
        verify(auctionRepositoryPort).validateAuctionTimeRangeForSave(anyLong(), any(), any());
        verify(auctionRepositoryPort, never()).saveAuction(any());
    }


    @Test
    @DisplayName("경매 수정 성공 테스트")
    void 경매_수정_성공_테스트() {
        // given
        when(auctionRepositoryPort.findAuctionByIdForUpdate(any())).thenReturn(auction);

        // when
        auctionCommandService.update(1L, auction.getId(), updateDTO);

        // then
        verify(auctionRepositoryPort).findAuctionByIdForUpdate(auction.getId());
        verify(auctionRepositoryPort).validateAuctionTimeRangeForUpdate(any(), any(), any(), any());
        verify(closeAuctionUseCase).delete(auction.getId());
        verify(closeAuctionUseCase).save(eq(auction.getId()), any());
        assertThat(auction.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    @DisplayName("동시성으로 경매 수정 실패 테스트")
    void 동시성으로_경매_수정_실패_테스트() {
        // given
        when(auctionRepositoryPort.findAuctionByIdForUpdate(any())).thenThrow(new ConflictAuctionUpdateException(ErrorCode.CONFLICT_AUCTION_UPDATE));

        // when & then
        assertThrows(ConflictAuctionUpdateException.class, () -> auctionCommandService.update(1L, 1L, mock(AuctionUseCaseReqDTO.Update.class)));
        verify(auctionRepositoryPort).findAuctionByIdForUpdate(any());
    }

    @Test
    @DisplayName("경매 삭제 성공 테스트")
    void 경매_삭제_성공_테스트() {
        // given
        when(auctionRepositoryPort.findAuctionByIdForUpdate(any())).thenReturn(auction);

        // when
        auctionCommandService.delete(1L, auction.getId());

        // then
        verify(auctionRepositoryPort).findAuctionByIdForUpdate(auction.getId());
        verify(closeAuctionUseCase).delete(auction.getId());
        assertThat(auction.getStatus()).isEqualTo(TicTocStatus.DISACTIVE);
    }

    @Test
    @DisplayName("동시성으로 경매 삭제 실패 테스트")
    void 동시성으로_경매_삭제_실패_테스트() {
        // given
        doThrow(new ConflictAuctionUpdateException(ErrorCode.CONFLICT_AUCTION_DELETE)).when(auctionRepositoryPort).findAuctionByIdForUpdate(any());

        // when & then
        assertThrows(ConflictAuctionUpdateException.class, () -> auctionCommandService.delete(1L, 1L));
        verify(auctionRepositoryPort).findAuctionByIdForUpdate(any());
    }
}