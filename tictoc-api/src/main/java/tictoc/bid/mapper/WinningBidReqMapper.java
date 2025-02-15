package tictoc.bid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tictoc.bid.dto.request.WinningBidReqDTO;
import tictoc.bid.dto.request.WinningBidUseCaseReqDTO;

@Mapper(componentModel = "spring")
public interface WinningBidReqMapper {
    WinningBidReqMapper INSTANCE = Mappers.getMapper(WinningBidReqMapper.class);

    WinningBidUseCaseReqDTO.Filter toUseCaseDTO(WinningBidReqDTO.Filter requestDTO);
}
