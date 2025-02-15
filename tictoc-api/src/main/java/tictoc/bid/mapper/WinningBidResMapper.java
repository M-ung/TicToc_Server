package tictoc.bid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tictoc.bid.dto.response.BidResDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.model.page.PageCustom;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WinningBidResMapper {
    WinningBidResMapper INSTANCE = Mappers.getMapper(WinningBidResMapper.class);

    BidResDTO.WinningBid toDTO(BidUseCaseResDTO.WinningBid responseDTO);

    default PageCustom<BidResDTO.WinningBid> toPageDTO(PageCustom<BidUseCaseResDTO.WinningBid> page) {
        List<BidResDTO.WinningBid> content = page.content().stream()
                .map(this::toDTO)
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
