package tictoc.auction.adapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tictoc.annotation.UserId;
import tictoc.auction.dto.request.AuctionReqDTO;

@Tag(name = "Auction", description = "경매 관련 API")
public interface AuctionCommandApi {
    @Operation(summary = "경매 등록 API", description = "경매 등록 API 입니다.")
    ResponseEntity<String> register (@UserId final Long userId, @RequestBody @Valid AuctionReqDTO.Register requestDTO);

    @Operation(summary = "경매 수정 API", description = "경매 수정 API 입니다.")
    ResponseEntity<String> update (@UserId final Long userId, @PathVariable("auctionId") Long auctionId, @RequestBody @Valid AuctionReqDTO.Update requestDTO);

    @Operation(summary = "경매 삭제 API", description = "경매 삭제 API 입니다.")
    ResponseEntity<String> delete (@UserId final Long userId, @PathVariable("auctionId") Long auctionId);
}