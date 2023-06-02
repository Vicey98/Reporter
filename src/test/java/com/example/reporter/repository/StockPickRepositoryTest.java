package com.example.reporter.repository;

import com.example.reporter.entity.StockPick;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockPickRepositoryTest {

    @Autowired
    private StockPickRepository stockPickRepository;

    @Test
    void shouldSaveRandomStockPick() {
        StockPick stockPick = StockPick.builder()
                .ticker("AAPL")
                .no_of_comments(20)
                .sentiment("Bullish")
                .sentiment_score(0.2f)
                .build();

        stockPickRepository.save(stockPick);
        assertEquals(stockPickRepository.findByTicker("AAPL").ticker, stockPick.ticker);
    }
}