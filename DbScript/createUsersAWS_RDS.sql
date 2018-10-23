use nskfdc;
GRANT SELECT,UPDATE,INSERT,DELETE ON *.* TO 'nskfdc_db_user'@'%' IDENTIFIED BY 'NsKfDc1234$$';
GRANT SELECT  ON `nskfdc`.* TO 'nskfdc_user'@'%' IDENTIFIED BY 'myteam';
GRANT SELECT,UPDATE,INSERT,DELETE  ON `nskfdc`.* TO '%' IDENTIFIED BY 'NsKfDc123$$';

