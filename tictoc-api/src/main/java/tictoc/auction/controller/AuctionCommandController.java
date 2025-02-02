package tictoc.auction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tictoc.annotation.UserId;
import tictoc.auction.dto.request.AuctionReqDTO;
import tictoc.auction.mapper.AuctionReqMapper;
import tictoc.auction.port.in.AuctionCommandUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/auction")
public class AuctionCommandController {
    private final AuctionReqMapper auctionReqMapper;
    private final AuctionCommandUseCase auctionCommandUseCase;

    @PostMapping("/register")
    @Operation(summary = "경매 등록 API", description = "경매 등록 API 입니다.")
    public ResponseEntity<String> register (@UserId final Long userId, @RequestBody @Valid AuctionReqDTO.Register requestDTO) throws JsonProcessingException {
        auctionCommandUseCase.register(userId, auctionReqMapper.toUseCaseDTO(requestDTO));
        return ResponseEntity.ok().body("경매가 등록되었습니다.");
    }

    @PostMapping("/update/{auctionId}")
    @Operation(summary = "경매 수정 API", description = "경매 수정 API 입니다.")
    public ResponseEntity<String> update (@UserId final Long userId, @PathVariable("auctionId") Long auctionId, @RequestBody @Valid AuctionReqDTO.Update requestDTO) {
        auctionCommandUseCase.update(userId, auctionId, auctionReqMapper.toUseCaseDTO(requestDTO));
        return ResponseEntity.ok().body("경매가 수정되었습니다.");
    }

    @PostMapping("/delete/{auctionId}")
    @Operation(summary = "경매 삭제 API", description = "경매 삭제 API 입니다.")
    public ResponseEntity<String> delete (@UserId final Long userId, @PathVariable("auctionId") Long auctionId) {
        auctionCommandUseCase.delete(userId, auctionId);
        return ResponseEntity.ok().body("경매가 삭제되었습니다.");
    }
}
