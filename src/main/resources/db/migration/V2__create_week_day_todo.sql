CREATE TABLE week (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    year        INT    NOT NULL,
    week_number INT    NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_week (year, week_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE day (
    id       BIGINT NOT NULL AUTO_INCREMENT,
    week_id  BIGINT NOT NULL,
    day_date DATE   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_day_week FOREIGN KEY (week_id) REFERENCES week (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE todo (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    day_id    BIGINT       NOT NULL,
    text      VARCHAR(500) NOT NULL,
    type      VARCHAR(20)  NOT NULL,
    important TINYINT(1)   NOT NULL DEFAULT 0,
    position  INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT fk_todo_day FOREIGN KEY (day_id) REFERENCES day (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
