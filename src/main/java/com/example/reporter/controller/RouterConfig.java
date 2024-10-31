package com.example.reporter.controller;

import com.example.reporter.service.FightHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@RequiredArgsConstructor
@Controller
@Slf4j
@CrossOrigin(maxAge = 3600)
public class RouterConfig {
    @Bean
    @CrossOrigin(maxAge = 3600)
    public RouterFunction<ServerResponse> routes(FightHandler fightHandler) {
        return RouterFunctions
                .route(GET("/api/fights"), fightHandler::getFights);
    }

}
