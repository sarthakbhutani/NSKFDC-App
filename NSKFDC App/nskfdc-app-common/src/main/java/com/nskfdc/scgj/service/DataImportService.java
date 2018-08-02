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


    /*-------- Master Sheet Import -----------*/


    public String masterSheetImport(MultipartFile file, int batchId) throws IOException {
        LOGGER.debug("In Service to read Excel sheet ");
        boolean flag = true; //for iterating
        Integer insertResult = -25;
        String fileNameReceived = "";
        String uploadedFolder = readApplicationConstants.getSaveExcelSheetAtLocation();

        LOGGER.debug("The path to uploaded folder is : " + uploadedFolder);
        byte[] bytes = file.getBytes();

        File folder = new File(uploadedFolder);

        /*-- Creating a directory --*/

        if (!folder.exists() || folder.canWrite()) {
            if (folder.mkdirs()) {
                LOGGER.debug("Folder Created whose path is : " + folder);
            } else if (folder.exists()) {
                LOGGER.debug("Folder Already exists");
            } else {
                LOGGER.error("Folder cannot be created");
            }

        }

        fileNameReceived = file.getOriginalFilename();
        LOGGER.debug("The file name is : " + fileNameReceived);

        Path path = Paths.get(uploadedFolder + fileNameReceived);
        LOGGER.debug("The path of file is : " + path);

        try {
            LOGGER.debug("Inside TRY block");
            LOGGER.debug("Writing file");
            Files.write(path, bytes);
            LOGGER.debug("Exiting TRY block");
        } catch (Exception e) {
            LOGGER.error("Error writting file" + e);
            return "Cannot write file on local machine";
        }


        String uploadFile = uploadedFolder + fileNameReceived;
        Workbook workbook = null;

        FileInputStream fileStream = new FileInputStream(uploadFile);

        if (path.toString().toLowerCase().endsWith(".xlsx")) {

            LOGGER.debug(".xlsx file");
            LOGGER.debug("Creating the workbook object for .xlsx file");
            workbook = new XSSFWorkbook(fileStream);
        } else if (path.toString().toLowerCase().endsWith(".xls")) {
            LOGGER.debug(".xls file");
            LOGGER.debug("Creating workbook object for .xls file type");
            workbook = new HSSFWorkbook(fileStream);
        }

        Sheet sheet = workbook.getSheetAt(0);

        /*--- Apache POI Code ----*/
        ArrayList<MasterSheetImportDto> candidateDetails = new ArrayList<MasterSheetImportDto>();

        Iterator < Row > rowIterator = sheet.rowIterator();

    //    LOGGER.debug("Physical Number of rows in the sheet are : " + sheet.getPhysicalNumberOfRows());

        while (rowIterator.hasNext()) {

            Row row = sheet.getRow(0);
            row = rowIterator.next();

            if (row.getRowNum() == 2) // Extract BatchId from the excel sheet
            {
                Iterator < Cell > cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        LOGGER.debug("The value of batchId is : " + cell.getNumericCellValue());
                        if (cell.getNumericCellValue() == batchId) //Checking the batchId from the front end and the batch id in excelsheet
                        {
                            LOGGER.debug("BatchId matched");
                        } else {
                            return "batchId in excel sheet and batch Id entered does not match";
                        }

                    }
                }
            }

            if (row.getRowNum() < 6) {
                continue; //just skip the rows if row number is less than 6
            }
            Iterator < Cell > cellIterator = row.cellIterator();

 
            /*--------- Mapping Excel Sheet Columns to DTO Objects ------------*/

            
            while (cellIterator.hasNext() && flag) {
                Cell cell = cellIterator.next();

                if (cell.getColumnIndex() == 0) {
                    if (cell.getStringCellValue().isEmpty()) {
                        LOGGER.error("No enrollment number found");
                        flag = false;
                        return "Enrollment Number of candidate cannot be null";
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
	                	if(cell.getDateCellValue().toString().isEmpty())
	                	{
	                		LOGGER.debug("Date of Birth column empty");
	                		flag = false;
	                		return "Date of Birth cannot be empty";
	                	}
	                	else if(DateUtil.isCellDateFormatted(cell)) {
	                			LOGGER.debug("Date is in date format");
	                			LOGGER.debug("The date of birth is " + cell.getDateCellValue());
	            				masterSheetImportDto.setDob(cell.getDateCellValue());
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
                		return "Please enter valid 12 digit adhar card number";
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
			return "File cannot be uplaoded";
		}
		else
		{
			return "File Uploaded Successfully";
		}
		

    }






    /*----------- Dashboard --------------*/
    public GetBatchDetailsDto BatchDetails(String userEmail, String batchId) {

        LOGGER.debug("Request received from Controller");
        LOGGER.debug("In BatchDetailsService");

        try {

            LOGGER.debug("email is" + userEmail);
            LOGGER.debug("In try block of BatchDetailsService to get search results for user Email1: " + userEmail);
            return dataImportDao.BatchDetails(userEmail, batchId);

        } catch (Exception e) {

            LOGGER.debug("An error occurred while getting the batch details for userEmail" + e);
            LOGGER.debug("Return NULL");
            return null;

        }
    }

    public Integer getTotalTargets(String userEmail) {

        LOGGER.debug("Request received from Control to get Total Targets");
        LOGGER.debug("In CandidatesTrained Service, to get Total Targets ");

        try {

            LOGGER.debug("In try block to get Total Targets");
            return dataImportDao.getTotalTargets(userEmail);
        } catch (Exception e) {

            LOGGER.error("An error occurred while getting the Total Targets" + e);
            return null;
        }
    }

    public Integer getTargetAchieved(String userEmail) {

        LOGGER.debug("Request received from Control to get Target Achieved");
        LOGGER.debug("In CandidatesTrained Service, to get Target Achieved ");

        try {

            LOGGER.debug("In try block to get Target Achieved");
            return dataImportDao.getTargetAchieved(userEmail);
        } catch (Exception e) {

            LOGGER.error("An error occurred while getting the Target Achieved" + e);
            return null;
        }
    }
    public Integer getRemainingTargets(String userEmail) {

        LOGGER.debug("Request received from Control to get Remaining Targets");
        LOGGER.debug("In CandidatesTrained Service, to get Remaining Targets ");

        try {

            LOGGER.debug("In try block to get Remaining Targets");
            return dataImportDao.getRemainingTargets(userEmail);
        } catch (Exception e) {

            LOGGER.error("An error occurred while getting the Remaining Targets" + e);
            return null;
        }
    }
    public Integer getFinancialYear(String userEmail) {

        LOGGER.debug("Request received from Control to get FinancialYear");
        LOGGER.debug("In CandidatesTrained Service, to get FinancialYear ");

        try {

            LOGGER.debug("In try block to get Remaining Targets");
            return dataImportDao.ShowFinancialYear(userEmail);
        } catch (Exception e) {

            LOGGER.error("An error occurred while getting the FinancialYear" + e);
            return null;
        }
    }

    String outputFile;

    public String downloadMasterSheetService(String userEmail) {

        LOGGER.debug("Request received from controller");
        LOGGER.debug("In Download Final Master Sheet Service");

        try {

            LOGGER.debug("In try block of Download Final Master Sheet Service");
            Collection < DownloadFinalMasterSheetDto > downloadMasterSheetInformation = dataImportDao.downloadMasterSheetDao(userEmail);
            if (CollectionUtils.isNotEmpty(downloadMasterSheetInformation)) {

                LOGGER.debug("Creating object of JRBean Collection Data Source ");
                JRBeanCollectionDataSource masterSheetBeans = new JRBeanCollectionDataSource(downloadMasterSheetInformation);

                /* Map to hold Jasper Report Parameters */

                LOGGER.debug("Creating Map to hold Jasper Report Parameters ");
                Map < String, Object > parameters = new HashMap < String, Object > ();
                parameters.put("DataSource", masterSheetBeans);


                LOGGER.debug("Creating object of Class Path Resource ");
                ClassPathResource resource = new ClassPathResource("/static/FinalMasterSheet.jasper");
                String userHomeDirectory = System.getProperty("user.home");

                LOGGER.debug("Getting input stream");
                InputStream inputStream = resource.getInputStream();
                LOGGER.debug("Input Stream successfully generated");
                LOGGER.debug("Creating the jrprint file..");
                JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
                LOGGER.debug("Successfuly created the jrprint file >> " + printFileName);

                if (printFileName != null) {

                    /*------------------------- In Excel---------------------------------------------*/
                    LOGGER.debug("Exporting the file to excel..");

                    JRXlsxExporter exporter = new JRXlsxExporter();
                    exporter.setExporterInput(new SimpleExporterInput(printFileName));

                    outputFile = userHomeDirectory + File.separatorChar + "FinalMasterSheet.xlsx";

                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
                    SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                    configuration.setDetectCellType(true);
                    configuration.setCollapseRowSpan(false);
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();

                    LOGGER.debug("Successfully Generated excel..");

                } else {
                    LOGGER.debug("jrprint file is empty..");
                }

                LOGGER.debug("Excel Sheet generated successfully....!!!");

            } else {
                outputFile = null;
                LOGGER.debug("Collection is null");
            }

        } catch (Exception e) {
            LOGGER.debug("Exception caught, Error in generating Excel Sheet" + e);

        }
        return outputFile;
    }


    public Integer getGenerateBatchService(String userEmail) {

        LOGGER.debug("Request received from Controller to create batch for email id: " + userEmail);

        LOGGER.debug("In Import Service to create batch for email id: " + userEmail);

        try {

            LOGGER.debug("In try block to generate batch for training partner with email id: " + userEmail);

            return dataImportDao.generateBatchDao(userEmail);

        } catch (DataAccessException d) {

            LOGGER.error("DataAccessException in service to create Batch" + d);

            return -1;
        } catch (Exception e) {

            LOGGER.error("An error occurred while creating the Batch" + e);

            return -1;
        }
    }

    public Collection < BatchDto > getBatchDetail(String userEmail) {
        try {

            return dataImportDao.getBatchDetail(userEmail);
        } catch (Exception e) {

            LOGGER.debug("An Exception occured while fetching batch id for email " + userEmail + e);
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
        //To check the existence of center and insert or update center information
        if (centreExistence == 1) 
        {
            LOGGER.debug("Centre Id already exist, hence update the records in database");
            updatedCentre = dataImportDao.updateCentreDetails(masterSheetSubmitDto);
        } 
        else {
            LOGGER.debug("Centre Id does not exist, hence inserting the centre id in database");
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
        		LOGGER.debug("Employer does not exists for this batch an user.Trying to insert now");
        		status = employerDao.insertEmployer(masterSheetSubmitDto.getEmployerName(), masterSheetSubmitDto.getEmployerNumber(), masterSheetSubmitDto.getBatchId().toString(), userEmail);
        		LOGGER.debug("Status of Employer insertion " + status);
        	}
        	else if (checkEmployer == 1)
        	{
        		LOGGER.debug("Employer does exists trying to update Employer information");
        		status = employerDao.updateEmployer(masterSheetSubmitDto.getEmployerName(), masterSheetSubmitDto.getEmployerNumber(), masterSheetSubmitDto.getBatchId().toString(), userEmail);
        		LOGGER.debug("Status of Employer udation " + status);
        	}
        }      
        LOGGER.debug("Updated the centre Details, updating batch details corresponding to the selected centre id & batch id");
        
        LOGGER.debug(masterSheetSubmitDto.getCentreId());
        //masterSheetSubmitDto.setCentreId(centerInserted);
        return dataImportDao.updateBatchDetails(masterSheetSubmitDto);

    }

}