package tictoc.bid.adapter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tictoc.annotation.UserId;
import tictoc.bid.mapper.BidReqMapper;
import tictoc.bid.port.BidCommandUseCase;
import tictoc.bid.dto.request.BidReqDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/bid")
public class BidCommandController implements BidCommandApi {
    private final BidReqMapper bidReqMapper;
    private final BidCommandUseCase bidCommandUseCase;

    @PostMapping("")
    public ResponseEntity<String> bid (@UserId final Long userId, @RequestBody @Valid BidReqDTO.Bid requestDTO) {
        bidCommandUseCase.bid(userId, bidReqMapper.toUseCaseDTO(requestDTO));
        return ResponseEntity.ok().body("입찰을 완료했습니다.");
    }
}
