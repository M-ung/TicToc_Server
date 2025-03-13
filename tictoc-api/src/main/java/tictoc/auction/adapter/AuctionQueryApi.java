package tictoc.auction.adapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tictoc.annotation.UserId;
import tictoc.auction.dto.request.AuctionReqDTO;
import tictoc.auction.dto.response.AuctionResDTO;
import tictoc.model.page.PageCustom;

@Tag(name = "Auction", description = "경매 관련 API")
public interface AuctionQueryApi {
    @Operation(summary = "경매 필터링 조회 API", description = "경매 필터링 조회 API 입니다.")
    ResponseEntity<PageCustom<AuctionResDTO.Auction>> getAuctions (@RequestBody @Valid AuctionReqDTO.Filter requestDTO, Pageable pageable);

    @Operation(summary = "경매 상세 조회 API", description = "경매 상세 조회 API 입니다.")
    ResponseEntity<AuctionResDTO.Detail> getDetail (@PathVariable("auctionId") Long auctionId);

    @Operation(summary = "내 경매 목록 조회 API", description = "내 경매 목록 조회 API 입니다.")
    ResponseEntity<PageCustom<AuctionResDTO.Auction>> getMyAuctions (@UserId final Long userId, Pageable pageable);
}