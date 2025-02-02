package tictoc.auction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.dto.response.AuctionUseCaseResDTO;
import tictoc.auction.repository.AuctionRepository;
import tictoc.auction.port.in.AuctionQueryUseCase;
import tictoc.model.page.PageCustom;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionQueryServiceImpl implements AuctionQueryUseCase {
    private final AuctionRepository auctionRepository;

    @Override
    public PageCustom<AuctionUseCaseResDTO.Auction> getAuctionsByFilter(AuctionUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return auctionRepository.findAuctionsByFilterWithPageable(requestDTO, pageable);
    }
}