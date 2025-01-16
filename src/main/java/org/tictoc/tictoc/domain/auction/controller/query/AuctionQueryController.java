package org.tictoc.tictoc.domain.auction.controller.query;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.response.AuctionResponseDTO;
import org.tictoc.tictoc.domain.auction.service.query.AuctionQueryService;
import org.tictoc.tictoc.global.common.entity.PageCustom;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/auction")
public class AuctionQueryController {
    private final AuctionQueryService auctionQueryService;

    @GetMapping("/")
    @Operation(summary = "경매 필터링 조회 API", description = "경매 필터링 조회 API 입니다.")
    public ResponseEntity<PageCustom<AuctionResponseDTO.Auctions>> getAuctions (@RequestBody @Valid AuctionRequestDTO.Filter requestDTO, Pageable pageable) {
        return ResponseEntity.ok().body(auctionQueryService.getAuctionsByFilter(requestDTO, pageable));
    }
}
