package tictoc.auction.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.dto.response.AuctionUseCaseResDTO;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.model.type.AuctionType;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.model.page.PageCustom;
import java.time.LocalDateTime;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuctionQueryServiceTest {
    @InjectMocks private AuctionQueryService auctionQueryService;

    @Mock private AuctionRepositoryPort auctionRepositoryPort;
    @Mock private Pageable pageable;

    private AuctionUseCaseReqDTO.Filter filterDTO;

    @BeforeEach
    void setUp() {
        filterDTO = new AuctionUseCaseReqDTO.Filter(
                1000, 5000,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                Collections.emptyList(),
                AuctionProgress.IN_PROGRESS,
                AuctionType.ONLINE
        );

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("경매 필터 조회 성공 테스트")
    void 경매_필터_조회_성공_테스트() {
        // given
        PageCustom<AuctionUseCaseResDTO.Auction> mockPage = mock(PageCustom.class);
        when(auctionRepositoryPort.findAuctionsByFilterWithPageable(filterDTO, pageable)).thenReturn(mockPage);

        // when
        PageCustom<AuctionUseCaseResDTO.Auction> result = auctionQueryService.getAuctionsByFilter(filterDTO, pageable);

        // then
        verify(auctionRepositoryPort).findAuctionsByFilterWithPageable(filterDTO, pageable);
        assertThat(result).isEqualTo(mockPage);
    }

    @Test
    @DisplayName("경매 상세 조회 성공 테스트")
    void 경매_상세_조회_성공_테스트() {
        // given
        Long auctionId = 1L;
        AuctionUseCaseResDTO.Detail detailDTO = mock(AuctionUseCaseResDTO.Detail.class);
        when(auctionRepositoryPort.findDetailById(auctionId)).thenReturn(detailDTO);

        // when
        AuctionUseCaseResDTO.Detail result = auctionQueryService.getDetail(auctionId);

        // then
        verify(auctionRepositoryPort).findDetailById(auctionId);
        assertThat(result).isEqualTo(detailDTO);
    }

    @Test
    @DisplayName("내 경매 조회 성공 테스트")
    void 내_경매_조회_성공_테스트() {
        // given
        Long userId = 1L;
        PageCustom<AuctionUseCaseResDTO.Auction> mockPage = mock(PageCustom.class);
        when(auctionRepositoryPort.findMyAuctionsWithPageable(userId, pageable)).thenReturn(mockPage);

        // when
        PageCustom<AuctionUseCaseResDTO.Auction> result = auctionQueryService.getMyAuctionsByUserId(userId, pageable);

        // then
        verify(auctionRepositoryPort).findMyAuctionsWithPageable(userId, pageable);
        assertThat(result).isEqualTo(mockPage);
    }
}