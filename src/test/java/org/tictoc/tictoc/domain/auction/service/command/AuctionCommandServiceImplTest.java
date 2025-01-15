package org.tictoc.tictoc.domain.auction.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tictoc.tictoc.domain.auction.repository.AuctionRepository;

@ExtendWith(MockitoExtension.class)
class AuctionCommandServiceImplTest {
    @InjectMocks
    private AuctionCommandServiceImpl auctionCommandService;

    @Mock
    private AuctionRepository auctionRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}