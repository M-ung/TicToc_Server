package org.tictoc.tictoc.domain.auction.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.Auction;
import org.tictoc.tictoc.domain.auction.entity.Zone;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.auction.repository.AuctionRepository;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionCommandServiceImplTest {

    @InjectMocks
    private AuctionCommandServiceImpl auctionCommandService;

    @Mock
    private AuctionRepository auctionRepository;

    private Auction auction;
    private AuctionRequestDTO.Update updateRequestDTO;
    private AuctionRequestDTO.Register registerRequestDTO;

    @BeforeEach
    void setUp() {
        Long userId = 1L;
        Long auctionId = 1L;

        auction = Auction.builder()
                .id(auctionId)
                .auctioneerId(userId)
                .title("수정 전 제목")
                .content("수정 전 내용")
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

        updateRequestDTO = new AuctionRequestDTO.Update(
                "수정된 제목", "수정된 내용", 1500,
                LocalDateTime.of(2024, 12, 16, 12, 0, 0),
                LocalDateTime.of(2024, 12, 16, 20, 0, 0),
                LocalDateTime.of(2024, 12, 14, 18, 0, 0),
                List.of(),
                AuctionType.ALL
        );

        registerRequestDTO = new AuctionRequestDTO.Register(
                "테스트 제목", "테스트 내용", 1000,
                LocalDateTime.of(2024, 12, 15, 12, 0, 0),
                LocalDateTime.of(2024, 12, 15, 20, 0, 0),
                LocalDateTime.of(2024, 12, 14, 15, 0, 0),
                List.of(),
                AuctionType.ALL
        );
    }

    @Test
    @DisplayName("정상적으로 경매가 등록되는 테스트")
    void 정상적으로_경매가_등록되는_테스트() {
        // given
        Long userId = 1L;

        when(auctionRepository.existsAuctionInTimeRange(userId, registerRequestDTO.sellStartTime(), registerRequestDTO.sellEndTime()))
                .thenReturn(false);

        // when
        auctionCommandService.register(userId, registerRequestDTO);

        // then
        verify(auctionRepository, times(1)).save(any(Auction.class));
    }

    @Test
    @DisplayName("정상적으로 경매가 수정되는 테스트")
    void 정상적으로_경매가_수정되는_테스트() {
        // given
        Long userId = 1L;
        Long auctionId = 1L;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));
        when(auctionRepository.existsAuctionInTimeRange(userId, updateRequestDTO.sellStartTime(), updateRequestDTO.sellEndTime()))
                .thenReturn(false);

        // when
        auctionCommandService.update(userId, auctionId, updateRequestDTO);

        // then
        assertThat(auction.getTitle()).isEqualTo("수정된 제목");
        assertThat(auction.getContent()).isEqualTo("수정된 내용");
        assertThat(auction.getStartPrice()).isEqualTo(1500);
        assertThat(auction.getSellStartTime()).isEqualTo(updateRequestDTO.sellStartTime());
        assertThat(auction.getSellEndTime()).isEqualTo(updateRequestDTO.sellEndTime());
        assertThat(auction.getAuctionCloseTime()).isEqualTo(updateRequestDTO.auctionCloseTime());
        assertThat(auction.getZones()).isEqualTo(updateRequestDTO.zones());
        assertThat(auction.getType()).isEqualTo(updateRequestDTO.type());
    }

    @Test
    @DisplayName("정상적으로 경매가 삭제되는 테스트")
    void 정상적으로_경매가_삭제되는_테스트() {
        // given
        Long userId = 1L;
        Long auctionId = 1L;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));

        // when
        auctionCommandService.delete(userId, auctionId);

        // then
        assertThat(auction.getStatus()).isEqualTo(TicTocStatus.DISACTIVE);
    }
}