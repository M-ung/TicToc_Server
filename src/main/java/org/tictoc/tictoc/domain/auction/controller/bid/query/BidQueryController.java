package org.tictoc.tictoc.domain.auction.controller.bid.query;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.bid.response.BidResponseDTO;
import org.tictoc.tictoc.domain.auction.service.bid.query.BidQueryService;
import org.tictoc.tictoc.global.annotation.UserId;
import org.tictoc.tictoc.global.common.entity.page.PageCustom;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/bid")
public class BidQueryController {
    private final BidQueryService bidQueryService;

    @GetMapping("")
    @Operation(summary = "입찰 필터링 조회 API", description = "입찰 필터링 조회 API 입니다.")
    public ResponseEntity<PageCustom<BidResponseDTO.Bid>> getBids (@UserId final Long userId, @RequestBody @Valid BidRequestDTO.Filter requestDTO, Pageable pageable) {
        return ResponseEntity.ok().body(bidQueryService.getBidsByFilter(userId, requestDTO, pageable));
    }
}
