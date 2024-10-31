package com.example.reporter.service;

import com.example.reporter.dto.FightResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@ExtendWith(MockitoExtension.class)
class FightHandlerTest {

    private WebTestClient webTestClient;

    @Mock
    private FightService fightService;

    private FightHandler fightHandler;

    @BeforeEach
    void setUp() {
        fightHandler = new FightHandler(fightService);
        RouterFunction<ServerResponse> routerFunction = route(GET("/api/fights"), fightHandler::getFights);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    void getFightsShouldReturnFightsWhenServiceReturnsData() {
        List<FightResponseDTO> expectedFights = Arrays.asList(
                new FightResponseDTO(), new FightResponseDTO()
        );
        when(fightService.getMostRecentFights()).thenReturn(expectedFights);

        webTestClient.get().uri("/api/fights")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FightResponseDTO.class)
                .hasSize(2)
                .isEqualTo(expectedFights);

        verify(fightService, times(1)).getMostRecentFights();
    }

    @Test
    void getFightsShouldReturnEmptyListWhenServiceReturnsNoData() {
        when(fightService.getMostRecentFights()).thenReturn(List.of());

        webTestClient.get().uri("/api/fights")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FightResponseDTO.class)
                .hasSize(0);

        verify(fightService, times(1)).getMostRecentFights();
    }

    @Test
    void getFightsShouldReturnServerErrorWhenServiceThrowsException() {
        when(fightService.getMostRecentFights()).thenThrow(new RuntimeException("Service error"));

        webTestClient.get().uri("/api/fights")
                .exchange()
                .expectStatus().is5xxServerError();

        verify(fightService, times(1)).getMostRecentFights();
    }
}