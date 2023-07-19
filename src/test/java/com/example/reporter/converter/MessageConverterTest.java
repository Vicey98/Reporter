package com.example.reporter.converter;

import com.example.reporter.entity.news.AlphaVantageNewsSentiment;
import com.example.reporter.entity.news.SentimentLabel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.*;


class MessageConverterTest {

    private final MessageConverter messageConverter = new MessageConverter();

    @Test
    void shouldConvertToMessage() {
        var newsSentiment = generateRandomNewsSentiment();
        var message = messageConverter.convertToMessage("APPL", newsSentiment);
        assertSoftly(softly -> {
            softly.assertThat(message.getSymbol()).isEqualTo("APPL");
            softly.assertThat(message.getOverallSentimentScore()).isEqualTo(newsSentiment.getOverallSentimentScore());
            softly.assertThat(message.getOverallSentimentLabel()).isEqualTo(newsSentiment.getOverallSentimentLabel());
            softly.assertThat(message.getMessageDate()).isEqualTo(LocalDate.now());
        });
    }

    public static AlphaVantageNewsSentiment generateRandomNewsSentiment() {
        return AlphaVantageNewsSentiment.builder()
                .overallSentimentScore(new Random().nextDouble())
                .build();
    }
}