package com.example.reporter.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.reporter.converter.MessageConverter;
import com.example.reporter.entity.news.AlphaVantageNewsSentiment;
import com.example.reporter.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
class StockPickServiceTest {

    @Autowired
    public StockPickService stockPickService;

    @Autowired
    public MessageRepository messageRepository;

    @MockBean
    public NotificationService notificationService;

    @MockBean
    public SNSService snsService;

    @Autowired
    public WebTestClient webTestClient;

    public MessageConverter messageConverter;

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(StockPickService.class)).addAppender(logWatcher);

        messageConverter = new MessageConverter();
    }

    @Test
    void shouldHandleSingleStockPicks() {
        var messageRequest = List.of("ICU", "GME", "TSLA", "BARK", "LUNR");
        var dummyNewsSentiment = new AlphaVantageNewsSentiment();
        dummyNewsSentiment.setOverallSentimentScore(0.36);

        when(notificationService.process(any())).thenReturn(Mono.just(dummyNewsSentiment));
        doNothing().when(snsService).pubTopic(any());

        webTestClient.post()
                .uri("/stonks")
                .body(Mono.just(messageRequest), List.class)
                .exchange()
                .expectStatus().isOk();

        assertThat(logWatcher.list.get(0).toString()).isEqualTo("[INFO] Starting processing on stock pick list");
        assertThat(messageRepository.findAll().size()).isEqualTo(5);
    }
}