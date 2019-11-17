## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
# Create Databases
CREATE DATABASE cities_dev;

# Create database service accounts
CREATE USER 'cities_dev_user'@'localhost' IDENTIFIED BY 'cadebe';
CREATE USER 'cities_dev_user'@'%' IDENTIFIED BY 'cadebe';

# Database grants
GRANT SELECT ON cities_dev.* to 'cities_dev_user'@'localhost';
GRANT INSERT ON cities_dev.* to 'cities_dev_user'@'localhost';
GRANT DELETE ON cities_dev.* to 'cities_dev_user'@'localhost';
GRANT UPDATE ON cities_dev.* to 'cities_dev_user'@'localhost';

GRANT SELECT ON cities_dev.* to 'cities_dev_user'@'%';
GRANT INSERT ON cities_dev.* to 'cities_dev_user'@'%';
GRANT DELETE ON cities_dev.* to 'cities_dev_user'@'%';
GRANT UPDATE ON cities_dev.* to 'cities_dev_user'@'%';

# Create and populate databases

DROP TABLE IF EXISTS cities;

CREATE TABLE cities (`id` BINARY(16) NOT NULL primary key,`name` VARCHAR(255),`country_code` VARCHAR(2),`population` INT,
                    `latitude` DOUBLE,`longitude` DOUBLE) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO cities values(unhex(replace(uuid(),'-','')), 'Bratislava', 'SK', 432508, 48.148598, 17.107748);
INSERT INTO cities values(unhex(replace(uuid(),'-','')), 'Budapest', 'HU', 1763913, 47.497913, 19.040236);
INSERT INTO cities values(unhex(replace(uuid(),'-','')), 'Prague', 'CZ', 1298804, 50.075539, 14.437800);
INSERT INTO cities values(unhex(replace(uuid(),'-','')), 'Warsaw', 'PL', 1775933, 52.229675, 21.012230);
INSERT INTO cities values(unhex(replace(uuid(),'-','')), 'Los Angeles', 'US', 4057841, 34.052235, -118.243683);
INSERT INTO cities values(unhex(replace(uuid(),'-','')), 'New York', 'US', 8601186, 40.712776, -74.005974);
INSERT INTO cities values(unhex(replace(uuid(),'-','')), 'Edinburgh', 'GB', 530741, 55.953251, -3.188267);
INSERT INTO cities values(unhex(replace(uuid(),'-','')), 'Berlin', 'DE', 3556792, 52.520008, 13.404954);

SELECT * FROM cities;