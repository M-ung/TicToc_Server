package tictoc.bid.mapper;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tictoc.bid.dto.request.WinningBidReqDTO;
import tictoc.bid.dto.request.WinningBidUseCaseReqDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-20T01:25:53+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Homebrew)"
)
@Component
public class WinningBidReqMapperImpl implements WinningBidReqMapper {

    @Override
    public WinningBidUseCaseReqDTO.Filter toUseCaseDTO(WinningBidReqDTO.Filter requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Integer price = null;
        LocalDateTime winningBidDate = null;
        LocalDateTime sellStartTime = null;
        LocalDateTime sellEndTime = null;

        price = requestDTO.price();
        winningBidDate = requestDTO.winningBidDate();
        sellStartTime = requestDTO.sellStartTime();
        sellEndTime = requestDTO.sellEndTime();

        WinningBidUseCaseReqDTO.Filter filter = new WinningBidUseCaseReqDTO.Filter( price, winningBidDate, sellStartTime, sellEndTime );

        return filter;
    }
}
