use nskfdc;
GRANT SELECT,UPDATE,INSERT,DELETE ON *.* TO 'nskfdc_db_user'@'testnskfdcapp.c9juqmumthfa.ap-south-1.rds.amazonaws.com' IDENTIFIED BY 'NsKfDc1234$$';
GRANT SELECT  ON `nskfdc`.* TO 'nskfdc_user'@'testnskfdcapp.c9juqmumthfa.ap-south-1.rds.amazonaws.com' IDENTIFIED BY 'myteam';
GRANT SELECT,UPDATE,INSERT,DELETE  ON `nskfdc`.* TO 'nskfdc_app_user'@'testnskfdcapp.c9juqmumthfa.ap-south-1.rds.amazonaws.com' IDENTIFIED BY 'NsKfDc123$$';

