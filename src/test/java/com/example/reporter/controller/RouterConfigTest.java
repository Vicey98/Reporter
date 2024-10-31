package com.example.reporter.controller;

import com.example.reporter.dto.FightResponseDTO;
import com.example.reporter.service.FightHandler;
import com.example.reporter.service.FightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(RouterConfig.class)
class RouterConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @SpyBean
    private FightHandler fightHandler;

    @MockBean
    private FightService fightService;

    @Test
    void shouldHandleStockPickCall() {
        var fightResponseDto = new FightResponseDTO();

        when(fightService.getMostRecentFights())
                .thenReturn(List.of(fightResponseDto));

        webTestClient.get()
                .uri("/api/fights")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FightResponseDTO.class)
                .hasSize(1)
                .contains(fightResponseDto);
    }
}