CREATE TABLE sport (
	sport_id		SERIAL,
	name			VARCHAR(20) NOT NULL,
	last_updated	TIMESTAMP,
	PRIMARY KEY (sport_id)
);

CREATE TABLE division (
	division_id		SERIAL,
	sport_id		INTEGER NOT NULL,
	name			VARCHAR(20) NOT NULL,
	PRIMARY KEY (division_id),
	FOREIGN KEY (sport_id) REFERENCES sport (sport_id)
);

CREATE TABLE team (
	team_id			SERIAL,
	division_id		INTEGER NOT NULL,
	name			VARCHAR(60) NOT NULL,
	PRIMARY KEY (team_id),
	FOREIGN KEY (division_id) REFERENCES division (division_id)
);

CREATE TABLE result (
	result_guid		VARCHAR(256) NOT NULL,
	division_id		INTEGER NOT NULL,
	team_x_id		INTEGER NOT NULL,
	team_y_id		INTEGER NOT NULL,
	score_x			INTEGER NOT NULL,
	score_y			INTEGER NOT NULL,
	description		VARCHAR(256),
	pub_date		TIMESTAMP NOT NULL,
	link 			VARCHAR(256),
	revision		INTEGER DEFAULT 1,
	PRIMARY KEY (result_guid),
	FOREIGN KEY (division_id) REFERENCES division (division_id),
	FOREIGN KEY (team_x_id) REFERENCES team (team_id),
	FOREIGN KEY (team_y_id) REFERENCES team (team_id)
);

CREATE TABLE result_history (
	result_guid		VARCHAR(256) NOT NULL,
	revision		INTEGER NOT NULL,
	division_id		INTEGER NOT NULL,
	team_x_id		INTEGER NOT NULL,
	team_y_id		INTEGER NOT NULL,
	score_x			INTEGER NOT NULL,
	score_y			INTEGER NOT NULL,
	description		VARCHAR(256),
	pub_date		TIMESTAMP NOT NULL,
	link 			VARCHAR(256),
	PRIMARY KEY (result_guid, revision),
	FOREIGN KEY (division_id) REFERENCES division (division_id),
	FOREIGN KEY (team_x_id) REFERENCES team (team_id),
	FOREIGN KEY (team_y_id) REFERENCES team (team_id)
);