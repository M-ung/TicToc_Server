package org.tictoc.tictoc.domain.auction.controller.auction.query;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.auction.response.AuctionResponseDTO;
import org.tictoc.tictoc.domain.auction.service.auction.query.AuctionQueryService;
import org.tictoc.tictoc.global.common.entity.page.PageCustom;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/auction")
public class AuctionQueryController {
    private final AuctionQueryService auctionQueryService;

    @GetMapping("")
    @Operation(summary = "경매 필터링 조회 API", description = "경매 필터링 조회 API 입니다.")
    public ResponseEntity<PageCustom<AuctionResponseDTO.Auction>> getAuctions (@RequestBody @Valid AuctionRequestDTO.Filter requestDTO, Pageable pageable) {
        return ResponseEntity.ok().body(auctionQueryService.getAuctionsByFilter(requestDTO, pageable));
    }

    //TODO 경매 상세 조회

    //TODO 내 경매 목록 조회 API 추가해야 함.
}
