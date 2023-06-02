package com.example.reporter.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class TransactionServiceTest {

    @Autowired
    public TransactionService transactionService;

    public WebClient.Builder webClient;

//    public StockPickRepository stockPickRepository;

    @Mock
    ServerRequest serverRequest;

    public String testJson = "[{\"no_of_comments\":46,\"sentiment\":\"Bullish\",\"sentiment_score\":0.232,\"ticker\":\"AI\"},{\"no_of_comments\":9,\"sentiment\":\"Bearish\",\"sentiment_score\":-0.107,\"ticker\":\"NVDA\"},{\"no_of_comments\":3,\"sentiment\":\"Bullish\",\"sentiment_score\":0.433,\"ticker\":\"QQQ\"},{\"no_of_comments\":3,\"sentiment\":\"Bullish\",\"sentiment_score\":0.087,\"ticker\":\"TGT\"}]";

    @Test
    void shouldRetrieveTransaction() {
//        var transactionService = new TransactionService();
//        when(any().retrieve()).thenReturn(testJson);
        var res = transactionService.getTransactions(serverRequest);
        assertNotNull(res);

    }

}