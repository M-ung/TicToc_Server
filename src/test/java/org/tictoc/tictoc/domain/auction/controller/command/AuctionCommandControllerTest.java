package org.tictoc.tictoc.domain.auction.controller.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tictoc.tictoc.domain.auction.service.command.AuctionCommandServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class AuctionCommandControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private AuctionCommandController auctionCommandController;

    @Mock
    private AuctionCommandServiceImpl auctionCommandService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(auctionCommandController).build();
        this.objectMapper = new ObjectMapper();
    }


}