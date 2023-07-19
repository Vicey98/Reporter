package com.example.reporter.controller;

import com.example.reporter.entity.Message;
import com.example.reporter.entity.news.SentimentLabel;
import com.example.reporter.repository.MessageRepository;
import com.example.reporter.service.StockPickService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(RouterConfig.class)
class RouterConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private StockPickService stockPickService;

    @MockBean
    private MessageRepository messageRepository;

    @Test
    void shouldHandleStockPickCall() {
        var message = new Message(1,"AAPL", 0.37, SentimentLabel.BULLISH, LocalDate.now());
        var messageTwo = new Message(2,"TSLA", 0.37, SentimentLabel.BULLISH, LocalDate.now());
        var expectedMessageList = List.of(message, messageTwo);

        when(stockPickService.getAllMessages())
                .thenReturn(expectedMessageList);

        webTestClient.get()
                .uri("/messages")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Message.class)
                .hasSize(2)
                .contains(message, messageTwo);
    }
}