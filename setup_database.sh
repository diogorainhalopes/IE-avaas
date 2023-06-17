sleep 300

echo "Setting up MariaDB..."
echo "Database: $database"
database=$1

# Install MariaDB client if not already installed
sudo yum install -y mariadb

# Run MariaDB commands
mysql -h $database -u admin -ppassword <<EOF
DROP DATABASE IF EXISTS quarkusdb;
CREATE DATABASE IF NOT EXISTS quarkusdb;
USE quarkusdb;
CREATE TABLE manufacturer( brand VARCHAR(100) PRIMARY KEY );
CREATE TABLE av( id INTEGER PRIMARY KEY, brand VARCHAR(100), model VARCHAR(100) );
CREATE TABLE apilot_dev( company VARCHAR(100) PRIMARY KEY );
CREATE TABLE apilot( id INTEGER PRIMARY KEY, company VARCHAR(100), model VARCHAR(100) );
CREATE TABLE user( id INTEGER PRIMARY KEY, name VARCHAR(100), age INTEGER );
CREATE TABLE employee( eid INTEGER PRIMARY KEY, ename VARCHAR(100) );
CREATE TABLE purchase( id INTEGER PRIMARY KEY, userId INTEGER, avId INTEGER, apilotId INTEGER );
EOF
echo "MariaDB setup complete."