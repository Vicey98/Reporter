package com.example.reporter.repository;

import com.example.reporter.testcontainer.TestBase;
import com.example.reporter.model.Bookmaker;
import com.example.reporter.model.Fight;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class BookmakerRepositoryTest extends TestBase {

    @Autowired
    private BookmakerRepository bookmakerRepository;

    @Autowired
    private FightsRepository fightsRepository;

    private Fight fight1;
    private Fight fight2;
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        fight1 = generateFight("Fighter1", "Fighter2", "Lightweight");
        fight1 = fightsRepository.save(fight1);

        fight2 = generateFight("Fighter3", "Fighter4", "Heavyweight");
        fight2 = fightsRepository.save(fight2);
    }

    @Test
    void findByFightIds_shouldReturnCorrectBookmakers() {
        Bookmaker bookmaker1 = createBookmaker(fight1, "Ladbroke", "1.50", "2.50");
        Bookmaker bookmaker2 = createBookmaker(fight1, "Sportsbet", "1.55", "2.45");
        Bookmaker bookmaker3 = createBookmaker(fight2, "Ladbroke", "1.60", "2.40");

        bookmakerRepository.saveAll(Arrays.asList(bookmaker1, bookmaker2, bookmaker3));

        List<Bookmaker> result = bookmakerRepository.findByFightIds(Arrays.asList(fight1.getFightId(), fight2.getFightId()));

        assertThat(result).hasSize(3)
                .extracting("bookmaker")
                .containsExactlyInAnyOrder("Ladbroke", "Sportsbet", "Ladbroke");
    }

    @Test
    void save_shouldPersistBookmaker() {
        Bookmaker bookmaker = createBookmaker(fight1, "Ladbroke", "1.50", "2.50");

        Bookmaker savedBookmaker = bookmakerRepository.save(bookmaker);

        assertThat(savedBookmaker.getOddsId()).isNotNull();
        assertThat(savedBookmaker.getFight().getFightId()).isEqualTo(fight1.getFightId());
        assertThat(savedBookmaker.getBookmaker()).isEqualTo("Ladbroke");
        assertThat(savedBookmaker.getFighter1Odds()).isEqualTo("1.50");
        assertThat(savedBookmaker.getFighter2Odds()).isEqualTo("2.50");
    }

    @Test
    void findById_shouldReturnBookmaker_whenExists() {
        Bookmaker bookmaker = createBookmaker(fight1, "Ladbroke", "1.50", "2.50");
        bookmaker = bookmakerRepository.save(bookmaker);

        Optional<Bookmaker> result = bookmakerRepository.findById(bookmaker.getOddsId());

        assertThat(result).isPresent();
        assertThat(result.get().getBookmaker()).isEqualTo("Ladbroke");
    }


    private Fight generateFight(String fighter1, String fighter2, String weightClass) {
        return Fight.builder()
                .fighter1(fighter1)
                .fighter2(fighter2)
                .fightDate(LocalDate.now().plusDays(30))
                .fightTime(LocalTime.of(20, 0))
                .timeZone("UTC")
                .weightClass(weightClass)
                .build();
    }

    private Bookmaker createBookmaker(Fight fight, String bookmakerName, String fighter1Odds, String fighter2Odds) {
        return Bookmaker.builder()
                .oddsId(Math.abs(random.nextLong()) % 1000000 + 1)
                .fight(fight)
                .bookmaker(bookmakerName)
                .fighter1Odds(fighter1Odds)
                .fighter2Odds(fighter2Odds)
                .build();
    }
}