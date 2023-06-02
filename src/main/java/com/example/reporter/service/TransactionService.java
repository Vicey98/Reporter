package com.example.reporter.service;

import com.example.reporter.entity.StockPick;
import com.example.reporter.repository.StockPickRepository;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
@Slf4j
public class TransactionService {

    public WebClient webClient;

    @Autowired
    public StockPickRepository stockPickRepository;

    public TransactionService(StockPickRepository stockPickRepository) {
        this.webClient = WebClient.builder().baseUrl("https://tradestie.com/api/v1/apps/reddit").build();
        this.stockPickRepository = stockPickRepository;
    }

    public Mono<ServerResponse> getTransactions(ServerRequest serverRequest) {
        log.info("Getting transactions");
        var stockPick = new StockPick("AAPL", 20, "Bullish", 0.2f);
        return ServerResponse.ok().body(Mono.just(stockPick), StockPick.class);
    }

    // todo - add filtering somehow
//    public Mono<ServerResponse> getTransactions() {
//        log.info("Getting transactions");
//        RateLimiter rateLimiter = RateLimiter.create(59);
//        rateLimiter.acquire(1);
//        var res =  webClient.get()
//                .retrieve()
//                .bodyToMono(StockPick[].class)
//                .block();
//        Arrays.stream(res)
//                .forEach(transaction -> {
//                    log.info("Saving transaction: " + transaction.toString() + " with ticker: " + transaction.ticker);
//                    stockPickRepository.save(transaction);
//                });
//        return ServerResponse.ok().body(res, StockPick[].class);
//    }
}
