package tictoc.bid.adapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import tictoc.annotation.UserId;
import tictoc.bid.dto.request.BidReqDTO;

@Tag(name = "Bid", description = "입찰 관련 API")
public interface BidCommandApi {
    @Operation(summary = "입찰 API", description = "입찰 API 입니다.")
    ResponseEntity<String> bid (@UserId final Long userId, @RequestBody @Valid BidReqDTO.Bid requestDTO);
}
