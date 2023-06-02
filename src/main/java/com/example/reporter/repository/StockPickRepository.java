package com.example.reporter.repository;

import com.example.reporter.entity.StockPick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPickRepository extends JpaRepository<StockPick, String> {

    StockPick findByTicker(String ticker);
}
