package com.example.reporter.converter;

import com.example.reporter.dto.FightResponseDTO;
import com.example.reporter.model.Bookmaker;
import com.example.reporter.model.Fight;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FightConverter {
    public FightResponseDTO toDTO(Fight fight, List<Bookmaker> bookmakers) {
        FightResponseDTO dto = new FightResponseDTO();
        dto.setFighter1(fight.getFighter1());
        dto.setFighter2(fight.getFighter2());
        dto.setDate(fight.getFightDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        dto.setTime(fight.getFightTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " " + fight.getTimeZone());
        dto.setWeightClass(fight.getWeightClass());
        dto.setBookmakers(bookmakers.stream()
                .map(this::toBookmakerDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private FightResponseDTO.BookmakerDTO toBookmakerDTO(Bookmaker bookmaker) {
        FightResponseDTO.BookmakerDTO dto = new FightResponseDTO.BookmakerDTO();
        dto.setName(bookmaker.getBookmaker());
        dto.setFighter1Odds(bookmaker.getFighter1Odds());
        dto.setFighter2Odds(bookmaker.getFighter2Odds());
        return dto;
    }
}
