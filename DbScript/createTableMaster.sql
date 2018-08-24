drop database if exists nskfdc;
create database nskfdc;
use nskfdc;

DROP TABLE IF EXISTS `bankdetails`;
DROP TABLE IF EXISTS `batchdetails`;

DROP TABLE IF EXISTS `candidate`;
DROP TABLE IF EXISTS `centredetails`;
DROP TABLE IF EXISTS `employerdetails`;
DROP TABLE IF EXISTS `generatereports`;
DROP TABLE IF EXISTS `trainingpartnerdetails`;
DROP TABLE IF EXISTS `uploadeddocuments`;
DROP TABLE IF EXISTS `user`;


CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userEmail` varchar(100) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `generatedOn` date DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `trainingPartnerEmail_UNIQUE` (`userEmail`)
);

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
);
CREATE TABLE `batchdetails` (
  `batchId` int(11) NOT NULL AUTO_INCREMENT,
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
  `centreId` bigint(20) DEFAULT NULL,
  `updatedOn` date DEFAULT NULL,
  PRIMARY KEY (`batchId`),
  KEY `tpEmail_idx` (`userEmail`),
  KEY `centreId_idx` (`centreId`),
  CONSTRAINT `centreId` FOREIGN KEY (`centreId`) REFERENCES `centredetails` (`centreId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tpEmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
);


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
  `batchId` int(11) NOT NULL AUTO_INCREMENT,
  `relationWithSKMS` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`enrollmentNumber`),
  KEY `batchId_idx` (`batchId`),
  CONSTRAINT `batchId` FOREIGN KEY (`batchId`) REFERENCES `batchdetails` (`batchId`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
CREATE TABLE `bankdetails` (
  `accountNumber` bigint(20) NOT NULL,
  `ifscCode` varchar(50) DEFAULT NULL,
  `bankName` varchar(50) DEFAULT NULL,
  `enrollmentNumber` varchar(50) NOT NULL,
  PRIMARY KEY (`accountNumber`),
  KEY `enrollmentNumber_idx` (`enrollmentNumber`),
  CONSTRAINT `enrollmentNumber` FOREIGN KEY (`enrollmentNumber`) REFERENCES `candidate` (`enrollmentNumber`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE employerdetails (
  employerId int(11) primary key auto_increment,
  employerName varchar(60) DEFAULT NULL,
  employerContactNumber bigint(20) DEFAULT NULL,
  batch_id int(11) NOT NULL,
  user_email varchar(100) NOT NULL,
  foreign key (batch_id) references batchdetails(batchId),
  foreign key (user_email) references user(userEmail)
);


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
);
CREATE TABLE `trainingpartnerdetails` (
  `nsdcRegNumber` varchar(50) NOT NULL,
  `trainingPartnerName` varchar(50) DEFAULT NULL,
  `sectorSkillCouncil` varchar(50) DEFAULT NULL,
  `targets` bigint(12) DEFAULT NULL,
  `jobRole` varchar(400) DEFAULT NULL,
  `userEmail` varchar(100) NOT NULL,
  `targetApprovalDate` date DEFAULT NULL,
  PRIMARY KEY (`nsdcRegNumber`),
  KEY `trainingPartnerEmail_idx` (`userEmail`),
  CONSTRAINT `trainingPartnerEmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
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
  `batchId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tpmail_idx` (`userEmail`),
  KEY `fk_1` (`batchId`),
  CONSTRAINT `fk_1` FOREIGN KEY (`batchId`) REFERENCES `batchdetails` (`batchId`),
  CONSTRAINT `tpmail` FOREIGN KEY (`userEmail`) REFERENCES `user` (`userEmail`) ON DELETE NO ACTION ON UPDATE NO ACTION
) 