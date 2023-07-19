package com.example.reporter.entity.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TickerSentiment {
    private String ticker;
    @JsonProperty("relevance_score")
    private String relevanceScore;
    @JsonProperty("ticker_sentiment_score")
    private String tickerSentimentScore;
    @JsonProperty("ticker_sentiment_label")
    private String tickerSentimentLabel;
}
