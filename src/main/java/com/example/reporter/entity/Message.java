package com.example.reporter.entity;

import com.example.reporter.entity.news.SentimentLabel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="my_entity_seq_gen")
    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="MY_ENTITY_SEQ", allocationSize = 1)
    private Integer id;

    private String symbol;

    private Double overallSentimentScore;

    @Enumerated(EnumType.STRING)
    private SentimentLabel overallSentimentLabel;

    private LocalDate messageDate;

    @Override
    public String toString() {
        return "Ticker %s is potentially %s with an overall sentiment score of %s at date %s"
                .formatted(symbol, overallSentimentLabel, overallSentimentScore, messageDate);
    }
}
