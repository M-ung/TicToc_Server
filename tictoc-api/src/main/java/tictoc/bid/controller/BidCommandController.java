package tictoc.bid.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tictoc.annotation.UserId;
import tictoc.bid.mapper.BidReqMapper;
import tictoc.bid.port.in.BidCommandUseCase;
import tictoc.bid.dto.request.BidReqDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/bid")
public class BidCommandController {
    private final BidReqMapper bidReqMapper;
    private final BidCommandUseCase bidCommandUseCase;

    @PostMapping("")
    @Operation(summary = "입찰 API", description = "입찰 API 입니다.")
    public ResponseEntity<String> bid (@UserId final Long userId, @RequestBody @Valid BidReqDTO.Bid requestDTO) {
        bidCommandUseCase.bid(userId, bidReqMapper.toUseCaseDTO(requestDTO));
        return ResponseEntity.ok().body("입찰을 완료했습니다.");
    }
}
