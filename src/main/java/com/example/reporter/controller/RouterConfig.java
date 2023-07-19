package com.example.reporter.controller;

import com.example.reporter.entity.Message;
import com.example.reporter.service.StockPickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class RouterConfig {

    private final StockPickService stockPickService;

    @GetMapping("/messages")
    @ResponseBody
    public Collection<Message> getAllMessages() {
        log.info("Received request to get all saved messages");
        return stockPickService.getAllMessages();
    }

    @PostMapping("/stonks")
    @ResponseBody
    public Mono<String> processStockPicks(@RequestBody List stockPicks) {
        log.info("Received request " + stockPicks.toString());
        return stockPickService.handleStockPicks(stockPicks);
//        return "Stonks";
    }

}
