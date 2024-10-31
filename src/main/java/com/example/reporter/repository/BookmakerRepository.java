package com.example.reporter.repository;

import com.example.reporter.model.Bookmaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmakerRepository extends JpaRepository<Bookmaker, Long> {
    @Query("SELECT b FROM Bookmaker b WHERE b.fightId IN :fightIds")
    List<Bookmaker> findByFightIds(@Param("fightIds") List<Long> fightIds);
}
