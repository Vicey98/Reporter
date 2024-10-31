INSERT INTO odds (fight_id, bookmaker, fighter1_odds, fighter2_odds) VALUES
-- Total implied probability ~98%
(1, 'Sportsbet', '1.90', '2.10'),
(1, 'Ladbrokes', '1.85', '2.15'),
(1, 'TAB', '1.95', '2.05'),

-- Total implied probability ~96%
(2, 'Sportsbet', '2.20', '2.00'),
(2, 'Ladbrokes', '2.15', '2.05'),
(2, 'TAB', '2.25', '1.95'),

-- Normal market with ~102% probability
(3, 'Sportsbet', '1.80', '2.20'),
(3, 'Ladbrokes', '1.75', '2.25'),
(3, 'TAB', '1.85', '2.15'),

-- Total implied probability ~97%
(4, 'Sportsbet', '1.95', '2.15'),
(4, 'Ladbrokes', '2.00', '2.10'),
(4, 'TAB', '1.90', '2.20'),

-- Normal market with ~103% probability
(5, 'Sportsbet', '1.50', '2.75'),
(5, 'Ladbrokes', '1.48', '2.80'),
(5, 'TAB', '1.52', '2.70');