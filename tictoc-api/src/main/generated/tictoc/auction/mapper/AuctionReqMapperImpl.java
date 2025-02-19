package tictoc.auction.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tictoc.auction.dto.request.AuctionReqDTO;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.model.type.AuctionType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T16:48:00+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Homebrew)"
)
@Component
public class AuctionReqMapperImpl implements AuctionReqMapper {

    @Override
    public AuctionUseCaseReqDTO.Register toUseCaseDTO(AuctionReqDTO.Register requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        String title = null;
        String content = null;
        Integer startPrice = null;
        LocalDateTime sellStartTime = null;
        LocalDateTime sellEndTime = null;
        LocalDateTime auctionCloseTime = null;
        List<AuctionUseCaseReqDTO.Location> locations = null;
        AuctionType type = null;

        title = requestDTO.title();
        content = requestDTO.content();
        startPrice = requestDTO.startPrice();
        sellStartTime = requestDTO.sellStartTime();
        sellEndTime = requestDTO.sellEndTime();
        auctionCloseTime = requestDTO.auctionCloseTime();
        locations = locationListToLocationList( requestDTO.locations() );
        type = requestDTO.type();

        AuctionUseCaseReqDTO.Register register = new AuctionUseCaseReqDTO.Register( title, content, startPrice, sellStartTime, sellEndTime, auctionCloseTime, locations, type );

        return register;
    }

    @Override
    public AuctionUseCaseReqDTO.Update toUseCaseDTO(AuctionReqDTO.Update requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        String title = null;
        String content = null;
        Integer startPrice = null;
        LocalDateTime sellStartTime = null;
        LocalDateTime sellEndTime = null;
        LocalDateTime auctionCloseTime = null;
        List<AuctionUseCaseReqDTO.Location> locations = null;
        AuctionType type = null;

        title = requestDTO.title();
        content = requestDTO.content();
        startPrice = requestDTO.startPrice();
        sellStartTime = requestDTO.sellStartTime();
        sellEndTime = requestDTO.sellEndTime();
        auctionCloseTime = requestDTO.auctionCloseTime();
        locations = locationListToLocationList( requestDTO.locations() );
        type = requestDTO.type();

        AuctionUseCaseReqDTO.Update update = new AuctionUseCaseReqDTO.Update( title, content, startPrice, sellStartTime, sellEndTime, auctionCloseTime, locations, type );

        return update;
    }

    @Override
    public AuctionUseCaseReqDTO.Filter toUseCaseDTO(AuctionReqDTO.Filter requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Integer startPrice = null;
        Integer endPrice = null;
        LocalDateTime sellStartTime = null;
        LocalDateTime sellEndTime = null;
        List<AuctionUseCaseReqDTO.Location> locations = null;
        AuctionProgress progress = null;
        AuctionType type = null;

        startPrice = requestDTO.startPrice();
        endPrice = requestDTO.endPrice();
        sellStartTime = requestDTO.sellStartTime();
        sellEndTime = requestDTO.sellEndTime();
        locations = locationListToLocationList( requestDTO.locations() );
        progress = requestDTO.progress();
        type = requestDTO.type();

        AuctionUseCaseReqDTO.Filter filter = new AuctionUseCaseReqDTO.Filter( startPrice, endPrice, sellStartTime, sellEndTime, locations, progress, type );

        return filter;
    }

    protected AuctionUseCaseReqDTO.Location locationToLocation(AuctionReqDTO.Location location) {
        if ( location == null ) {
            return null;
        }

        String region = null;
        String city = null;
        String district = null;
        String subDistrict = null;

        region = location.region();
        city = location.city();
        district = location.district();
        subDistrict = location.subDistrict();

        AuctionUseCaseReqDTO.Location location1 = new AuctionUseCaseReqDTO.Location( region, city, district, subDistrict );

        return location1;
    }

    protected List<AuctionUseCaseReqDTO.Location> locationListToLocationList(List<AuctionReqDTO.Location> list) {
        if ( list == null ) {
            return null;
        }

        List<AuctionUseCaseReqDTO.Location> list1 = new ArrayList<AuctionUseCaseReqDTO.Location>( list.size() );
        for ( AuctionReqDTO.Location location : list ) {
            list1.add( locationToLocation( location ) );
        }

        return list1;
    }
}
