package com.example.reporter.integration;

import com.example.reporter.converter.FightConverter;
import com.example.reporter.model.Bookmaker;
import com.example.reporter.model.Fight;
import com.example.reporter.repository.BookmakerRepository;
import com.example.reporter.repository.FightsRepository;
import com.example.reporter.service.FightService;
import com.example.reporter.testcontainer.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class FightServiceIT extends TestBase {

    @Autowired
    private FightService fightService;

    @Autowired
    private FightsRepository fightsRepository;

    @Autowired
    private BookmakerRepository bookmakerRepository;

    @Autowired
    private FightConverter fightConverter;

    @BeforeEach
    void setUp() {
        fightsRepository.deleteAll();
        bookmakerRepository.deleteAll();
    }

    @Test
    void shouldRetrieveMostRecentFights() {
        var previousDayFights = List.of(
            generateFight("Fighter1", "Fighter2", "Lightweight", LocalDate.now().minusDays(1)),
            generateFight("Fighter3", "Fighter4", "Heavyweight", LocalDate.now().minusDays(1))
        );
        var mostRecentFights = List.of(
            generateFight("Fighter5", "Fighter6", "Lightweight", LocalDate.now()),
            generateFight("Fighter7", "Fighter8", "Heavyweight", LocalDate.now())
        );
        
        fightsRepository.saveAll(previousDayFights);
        fightsRepository.saveAll(mostRecentFights);

        assertThat(fightsRepository.findMostRecentFightDate()).isEqualTo(LocalDate.now());
        assertThat(fightsRepository.findByFightDate(LocalDate.now())).hasSize(2);

        var bookmakers = List.of(
            createBookmaker(mostRecentFights.get(0), "Ladbroke", "1.50", "2.50", 8L),
            createBookmaker(mostRecentFights.get(0), "Sportsbet", "1.55", "2.45", 8L),
            createBookmaker(mostRecentFights.get(1), "Ladbroke", "1.60", "2.40", 9L),
            createBookmaker(mostRecentFights.get(1), "Sportsbet", "1.55", "2.45", 9L)
        );

        bookmakerRepository.saveAll(bookmakers);
//        log.info("Bookmakers: {}", bookmakerRepository.findAll().toString());

        var bookmakersByFightId = bookmakerRepository.findByFightIds(List.of(8L, 9L)).stream()
            .collect(Collectors.groupingBy(Bookmaker::getFightId));
        assertThat(bookmakersByFightId).hasSize(2);
        assertThat(bookmakersByFightId.get(8L)).hasSize(2);
        assertThat(bookmakersByFightId.get(9L)).hasSize(2);

        var expectedFightsDTO = mostRecentFights.stream()
            .map(fight -> fightConverter.toDTO(fight, bookmakersByFightId.getOrDefault(fight.getFightId(), List.of())))
            .collect(Collectors.toList());

        var fights = fightService.getMostRecentFights();
        assertThat(fights).hasSize(2);
        assertThat(fights.get(0).getFighter1()).isEqualTo("Fighter5");
        assertThat(fights.get(0).getFighter2()).isEqualTo("Fighter6");
        assertThat(fights.get(0).getWeightClass()).isEqualTo("Lightweight");
        assertThat(fights.get(0).getBookmakers()).hasSize(2);
        assertThat(fights.get(1).getFighter1()).isEqualTo("Fighter7");
        assertThat(fights.get(1).getFighter2()).isEqualTo("Fighter8");
        assertThat(fights.get(1).getWeightClass()).isEqualTo("Heavyweight");
        assertThat(fights.get(1).getBookmakers()).hasSize(2);
        assertThat(fights).isEqualTo(expectedFightsDTO);
    }

    private Bookmaker createBookmaker(Fight fight, String bookie, String odds1, String odds2, Long fightId) {
        return Bookmaker.builder()
                .fightId(fightId)
                .fight(fight)
                .bookmaker(bookie)
                .fighter1Odds(odds1)
                .fighter2Odds(odds2)
                .build();
    }

    private Fight generateFight(String fighter1, String fighter2, String lightweight, LocalDate fightDate) {
        return Fight.builder()
                .fighter1(fighter1)
                .fighter2(fighter2)
                .weightClass(lightweight)
                .fightDate(fightDate)
                .fightTime(LocalTime.now())
                .timeZone("UTC")
                .bookmakers(List.of())
                .build();
    }
}
