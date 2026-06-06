CREATE DATABASE IF NOT EXISTS map_project_ibtissam;
USE map_project_ibtissam;

CREATE TABLE IF NOT EXISTS positions_ibtissam (
  id int(11) NOT NULL AUTO_INCREMENT,
  latitude double NOT NULL,
  longitude double NOT NULL,
  date datetime NOT NULL,
  imei varchar(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
