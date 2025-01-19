package org.tictoc.tictoc.domain.auction.controller.auction.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tictoc.tictoc.domain.auction.controller.auction.query.AuctionQueryController;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.response.AuctionResponseDTO;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.auction.service.auction.query.AuctionQueryServiceImpl;
import org.tictoc.tictoc.global.common.entity.PageCustom;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionQueryControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private AuctionQueryController auctionQueryController;
    @Mock
    private AuctionQueryServiceImpl auctionQueryService;

    private ObjectMapper objectMapper;
    private String jwtToken;
    private Pageable pageable;
    private PageCustom<AuctionResponseDTO.Auctions> result;
    private AuctionRequestDTO.Filter filterRequestDTO1;
    private AuctionRequestDTO.Filter filterRequestDTO2;
    private AuctionRequestDTO.Filter filterRequestDTO3;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(auctionQueryController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        this.objectMapper = new ObjectMapper();

        this.objectMapper.registerModule(new JavaTimeModule());

        jwtToken = "gw4uH9ff1+RvDLBP3RTONuYxhjQ+++HrzFVXT3mRiNnGPA5hwu5jX3Yxo5do";

        result = new PageCustom<>(new ArrayList<>(), 1, 10, 10, 1);
        pageable = PageRequest.of(0, 10);
        filterRequestDTO1 = new AuctionRequestDTO.Filter(
                1000,
                5000,
                LocalDateTime.of(2025, 1, 1, 0, 0),
                LocalDateTime.of(2025, 12, 31, 23, 59),
                null,
                null,
                null
        );

        filterRequestDTO2 = new AuctionRequestDTO.Filter(
                null,
                null,
                null,
                null,
                null,
                null,
                AuctionType.ONLINE
        );

        filterRequestDTO3 = new AuctionRequestDTO.Filter(
                null,
                null,
                LocalDateTime.of(2025, 1, 1, 0, 0),
                LocalDateTime.of(2025, 12, 31, 23, 59),
                null,
                null,
                AuctionType.ALL
        );
    }

    @Test
    @DisplayName("정상적으로 동작하는 getAuctionsByFilter API 테스트 1")
    void 정상적으로_동작하는_getAuctionsByFilter_API_테스트_1() throws Exception {
        // given
        String registerRequestJson = objectMapper.writeValueAsString(filterRequestDTO1);

        // when
        when(auctionQueryService.getAuctionsByFilter(any(AuctionRequestDTO.Filter.class), any(Pageable.class)))
                .thenReturn(result);

        // then
        mockMvc.perform(get("/api/v1/member/auction/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(registerRequestJson)
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("정상적으로 동작하는 getAuctionsByFilter API 테스트 2")
    void 정상적으로_동작하는_getAuctionsByFilter_API_테스트_2() throws Exception {
        // given
        String registerRequestJson = objectMapper.writeValueAsString(filterRequestDTO2);

        // when
        when(auctionQueryService.getAuctionsByFilter(any(AuctionRequestDTO.Filter.class), any(Pageable.class)))
                .thenReturn(result);

        // then
        mockMvc.perform(get("/api/v1/member/auction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(registerRequestJson)
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("정상적으로 동작하는 getAuctionsByFilter API 테스트 3")
    void 정상적으로_동작하는_getAuctionsByFilter_API_테스트_3() throws Exception {
        // given
        String registerRequestJson = objectMapper.writeValueAsString(filterRequestDTO3);

        // when
        when(auctionQueryService.getAuctionsByFilter(any(AuctionRequestDTO.Filter.class), any(Pageable.class)))
                .thenReturn(result);

        // then
        mockMvc.perform(get("/api/v1/member/auction/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(registerRequestJson)
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }
}