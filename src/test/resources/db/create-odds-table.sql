CREATE TABLE IF NOT EXISTS odds (
    odds_id SERIAL PRIMARY KEY,
    fight_id INTEGER REFERENCES fights(fight_id),
    bookmaker VARCHAR(100) NOT NULL,
    fighter1_odds VARCHAR(10) NOT NULL,
    fighter2_odds VARCHAR(10) NOT NULL,
    UNIQUE (fight_id, bookmaker)
);