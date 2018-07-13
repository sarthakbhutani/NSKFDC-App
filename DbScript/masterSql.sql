-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: nskfdc
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
  `enrollmentNumber` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`accountNumber`),
  KEY `enrollmentNumber_idx` (`enrollmentNumber`),
  CONSTRAINT `enrollmentNumber` FOREIGN KEY (`enrollmentNumber`) REFERENCES `candidate` (`enrollmentNumber`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batchdetails`
--

DROP TABLE IF EXISTS `batchdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batchdetails` (
  `batchId` bigint(20) NOT NULL,
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
  `userEmail` varchar(100) DEFAULT NULL,
  `centreId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`batchId`),
  KEY `tpEmail_idx` (`userEmail`),
  KEY `centreId_idx` (`centreId`),
  CONSTRAINT `centreId` FOREIGN KEY (`centreId`) REFERENCES `centredetails` (`centreId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tpEmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `batchId-FK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`enrollmentNumber`),
  KEY `batchId_idx` (`batchId-FK`),
  CONSTRAINT `batchId` FOREIGN KEY (`batchId-FK`) REFERENCES `batchdetails` (`batchId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `userEmail` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `centreId_UNIQUE` (`centreId`),
  KEY `traningPartnerEmail_idx` (`userEmail`),
  CONSTRAINT `traningPartnerEmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `generatereports`
--

DROP TABLE IF EXISTS `generatereports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `generatereports` (
  `generateReportId` varchar(100) NOT NULL,
  `reportType` varchar(40) DEFAULT NULL,
  `scGeneratedOn` datetime DEFAULT NULL,
  `userEmail` varchar(100) DEFAULT NULL,
  `batchId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`generateReportId`),
  KEY `tpreportmail_idx` (`userEmail`),
  KEY `batchIdReport_idx` (`batchId`),
  CONSTRAINT `batchIdReport` FOREIGN KEY (`batchId`) REFERENCES `batchdetails` (`batchId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tpreportmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `userEmail` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`nsdcRegNumber`),
  KEY `trainingPartnerEmail_idx` (`userEmail`),
  CONSTRAINT `trainingPartnerEmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `batchId` bigint(20) DEFAULT NULL,
  `userEmail` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `batchIds_idx` (`batchId`),
  KEY `tpmail_idx` (`userEmail`),
  CONSTRAINT `batchIds` FOREIGN KEY (`batchId`) REFERENCES `batchdetails` (`batchId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tpmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  PRIMARY KEY (`userId`),
  UNIQUE KEY `trainingPartnerEmail_UNIQUE` (`userEmail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-13 13:52:38
