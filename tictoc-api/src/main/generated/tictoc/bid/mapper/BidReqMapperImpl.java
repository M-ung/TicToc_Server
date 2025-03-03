package tictoc.bid.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.bid.dto.request.BidReqDTO;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.model.type.BidStatus;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-03T21:54:38+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Homebrew)"
)
@Component
public class BidReqMapperImpl implements BidReqMapper {

    @Override
    public BidUseCaseReqDTO.Bid toUseCaseDTO(BidReqDTO.Bid requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Long auctionId = null;
        Integer price = null;

        auctionId = requestDTO.auctionId();
        price = requestDTO.price();

        BidUseCaseReqDTO.Bid bid = new BidUseCaseReqDTO.Bid( auctionId, price );

        return bid;
    }

    @Override
    public BidUseCaseReqDTO.Filter toUseCaseDTO(BidReqDTO.Filter requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        BidStatus bidStatus = null;
        AuctionProgress auctionProgress = null;

        bidStatus = requestDTO.bidStatus();
        auctionProgress = requestDTO.auctionProgress();

        BidUseCaseReqDTO.Filter filter = new BidUseCaseReqDTO.Filter( bidStatus, auctionProgress );

        return filter;
    }
}
