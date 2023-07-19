package com.example.reporter.entity;

import com.example.reporter.entity.news.AlphaVantageNewsSentiment;
import com.example.reporter.entity.news.NewsPiece;
import com.example.reporter.entity.news.SentimentLabel;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AlphaVantageNewsSentimentTest {

    @SneakyThrows
    @Test
    void matchesJson() {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File file = new ClassPathResource("/sample-AV-news-sentiment-response.json").getFile();
        AlphaVantageNewsSentiment json = mapper.readValue(file, AlphaVantageNewsSentiment.class);

        assertNotNull(json);
    }

    @Test
    void shouldCalculateOverallSentimentCorrectly() {
        AlphaVantageNewsSentiment alphaVantageNewsSentiment = new AlphaVantageNewsSentiment();
        NewsPiece newsPieceOne = new NewsPiece();
        NewsPiece newsPieceTwo = new NewsPiece();
        newsPieceOne.setOverallSentimentScore(0.3);
        newsPieceTwo.setOverallSentimentScore(0.1);

        alphaVantageNewsSentiment.setNewsPieces(new NewsPiece[] {newsPieceOne, newsPieceTwo});
        alphaVantageNewsSentiment.calculateOverallSentiment();

        assertThat(alphaVantageNewsSentiment.getOverallSentimentScore()).isEqualTo(0.2);
    }

    @Test
    void shouldCalculateOverallSentimentLabel() {
        AlphaVantageNewsSentiment alphaVantageNewsSentiment = new AlphaVantageNewsSentiment();
        alphaVantageNewsSentiment.setOverallSentimentScore(0.36);

        assertThat(alphaVantageNewsSentiment.getOverallSentimentLabel()).isEqualTo(SentimentLabel.BULLISH);

        alphaVantageNewsSentiment.setOverallSentimentScore(-0.36);

        assertThat(alphaVantageNewsSentiment.getOverallSentimentLabel()).isEqualTo(SentimentLabel.BEARISH);
    }
}