package com.example.reporter.service;

import com.example.reporter.converter.FightConverter;
import com.example.reporter.dto.FightResponseDTO;
import com.example.reporter.model.Bookmaker;
import com.example.reporter.model.Fight;
import com.example.reporter.repository.BookmakerRepository;
import com.example.reporter.repository.FightsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FightServiceTest {

    @Mock
    private FightsRepository fightRepository;

    @Mock
    private BookmakerRepository bookmakerRepository;

    @Mock
    private FightConverter fightConverter;

    private FightService fightService;

    @BeforeEach
    void setUp() {
        fightService = new FightService(fightRepository, bookmakerRepository, fightConverter);
    }

    @Test
    void getMostRecentFights_ShouldReturnFights_WhenDataExists() {
        LocalDate mostRecentDate = LocalDate.now();
        Fight fight1 = new Fight();
        fight1.setFightId(1L);
        Fight fight2 = new Fight();
        fight2.setFightId(2L);
        List<Fight> fights = Arrays.asList(fight1, fight2);

        Bookmaker bookmaker1 = new Bookmaker();
        bookmaker1.setFightId(1L);
        Bookmaker bookmaker2 = new Bookmaker();
        bookmaker2.setFightId(2L);
        List<Bookmaker> bookmakers = Arrays.asList(bookmaker1, bookmaker2);

        FightResponseDTO dto1 = new FightResponseDTO();
        FightResponseDTO dto2 = new FightResponseDTO();

        when(fightRepository.findMostRecentFightDate()).thenReturn(mostRecentDate);
        when(fightRepository.findByFightDate(mostRecentDate)).thenReturn(fights);
        when(bookmakerRepository.findByFightIds(Arrays.asList(1L, 2L))).thenReturn(bookmakers);
        when(fightConverter.toDTO(eq(fight1), anyList())).thenReturn(dto1);
        when(fightConverter.toDTO(eq(fight2), anyList())).thenReturn(dto2);

        List<FightResponseDTO> result = fightService.getMostRecentFights();

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        verify(fightRepository).findMostRecentFightDate();
        verify(fightRepository).findByFightDate(mostRecentDate);
        verify(bookmakerRepository).findByFightIds(Arrays.asList(1L, 2L));
        verify(fightConverter, times(2)).toDTO(any(Fight.class), anyList());
    }

    @Test
    void getMostRecentFights_ShouldHandleFightsWithNoBookmakers() {
        LocalDate mostRecentDate = LocalDate.now();
        Fight fight = new Fight();
        fight.setFightId(1L);
        List<Fight> fights = Collections.singletonList(fight);

        FightResponseDTO dto = new FightResponseDTO();

        when(fightRepository.findMostRecentFightDate()).thenReturn(mostRecentDate);
        when(fightRepository.findByFightDate(mostRecentDate)).thenReturn(fights);
        when(bookmakerRepository.findByFightIds(Collections.singletonList(1L))).thenReturn(Collections.emptyList());
        when(fightConverter.toDTO(eq(fight), eq(Collections.emptyList()))).thenReturn(dto);

        List<FightResponseDTO> result = fightService.getMostRecentFights();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(fightRepository).findMostRecentFightDate();
        verify(fightRepository).findByFightDate(mostRecentDate);
        verify(bookmakerRepository).findByFightIds(Collections.singletonList(1L));
        verify(fightConverter).toDTO(eq(fight), eq(Collections.emptyList()));
    }

    @Test
    void getMostRecentFights_ShouldHandleExceptionInRepository() {
        when(fightRepository.findMostRecentFightDate()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> fightService.getMostRecentFights());
        verify(fightRepository).findMostRecentFightDate();
        verifyNoMoreInteractions(fightRepository);
        verifyNoInteractions(bookmakerRepository, fightConverter);
    }
}