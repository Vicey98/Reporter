package com.example.reporter.entity.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsPiece {
    private String title;

    private String url;

    @JsonProperty("time_published")
    private String timePublished;

    private String summary;

    private String source;

    @JsonProperty("overall_sentiment_score")
    private double overallSentimentScore;

    @JsonProperty("overall_sentiment_label")
    private String overallSentimentLabel;

    @JsonProperty("ticker_sentiment")
    private TickerSentiment[] tickerSentiments;

}
