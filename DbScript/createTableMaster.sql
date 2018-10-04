DROP DATABASE IF EXISTS `nskfdc`;
CREATE DATABASE  IF NOT EXISTS `nskfdc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `nskfdc`;


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

DROP TABLE IF EXISTS `centredetails`;
--
-- Table structure for table `employerdetails`
--

DROP TABLE IF EXISTS `employerdetails`;

DROP TABLE IF EXISTS `bankdetails`;

--
-- Table structure for table `batchdetails`
--

DROP TABLE IF EXISTS `batchdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batchdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(200) DEFAULT NULL,
  `batchStartDate` date DEFAULT NULL,
  `batchEndDate` date DEFAULT NULL,
  `assessmentDate` date DEFAULT NULL,
  `medicalExamDate` date DEFAULT NULL,
  `selectionCommitteeDate` date DEFAULT NULL,
  `municipality` varchar(50) DEFAULT NULL,
  `wardType` varchar(50) DEFAULT NULL,
  `wardNumber` varchar(20) DEFAULT NULL,
  `scgjBatchNumber` varchar(50) DEFAULT NULL,
  `principalTrainerName` varchar(50) DEFAULT NULL,
  `userEmail` varchar(100) NOT NULL,
  `centreId` bigint(20),
  `centreState` varchar (100),
  `centreCity` varchar (100),
  `employerName` varchar(60),
  `employerContactNumber` bigint(20),
  `updatedOn` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `batchId_UNIQUE` (`batchId`),
  UNIQUE KEY `scgjBatchNumber_UNIQUE` (`scgjBatchNumber`),
  KEY `tpEmail_idx` (`userEmail`),
  CONSTRAINT `tpEmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `candidate` (
  `enrollmentNumber` varchar(50) NOT NULL,
  `salutation` varchar(50) DEFAULT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `mobileNumber` bigint(20) DEFAULT NULL,
  `educationLevel` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `aadharCardNumber` bigint(20) DEFAULT NULL,
  `idProofType` varchar(50) DEFAULT NULL,
  `idProofNumber` varchar(50) DEFAULT NULL,
  `disabilityType` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `accountNumber` bigint DEFAULT NULL,
  `ifscCode` varchar(50) DEFAULT NULL,
  `bankName` varchar(50) DEFAULT NULL,
  `guardianType` varchar(50) DEFAULT NULL,
  `firstNameFather` varchar(50) DEFAULT NULL,
  `lastNameFather` varchar(20) DEFAULT NULL,
  `motherName` varchar(50) DEFAULT NULL,
  `residentialAddress` varchar(100) DEFAULT NULL,
  `msId` varchar(60) DEFAULT NULL,
  `occupationType` varchar(100) DEFAULT NULL,
  `employmentType` varchar(100) DEFAULT NULL,
  `workplaceAddress` varchar(100) DEFAULT NULL,
  `assessmentResult` varchar(45) DEFAULT NULL,
  `medicalExamConducted` varchar(50) DEFAULT NULL,
  `relationWithSKMS` varchar(45) DEFAULT NULL,
  `batchId` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`enrollmentNumber`),
  KEY `batchId_idx` (`batchId`),
  CONSTRAINT `batchId` FOREIGN KEY (`batchId`) REFERENCES `batchdetails` (`batchId`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  `generatedOn` datetime DEFAULT NULL,
  `userEmail` varchar(100) NOT NULL,
  `batchId` varchar(100) NOT NULL,
  PRIMARY KEY (`generateReportId`),
  KEY `tpreportmail_idx` (`userEmail`),
  KEY `batchId` (`batchId`),
  CONSTRAINT `generatereports_ibfk_1` FOREIGN KEY (`batchId`) REFERENCES `batchdetails` (`batchId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trainingpartnerdetails`
--

DROP TABLE IF EXISTS `trainingpartnerdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainingpartnerdetails` (
  `nsdcRegNumber` varchar(50) NOT NULL,
  `trainingPartnerName` varchar(100) DEFAULT NULL,
  `sectorSkillCouncil` varchar(50) DEFAULT NULL,
  `targets` bigint(12) DEFAULT NULL,
  `jobRole` varchar(400) DEFAULT NULL,
  `userEmail` varchar(100) NOT NULL,
  `targetApprovalDate` date DEFAULT NULL,
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  `batchId` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tpmail_idx` (`userEmail`),
  KEY `batchId` (`batchId`),
  CONSTRAINT `tpmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `uploadeddocuments_ibfk_1` FOREIGN KEY (`batchId`) REFERENCES `batchdetails` (`batchId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
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
  `generatedOn` date DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `trainingPartnerEmail_UNIQUE` (`userEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-29 12:41:45
