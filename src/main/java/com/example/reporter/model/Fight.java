package com.example.reporter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fights")
@Builder
public class Fight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fight_id")
    private Long fightId;

    @Column(name = "fighter1")
    private String fighter1;

    @Column(name = "fighter2")
    private String fighter2;

    @Column(name = "fight_date")
    private LocalDate fightDate;

    @Column(name = "fight_time")
    private LocalTime fightTime;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "weight_class")
    private String weightClass;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fight_id")
    @JsonIgnore
    private List<Bookmaker> bookmakers = new ArrayList<>();

}