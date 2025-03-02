package tictoc.bid.mapper;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tictoc.bid.dto.response.BidResDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-02T14:26:42+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Homebrew)"
)
@Component
public class WinningBidResMapperImpl implements WinningBidResMapper {

    @Override
    public BidResDTO.WinningBid toWinningBid(BidUseCaseResDTO.WinningBid responseDTO) {
        if ( responseDTO == null ) {
            return null;
        }

        Integer price = null;
        LocalDateTime winningBidDate = null;
        LocalDateTime sellStartTime = null;
        LocalDateTime sellEndTime = null;

        price = responseDTO.price();
        winningBidDate = responseDTO.winningBidDate();
        sellStartTime = responseDTO.sellStartTime();
        sellEndTime = responseDTO.sellEndTime();

        BidResDTO.WinningBid winningBid = new BidResDTO.WinningBid( price, winningBidDate, sellStartTime, sellEndTime );

        return winningBid;
    }
}
