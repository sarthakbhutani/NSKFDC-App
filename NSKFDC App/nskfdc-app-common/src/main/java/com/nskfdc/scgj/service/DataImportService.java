package com.nskfdc.scgj.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nskfdc.scgj.common.ReadApplicationConstants;
import com.nskfdc.scgj.dao.DataImportDao;
import com.nskfdc.scgj.dao.EmployerDao;
import com.nskfdc.scgj.dto.BatchDto;
import com.nskfdc.scgj.dto.BatchImportDto;
import com.nskfdc.scgj.dto.DownloadFinalMasterSheetDto;
import com.nskfdc.scgj.dto.GetBatchDetailsDto;
import com.nskfdc.scgj.dto.MasterSheetImportDto;
import com.nskfdc.scgj.dto.MasterSheetSubmitDto;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;


@Service
public class DataImportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataImportService.class);


    private MasterSheetImportDto masterSheetImportDto = new MasterSheetImportDto();

    @Autowired
    private DataImportDao dataImportDao;
    
    @Autowired
    private EmployerDao employerDao;

    @Autowired
    private ReadApplicationConstants readApplicationConstants;


    public String masterSheetImport(MultipartFile file, int batchId,String userEmail) throws IOException {
    	LOGGER.debug("Request received from Controller to DataImportService");
        LOGGER.debug("In masterSheetImport - to read Excel sheet ");
        boolean flag = true; 
        Integer insertResult = -25;

    	String fileName = "Master Sheet"+file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
    	
        String uploadedFolder = readApplicationConstants.getSaveExcelSheetAtLocation();
        LOGGER.debug("The path to folder where file would be uploaded "+ uploadedFolder);
       
        byte[] bytes = file.getBytes();
        LOGGER.debug("Creating a Folder at the above path");
        String pathTillBatchId = uploadedFolder + "//" +userEmail + "//" +batchId+"//";
        
        File folder = new File(pathTillBatchId);

        if (!folder.exists() || folder.canWrite()) {
        	LOGGER.debug("In IF -- When Folder doesn't exist OR folder cannot be updated");
            if (folder.mkdirs()) {
                LOGGER.debug("In IF -- When Folder is Created ");
                LOGGER.debug("File Name is : " + fileName);
            } else if (folder.exists()) {
                LOGGER.debug("In ELSE-IF Folder Already exists");
            } else {
                LOGGER.error("In ELSE - When Folder cannot be created");
            }

        }

    	String fileNameReceived = fileName;
        Path path = Paths.get(pathTillBatchId + fileNameReceived);
        LOGGER.debug("The path to the file is : " + path);

        try {
            LOGGER.debug("TRYING -- To write a file");
            LOGGER.debug("Writing file");
            Files.write(path, bytes);
            LOGGER.debug("Exiting TRY block");
        } catch (Exception e) {
            LOGGER.error("CATCHING -- Exception handled while writting file");
            LOGGER.error("In DataImportService - masterSheetImport");
            LOGGER.error("Returning message 'Cannot write file on local machine'");
            return "Cannot write file on local machine";
        }


        String uploadFile = pathTillBatchId + fileNameReceived;
        Workbook workbook = null;

        FileInputStream fileStream = new FileInputStream(uploadFile);

        if (path.toString().toLowerCase().endsWith(".xlsx")) {

            LOGGER.debug("In IF -- When file is .xlsx file");
            LOGGER.debug("Creating the workbook object for .xlsx file");
            workbook = new XSSFWorkbook(fileStream);
        } else if (path.toString().toLowerCase().endsWith(".xls")) {
            LOGGER.debug("In ELSE-IF -- When File is .xls file");
            LOGGER.debug("Creating workbook object for .xls file type");
            workbook = new HSSFWorkbook(fileStream);
        }

        Sheet sheet = workbook.getSheetAt(0);

        /*--- Apache POI Code ----*/
        ArrayList<MasterSheetImportDto> candidateDetails = new ArrayList<MasterSheetImportDto>();

        Iterator < Row > rowIterator = sheet.rowIterator();

        while (rowIterator.hasNext()) {

            Row row = sheet.getRow(0);
            row = rowIterator.next();

            if (row.getRowNum() == 2) 
            {
                Iterator < Cell > cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        LOGGER.debug("The value of batchId from ExcelSheet is : " + cell.getNumericCellValue());
                        if (cell.getNumericCellValue() == batchId) 
                        {
                            LOGGER.debug("In IF -- When BatchId matched");
                        } else {
                        	LOGGER.debug("In ELSE -- When BatchId doesn't matched");
                        	LOGGER.debug("Returning message - 'batchId in excel sheet and batch Id entered does not match'");
                            return "batchId in Excel Sheet and batch Id selected does not match";
                        }

                    }
                }
            }

            if (row.getRowNum() < 6) {
            	LOGGER.debug("Skipping the Rows at index less than 6");
                continue; 
            }
            Iterator < Cell > cellIterator = row.cellIterator();

 
            /*--------- Mapping Excel Sheet Columns to DTO Objects ------------*/

            
            while (cellIterator.hasNext() && flag) {
                Cell cell = cellIterator.next();

                if (cell.getColumnIndex() == 0) {
                    if (cell.getStringCellValue().isEmpty()) {
                        LOGGER.error("No enrollment number found");
                        flag = false;
                        return "Enrollment Number of candidate is mandatory";
                    } else {

                        LOGGER.debug("The cell value of enrollment number is : " + cell.getStringCellValue());
                        masterSheetImportDto.setEnrollmentNumber(cell.getStringCellValue());
                    }
                } else if (cell.getColumnIndex() == 1) {
                    if (cell.getStringCellValue().isEmpty()) {
                        LOGGER.error("Null value for salutation");
                        flag = false;
                        return "Please enter the salutaion";
                    } else {
                        LOGGER.debug("The salutation is : " + cell.getStringCellValue());
                        masterSheetImportDto.setSalutation(cell.getStringCellValue());
                    }
                } else if (cell.getColumnIndex() == 2) {
                    if (cell.getStringCellValue().isEmpty()) {
                        LOGGER.error("First Name of candidate is null");
                        flag = false;
                        return "Please enter first name of candidate";
                    } else {
                        LOGGER.debug("The first name of candidate is : " + cell.getStringCellValue());
                        masterSheetImportDto.setFirstName(cell.getStringCellValue());
                    }
                } else if (cell.getColumnIndex() == 3) {
                    LOGGER.debug("Last Name of canididate is : " + cell.getStringCellValue());
                    masterSheetImportDto.setLastName(cell.getStringCellValue());
                }

                else if (cell.getColumnIndex() == 4) {
                    if (cell.getStringCellValue().isEmpty()) {
                        LOGGER.debug("Gender is set to null");
                        flag = false;
                        return "Please enter the gender";
                    } else {
                        LOGGER.debug("The value of Gender is : " + cell.getStringCellValue());
                        masterSheetImportDto.setGender(cell.getStringCellValue());
                    }
                }
                else if(cell.getColumnIndex() == 5)
                {
                	LOGGER.debug("Capturing value of header : Disability Type is : " + cell.getStringCellValue());
                	masterSheetImportDto.setDisabilityType(cell.getStringCellValue());
                	
                }
                else if(cell.getColumnIndex() == 6)
                {
	                	LOGGER.debug("Capturing value of header : DateOfBirth ");
	                	try {
							if(!cell.getDateCellValue().toString().equals(null))
							{
								LOGGER.debug("Date of Birth column is not empty");
								flag = false;
								masterSheetImportDto.setDob(cell.getDateCellValue());
								
							}
							else {
									LOGGER.debug("Date is in date format");
									LOGGER.debug("The date of birth is " + cell.getDateCellValue());
									return "Date of Birth is not be empty";
								}
						} catch (Exception e) {
							LOGGER.error("CATCHING -- Null pointer Exception while entering date");
							LOGGER.error("Returning 'Date of birth cannot be empty'");
							return "DOB is mandatory with date format";
						}
	                	
                }
                else if(cell.getColumnIndex() == 7)
                {
                	LOGGER.debug("Capturing value for age");
                	LOGGER.debug("The value for age is : " + cell.getNumericCellValue());
                	masterSheetImportDto.setAge((int) cell.getNumericCellValue());
                }
                
                else if(cell.getColumnIndex() == 8)
                {
                	LOGGER.debug("Capturing the value of header : Guardian Type");
                	LOGGER.debug("The value of guardian type : " + cell.getStringCellValue());
                	masterSheetImportDto.setGuardianType(cell.getStringCellValue());
                }

                else if(cell.getColumnIndex() == 9)
                {
                	LOGGER.debug("Capturing value of header : Father's First Name");
                	LOGGER.debug("Father's first name is : " + cell.getStringCellValue());
                	masterSheetImportDto.setFirstNameFather(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 10)
                {
                	LOGGER.debug("Capturing value of header : Father's Last Name");
                	LOGGER.debug("Father's last name is : " + cell.getStringCellValue());
                	masterSheetImportDto.setLastNameFather(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 11)
                {
                	LOGGER.debug("Capturing value of header : Mother Name");
                	LOGGER.debug("Mother's Last Name is : " + cell.getStringCellValue());
                	masterSheetImportDto.setMotherName(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 12)
                {
                	LOGGER.debug("Capturing value of header : Mobile Number ");
                	LOGGER.debug("Mobile Number is : " + (long) cell.getNumericCellValue());
                	masterSheetImportDto.setMobileNumber((long) cell.getNumericCellValue());
                }
                else if(cell.getColumnIndex() == 13)
                {
                	LOGGER.debug("Capturing value for header : Education Level");
                	LOGGER.debug("Education Level is : " + cell.getStringCellValue());
                	masterSheetImportDto.setEducationQualification(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 14)
                {
                	LOGGER.debug("Capturing the value of header : State ");
                	if(cell.getStringCellValue().isEmpty())
                	{
                		LOGGER.error("State is null");
                		flag = false;
                		return "Please enter state of the candidate";
                	}
                	else
                	{
                		LOGGER.debug("The value of state is : " + cell.getStringCellValue());
                		masterSheetImportDto.setState(cell.getStringCellValue());
                	}
                }

                else if(cell.getColumnIndex() == 15)
                {
                	LOGGER.debug("Capturing value of header : District");
                		LOGGER.debug("The value of district is : " + cell.getStringCellValue());
                		masterSheetImportDto.setDistrict(cell.getStringCellValue());
                	
                }
                
                else if(cell.getColumnIndex() == 16)
                {
                	LOGGER.debug("Capturing the value of header : Aadhar Card");
                	LOGGER.debug("Calculating the number of digits in aadhar number");
                	 
                	long aadharNumber = (long) cell.getNumericCellValue();
                	  int count = 0;
                	  for(; aadharNumber != 0; aadharNumber/=10, ++count) {   
                      }
                	  
                	  LOGGER.debug("The number of digits in aadhar number : " + count);
                	
                	  if(count<12)
                	{
                		LOGGER.error("Aadhar Card Number is not valid");
                		flag = false;
                		return "Please enter valid 12 digit aadhar card number";
                	}
                	  else if(count>12)
                	  {
                		  LOGGER.error("The number of digits in aadhar card is : " + count);
                		  flag = false;
                		  return "Aadhar card number cannot be more than 12 digits";
                	  }
                	else
                	{
                		LOGGER.debug("The value of aadhar card number is : " + cell.getNumericCellValue());
                		masterSheetImportDto.setAdhaarCardNumber((long) cell.getNumericCellValue());
                	}
                }

                else if(cell.getColumnIndex() == 17)
                {
                	LOGGER.debug("Capturing the value of header : ID Proof");
                	masterSheetImportDto.setIdProofType(cell.getStringCellValue());
                }

                else if(cell.getColumnIndex() == 18)
                {
                	LOGGER.debug("Capturing value of header : Id Proof Number");
                	masterSheetImportDto.setIdProofNumber(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 19)
                {
                	LOGGER.debug("Capturing value of header : Occupation Category");
                	masterSheetImportDto.setOccupationType(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 20)
                {
                	LOGGER.debug("Capturing value of header : MS ID");
                	masterSheetImportDto.setMsId(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 21)
                {
                	LOGGER.debug("Capturing value of header : Relation with SK/MS");
                	masterSheetImportDto.setRelationWithSKMS(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 22)
                {
                	LOGGER.debug("Capturing value of header : Bank Name");
                	masterSheetImportDto.setBankName(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 23)
                {
                	LOGGER.debug("Capturing value of header : IFSC Code");
                	masterSheetImportDto.setIfscCode(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 24)
                {
                	LOGGER.debug("Capturing value of header : Bank Account Number");
                	masterSheetImportDto.setAccountNumber((long)cell.getNumericCellValue());
                }

                else if(cell.getColumnIndex() == 25)
                {
                	LOGGER.debug("Capturing value of header : Residential Address ");
                	masterSheetImportDto.setResidentialAddress(cell.getStringCellValue());	                	
                }
                
                else if(cell.getColumnIndex() == 26)
                {
                	LOGGER.debug("Capturing value of header : Workplace Address");
                	masterSheetImportDto.setWorkplaceAddress(cell.getStringCellValue());
                
                }
                else if(cell.getColumnIndex() == 27)
                {
                	LOGGER.debug("Capturing value of header : Medical Examination Conducted");
                	masterSheetImportDto.setMedicalExaminationConducted(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 28)
                {
                	LOGGER.debug("Capturing the value of header : Assessmet Result");
                	masterSheetImportDto.setAssessmentResult(cell.getStringCellValue());
                }
                else if(cell.getColumnIndex() == 29)
                {
                	LOGGER.debug("Capturing value of header : Employment Type");
                	masterSheetImportDto.setEmploymentType(cell.getStringCellValue());
                }               

            }
            
            candidateDetails.add(masterSheetImportDto);
            insertResult = dataImportDao.masterSheetImport(candidateDetails,batchId);

        }

        fileStream.close();
        
		if(insertResult < 1)
		{
			LOGGER.debug("In IF -- When insertResult of Excel Sheet is <1 :"+insertResult);
			LOGGER.debug("Returning message - 'File cannot be uplaoded'");
			return "File cannot be uplaoded";
		}

		if(insertResult == 137)
		{
			LOGGER.debug("In IF -- When insertResult is 137");
			LOGGER.debug("Returning message - 'Bank details are not updated'");
			return "Bank details are not updated";
		}
		if(insertResult == 114) {
			LOGGER.debug("In IF -- When insertResult is 114");
			LOGGER.debug("Returning message - 'Candidate details cannot be inserted'");
			return "New Candidate details cannot be inserted, Please try again";
		}
		if(insertResult == 165) {
			LOGGER.debug("In IF -- When insertResult is 165");
			LOGGER.debug("Returning message - 'Candidate details cannot be updated'");
			return "Candidate bank details cannot be updated";
		}
		else
		{
			LOGGER.debug("In ELSE -- When insertResult of Excel Sheet is not <1");
			LOGGER.debug("Returning message - 'File Uploaded Successfully'");
			return "File Uploaded Successfully";
		}
		

    }






   /**
    * Method to return details of Batch Import Dto
    * @param userEmail
    * @param batchId
    * @return
    */
    public BatchImportDto BatchDetails(String userEmail, String batchId) {

        LOGGER.debug("Request received from Controller - DataImportService");
        LOGGER.debug("In BatchDetails -- To get batchDetails for entered batchId");

        try {

            LOGGER.debug("TRYING -- To get batchDetails for batchId entered by Logged in TP");
            LOGGER.debug("Sending request to dataImportDao - BatchDetails");
            return dataImportDao.BatchDetails(userEmail, batchId);

        } catch (Exception e) {

            LOGGER.error("CATCHING -- Exception handled while getting entered batchDetails");
            LOGGER.error("In DataImportService - BatchDetails");
            LOGGER.error("Exception is "+e);
            LOGGER.error("Returning null");
            return null;

        }
    }

    public Integer getTotalTargets(String userEmail) {

        LOGGER.debug("Request received from Controller - to get Total Targets");
        LOGGER.debug("In DataImportService - getTotalTargets");

        try {

            LOGGER.debug("TRYING -- To get Total Targets of logged in TP");
            LOGGER.debug("Sending request to DataImportDao - getTotalTargets");
            return dataImportDao.getTotalTargets(userEmail);
        } catch (Exception e) {

        	LOGGER.error("CATCHING -- Exception handled while getting total Targets of Logged in TP");
            LOGGER.error("In DataImportService - getTotalTargets");
            LOGGER.error("Exception is "+e);
            LOGGER.error("Returning null");
            return null;
        }
    }

    public Integer getTargetAchieved(String userEmail) {

        LOGGER.debug("Request received from Controller - to get Targets Achieved");
        LOGGER.debug("In DataImportService -  getTargetAchieved");

        try {

        	LOGGER.debug("TRYING -- To get Total Acheived Targets of logged in TP");
            LOGGER.debug("Sending request to DataImportDao - getTargetAchieved");
            return dataImportDao.getTargetAchieved(userEmail);
        } catch (Exception e) {

        	LOGGER.error("CATCHING -- Exception handled while getting total Targets acheived of Logged in TP");
            LOGGER.error("In DataImportService - getTargetAchieved");
            LOGGER.error("Exception is "+e);
            LOGGER.error("Returning null");
            return null;
        }
    }
    public Integer getRemainingTargets(String userEmail) {

        LOGGER.debug("Request received from Controller - To get Remaining Targets");
        LOGGER.debug("In DataImportService - getRemainingTargets");

        try {

        	LOGGER.debug("TRYING -- To get Total Remaining Targets of logged in TP");
            LOGGER.debug("Sending request to DataImportDao - getTargetAchieved");
            return dataImportDao.getRemainingTargets(userEmail);
        } catch (Exception e) {

        	LOGGER.error("CATCHING -- Exception handled while getting total Targets remaining of Logged in TP");
            LOGGER.error("In DataImportService - getRemainingTargets");
            LOGGER.error("Exception is "+e);
            LOGGER.error("Returning null");
            return null;
        }
    }
    public Integer getFinancialYear(String userEmail) {

        LOGGER.debug("Request received from Controller - to get FinancialYear");
        LOGGER.debug("In DataImportService, to getFinancialYear ");

        try {

        	LOGGER.debug("TRYING -- To get Total Remaining Targets of logged in TP");
            LOGGER.debug("Sending request to DataImportDao - ShowFinancialYear");
            return dataImportDao.ShowFinancialYear(userEmail);
        } catch (Exception e) {

        	LOGGER.error("CATCHING -- Exception handled while getting Financial Year");
            LOGGER.error("In DataImportService - getFinancialYear");
            LOGGER.error("Exception is "+e);
            LOGGER.error("Returning null");
            return null;
        }
    }

    String outputFile;

    public String downloadMasterSheetService(String userEmail) {

        LOGGER.debug("Request received from controller - DataImportService");
        LOGGER.debug("To Download Final Master Sheet while generating batch");

        try {

        	LOGGER.debug("TRYING -- To Generate Master Sheet");
            Collection < DownloadFinalMasterSheetDto > downloadMasterSheetInformation = dataImportDao.downloadMasterSheetDao(userEmail);
            if (CollectionUtils.isNotEmpty(downloadMasterSheetInformation)) {

                LOGGER.debug("Creating object of JRBean Collection Data Source ");
                JRBeanCollectionDataSource masterSheetBeans = new JRBeanCollectionDataSource(downloadMasterSheetInformation);

                /* Map to hold Jasper Report Parameters */

                LOGGER.debug("Creating Map to hold Jasper Report Parameters ");
                Map < String, Object > parameters = new HashMap < String, Object > ();
                parameters.put("DataSource", masterSheetBeans);


                LOGGER.debug("Creating object of Class Path Resource - With FinalMasterSheet Template");
                ClassPathResource resource = new ClassPathResource("/static/FinalMasterSheet.jasper");
                String userHomeDirectory = System.getProperty("user.home");

                LOGGER.debug("Getting input stream for FinalMasterSheet");
                InputStream inputStream = resource.getInputStream();
                LOGGER.debug("Input Stream successfully generated");
                LOGGER.debug("Creating the jrprint file..");
                JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
                LOGGER.debug("Successfuly created the jrprint file for MasterSheet ");

                if (printFileName != null) {

                	LOGGER.debug("In IF -- When printFileName is not NULL");
                    LOGGER.debug("Exporting the file to EXCEL");
                    JRXlsxExporter exporter = new JRXlsxExporter();
                    exporter.setExporterInput(new SimpleExporterInput(printFileName));
                    outputFile = userHomeDirectory + File.separatorChar + "FinalMasterSheet.xlsx";
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
                    SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                    configuration.setDetectCellType(true);
                    configuration.setCollapseRowSpan(false);
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();

                    LOGGER.debug("Successfully Generated excel of FinalBatchSheet");

                } else {
                    LOGGER.debug("In ELSE -- When jrprint file is empty..");
                }

                LOGGER.debug("Excel Sheet generated successfully");

            } else {
            	LOGGER.debug("When MasterSheetImport Collection is empty");
            	LOGGER.debug("Setting outputFile as null");
                outputFile = null;
                LOGGER.debug("Collection is null");
            }

        } catch (Exception e) {
        	LOGGER.error("CATCHING -- Exception handled - DataImportService - downloadMasterSheetService ");
        	LOGGER.error("While generating BatchId");
        	LOGGER.error("Exception is "+ e);

        }
        return outputFile;
    }


    public Integer getGenerateBatchService(String userEmail) {

        LOGGER.debug("Request received from Controller to create batch for logged in TP ");
        LOGGER.debug("In DataImportService  - getGenerateBatchService");

        try {

        	LOGGER.debug("TRYING -- To get batchdetails of TP");
            LOGGER.debug("Sending request to DataImportDao - generateBatchDao");
            return dataImportDao.generateBatchDao(userEmail);

        } catch (DataAccessException d) {

            LOGGER.error("CATCHING -- DataAccessException in DataImportService to create Batch");
            LOGGER.error("Exception is "+d);
            LOGGER.error("Returning status code as -1");
            return -1;
        } catch (Exception e) {

        	LOGGER.error("CATCHING -- Exception in DataImportService while creating Batch");
            LOGGER.error("Exception in getGenerateBatchService "+e);
            LOGGER.error("Returning status code as -1");
            return -1;
        }
    }

    public Collection < BatchDto > getBatchDetail(String userEmail) {
    	LOGGER.debug("Request received to get batchId of TP logged in");
    	LOGGER.debug("In the method - getBatchDetail");
        try {
        	LOGGER.debug("TRYING -- to get batchId for Logged in TP");
        	LOGGER.debug("Sending Request to DataImportDao - getBatchDetail");
            return dataImportDao.getBatchDetail(userEmail);
        } catch (Exception e) {
        	LOGGER.error("CATCHING -- Exception in DataImportService while getting BatchId");
            LOGGER.error("In DataImportService - getBatchDetail "+e);
            LOGGER.error("Returning null");
            return null;
        }
    }



    /*-------------------- Submit data from MasterSheet Import into the database---------------*/

    public int submitBatchDetails(String userEmail, MasterSheetSubmitDto masterSheetSubmitDto) {
    	int updatedCentre = 0;
    	String centerInserted = "";
        LOGGER.debug("In Data Import Service");
        LOGGER.debug("1. Check if the centre Id exists in the database");
        int centreExistence = dataImportDao.checkCentreExistence(masterSheetSubmitDto);
        LOGGER.debug("The response of existence" + centreExistence);
        
        if (centreExistence == 1) 
        {
            LOGGER.debug("In IF -- When Centre Id already exist");
            LOGGER.debug("Sending Data to DataImportDao - updateCentreDetails");
            updatedCentre = dataImportDao.updateCentreDetails(masterSheetSubmitDto);
        } 
        else {
            LOGGER.debug("In ELSE -- When Centre Id does not exist, hence inserting the centre id");
            LOGGER.debug("Sending request to DAO to insert new centreDetais");
            centerInserted = dataImportDao.insertCentreDetails(userEmail, masterSheetSubmitDto);
            LOGGER.debug("The center Id inserted is" + updatedCentre);
        }

        //To insert the employer
        if(masterSheetSubmitDto.getEmployerName() != null || masterSheetSubmitDto.getEmployerName() != null)
        {
        	int status =-2;
        	int checkEmployer = employerDao.employerExists(masterSheetSubmitDto.getBatchId().toString(), userEmail);
        	if (checkEmployer == 0)
        	{
        		LOGGER.debug("In IF -- When Employer does not exist for this entered batch");
        		LOGGER.debug("Sending request to employerDao - to insert new employer");
        		status = employerDao.insertEmployer(masterSheetSubmitDto.getEmployerName(), masterSheetSubmitDto.getEmployerNumber(), masterSheetSubmitDto.getBatchId().toString(), userEmail);
        		LOGGER.debug("Status of Employer insertion " + status);
        	}
        	else if (checkEmployer == 1)
        	{
        		LOGGER.debug("In ELSE-IF -- When Employer does exist");
        		LOGGER.debug("Sending request to employerDao - to update employer details");
        		status = employerDao.updateEmployer(masterSheetSubmitDto.getEmployerName(), masterSheetSubmitDto.getEmployerNumber(), masterSheetSubmitDto.getBatchId().toString(), userEmail);
        		LOGGER.debug("Status of Employer updation " + status);
        	}
        }      
       LOGGER.debug("Updated the centre Details, updating batch details corresponding to the selected centre id & batch id");
       LOGGER.debug("Sending request to dataImportDao - updateBatchDetails");
        return dataImportDao.updateBatchDetails(masterSheetSubmitDto);

    }

}