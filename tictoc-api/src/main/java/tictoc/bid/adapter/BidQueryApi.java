package tictoc.bid.adapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import tictoc.annotation.UserId;
import tictoc.bid.dto.request.BidReqDTO;
import tictoc.bid.dto.request.WinningBidReqDTO;
import tictoc.bid.dto.response.BidResDTO;
import tictoc.model.page.PageCustom;

@Tag(name = "Bid", description = "입찰 관련 API")
public interface BidQueryApi {
    @Operation(summary = "입찰 필터링 조회 API", description = "입찰 필터링 조회 API 입니다.")
    ResponseEntity<PageCustom<BidResDTO.Bid>> getBids (@UserId final Long userId, @RequestBody @Valid BidReqDTO.Filter requestDTO, Pageable pageable);

    @Operation(summary = "입찰가 필터링 조회 API", description = "입찰가 필터링 조회 API 입니다.")
    ResponseEntity<PageCustom<BidResDTO.WinningBid>> getWinningBids (@UserId final Long userId, @RequestBody @Valid WinningBidReqDTO.Filter requestDTO, Pageable pageable);
}
