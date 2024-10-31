CREATE TABLE IF NOT EXISTS fights (
    fight_id SERIAL PRIMARY KEY,
    fighter1 VARCHAR(100) NOT NULL,
    fighter2 VARCHAR(100) NOT NULL,
    fight_date DATE NOT NULL,
    fight_time TIME,
    time_zone VARCHAR(5),
    weight_class VARCHAR(50)
);