package com.example.reporter.service;

import com.example.reporter.entity.news.AlphaVantageNewsSentiment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class NotificationService {

    private WebClient webClient;

    public Mono<AlphaVantageNewsSentiment> process(String stockPicks) {
        log.info("Processing stock pick = " + stockPicks);
        return retrieveNews(stockPicks)
                .filter(newsSentiment -> {
                    System.out.println(newsSentiment.getOverallSentimentScore());
                    return newsSentiment.getOverallSentimentScore() > 0.15 || newsSentiment.getOverallSentimentScore() < -0.15;
                });
    }


    public Mono<AlphaVantageNewsSentiment> retrieveNews(String ticker) {
        // Abstract to environment variable
        webClient = WebClient.builder().baseUrl("https://www.alphavantage.co/query").build();

        final String function = "NEWS_SENTIMENT";
        String tickers = ticker;
        final String topic = "earnings";
        final String key = "FY0UIOQ6UFT77U4L";

        var res = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("query")
                        .queryParam("function", "{function}")
                        .queryParam("tickers", "{tickers}")
                        .queryParam("topic", "{topic}")
                        .queryParam("apikey", "{key}")
                        .build(function, tickers, topic, key))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AlphaVantageNewsSentiment.class);

        return res;

    }
}
