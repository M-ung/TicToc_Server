package tictoc.bid.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tictoc.annotation.UserId;
import tictoc.bid.dto.request.WinningBidReqDTO;
import tictoc.bid.mapper.BidReqMapper;
import tictoc.bid.mapper.BidResMapper;
import tictoc.bid.mapper.WinningBidReqMapper;
import tictoc.bid.mapper.WinningBidResMapper;
import tictoc.bid.port.BidQueryUseCase;
import tictoc.bid.dto.request.BidReqDTO;
import tictoc.bid.dto.response.BidResDTO;
import tictoc.model.page.PageCustom;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/bid")
public class BidQueryController {
    private final BidReqMapper bidReqMapper;
    private final BidResMapper bidResMapper;
    private final WinningBidReqMapper winningBidReqMapper;
    private final WinningBidResMapper winningBidResMapper;
    private final BidQueryUseCase bidQueryUseCase;

    @GetMapping("")
    @Operation(summary = "입찰 필터링 조회 API", description = "입찰 필터링 조회 API 입니다.")
    public ResponseEntity<PageCustom<BidResDTO.Bid>> getBids (@UserId final Long userId, @RequestBody @Valid BidReqDTO.Filter requestDTO, Pageable pageable) {
        return ResponseEntity.ok().body(bidResMapper.toBidPage(bidQueryUseCase.getBidsByFilter(userId, bidReqMapper.toUseCaseDTO(requestDTO), pageable)));
    }

    @GetMapping("/winning")
    @Operation(summary = "입찰가 필터링 조회 API", description = "입찰가 필터링 조회 API 입니다.")
    public ResponseEntity<PageCustom<BidResDTO.WinningBid>> getWinningBids (@UserId final Long userId, @RequestBody @Valid WinningBidReqDTO.Filter requestDTO, Pageable pageable) {
        return ResponseEntity.ok().body(winningBidResMapper.toWinningBidPage(bidQueryUseCase.getWinningBidsByFilter(userId, winningBidReqMapper.toUseCaseDTO(requestDTO), pageable)));
    }
}
