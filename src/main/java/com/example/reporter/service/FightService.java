package com.example.reporter.service;

import com.example.reporter.converter.FightConverter;
import com.example.reporter.dto.FightResponseDTO;
import com.example.reporter.model.Bookmaker;
import com.example.reporter.model.Fight;
import com.example.reporter.repository.BookmakerRepository;
import com.example.reporter.repository.FightsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FightService {
    private final FightsRepository fightRepository;
    private final BookmakerRepository bookmakerRepository;
    private final FightConverter fightConverter;
    private static final String RECENT_FIGHTS_CACHE = "recentFights";

    @Cacheable(value = RECENT_FIGHTS_CACHE, key = "'all'")
    @Transactional(readOnly = true)
    public List<FightResponseDTO> getMostRecentFights() {
        log.info("Compiling most recent fights");
        var mostRecentDate = fightRepository.findMostRecentFightDate();
        var fights = fightRepository.findByFightDate(mostRecentDate);

        var fightIds = fights.stream().map(Fight::getFightId).collect(Collectors.toList());
        var bookmakers = bookmakerRepository.findByFightIds(fightIds);

        var bookmakersByFightId = bookmakers.stream()
                .collect(Collectors.groupingBy(Bookmaker::getFightId));

        return fights.stream()
                .map(fight -> fightConverter.toDTO(fight, bookmakersByFightId.getOrDefault(fight.getFightId(), List.of())))
                .collect(Collectors.toList());
    }
}
