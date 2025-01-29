package org.tictoc.tictoc.domain.auction.controller.bid.command;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tictoc.tictoc.domain.auction.dto.bid.request.BidRequestDTO;
import org.tictoc.tictoc.domain.auction.service.bid.command.BidCommandService;
import org.tictoc.tictoc.global.annotation.UserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/bid")
public class BidCommandController {
    private final BidCommandService bidCommandService;

    @PostMapping("")
    @Operation(summary = "입찰 API", description = "입찰 API 입니다.")
    public ResponseEntity<String> bid (@UserId final Long userId, @RequestBody @Valid BidRequestDTO.Bid requestDTO) {
        bidCommandService.bid(userId, requestDTO);
        return ResponseEntity.ok().body("입찰을 완료했습니다.");
    }
}
