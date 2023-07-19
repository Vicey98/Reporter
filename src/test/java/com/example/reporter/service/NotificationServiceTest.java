package com.example.reporter.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.reporter.entity.news.AlphaVantageNewsSentiment;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @MockBean
    private SNSService snsService;

    private ListAppender<ILoggingEvent> logWatcher;

    // Todo add real call?

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(StockPickService.class)).addAppender(logWatcher);
    }

    @SneakyThrows
    @Test
    void shouldRetrieveAndProcessNews() {
        // Setup AlphaVantage call stub
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File file = new ClassPathResource("/sample-AV-news-sentiment-response.json").getFile();
        AlphaVantageNewsSentiment expectedOutput = mapper.readValue(file, AlphaVantageNewsSentiment.class);

        var output = notificationService.process("SIX");

        assertThat(output.block().toString().equals(expectedOutput));
    }
}