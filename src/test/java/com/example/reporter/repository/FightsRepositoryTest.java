package com.example.reporter.repository;

import com.example.reporter.testcontainer.TestBase;
import com.example.reporter.model.Fight;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class FightsRepositoryTest extends TestBase {

    @Autowired
    private FightsRepository fightsRepository;

    @BeforeEach
    void setUp() {
        fightsRepository.deleteAll();
    }

    @Test
    void findMostRecentFightDate_ShouldReturnMostRecentDate_WhenFightsExist() {
        LocalDate date1 = LocalDate.now().minusDays(1);
        LocalDate date2 = LocalDate.now();
        fightsRepository.save(createFight("Fighter1", "Fighter2", date1));
        fightsRepository.save(createFight("Fighter3", "Fighter4", date2));

        LocalDate result = fightsRepository.findMostRecentFightDate();

        assertEquals(date2, result);
    }

    @Test
    void findMostRecentFightDate_ShouldReturnNull_WhenNoFightsExist() {
        LocalDate result = fightsRepository.findMostRecentFightDate();

        assertNull(result);
    }

    @Test
    void findByFightDate_ShouldReturnFights_WhenFightsExistForDate() {
        LocalDate date = LocalDate.now();
        Fight fight1 = createFight("Fighter1", "Fighter2", date);
        Fight fight2 = createFight("Fighter3", "Fighter4", date);
        fightsRepository.saveAll(List.of(fight1, fight2));

        List<Fight> result = fightsRepository.findByFightDate(date);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(f -> f.getFighter1().equals("Fighter1")));
        assertTrue(result.stream().anyMatch(f -> f.getFighter1().equals("Fighter3")));
    }

    private Fight createFight(String fighter1, String fighter2, LocalDate fightDate) {
        return Fight.builder()
                .fighter1(fighter1)
                .fighter2(fighter2)
                .fightDate(fightDate)
                .fightTime(LocalTime.NOON)
                .timeZone("UTC")
                .weightClass("Lightweight")
                .build();
    }
}