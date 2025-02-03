package tictoc.auction.port.in;

import org.springframework.data.domain.Pageable;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.dto.response.AuctionUseCaseResDTO;
import tictoc.model.page.PageCustom;

public interface AuctionQueryUseCase {
    PageCustom<AuctionUseCaseResDTO.Auction> getAuctionsByFilter(AuctionUseCaseReqDTO.Filter requestDTO, Pageable pageable);
}
