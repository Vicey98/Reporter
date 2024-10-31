package com.example.reporter.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FightResponseDTO implements Serializable {
    private String fighter1;
    private String fighter2;
    private String date;
    private String time;
    private String weightClass;
    private List<BookmakerDTO> bookmakers;

    @Data
    public static class BookmakerDTO {
        private String name;
        private String fighter1Odds;
        private String fighter2Odds;
    }
}
