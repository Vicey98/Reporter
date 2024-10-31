package com.example.reporter.service;

import com.example.reporter.dto.FightResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
public class FightHandler {

    private final FightService fightService;

    public Mono<ServerResponse> getFights(ServerRequest request) {
        log.info("Received request to get fights");
        return ServerResponse.ok()
                .body(Mono.just(fightService.getMostRecentFights()), FightResponseDTO.class);
    }
}
