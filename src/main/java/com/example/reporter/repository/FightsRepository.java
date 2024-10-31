package com.example.reporter.repository;

import com.example.reporter.model.Fight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FightsRepository extends JpaRepository<Fight, Long> {

    @Query("SELECT MAX(f.fightDate) FROM Fight f")
    LocalDate findMostRecentFightDate();

    @Query("SELECT f FROM Fight f WHERE f.fightDate = :date ORDER BY f.fightTime")
    List<Fight> findByFightDate(@Param("date") LocalDate date);
}
