package com.example.reporter.controller;

import com.example.reporter.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Controller
public class RouterConfig {

//    @Autowired
//    public TransactionService transactionService;

//    @Bean
//    public RouterFunction<ServerResponse> routeTransactions(TransactionService transactionService) {
//        return route().GET("/transactions", serverRequest -> ServerResponse.ok().body(transactionService.getTransactions(), String.class))
//                .build();
//    }

    @Bean
    public RouterFunction<ServerResponse> routeTransactions(TransactionService transactionService) {
        return route().GET("/transactions", transactionService::getTransactions)
                .build();
    }

//    @RequestMapping("/person")
//    public String person() {
//        var p = transactionService.getTransactions();
//        return "personview";
//    }
}
