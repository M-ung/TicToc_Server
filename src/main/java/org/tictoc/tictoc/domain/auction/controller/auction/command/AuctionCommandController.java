package org.tictoc.tictoc.domain.auction.controller.auction.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.service.auction.command.AuctionCommandService;
import org.tictoc.tictoc.global.annotation.UserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/auction")
public class AuctionCommandController {
    private final AuctionCommandService auctionCommandService;

    @PostMapping("/register")
    @Operation(summary = "경매 등록 API", description = "경매 등록 API 입니다.")
    public ResponseEntity<String> register (@UserId final Long userId, @RequestBody @Valid AuctionRequestDTO.Register requestDTO) throws JsonProcessingException {
        auctionCommandService.register(userId, requestDTO);
        return ResponseEntity.ok().body("경매가 등록되었습니다.");
    }

    @PostMapping("/update/{auctionId}")
    @Operation(summary = "경매 수정 API", description = "경매 수정 API 입니다.")
    public ResponseEntity<String> update (@UserId final Long userId, @PathVariable("auctionId") Long auctionId, @RequestBody @Valid AuctionRequestDTO.Update requestDTO) {
        auctionCommandService.update(userId, auctionId, requestDTO);
        return ResponseEntity.ok().body("경매가 수정되었습니다.");
    }

    @PostMapping("/delete/{auctionId}")
    @Operation(summary = "경매 삭제 API", description = "경매 삭제 API 입니다.")
    public ResponseEntity<String> delete (@UserId final Long userId, @PathVariable("auctionId") Long auctionId) {
        auctionCommandService.delete(userId, auctionId);
        return ResponseEntity.ok().body("경매가 삭제되었습니다.");
    }
}
