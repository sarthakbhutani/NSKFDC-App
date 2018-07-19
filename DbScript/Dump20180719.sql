-- MySQL dump 10.13  Distrib 5.6.23, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: nskfdc
-- ------------------------------------------------------
-- Server version	5.6.24-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bankdetails`
--

DROP TABLE IF EXISTS `bankdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bankdetails` (
  `accountNumber` bigint(20) NOT NULL,
  `ifscCode` varchar(45) DEFAULT NULL,
  `bankName` varchar(100) DEFAULT NULL,
  `enrollmentNumber` varchar(30) NOT NULL,
  PRIMARY KEY (`accountNumber`),
  KEY `enrollmentNumber_idx` (`enrollmentNumber`),
  CONSTRAINT `enrollmentNumber` FOREIGN KEY (`enrollmentNumber`) REFERENCES `candidate` (`enrollmentNumber`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bankdetails`
--

LOCK TABLES `bankdetails` WRITE;
/*!40000 ALTER TABLE `bankdetails` DISABLE KEYS */;
INSERT INTO `bankdetails` VALUES (46767,'HDFC005','HDFC','4'),(68968,'HDFC005','HDFC','6'),(134841,'PUNB001','Punjab National Bank','1'),(578586,'HDFC005','HDFC','7'),(684681,'HDFC004','HDFC','2'),(757484,'HDFC005','HDFC','3'),(786767,'HDFC005','HDFC','5');
/*!40000 ALTER TABLE `bankdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `batchdetails`
--

DROP TABLE IF EXISTS `batchdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batchdetails` (
  `batchId` int(11) NOT NULL AUTO_INCREMENT,
  `batchStartDate` date DEFAULT NULL,
  `batchEndDate` date DEFAULT NULL,
  `assessmentDate` date DEFAULT NULL,
  `medicalExamDate` date DEFAULT NULL,
  `selectionCommitteeDate` date DEFAULT NULL,
  `municipality` varchar(80) DEFAULT NULL,
  `wardType` varchar(45) DEFAULT NULL,
  `wardNumber` varchar(20) DEFAULT NULL,
  `scgjBatchNumber` varchar(50) DEFAULT NULL,
  `principalTrainerName` varchar(50) DEFAULT NULL,
  `userEmail` varchar(100) NOT NULL,
  `centreId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`batchId`),
  KEY `tpEmail_idx` (`userEmail`),
  KEY `centreId_idx` (`centreId`),
  CONSTRAINT `centreId` FOREIGN KEY (`centreId`) REFERENCES `centredetails` (`centreId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tpEmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batchdetails`
--

LOCK TABLES `batchdetails` WRITE;
/*!40000 ALTER TABLE `batchdetails` DISABLE KEYS */;
INSERT INTO `batchdetails` VALUES (1,'2018-07-09','2018-08-09','2018-10-10','2018-07-07','2018-09-09','Ghaziabad','Small','1','1','Ajay','shivanshu@gmail.com',1),(2,'2018-07-06','2018-06-07','2018-06-12','2019-01-01','2019-04-01','Meerut','Large','2','2','Atul','ceo@scgj.in',2),(3,'2017-04-07','2018-05-11','2018-12-12','2018-12-12','2018-12-12','Delhi','Large','3','3','Amit','kamal@scgj.in',3),(4,'2017-02-02','2018-02-02','2018-12-12','2018-12-12','2018-12-12','Delhi','Large','4','4','Amit','prashant@scgj.in',4),(5,'2017-02-02','2018-01-02','2017-02-02','2017-02-02','2017-02-02','Delhi','Large','5','5','Amit','pravek@gmail.com',5),(6,'2017-02-02','2018-04-05','2018-08-08','2018-08-08','2018-08-08','Delhi','Large','6','6','Amit','prateek@gmail.com',6),(7,'2017-02-02','2018-03-05','2018-08-08','2018-08-08',NULL,'Delhi','Large','7','7','Amit','ram@gmail.com',7);
/*!40000 ALTER TABLE `batchdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `candidate` (
  `enrollmentNumber` varchar(30) NOT NULL,
  `salutation` varchar(50) DEFAULT NULL,
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  `gender` varchar(15) DEFAULT NULL,
  `mobileNumber` bigint(20) DEFAULT NULL,
  `educationLevel` varchar(50) DEFAULT NULL,
  `state` varchar(60) DEFAULT NULL,
  `district` varchar(60) DEFAULT NULL,
  `aadharCardNumber` bigint(20) DEFAULT NULL,
  `idProofType` varchar(50) DEFAULT NULL,
  `idProofNumber` varchar(50) DEFAULT NULL,
  `disabilityType` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `guardianType` varchar(60) DEFAULT NULL,
  `firstNameFather` varchar(20) DEFAULT NULL,
  `lastNameFather` varchar(20) DEFAULT NULL,
  `motherName` varchar(45) DEFAULT NULL,
  `residentialAddress` varchar(100) DEFAULT NULL,
  `msId` varchar(60) DEFAULT NULL,
  `occupationType` varchar(100) DEFAULT NULL,
  `employmentType` varchar(100) DEFAULT NULL,
  `workplaceAddress` varchar(100) DEFAULT NULL,
  `assessmentResult` varchar(45) DEFAULT NULL,
  `medicalExamConducted` tinyint(1) DEFAULT NULL,
  `batchId` int(11) NOT NULL AUTO_INCREMENT,
  `relationWithSKMS` varchar(45) DEFAULT NULL,
  `candidatecol` int(11) DEFAULT NULL,
  PRIMARY KEY (`enrollmentNumber`),
  KEY `batchId_idx` (`batchId`),
  CONSTRAINT `batchId` FOREIGN KEY (`batchId`) REFERENCES `batchdetails` (`batchId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidate`
--

LOCK TABLES `candidate` WRITE;
/*!40000 ALTER TABLE `candidate` DISABLE KEYS */;
INSERT INTO `candidate` VALUES ('1','Mr','Prateek','Kapoor','Male',9650221366,'Graduate','Uttar Pradesh','Ghaziabad',21384875649846,'Aadhar Card','1265478','Nil',23,'1993-04-04','Father','Ram','Kapoor','Bhawana','Kavi nagar','1','Job','Job','Ghaziabad','Pass',1,1,'brother',NULL),('2','Miss','Meghna','Sharma','Female',7417795851,'Graduate','Karnataka','Bengaluru',54894187948658,'Licence','5198184','Nil',21,'1997-04-03','Father','Akshay','Sharma','Twinkle','Shastri nagar','2','Job','Job','Delhi','Pass',1,2,'brother',NULL),('3','Mr','Ruchi','Pareek','Female',9650221366,'Post Graduate','Uttar Pradesh','Ghaziabad',56498448456451,'Licence','6515747','Nil',28,'1990-04-04','Father','Ram','Sharma','Twinkle','Kavi nagar','3','Job','Job','Delhi','Pass',1,3,'brother',NULL),('4','Mr','Aman','Singh','Male',9650221366,'Graduate','Andhra Pradesh','Ghaziabad',56498448456452,'Licence','6515745','Nil',28,'1997-04-03','Father','Ram','Sharma','Twinkle','Shastri nagar','4','Job','Job','Delhi','Pass',1,4,'brother',NULL),('5','Mr','Sarthak','Sharma','Male',9650221366,'Graduate','Bihar','Patna',56498448456454,'Licence','6515746','Nil',28,'1997-04-03','Father','Ram','Sharma','Twinkle','Shastri nagar','5','Job','Job','Delhi','Pass',1,5,'brother',NULL),('6','Mr','Abhishek','Sharma','Male',9650221366,'Graduate','Tamil Nadu','Chennai',56498448456454,'Licence','6515746','Nil',28,'1997-04-03','Father','Ram','Sharma','Twinkle','Shastri nagar','6','Job','Job','Delhi','Pass',1,6,'brother',NULL),('7','Mr','Abhishek','Sharma','Male',9650221366,'Graduate','Tamil Nadu','Chennai',56498448456454,'Licence','6515746','Nil',28,'1997-04-03','Father','Ram','Sharma','Twinkle','Shastri nagar','7','Job','Job','Delhi','Pass',1,7,'brother',NULL);
/*!40000 ALTER TABLE `candidate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `centredetails`
--

DROP TABLE IF EXISTS `centredetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `centredetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `centreId` bigint(20) DEFAULT NULL,
  `centreState` varchar(60) DEFAULT NULL,
  `centreCity` varchar(60) DEFAULT NULL,
  `userEmail` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `centreId_UNIQUE` (`centreId`),
  KEY `traningPartnerEmail_idx` (`userEmail`),
  CONSTRAINT `traningPartnerEmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `centredetails`
--

LOCK TABLES `centredetails` WRITE;
/*!40000 ALTER TABLE `centredetails` DISABLE KEYS */;
INSERT INTO `centredetails` VALUES (1,1,'Uttar Pradesh','Ghaziabad','shivanshu@gmail.com'),(2,2,'Karnataka','Bengaluru','ceo@scgj.in'),(3,3,'TamilNadu','Chennai','kamal@scgj.in'),(4,4,'Bihar','Patna','pravek@gmail.com'),(5,5,'Andhra Pradesh','Tirupati','prashant@scgj.in'),(6,6,'Andhra Pradesh','Tirupati','prateek@gmail.com'),(7,7,'Andhra Pradesh','Tirupati','ram@gmail.com');
/*!40000 ALTER TABLE `centredetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employerdetails`
--

DROP TABLE IF EXISTS `employerdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employerdetails` (
  `employerId` int(11) NOT NULL,
  `employerName` varchar(60) DEFAULT NULL,
  `employerContactNumber` bigint(12) DEFAULT NULL,
  `enrollmentNumber` varchar(30) NOT NULL,
  PRIMARY KEY (`employerId`),
  KEY `enrollNumber_idx` (`enrollmentNumber`),
  CONSTRAINT `enrollNumber` FOREIGN KEY (`enrollmentNumber`) REFERENCES `candidate` (`enrollmentNumber`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employerdetails`
--

LOCK TABLES `employerdetails` WRITE;
/*!40000 ALTER TABLE `employerdetails` DISABLE KEYS */;
INSERT INTO `employerdetails` VALUES (1,'Prateek',7417795851,'1'),(2,'Ruchi',9650221366,'2'),(3,'Ruchi',9652121552,'3'),(4,'Ruchi',9652121552,'4'),(5,'Ruchi',9652121552,'5'),(6,'Ruchi',9652121552,'6'),(7,'Ruchi',9652121552,'7');
/*!40000 ALTER TABLE `employerdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `generatereports`
--

DROP TABLE IF EXISTS `generatereports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `generatereports` (
  `generateReportId` varchar(100) NOT NULL,
  `reportType` varchar(40) DEFAULT NULL,
  `generatedOn` datetime DEFAULT NULL,
  `userEmail` varchar(100) NOT NULL,
  `batchId` int(11) DEFAULT NULL,
  PRIMARY KEY (`generateReportId`),
  KEY `tpreportmail_idx` (`userEmail`),
  KEY `batchId_idx` (`batchId`),
  CONSTRAINT `batchIdFKey` FOREIGN KEY (`batchId`) REFERENCES `batchdetails` (`batchId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generatereports`
--

LOCK TABLES `generatereports` WRITE;
/*!40000 ALTER TABLE `generatereports` DISABLE KEYS */;
INSERT INTO `generatereports` VALUES ('1','Master','2018-12-07 00:00:00','shivanshu@gmail.com',NULL),('2','Master','2018-11-05 00:00:00','ceo@scgj.in',NULL),('3','Master','2018-11-05 00:00:00','kamal@scgj.in',NULL),('4','Master','2018-11-05 00:00:00','pravek@gmail.com',NULL),('5','Master','2018-11-05 00:00:00','prashant@scgj.in',NULL),('6','Master','2018-11-05 00:00:00','prateek@gmail.com',NULL),('7','Master','2018-11-05 00:00:00','ram@gmail.com',NULL);
/*!40000 ALTER TABLE `generatereports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainingpartnerdetails`
--

DROP TABLE IF EXISTS `trainingpartnerdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainingpartnerdetails` (
  `nsdcRegNumber` int(11) NOT NULL,
  `trainingPartnerName` varchar(150) DEFAULT NULL,
  `sectorSkillCouncil` varchar(350) DEFAULT NULL,
  `targets` bigint(12) DEFAULT NULL,
  `jobRole` varchar(400) DEFAULT NULL,
  `userEmail` varchar(100) NOT NULL,
  `financialYear` date DEFAULT NULL,
  PRIMARY KEY (`nsdcRegNumber`),
  KEY `trainingPartnerEmail_idx` (`userEmail`),
  CONSTRAINT `trainingPartnerEmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainingpartnerdetails`
--

LOCK TABLES `trainingpartnerdetails` WRITE;
/*!40000 ALTER TABLE `trainingpartnerdetails` DISABLE KEYS */;
INSERT INTO `trainingpartnerdetails` VALUES (1,'ABESIT','Ghaziabad',10,'Job','shivanshu@gmail.com','2017-04-06'),(2,'KEC','Delhi',20,'Job','ceo@scgj.in','2018-04-06'),(3,'ABESEC','Ghaziabad',30,'Job','kamal@scgj.in','2018-04-06'),(4,'ABESEC','Ghaziabad',22,'Job','pravek@gmail.com','2018-04-06'),(5,'ABESEC','Ghaziabad',22,'Job','prashant@scgj.in','2018-04-06'),(6,'ABESEC','Ghaziabad',22,'Job','prateek@gmail.com','2018-08-08'),(7,'ABESEC','Ghaziabad',22,'Job','ram@gmail.com','2018-08-08');
/*!40000 ALTER TABLE `trainingpartnerdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uploadeddocuments`
--

DROP TABLE IF EXISTS `uploadeddocuments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uploadeddocuments` (
  `id` int(11) NOT NULL,
  `finalBatchReport` tinyint(1) DEFAULT NULL,
  `occupationCertificate` tinyint(1) DEFAULT NULL,
  `minuteOfSelectionCommittee` tinyint(1) DEFAULT NULL,
  `dataSheetForSDDMS` tinyint(1) DEFAULT NULL,
  `dataSheetForNSKFC` tinyint(1) DEFAULT NULL,
  `attendanceSheet` tinyint(1) DEFAULT NULL,
  `finalBatchReportPath` varchar(500) DEFAULT NULL,
  `occupatioCertificatePath` varchar(500) DEFAULT NULL,
  `minuteOfSelectionCommitteePath` varchar(500) DEFAULT NULL,
  `dataSheetForSDMSPath` varchar(500) DEFAULT NULL,
  `dataSheetForNSKFCPath` varchar(500) DEFAULT NULL,
  `attendanceSheetPath` varchar(500) DEFAULT NULL,
  `dateUploaded` date DEFAULT NULL,
  `userEmail` varchar(100) NOT NULL,
  `batchId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tpmail_idx` (`userEmail`),
  KEY `batchId_idx` (`batchId`),
  CONSTRAINT `batchIdFK` FOREIGN KEY (`batchId`) REFERENCES `batchdetails` (`batchId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tpmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploadeddocuments`
--

LOCK TABLES `uploadeddocuments` WRITE;
/*!40000 ALTER TABLE `uploadeddocuments` DISABLE KEYS */;
INSERT INTO `uploadeddocuments` VALUES (1,1,1,1,1,1,1,'C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','2018-07-07','shivanshu@gmail.com',NULL),(2,1,1,1,1,1,1,'C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','2018-07-07','ceo@scgj.in',NULL),(3,1,1,1,1,1,1,'C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','2018-06-06','kamal@scgj.in',NULL),(4,1,1,1,1,1,1,'C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','2018-06-06','pravek@gmail.com',NULL),(5,1,1,1,1,1,1,'C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','2018-06-06','prashant@scgj.in',NULL),(6,1,1,1,1,1,1,'C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','2018-06-06','prateek@gmail.com',NULL),(7,1,1,1,1,1,1,'C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','C:\\Storage','2018-06-06','ram@gmail.com',NULL);
/*!40000 ALTER TABLE `uploadeddocuments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userEmail` varchar(100) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `generatedOn` date DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `trainingPartnerEmail_UNIQUE` (`userEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'shivanshu@gmail.com','shivanshu123','scgj','2018-07-18'),(2,'ceo@scgj.in','ceo123','scgj','2018-07-18'),(3,'kamal@scgj.in','kamal123','TP','2018-07-18'),(4,'pravek@gmail.com','pravek123','scgj','2018-07-18'),(5,'prashant@scgj.in','prashant123','scgj','2018-07-18'),(6,'prateek@gmail.com','prateek123','TP','2018-07-18'),(7,'ram@gmail.com','ram123','TP','2018-07-18');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-19 11:22:01
