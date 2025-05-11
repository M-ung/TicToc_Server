package tictoc.bid.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.dto.request.WinningBidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.bid.port.BidRepositoryPort;
import tictoc.bid.port.WinningBidRepositoryPort;
import tictoc.model.page.PageCustom;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BidQueryServiceTest {
    @InjectMocks private BidQueryService bidQueryService;

    @Mock private BidRepositoryPort bidRepositoryPort;
    @Mock private WinningBidRepositoryPort winningBidRepositoryPort;

    private BidUseCaseReqDTO.Filter filterDTO;
    private WinningBidUseCaseReqDTO.Filter winningBidFilterDTO;
    private Pageable pageable;
    private PageCustom<BidUseCaseResDTO.Bid> bidMockPage;
    private PageCustom<BidUseCaseResDTO.WinningBid> winningMockPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        filterDTO = new BidUseCaseReqDTO.Filter(null, null);
        winningBidFilterDTO = new WinningBidUseCaseReqDTO.Filter(null, null, null, null);
        pageable = PageRequest.of(0, 10);

        var bidPage = new PageImpl<>(List.of(mock(BidUseCaseResDTO.Bid.class)), pageable, 1);
        bidMockPage = new PageCustom<>(
                bidPage.getContent(),
                bidPage.getTotalPages(),
                bidPage.getTotalElements(),
                bidPage.getSize(),
                bidPage.getNumber()
        );

        var winningPage = new PageImpl<>(List.of(mock(BidUseCaseResDTO.WinningBid.class)), pageable, 1);
        winningMockPage = new PageCustom<>(
                winningPage.getContent(),
                winningPage.getTotalPages(),
                winningPage.getTotalElements(),
                winningPage.getSize(),
                winningPage.getNumber()
        );
    }

    @Test
    @DisplayName("입찰 내역 조회 성공 테스트")
    void 입찰_내역_조회_성공_테스트() {
        // given
        when(bidRepositoryPort.findBidsByFilterWithPageable(1L, filterDTO, pageable)).thenReturn(bidMockPage);

        // when
        PageCustom<BidUseCaseResDTO.Bid> result = bidQueryService.getBidsByFilter(1L, filterDTO, pageable);

        // then
        assertThat(result.content()).hasSize(1);
        verify(bidRepositoryPort).findBidsByFilterWithPageable(1L, filterDTO, pageable);
    }

    @Test
    @DisplayName("낙찰 내역 조회 성공 테스트")
    void 낙찰_내역_조회_성공_테스트() {
        // given
        when(winningBidRepositoryPort.findWinningBidsByFilterWithPageable(winningBidFilterDTO, pageable)).thenReturn(winningMockPage);

        // when
        PageCustom<BidUseCaseResDTO.WinningBid> result = bidQueryService.getWinningBidsByFilter(1L, winningBidFilterDTO, pageable);

        // then
        assertThat(result.content()).hasSize(1);
        verify(winningBidRepositoryPort).findWinningBidsByFilterWithPageable(winningBidFilterDTO, pageable);
    }
}