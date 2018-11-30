DROP TABLE IF EXISTS heroes;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE hero
(
  id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name          VARCHAR(30)      NOT NULL,
  universe      VARCHAR(100)      NOT NULL,
  power         INTEGER           NOT NULL,
  description   VARCHAR(255),
  alive         BOOLEAN           NOT NULL
);
CREATE UNIQUE INDEX hero_unique_name ON hero (name);

INSERT INTO hero (name, universe, power, description, alive) VALUES
  ('Hulk', 'Marvel', 80, 'ЛООООМААААТЬ', TRUE),
  ('Iron man', 'Marvel', 50, 'Рядовой шутник, гений, миллиардер, плейбой, филантроп', TRUE),
  ('Captain America', 'Marvel', 1, 'Щитоносец, унтерсолдат', TRUE),
  ('Spider-man', 'Marvel', 70, 'Спуди', FALSE),
  ('Hellboy', 'Dark Horse Comics', 40, 'Зверь апокалипсиса', TRUE),
  ('Spawn', 'Image Comics', 99, 'Демон с человеческой душой', TRUE),
  ('Teenage Mutant Ninja Turtles', 'Mirage Studios', 70, 'Мы не жалкие букашки, Супер-ниндзя-черепашки', TRUE),
  ('Batman', 'DC', 3, 'Мальчик из колодца', TRUE),
  ('Super-man', 'DC', 99, 'Криптонский маг', FALSE),
  ('Flash', 'DC', 10, 'Марафонец', TRUE);

