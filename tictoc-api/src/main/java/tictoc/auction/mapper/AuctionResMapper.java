package tictoc.auction.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tictoc.auction.dto.response.AuctionResDTO;
import tictoc.auction.dto.response.AuctionUseCaseResDTO;
import tictoc.model.page.PageCustom;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuctionResMapper {

    AuctionResMapper INSTANCE = Mappers.getMapper(AuctionResMapper.class);

    AuctionResDTO.Auction toAuction(AuctionUseCaseResDTO.Auction responseDTO);
    AuctionResDTO.Detail toDetail(AuctionUseCaseResDTO.Detail responseDTO);

    default PageCustom<AuctionResDTO.Auction> toAuctionPage(PageCustom<AuctionUseCaseResDTO.Auction> page) {
        List<AuctionResDTO.Auction> content = page.content().stream()
                .map(this::toAuction)
                .collect(Collectors.toList());
        return new PageCustom<>(
                content,
                page.totalPages(),
                page.totalElements(),
                page.size(),
                page.number()
        );
    }
}