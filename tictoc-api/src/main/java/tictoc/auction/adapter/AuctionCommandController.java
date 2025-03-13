package tictoc.auction.adapter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tictoc.annotation.UserId;
import tictoc.auction.dto.request.AuctionReqDTO;
import tictoc.auction.mapper.AuctionReqMapper;
import tictoc.auction.port.AuctionCommandUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/auction")
public class AuctionCommandController implements AuctionCommandApi {
    private final AuctionReqMapper auctionReqMapper;
    private final AuctionCommandUseCase auctionCommandUseCase;

    @PostMapping("/register")
    public ResponseEntity<String> register (@UserId final Long userId, @RequestBody @Valid AuctionReqDTO.Register requestDTO) {
        auctionCommandUseCase.register(userId, auctionReqMapper.toUseCaseDTO(requestDTO));
        return ResponseEntity.ok().body("경매가 등록되었습니다.");
    }

    @PostMapping("/update/{auctionId}")
    public ResponseEntity<String> update (@UserId final Long userId, @PathVariable("auctionId") Long auctionId, @RequestBody @Valid AuctionReqDTO.Update requestDTO) {
        auctionCommandUseCase.update(userId, auctionId, auctionReqMapper.toUseCaseDTO(requestDTO));
        return ResponseEntity.ok().body("경매가 수정되었습니다.");
    }

    @PostMapping("/delete/{auctionId}")
    public ResponseEntity<String> delete (@UserId final Long userId, @PathVariable("auctionId") Long auctionId) {
        auctionCommandUseCase.delete(userId, auctionId);
        return ResponseEntity.ok().body("경매가 삭제되었습니다.");
    }
}