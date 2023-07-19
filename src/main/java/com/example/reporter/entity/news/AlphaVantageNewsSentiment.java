package com.example.reporter.entity.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.text.DecimalFormat;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlphaVantageNewsSentiment {

    private String items;

    @JsonProperty("sentiment_score_definition")
    private String sentimentScoreDefinition;

    @JsonProperty("relevance_score_definition")
    private String relevanceScoreDefinition;

    @JsonProperty("feed")
    private NewsPiece[] newsPieces;

    @Getter(AccessLevel.NONE)
    private Double overallSentimentScore;

    @Getter(AccessLevel.NONE)
    private SentimentLabel overallSentimentLabel;

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public SentimentLabel getOverallSentimentLabel() {
        calculateOverallSentimentLabel();
        return this.overallSentimentLabel;
    }

    public void calculateOverallSentimentLabel(){
        var label = "";
        if (this.overallSentimentScore >= 0.35) {
            this.overallSentimentLabel = SentimentLabel.BULLISH;
        } else if (this.overallSentimentScore < 0.35 && this.overallSentimentScore >= 0.15) {
            this.overallSentimentLabel = SentimentLabel.SOMEWHAT_BULLISH;
        } else if (this.overallSentimentScore < -0.15 && this.overallSentimentScore > 0.15) {
            this.overallSentimentLabel = SentimentLabel.NEUTRAL;
        } else if (this.overallSentimentScore > -0.35 && this.overallSentimentScore <= -0.15) {
            this.overallSentimentLabel = SentimentLabel.SOMEWHAT_BEARISH;
        } else if (this.overallSentimentScore < -0.15 && this.overallSentimentScore <= -0.35) {
            this.overallSentimentLabel = SentimentLabel.BEARISH;
        } else {
            this.overallSentimentLabel = SentimentLabel.INCONCLUSIVE;
        }
    }

    public double getOverallSentimentScore() {
        if (this.overallSentimentScore == null) {
            calculateOverallSentiment();
        }
        return this.overallSentimentScore;
    }

    public void calculateOverallSentiment() {
        var overallSentimentSum = List.of(this.newsPieces)
                .stream()
                .mapToDouble(piece -> piece.getOverallSentimentScore())
                .sum();
        var overallSentimentAverage = Double.valueOf(decimalFormat.format(overallSentimentSum / newsPieces.length));
        this.overallSentimentScore = overallSentimentAverage;
    }

    // Todo - calculate relevance score
}
