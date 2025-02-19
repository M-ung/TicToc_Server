package tictoc.auction.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tictoc.auction.dto.response.AuctionResDTO;
import tictoc.auction.dto.response.AuctionUseCaseResDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T16:48:00+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Homebrew)"
)
@Component
public class AuctionResMapperImpl implements AuctionResMapper {

    @Override
    public AuctionResDTO.Auction toAuction(AuctionUseCaseResDTO.Auction responseDTO) {
        if ( responseDTO == null ) {
            return null;
        }

        AuctionResDTO.Auction.AuctionBuilder auction = AuctionResDTO.Auction.builder();

        auction.auctionId( responseDTO.getAuctionId() );
        auction.title( responseDTO.getTitle() );
        auction.startPrice( responseDTO.getStartPrice() );
        auction.currentPrice( responseDTO.getCurrentPrice() );
        auction.sellStartTime( responseDTO.getSellStartTime() );
        auction.sellEndTime( responseDTO.getSellEndTime() );
        auction.auctionOpenTime( responseDTO.getAuctionOpenTime() );
        auction.auctionCloseTime( responseDTO.getAuctionCloseTime() );
        auction.progress( responseDTO.getProgress() );
        auction.type( responseDTO.getType() );
        auction.locations( locationListToLocationList( responseDTO.getLocations() ) );

        return auction.build();
    }

    @Override
    public AuctionResDTO.Detail toDetail(AuctionUseCaseResDTO.Detail responseDTO) {
        if ( responseDTO == null ) {
            return null;
        }

        AuctionResDTO.Detail.DetailBuilder detail = AuctionResDTO.Detail.builder();

        detail.auctionId( responseDTO.getAuctionId() );
        detail.title( responseDTO.getTitle() );
        detail.content( responseDTO.getContent() );
        detail.startPrice( responseDTO.getStartPrice() );
        detail.currentPrice( responseDTO.getCurrentPrice() );
        detail.sellStartTime( responseDTO.getSellStartTime() );
        detail.sellEndTime( responseDTO.getSellEndTime() );
        detail.auctionOpenTime( responseDTO.getAuctionOpenTime() );
        detail.auctionCloseTime( responseDTO.getAuctionCloseTime() );
        detail.progress( responseDTO.getProgress() );
        detail.type( responseDTO.getType() );
        detail.locations( locationListToLocationList( responseDTO.getLocations() ) );

        return detail.build();
    }

    protected AuctionResDTO.Location locationToLocation(AuctionUseCaseResDTO.Location location) {
        if ( location == null ) {
            return null;
        }

        AuctionResDTO.Location.LocationBuilder location1 = AuctionResDTO.Location.builder();

        location1.auctionId( location.getAuctionId() );
        location1.region( location.getRegion() );
        location1.city( location.getCity() );
        location1.district( location.getDistrict() );
        location1.subDistrict( location.getSubDistrict() );

        return location1.build();
    }

    protected List<AuctionResDTO.Location> locationListToLocationList(List<AuctionUseCaseResDTO.Location> list) {
        if ( list == null ) {
            return null;
        }

        List<AuctionResDTO.Location> list1 = new ArrayList<AuctionResDTO.Location>( list.size() );
        for ( AuctionUseCaseResDTO.Location location : list ) {
            list1.add( locationToLocation( location ) );
        }

        return list1;
    }
}
