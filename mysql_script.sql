DROP DATABASE IF EXISTS quarkus_db;
CREATE DATABASE IF NOT EXISTS quarkus_db;
USE quarkus_db;

CREATE TABLE manufacturer( brand VARCHAR(100)  PRIMARY KEY );

CREATE TABLE av( id INTEGER PRIMARY KEY,
                  brand VARCHAR(100),
                  model VARCHAR(100) NOT NULL,
                  FOREIGN KEY (brand) REFERENCES manufacturer(brand) );
                  
                  
CREATE TABLE apilot_dev( company VARCHAR(100) PRIMARY KEY );

CREATE TABLE apilot( id INTEGER PRIMARY KEY,
                  company VARCHAR(100),
                  model VARCHAR(100) NOT NULL,
                  FOREIGN KEY (company) REFERENCES apilot_dev(company) );
                  

CREATE TABLE user( id INTEGER PRIMARY KEY,
                  name VARCHAR(100),
                  age INTEGER );

CREATE TABLE employee( eid INTEGER PRIMARY KEY,
						ename VARCHAR(100) NOT NULL);


CREATE TABLE purchase( id INTEGER PRIMARY KEY,
                        userId INTEGER,
                        avId INTEGER,
                        FOREIGN KEY (userId) REFERENCES user(id),
                        FOREIGN KEY (avId) REFERENCES av(id));
                