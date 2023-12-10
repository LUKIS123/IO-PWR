CREATE TABLE IF NOT EXISTS Clients
(
    account_id INT PRIMARY KEY,
    nick       VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Admins
(
    account_id INT PRIMARY KEY,
    nick       VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Drivers
(
    account_id             INT PRIMARY KEY,
    nick                   VARCHAR(50) UNIQUE NOT NULL,
    duringRest             BOOLEAN,
    duringExecutionOfOrder BOOLEAN
);

CREATE TABLE IF NOT EXISTS Jobs
(
    jobId     INT PRIMARY KEY,
    driverId  INT,
    clientId  INT,
    CargoType VARCHAR(50),
    JobStatus VARCHAR(50),
    distance  DOUBLE PRECISION,
    weight    DOUBLE PRECISION,
    isPaid    bool,
    FOREIGN KEY (driverId) REFERENCES Drivers (account_id),
    FOREIGN KEY (clientId) REFERENCES Clients (account_id)
);

INSERT INTO Drivers (account_id, nick, duringRest, duringExecutionOfOrder)
VALUES (nextval('Drivers_ID_seq'), 'Janusz', FALSE, FALSE),
       (nextval('Drivers_ID_seq'), 'Wiktor', FALSE, FALSE);

INSERT INTO Clients (account_id, nick)
VALUES (nextval('Clients_ID_seq'),'Adam');

INSERT INTO Jobs (jobId, driverId, clientId, JobStatus, distance, weight, isPaid)
VALUES (nextval('Jobs_ID_seq'), 3, 3, 'VERIFIED', 200, 340, True),
       (nextval('Jobs_ID_seq'), 4, 3, 'PAID', 300, 80, True);


--test
SELECT * from Drivers;


CREATE SEQUENCE Jobs_ID_seq
    START WITH 3
    OWNED BY jobs.jobId
    INCREMENT BY 1;

CREATE SEQUENCE Drivers_ID_seq
    START WITH 3
    OWNED BY drivers.account_id
    INCREMENT BY 1;

CREATE SEQUENCE Clients_ID_seq
    START WITH 3
    OWNED BY admins.account_id
    INCREMENT BY 1;

CREATE SEQUENCE Admins_ID_seq
    START WITH 3
    OWNED BY clients.account_id
    INCREMENT BY 1;