package tictoc.bid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tictoc.bid.dto.request.BidReqDTO;
import tictoc.bid.dto.request.BidUseCaseReqDTO;

@Mapper(componentModel = "spring")
public interface BidReqMapper {
    BidReqMapper INSTANCE = Mappers.getMapper(BidReqMapper.class);

    BidUseCaseReqDTO.Bid toUseCaseDTO(BidReqDTO.Bid requestDTO);
    BidUseCaseReqDTO.Filter toUseCaseDTO(BidReqDTO.Filter requestDTO);
}