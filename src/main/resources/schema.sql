DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
    ID IDENTITY NOT NULL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    ROLE VARCHAR(10) NOT NULL
);