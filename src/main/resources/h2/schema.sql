DROP TABLE IF EXISTS mem;

CREATE TABLE mem
(
    mem_no  BIGINT NOT NULL AUTO_INCREMENT,
    mem_id  VARCHAR(100) NOT NULL,
    passwd  VARCHAR(80) NOT NULL,
    email   VARCHAR(100) NOT NULL,
    cell_no VARCHAR(100) NOT NULL,
    create_dt   TIMESTAMP NOT NULL,
    update_dt   TIMESTAMP NOT NULL,
    PRIMARY KEY (mem_no)
);

CREATE UNIQUE INDEX UK_01 ON mem (mem_id);

