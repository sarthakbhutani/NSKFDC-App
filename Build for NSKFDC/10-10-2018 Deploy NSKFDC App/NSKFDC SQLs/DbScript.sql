
GRANT ALL PRIVILEGES ON *.* TO 'nskfdc_db_user'@'localhost' IDENTIFIED BY 'NsKfDc1234$$' with GRANT option;
GRANT SELECT  ON `nskfdc`.* TO 'nskfdc_user'@'localhost' IDENTIFIED BY 'myteam';
GRANT SELECT,UPDATE,INSERT,DELETE  ON `nskfdc`.* TO 'nskfdc_app_user'@'localhost' IDENTIFIED BY 'NsKfDc123$$';