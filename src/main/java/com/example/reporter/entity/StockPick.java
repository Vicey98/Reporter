package com.example.reporter.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
public class StockPick {

    @Id
    public String ticker;

    public int no_of_comments;

    public String sentiment;

    public float sentiment_score;

    // Create all arg constructor
    public StockPick(String ticker, int no_of_comments, String sentiment, float sentiment_score) {
        this.ticker = ticker;
        this.no_of_comments = no_of_comments;
        this.sentiment = sentiment;
        this.sentiment_score = sentiment_score;
    }



}
