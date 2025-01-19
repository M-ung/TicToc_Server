package org.tictoc.tictoc.domain.auction.service.auction.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.entity.location.AuctionLocation;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.domain.auction.repository.location.AuctionLocationRepository;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionCommandServiceImplTest {

    @InjectMocks
    private AuctionCommandServiceImpl auctionCommandService;

    @Mock
    private AuctionRepository auctionRepository;
    @Mock
    private AuctionLocationRepository auctionLocationRepository;

    private Auction auction;
    private AuctionRequestDTO.Register registerRequestDTO;
    private AuctionRequestDTO.Update updateRequestDTO1;
    private AuctionRequestDTO.Update updateRequestDTO2;

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
                .progress(AuctionProgress.NOT_STARTED)
                .type(AuctionType.OFFLINE)
                .status(TicTocStatus.ACTIVE)
                .version(0)
                .build();

        registerRequestDTO = new AuctionRequestDTO.Register(
                "테스트 제목", "테스트 내용", 1000,
                LocalDateTime.of(2024, 12, 15, 12, 0, 0),
                LocalDateTime.of(2024, 12, 15, 20, 0, 0),
                LocalDateTime.of(2024, 12, 14, 15, 0, 0),
                List.of(),
                AuctionType.ALL
        );

        updateRequestDTO1 = new AuctionRequestDTO.Update(
                "수정된 제목 1", "수정된 내용 1", 1500,
                LocalDateTime.of(2024, 12, 16, 12, 0, 0),
                LocalDateTime.of(2024, 12, 16, 20, 0, 0),
                LocalDateTime.of(2024, 12, 14, 18, 0, 0),
                List.of(),
                AuctionType.ALL
        );

        updateRequestDTO2 = new AuctionRequestDTO.Update(
                "수정된 제목 2", "수정된 내용 2", 1500,
                LocalDateTime.of(2024, 12, 16, 12, 0, 0),
                LocalDateTime.of(2024, 12, 16, 20, 0, 0),
                LocalDateTime.of(2024, 12, 14, 18, 0, 0),
                List.of(),
                AuctionType.ALL
        );
    }

    @Test
    @DisplayName("정상적으로 경매가 등록되는 테스트")
    void 정상적으로_경매가_등록되는_테스트() throws Exception {
        // given
        Long userId = 1L;
        Auction mockAuction = Auction.builder()
                .id(1L) // 반환될 ID 설정
                .auctioneerId(userId)
                .title(registerRequestDTO.title())
                .content(registerRequestDTO.content())
                .startPrice(registerRequestDTO.startPrice())
                .build();

        when(auctionRepository.existsAuctionInTimeRange(userId, registerRequestDTO.sellStartTime(), registerRequestDTO.sellEndTime()))
                .thenReturn(false);
        when(auctionRepository.save(any(Auction.class)))
                .thenReturn(mockAuction);

        // when
        auctionCommandService.register(userId, registerRequestDTO);

        // then
        verify(auctionRepository, times(1)).save(any(Auction.class));
    }

    @Test
    @DisplayName("정상적으로 경매가 수정되는 테스트")
    void 정상적으로_경매가_수정되는_테스트() throws Exception {
        // given
        Long userId = 1L;
        Long auctionId = 1L;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));
        when(auctionRepository.existsAuctionInTimeRange(userId, updateRequestDTO1.sellStartTime(), updateRequestDTO1.sellEndTime()))
                .thenReturn(false);
        when(auctionLocationRepository.findByAuctionId(any()))
                .thenReturn(Optional.of(new AuctionLocation()));

        // when
        auctionCommandService.update(userId, auctionId, updateRequestDTO1);

        // then
        assertThat(auction.getTitle()).isEqualTo("수정된 제목 1");
        assertThat(auction.getContent()).isEqualTo("수정된 내용 1");
        assertThat(auction.getStartPrice()).isEqualTo(1500);
        assertThat(auction.getSellStartTime()).isEqualTo(updateRequestDTO1.sellStartTime());
        assertThat(auction.getSellEndTime()).isEqualTo(updateRequestDTO1.sellEndTime());
        assertThat(auction.getAuctionCloseTime()).isEqualTo(updateRequestDTO1.auctionCloseTime());
        assertThat(auction.getType()).isEqualTo(updateRequestDTO1.type());
    }

    @Test
    @DisplayName("경매가 동시에 수정되는 테스트")
    void 경매가_동시에_수정되는_테스트() throws InterruptedException {
        // given
        Long userId = 1L;
        Long auctionId = 1L;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));
        when(auctionRepository.existsAuctionInTimeRange(userId, updateRequestDTO1.sellStartTime(), updateRequestDTO1.sellEndTime()))
                .thenReturn(false);
        when(auctionRepository.existsAuctionInTimeRange(userId, updateRequestDTO2.sellStartTime(), updateRequestDTO2.sellEndTime()))
                .thenReturn(false);

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            auctionCommandService.update(userId, auctionId, updateRequestDTO1);
        });

        executorService.submit(() -> {
            auctionCommandService.update(userId, auctionId, updateRequestDTO2);
        });
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        // then
        verify(auctionRepository, times(2)).findById(auctionId);
    }

    @Test
    @DisplayName("동시 경매 수정 시 낙관적 잠금 실패 테스트")
    void 경매가_동시에_수정되면_낙관적_잠금_실패하는_테스트() throws InterruptedException {
        // given
        Long userId = 1L;
        Long auctionId = 1L;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));
        when(auctionRepository.existsAuctionInTimeRange(userId, updateRequestDTO1.sellStartTime(), updateRequestDTO1.sellEndTime()))
                .thenReturn(false);
        when(auctionRepository.existsAuctionInTimeRange(userId, updateRequestDTO2.sellStartTime(), updateRequestDTO2.sellEndTime()))
                .thenReturn(false);

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            try {
                auctionCommandService.update(userId, auctionId, updateRequestDTO1);
            } catch (OptimisticLockingFailureException e) {
                assertThat(e).isInstanceOf(OptimisticLockingFailureException.class);
            }
        });

        executorService.submit(() -> {
            try {
                auctionCommandService.update(userId, auctionId, updateRequestDTO2);
            } catch (OptimisticLockingFailureException e) {
                assertThat(e).isInstanceOf(OptimisticLockingFailureException.class);
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        // then
        verify(auctionRepository, times(2)).findById(auctionId);
        verify(auctionRepository, never()).save(any(Auction.class));
    }

    @Test
    @DisplayName("정상적으로 경매가 삭제되는 테스트")
    void 정상적으로_경매가_삭제되는_테스트() throws Exception {
        // given
        Long userId = 1L;
        Long auctionId = 1L;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));

        // when
        auctionCommandService.delete(userId, auctionId);

        // then
        assertThat(auction.getStatus()).isEqualTo(TicTocStatus.DISACTIVE);
    }

    @Test
    @DisplayName("경매가 동시에 삭제되는 테스트")
    void 경매가_동시에_삭제되는_테스트() throws InterruptedException {
        // given
        Long userId = 1L;
        Long auctionId = 1L;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            auctionCommandService.delete(userId, auctionId);
        });

        executorService.submit(() -> {
            auctionCommandService.delete(userId, auctionId);
        });
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        // then
        verify(auctionRepository, times(2)).findById(auctionId);
    }

    @Test
    @DisplayName("동시 경매 삭제 시 낙관적 잠금 실패 테스트")
    void 경매가_동시에_삭제되면_낙관적_잠금_실패하는_테스트() throws InterruptedException {
        // given
        Long userId = 1L;
        Long auctionId = 1L;

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            try {
                auctionCommandService.delete(userId, auctionId);
            } catch (OptimisticLockingFailureException e) {
                assertThat(e).isInstanceOf(OptimisticLockingFailureException.class);
            }
        });

        executorService.submit(() -> {
            try {
                auctionCommandService.delete(userId, auctionId);
            } catch (OptimisticLockingFailureException e) {
                assertThat(e).isInstanceOf(OptimisticLockingFailureException.class);
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        // then
        verify(auctionRepository, times(2)).findById(auctionId);
        verify(auctionRepository, never()).save(any(Auction.class));
    }
}