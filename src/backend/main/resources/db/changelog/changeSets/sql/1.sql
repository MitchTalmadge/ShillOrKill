-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-10-09 02:35:23.496

-- tables
-- Table: TWEET
CREATE TABLE TWEET (
    ID bigint  NOT NULL,
    STATUS_ID bigint  NOT NULL,
    CREATED_AT timestamp  NOT NULL,
    TEXT text  NOT NULL,
    AUTHOR_NAME text  NOT NULL,
    AUTHOR_IMAGE_URL text  NOT NULL,
    SHILLS int  NOT NULL,
    KILLS int  NOT NULL,
    WRONGS int  NOT NULL,
    CONSTRAINT TWEET_pk PRIMARY KEY (ID)
);

-- sequences
-- Sequence: HIBERNATE_SEQUENCE
CREATE SEQUENCE HIBERNATE_SEQUENCE
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
START WITH 1
CACHE 1
NO CYCLE
;

-- End of file.

