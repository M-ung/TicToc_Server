package tictoc.bid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tictoc.bid.dto.response.BidResDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.model.page.PageCustom;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BidResMapper {
    BidResMapper INSTANCE = Mappers.getMapper(BidResMapper.class);

    BidResDTO.Bid toDTO(BidUseCaseResDTO.Bid responseDTO);

    default PageCustom<BidResDTO.Bid> toPageDTO(PageCustom<BidUseCaseResDTO.Bid> page) {
        List<BidResDTO.Bid> content = page.content().stream()
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