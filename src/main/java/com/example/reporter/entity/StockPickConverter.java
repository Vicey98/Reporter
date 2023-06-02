package com.example.reporter.entity;

public class StockPickConverter {

    public StockPick toStockPickEntity(String stockPick) {
        return StockPick.builder()
                .ticker(stockPick)
                .build();
    }
}
