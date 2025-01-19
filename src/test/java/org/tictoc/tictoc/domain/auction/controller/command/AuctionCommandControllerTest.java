package org.tictoc.tictoc.domain.auction.controller.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tictoc.tictoc.domain.auction.controller.auction.command.AuctionCommandController;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.auction.service.auction.command.AuctionCommandServiceImpl;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionCommandControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private AuctionCommandController auctionCommandController;
    @Mock
    private AuctionCommandServiceImpl auctionCommandService;

    private ObjectMapper objectMapper;
    private String jwtToken;
    private AuctionRequestDTO.Register registerRequestDTO;
    private AuctionRequestDTO.Update updateRequestDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(auctionCommandController)
                .build();
        this.objectMapper = new ObjectMapper();

        this.objectMapper.registerModule(new JavaTimeModule());

        jwtToken = "gw4uH9ff1+RvDLBP3RTONuYxhjQ+++HrzFVXT3mRiNnGPA5hwu5jX3Yxo5do";
        registerRequestDTO = new AuctionRequestDTO.Register(
                "테스트 제목", "테스트 내용", 1000,
                LocalDateTime.of(2024, 12, 15, 12, 0, 0),
                LocalDateTime.of(2024, 12, 15, 20, 0, 0),
                LocalDateTime.of(2024, 12, 14, 15, 0, 0),
                List.of(),
                AuctionType.ALL
        );

        updateRequestDTO = new AuctionRequestDTO.Update(
                "수정된 제목", "수정된 내용", 1500,
                LocalDateTime.of(2024, 12, 16, 12, 0, 0),
                LocalDateTime.of(2024, 12, 16, 20, 0, 0),
                LocalDateTime.of(2024, 12, 14, 18, 0, 0),
                List.of(),
                AuctionType.ALL
        );
    }

    @Test
    @DisplayName("정상적으로 동작하는 register API 테스트")
    void 정상적으로_동작하는_register_API_테스트() throws Exception {
        // given
        Long userId = 1L;
        String registerRequestJson = objectMapper.writeValueAsString(registerRequestDTO);

        // when
        doNothing().when(auctionCommandService).register(eq(userId), any(AuctionRequestDTO.Register.class));

        // then
        mockMvc.perform(post("/api/v1/member/auction/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(registerRequestJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("정상적으로 동작하는 update API 테스트")
    void 정상적으로_동작하는_update_API_테스트() throws Exception {
        // given
        Long userId = 1L;
        Long auctionId = 1L;
        String registerRequestJson = objectMapper.writeValueAsString(updateRequestDTO);

        // when
        doNothing().when(auctionCommandService).update(eq(userId), eq(auctionId), any(AuctionRequestDTO.Update.class));

        // then
        mockMvc.perform(post("/api/v1/member/auction/update/{auctionId}", auctionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(registerRequestJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("정상적으로 동작하는 delete API 테스트")
    void 정상적으로_동작하는_delete_API_테스트() throws Exception {
        // given
        Long userId = 1L;
        Long auctionId = 1L;
        String registerRequestJson = objectMapper.writeValueAsString(registerRequestDTO);

        // when
        doNothing().when(auctionCommandService).delete(eq(userId), eq(auctionId));

        // then
        mockMvc.perform(post("/api/v1/member/auction/delete/{auctionId}", auctionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(registerRequestJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }
}