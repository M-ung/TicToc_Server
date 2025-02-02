package tictoc.auction.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tictoc.auction.dto.request.AuctionReqDTO;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;

@Mapper(componentModel = "spring")
public interface AuctionReqMapper {
    AuctionReqMapper INSTANCE = Mappers.getMapper(AuctionReqMapper.class);

    AuctionUseCaseReqDTO.Register toUseCaseDTO(AuctionReqDTO.Register requestDTO);
    AuctionUseCaseReqDTO.Update toUseCaseDTO(AuctionReqDTO.Update requestDTO);
    AuctionUseCaseReqDTO.Filter toUseCaseDTO(AuctionReqDTO.Filter requestDTO);
}