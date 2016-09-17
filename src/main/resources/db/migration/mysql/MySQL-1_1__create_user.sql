CREATE TABLE User (
	ID          BIGINT PRIMARY KEY   AUTO_INCREMENT,
	FIRST_NAME  varchar(64) NOT NULL,
	LAST_NAME   varchar(64) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

insert into User (FIRST_NAME, LAST_NAME) values ('Laijin', 'Lu');