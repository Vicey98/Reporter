package com.example.reporter.converter;

import com.example.reporter.entity.Message;
import com.example.reporter.entity.news.AlphaVantageNewsSentiment;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class MessageConverter {

    public Message convertToMessage(String symbol, AlphaVantageNewsSentiment newsSentiment) {
        log.debug("Converting news %s to message".formatted(newsSentiment.toString()));
        return Message.builder()
                .symbol(symbol)
                .overallSentimentScore(newsSentiment.getOverallSentimentScore())
                .overallSentimentLabel(newsSentiment.getOverallSentimentLabel())
                .messageDate(LocalDate.now())
                .build();
    }
}
