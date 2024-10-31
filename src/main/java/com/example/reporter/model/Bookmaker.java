package com.example.reporter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "odds")
@Builder
public class Bookmaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odds_id")
    private Long oddsId;

    @Column(name = "fight_id", insertable = false, updatable = false)
    private Long fightId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fight_id", nullable = false)
    private Fight fight;

    @Column(name = "bookmaker")
    private String bookmaker;

    @Column(name = "fighter1_odds")
    private String fighter1Odds;

    @Column(name = "fighter2_odds")
    private String fighter2Odds;

}