package tictoc.bid.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.bid.dto.response.BidResDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.bid.model.type.BidStatus;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-03T21:54:38+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Homebrew)"
)
@Component
public class BidResMapperImpl implements BidResMapper {

    @Override
    public BidResDTO.Bid toBid(BidUseCaseResDTO.Bid responseDTO) {
        if ( responseDTO == null ) {
            return null;
        }

        Long auctionId = null;
        String title = null;
        Integer myPrice = null;
        Integer currentPrice = null;
        BidStatus bidStatus = null;
        AuctionProgress auctionProgress = null;

        auctionId = responseDTO.auctionId();
        title = responseDTO.title();
        myPrice = responseDTO.myPrice();
        currentPrice = responseDTO.currentPrice();
        bidStatus = responseDTO.bidStatus();
        auctionProgress = responseDTO.auctionProgress();

        BidResDTO.Bid bid = new BidResDTO.Bid( auctionId, title, myPrice, currentPrice, bidStatus, auctionProgress );

        return bid;
    }
}
