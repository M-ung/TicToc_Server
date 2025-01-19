package org.tictoc.tictoc.domain.auction.service.auction.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.auction.response.AuctionResponseDTO;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.global.common.entity.PageCustom;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuctionQueryServiceImplTest {
    @InjectMocks
    private AuctionQueryServiceImpl auctionQueryService;

    @Mock
    private AuctionRepository auctionRepository;

    private AuctionRequestDTO.Filter filterRequestDTO;
    private Pageable pageable;
    private List<AuctionResponseDTO.Auction> auctions;
    private PageCustom<AuctionResponseDTO.Auction> pageCustom;

    @BeforeEach
    void setUp() {
        filterRequestDTO = new AuctionRequestDTO.Filter(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        pageable = PageRequest.of(0, 10);

        auctions = List.of(
                AuctionResponseDTO.Auction.builder()
                        .auctionId(1L)
                        .title("Auction 1")
                        .startPrice(1000)
                        .currentPrice(2000)
                        .sellStartTime(LocalDateTime.now().minusDays(1))
                        .sellEndTime(LocalDateTime.now().plusDays(1))
                        .auctionOpenTime(LocalDateTime.now().minusDays(2))
                        .auctionCloseTime(LocalDateTime.now().plusDays(2))
                        .progress(AuctionProgress.IN_PROGRESS)
                        .type(AuctionType.ONLINE)
                        .locations(null)
                        .build(),
                AuctionResponseDTO.Auction.builder()
                        .auctionId(2L)
                        .title("Auction 2")
                        .startPrice(1500)
                        .currentPrice(2500)
                        .sellStartTime(LocalDateTime.now().minusDays(1))
                        .sellEndTime(LocalDateTime.now().plusDays(1))
                        .auctionOpenTime(LocalDateTime.now().minusDays(2))
                        .auctionCloseTime(LocalDateTime.now().plusDays(2))
                        .progress(AuctionProgress.IN_PROGRESS)
                        .type(AuctionType.ONLINE)
                        .locations(null)
                        .build()
        );

        PageImpl<AuctionResponseDTO.Auction> pageImpl = new PageImpl<>(auctions, pageable, auctions.size());
        pageCustom = new PageCustom<>(
                pageImpl.getContent(),
                pageImpl.getTotalPages(),
                pageImpl.getTotalElements(),
                pageImpl.getSize(),
                pageImpl.getNumber()
        );
    }

    @Test
    @DisplayName("정상적으로 필터링 경매 조회가 되는 테스트")
    void 정상적으로_필터링_경매_조회가_되는_테스트() throws Exception {
        // Given
        when(auctionRepository.findAuctionsByFilterWithPageable(filterRequestDTO, pageable))
                .thenReturn(pageCustom);

        // When
        PageCustom<AuctionResponseDTO.Auction> result =
                auctionQueryService.getAuctionsByFilter(filterRequestDTO, pageable);

        // Then
        assertNotNull(result);
        assertThat(result.getContent()).hasSize(2);
        assertEquals("Auction 1", result.getContent().get(0).getTitle());
        assertEquals("Auction 2", result.getContent().get(1).getTitle());
        assertEquals(1000, result.getContent().get(0).getStartPrice());
        assertEquals(1500, result.getContent().get(1).getStartPrice());
    }
}