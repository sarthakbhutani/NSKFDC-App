/*
 * 
 */
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
import com.nskfdc.scgj.common.StringUtility;
import com.nskfdc.scgj.dao.DataImportDao;
import com.nskfdc.scgj.dao.EmployerDao;
import com.nskfdc.scgj.dto.BatchImportDto;
import com.nskfdc.scgj.dto.DownloadFinalMasterSheetDto;
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

	

	@Autowired
	private DataImportDao dataImportDao;
	
	

	@Autowired
	private EmployerDao employerDao;

	@Autowired
	private ReadApplicationConstants readApplicationConstants;
	
	
	private StringUtility stringUtility = new StringUtility();

	/**
	 * Method to import master sheet, check if batch size is greater than 50 and
	 * greater than remaining targets
	 * 
	 * @param file
	 * @param batchId
	 * @param userEmail
	 * @return
	 * @throws IOException
	 */
	public String masterSheetImport(MultipartFile file, String batchId, String userEmail) throws IOException {
		LOGGER.debug("Request received from Controller to DataImportService");
		LOGGER.debug("In masterSheetImport - to read Excel sheet ");
		boolean flag = true;
		Integer insertResult = -25;

		String fileName = "Master Sheet"
				+ file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

		String uploadedFolder = readApplicationConstants.getSaveExcelSheetAtLocation();
		LOGGER.debug("The path to folder where file would be uploaded " + uploadedFolder);

		byte[] bytes = file.getBytes();
		LOGGER.debug("Creating a Folder at the above path");
		String pathTillBatchId = uploadedFolder + "//" + userEmail + "//" + batchId + "//";

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
		Iterator<Row> rowIterator = sheet.rowIterator();
		Integer existingCandidates = 0;
		Integer nonExistingCandidates = 0;
		
		Integer remainingTargets = getRemainingTargets(userEmail);
		while(rowIterator.hasNext())
		{
			Row row = sheet.getRow(0);
			row = rowIterator.next();
			
			if(row.getRowNum()>6)
			{
				LOGGER.debug("Checking the enrollment number of the candidate");
				Cell cell = row.getCell(0);
				String enrollmentNumber = cell.toString();
				if(cell.getCellType() == Cell.CELL_TYPE_BLANK )
				{
					int rowNumber = row.getRowNum() + 1;
					LOGGER.error("Enrollment Number is blank");
					return "Please insert enrollment number at row : " +rowNumber;
				}
				
				Integer existence = dataImportDao.checkCandidate(enrollmentNumber, batchId);
				if(existence == 1)
				{
					LOGGER.debug("Candidate with enrolment number " + enrollmentNumber +" Exists");
					existingCandidates++;
					LOGGER.debug("Count of existing candidates is : " + existingCandidates);
					
				}
				else if(existence == 0)
				{
					LOGGER.debug("Candidate with enrollment number " + enrollmentNumber + " does not exists");
					nonExistingCandidates++;
					LOGGER.debug("Count of non existing candidates is : " + nonExistingCandidates);
				}
			}
		}
		
		LOGGER.debug("The value of existence = " + existingCandidates);
		LOGGER.debug("The value of non existing candidates = " + nonExistingCandidates);
		
		if (nonExistingCandidates > remainingTargets) {
			int sizeOfBatch = nonExistingCandidates;
			int remainder = remainingTargets;
			return "Cannot create batch. Your remaining targets are " + " " + remainder + ". Please contact Sector Skill Council for more targets";
		}
		
		int numberOfRows = sheet.getPhysicalNumberOfRows();
		if (numberOfRows > 56) {
			LOGGER.debug("A batch cannot have more than 50 candidates in a batch");

			return "A batch cannot have more than 50 Candidates";

		}
	

		LOGGER.debug("The number of physical rows are : " + numberOfRows);

		Iterator<Row> rowIteratorReader = sheet.rowIterator();
		while (rowIteratorReader.hasNext()) {
			
			ArrayList<MasterSheetImportDto> candidateDetails = new ArrayList<MasterSheetImportDto>();
			 MasterSheetImportDto masterSheetImportDto = new MasterSheetImportDto();
			 

			Row row = sheet.getRow(0);
			row = rowIteratorReader.next();

			if (row.getRowNum() == 2) {
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					
					Cell cell = cellIterator.next();
					Cell batchIdAtCell = row.getCell(4);
					
					String sheetBatchId = batchIdAtCell.toString();
					if(sheetBatchId == "")
					{
						LOGGER.error("Batch Id in sheet is null");
						return "batch Id in sheet cannot be Empty";
					}
					LOGGER.debug("The batchId at cell 4 in sheet is : " + sheetBatchId);
					
						if (batchId.equals(sheetBatchId)) {
							LOGGER.debug("In IF -- When BatchId matched");
						} else {
							LOGGER.debug("In ELSE -- When BatchId doesn't matched");
							LOGGER.debug(
									"Returning message - 'batchId in excel sheet and batch Id entered does not match'");
							return "batchId in Excel Sheet and batch Id selected does not match";
						}

				}
			}

			if (row.getRowNum() < 6) {
				LOGGER.debug("Skipping the Rows at index less than 6");
				continue;
			}
			Iterator<Cell> cellIterator = row.cellIterator();

			/*--------- Mapping Excel Sheet Columns to DTO Objects ------------*/

			while (cellIterator.hasNext() && flag) {
				Cell cell = cellIterator.next();

				if (cell.getColumnIndex() == 0) {
					if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
						LOGGER.error("No enrollment number found");
						int rowNumber = row.getRowNum() + 1;
						int columnNumber = cell.getColumnIndex() + 1;
						LOGGER.error("The row number is " + rowNumber + " and column Number is : " + columnNumber);
						flag = false;
						return "Please enter Enrollment Number at row number " + " " + rowNumber + " and column number "
								+ columnNumber;
					} 
					else
					{

						LOGGER.debug("The cell value of enrollment number is : " + cell.getStringCellValue());
						masterSheetImportDto.setEnrollmentNumber(cell.getStringCellValue());
					}
				} 
				else if (cell.getColumnIndex() == 1)
				{
					if (cell.getCellType() == Cell.CELL_TYPE_BLANK) 
					{
						LOGGER.error("Null value for salutation");
						int rowNumber = row.getRowNum() + 1;
						int columnNumber = cell.getColumnIndex() + 1;
						LOGGER.error("The row number is " + rowNumber + " and column Number is : " + columnNumber);
						flag = false;
						return "Please enter salutation at row number" + " " + rowNumber + " and column number "
								+ columnNumber;

					}
					else
					{
						LOGGER.debug("The salutation is : " + cell.getStringCellValue());
						masterSheetImportDto.setSalutation(cell.getStringCellValue());
					}
				}
				else if (cell.getColumnIndex() == 2) 
				{
					if (cell.getCellType()==Cell.CELL_TYPE_BLANK) 
					{
						LOGGER.error("First Name of candidate is null");
						int rowNumber = row.getRowNum() + 1;
						int columnNumber = cell.getColumnIndex() + 1;
						LOGGER.error("The row number is " + rowNumber + " and column Number is : " + columnNumber);
						flag = false;
						return "Please enter first name of candidate at row number " + " " + rowNumber + " and column number " + columnNumber;
					}
					else
					{
						LOGGER.debug("The first name of candidate is : " + cell.getStringCellValue());
						masterSheetImportDto.setFirstName(cell.getStringCellValue());
					}
				}
				else if (cell.getColumnIndex() == 3)
				{
					LOGGER.debug("Last Name of canididate is : " + cell.getStringCellValue());
					masterSheetImportDto.setLastName(cell.getStringCellValue());
				}

				else if (cell.getColumnIndex() == 4) 
				{
					if (cell.getCellType()==Cell.CELL_TYPE_BLANK) 
					{
						LOGGER.debug("Gender is set to null");
						int rowNumber = row.getRowNum() + 1;
						int columnNumber = cell.getColumnIndex() + 1;
						LOGGER.error("The row number is " + rowNumber + " and column Number is : " + columnNumber);
						flag = false;
						return "Please enter the gender at row number " + " " + rowNumber + " and column number " + columnNumber;
					}
					else
					{
						LOGGER.debug("The value of Gender is : " + cell.getStringCellValue());
						masterSheetImportDto.setGender(cell.getStringCellValue());
					}
				} 
				else if (cell.getColumnIndex() == 5)
				{
					LOGGER.debug("Capturing value of header : Disability Type is : " + cell.getStringCellValue());
					masterSheetImportDto.setDisabilityType(cell.getStringCellValue());

				}
				else if (cell.getColumnIndex() == 6) 
				{
							 LOGGER.debug("Capturing value of header : DateOfBirth ");

							 if(cell.getCellType()==Cell.CELL_TYPE_STRING)
								{ 
										 LOGGER.error("Cells are String formatted");
										 return "Please set the format of date of birth as dd-mm-yyyy";	 
								}
							 
							 else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
							 {
								 LOGGER.error("Cells are blank formatted");
								 flag = false;
									int rowNumber = row.getRowNum() + 1;
									int columnNumber = cell.getColumnIndex() + 1;
								 return "Please insert value in the DOB Column at row : " + rowNumber + " & column number " + columnNumber;
							 }
							 
							 else if(DateUtil.isCellDateFormatted(cell) == false)
								{
								 LOGGER.error("Cells are not date formatted");
								 return "Please set the format of date of birth as dd-mm-yyyy";
								}
							 else if(DateUtil.isCellDateFormatted(cell) == true)
								{
									 
								 LOGGER.error("Cells are date formatted");								
									LOGGER.debug("Date is in date format");
									LOGGER.debug("The date of birth is " + cell.getDateCellValue());
									masterSheetImportDto.setDob(cell.getDateCellValue());
							}

					}
							
				else if (cell.getColumnIndex() == 7)
				{
					LOGGER.debug("Capturing value for age");
					int age = (int) cell.getNumericCellValue();
					
					if(age<18)
					{
						LOGGER.debug("Age is less than 18");
						int rowNumber = row.getRowNum() + 1;
						flag=false;
						
						return("Candidate's age cannot be less than 18 years for the training at row " + rowNumber );
						
					}
					
					
					LOGGER.debug("The value for age is : " + cell.getNumericCellValue());
					masterSheetImportDto.setAge((int) cell.getNumericCellValue());
				}

				else if (cell.getColumnIndex() == 8) 
				{
					LOGGER.debug("Capturing the value of header : Guardian Type");
					LOGGER.debug("The value of guardian type : " + cell.getStringCellValue());
					masterSheetImportDto.setGuardianType(cell.getStringCellValue());
				}

				else if (cell.getColumnIndex() == 9) 
				{
					LOGGER.debug("Capturing value of header : Father's First Name");
					LOGGER.debug("Father's first name is : " + cell.getStringCellValue());
					masterSheetImportDto.setFirstNameFather(cell.getStringCellValue());
				}
				else if (cell.getColumnIndex() == 10)
				{
					LOGGER.debug("Capturing value of header : Father's Last Name");
					LOGGER.debug("Father's last name is : " + cell.getStringCellValue());
					masterSheetImportDto.setLastNameFather(cell.getStringCellValue());
				}
				else if (cell.getColumnIndex() == 11)
				{
					LOGGER.debug("Capturing value of header : Mother Name");
					LOGGER.debug("Mother's Last Name is : " + cell.getStringCellValue());
					masterSheetImportDto.setMotherName(cell.getStringCellValue());
				}
				else if (cell.getColumnIndex() == 12) 
				{
					LOGGER.debug("Capturing value of header : Mobile Number ");
					LOGGER.debug("Mobile Number is : " + (long) cell.getNumericCellValue());
					masterSheetImportDto.setMobileNumber((long) cell.getNumericCellValue());
				}
				else if (cell.getColumnIndex() == 13)
				{
					LOGGER.debug("Capturing value for header : Education Level");
					LOGGER.debug("Education Level is : " + cell.getStringCellValue());
					masterSheetImportDto.setEducationQualification(cell.getStringCellValue());
				}
				else if (cell.getColumnIndex() == 14)
				{
					LOGGER.debug("Capturing the value of header : State ");
					if(cell.getCellType() == Cell.CELL_TYPE_BLANK) 
					{
						LOGGER.error("State is null");
						int rowNumber = row.getRowNum() + 1;
						int columnNumber = cell.getColumnIndex() + 1;
						LOGGER.error("The row number is " + rowNumber + " and column Number is : " + columnNumber);
						flag = false;
						return "Please enter State at row number " + " " + rowNumber + " and column number "
								+ columnNumber;
					}
					else if(cell.getStringCellValue() != null)
					{
						LOGGER.debug("The value of state is : " + cell.getStringCellValue());
						masterSheetImportDto.setState(cell.getStringCellValue());
					}
				}

				else if (cell.getColumnIndex() == 15) 
				{
					LOGGER.debug("Capturing value of header : District");
					LOGGER.debug("The value of district is : " + cell.getStringCellValue());
					masterSheetImportDto.setDistrict(cell.getStringCellValue());

				}

				else if (cell.getColumnIndex() == 16) 
				{
					LOGGER.debug("Capturing the value of header : Aadhar Card");
					LOGGER.debug("Calculating the number of digits in aadhar number");

					long aadharNumber = (long) cell.getNumericCellValue();
					int count = String.valueOf(aadharNumber).length();
					LOGGER.debug("The number of digits in aadhar number : " + count);

					if (count < 12) 
					{
						int rowNumber = row.getRowNum() + 1;
						int columnNumber = cell.getColumnIndex() + 1;
						LOGGER.error("The row number is " + rowNumber + " and column Number is : " + columnNumber);
						LOGGER.error("Aadhar Card Number is not valid");
						flag = false;
						return "Please enter valid 12 digit aadhar card number at row number : " + " " + rowNumber + " and column number " + columnNumber;
					} 
					else if (count > 12)
					{
						int rowNumber = row.getRowNum() + 1;
						int columnNumber = cell.getColumnIndex() + 1;
						LOGGER.error("The row number is " + rowNumber + " and column Number is : " + columnNumber);
						LOGGER.error("Aadhar Card Number is not valid");
						LOGGER.error("The number of digits in aadhar card is : " + count);
						flag = false;
						return "Aadhar card number cannot be more than 12 digits at row number " + " " + rowNumber + " and column Number " + columnNumber;
					} 
					else 
					{
						LOGGER.debug("The value of aadhar card number is : " + cell.getNumericCellValue());
						masterSheetImportDto.setAdhaarCardNumber((long) cell.getNumericCellValue());
					}
				}

				else if (cell.getColumnIndex() == 17)
				{
					LOGGER.debug("Capturing the value of header : ID Proof");
					masterSheetImportDto.setIdProofType(cell.getStringCellValue());
				}

				else if (cell.getColumnIndex() == 18)
				{
					LOGGER.debug("Capturing value of header : Id Proof Number");
					masterSheetImportDto.setIdProofNumber(cell.getStringCellValue());
				}
				else if (cell.getColumnIndex() == 19) 
				{
					LOGGER.debug("Capturing value of header : Occupation Category");
					masterSheetImportDto.setOccupationType(cell.getStringCellValue());
				}
				else if (cell.getColumnIndex() == 20) 
				{
					LOGGER.debug("Capturing value of header : MS ID");
					masterSheetImportDto.setMsId(cell.getStringCellValue());
				}
				else if (cell.getColumnIndex() == 21)
				{
					LOGGER.debug("Capturing value of header : Relation with SK/MS");
					masterSheetImportDto.setRelationWithSKMS(cell.getStringCellValue());
				}
				else if (cell.getColumnIndex() == 22)
				{
					LOGGER.debug("Capturing value of header : Bank Name");
					masterSheetImportDto.setBankName(cell.getStringCellValue());
				}
				else if (cell.getColumnIndex() == 23)
				{
					LOGGER.debug("Capturing value of header : IFSC Code");
					masterSheetImportDto.setIfscCode(cell.getStringCellValue());
				}
				else if (cell.getColumnIndex() == 24) 
				{
					LOGGER.debug("Capturing value of header : Bank Account Number");
					masterSheetImportDto.setAccountNumber((long) cell.getNumericCellValue());
				}

				else if (cell.getColumnIndex() == 25) 
				{
					LOGGER.debug("Capturing value of header : Residential Address ");
					masterSheetImportDto.setResidentialAddress(cell.getStringCellValue());
				}

				else if (cell.getColumnIndex() == 26) 
				{
					LOGGER.debug("Capturing value of header : Workplace Address");
					masterSheetImportDto.setWorkplaceAddress(cell.getStringCellValue());

				}
				else if (cell.getColumnIndex() == 27)
				{
					LOGGER.debug("Capturing value of header : Medical Examination Conducted");
					masterSheetImportDto.setMedicalExaminationConducted(cell.getStringCellValue());
				}
				else if (cell.getColumnIndex() == 28) 
				{
					LOGGER.debug("Capturing the value of header : Assessmet Result");
					masterSheetImportDto.setAssessmentResult(cell.getStringCellValue());
				} 
				else if (cell.getColumnIndex() == 29)
				{
					LOGGER.debug("Capturing value of header : Employment Type");
					masterSheetImportDto.setEmploymentType(cell.getStringCellValue());
				}

			}

			candidateDetails.add(masterSheetImportDto);
			insertResult = dataImportDao.masterSheetImport(candidateDetails, batchId);

		}

		fileStream.close();
		if (insertResult < 1) {
			LOGGER.debug("In IF -- When insertResult of Excel Sheet is < 1 :" + insertResult);
			LOGGER.debug("Returning message - 'File cannot be uplaoded'");
			return "File cannot be uploaded";
		}

		else {
			LOGGER.debug("In ELSE -- When insertResult of Excel Sheet is not <1");
			LOGGER.debug("Returning message - 'File Uploaded Successfully'");
			return "File Uploaded Successfully";
		}

	}

	/**
	 * Method to return details of Batch Import Dto
	 * 
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
			LOGGER.error("Exception is " + e);
			LOGGER.error("Returning null");
			return null;

		}
	}

	/**
	 * Method to get total targets
	 * 
	 * @param userEmail
	 * @return
	 */
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
			LOGGER.error("Exception is " + e);
			LOGGER.error("Returning null");
			return null;
		}
	}

	/**
	 * Get targets achieved
	 * 
	 * @param userEmail
	 * @return integer
	 */
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
			LOGGER.error("Exception is " + e);
			LOGGER.error("Returning null");
			return null;
		}
	}

	/**
	 * Method to get remaining tragets
	 * 
	 * @param userEmail
	 * @return
	 */
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
			LOGGER.error("Exception is " + e);
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
			LOGGER.error("Exception is " + e);
			LOGGER.error("Returning null");
			return null;
		}
	}

	String outputFile;

	public String downloadMasterSheetService(String userEmail, String batchId) {

		LOGGER.debug("Request received from controller - DataImportService");
		LOGGER.debug("To Download Final Master Sheet while generating batch");

		try {

			LOGGER.debug("TRYING -- To Generate Master Sheet");
			Collection<DownloadFinalMasterSheetDto> downloadMasterSheetInformation = dataImportDao
					.downloadMasterSheetDao(userEmail, batchId);
			
			Collection<MasterSheetImportDto> candidateSheetInformation = dataImportDao.candidateSheetDetails(batchId);
			
			if (CollectionUtils.isNotEmpty(downloadMasterSheetInformation)) {
				
				if(CollectionUtils.isEmpty(candidateSheetInformation))
					LOGGER.debug("No Candidate Details available");
				
				LOGGER.debug("Creating object of JRBean Collection Data Source " );
				JRBeanCollectionDataSource masterSheetBeans = new JRBeanCollectionDataSource(
						downloadMasterSheetInformation);
				JRBeanCollectionDataSource candidateBeans = new JRBeanCollectionDataSource(
						candidateSheetInformation);

				/* Map to hold Jasper Report Parameters */

				LOGGER.debug("Creating Map to hold Jasper Report Parameters ");
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("DataSource", masterSheetBeans);
				parameters.put("CandidateSheetDataSource", candidateBeans);

				LOGGER.debug("Creating object of Class Path Resource - With FinalMasterSheet Template");
				ClassPathResource resource = new ClassPathResource("/static/FinalMasterSheet.jasper");
				String userHomeDirectory = System.getProperty("user.home");

				LOGGER.debug("Getting input stream for FinalMasterSheet");
				InputStream inputStream = resource.getInputStream();
				LOGGER.debug("Input Stream successfully generated");
				LOGGER.debug("Creating the jrprint file..");
				JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters,
						new JREmptyDataSource());
				LOGGER.debug("Successfuly created the jrprint file for MasterSheet ");

				if (printFileName != null) {

					LOGGER.debug("In IF -- When printFileName is not NULL");
					LOGGER.debug("Exporting the file to EXCEL");
					JRXlsxExporter exporter = new JRXlsxExporter();
					exporter.setExporterInput(new SimpleExporterInput(printFileName));
					outputFile = userHomeDirectory + File.separatorChar + "FinalMasterSheet.xlsx";
					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
					SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
					configuration.setRemoveEmptySpaceBetweenRows(true);
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
			LOGGER.error("Exception is " + e);

		}
		return outputFile;
	}
/**
 * This method is used to - 
 1. First construct the batch ID as per the requirements using utility methods
 2. Invoke the DAO to insert the record in the batchDetails table.
 
 * @param userEmail
 * @param municipality
 * @return
 */
	
	public String getGenerateBatchService(String userEmail,String municipality) {

		LOGGER.debug("Inside getGenerateBatchService() | Request received from Controller method generateBatchController() to create the batch record and insert in the database for training partner " + userEmail);
		
		
		try {

			LOGGER.debug("Constructing batch ID from user name and Municipality ");
			LOGGER.debug("Calling StringUtility class method -> constructedBatchId to get the name of the training partner having userEmail " +userEmail);
			Integer countBatches=dataImportDao.getNumberOfBatches(userEmail);
			Integer latestBatchCount = countBatches+1;
			String trainingPartnerName = dataImportDao.getTrainingPartnerName(userEmail);
			Integer nsdcRegNumber = dataImportDao.getNsdcRegNumber(userEmail);
			
			String constructedBatchID = stringUtility.constructedBatchId(trainingPartnerName, latestBatchCount, municipality,nsdcRegNumber);
			
			if(constructedBatchID==null)
			{
				LOGGER.error("Batch ID is null");
				return null;
			}
			LOGGER.debug("Calling DAO to insert record for batch ID " + constructedBatchID );
			LOGGER.debug("Sending parameters :batchId" +constructedBatchID+" municipality : " +municipality+" userEmail: " +userEmail+ " to insertBatchId() of Dao");
			
			Integer insertStatus = dataImportDao.insertBatchId(userEmail, constructedBatchID, municipality);
			
			if(insertStatus==1)
			{
				LOGGER.debug("Batch id inserted successfully");
				return constructedBatchID;
			}
			else
			{
				LOGGER.error("Batch id cannot be inserted");
				return null;
			}
	
		} catch (DataAccessException d) {

			LOGGER.error("CATCHING -- DataAccessException in DataImportService to create Batch");
			LOGGER.error("Exception is " + d);
			LOGGER.error("Returning null");
			return null;
		} catch (Exception e) {

			LOGGER.error("CATCHING -- Exception in DataImportService while creating Batch");
			LOGGER.error("Exception in getGenerateBatchService " + e);
			LOGGER.error("Returning status code as null");
			return null;
		}
	}


	/*-------------------- Submit data from MasterSheet Import into the database---------------*/

	public int submitBatchDetails(String userEmail, MasterSheetSubmitDto masterSheetSubmitDto) {
		int batchStatus = -10;
		int status = -10;
		int employerStatus = -10;
//		int updatedCentre = 0;
//		String centerInserted = "";
//		LOGGER.debug("In Data Import Service");
//		LOGGER.debug("1. Check if the centre Id exists in the database");
//		int centreExistence = dataImportDao.checkCentreExistence(masterSheetSubmitDto);
//		LOGGER.debug("The response of existence" + centreExistence);
//
//		if (centreExistence == 1) {
//			LOGGER.debug("In IF -- When Centre Id already exist");
//			LOGGER.debug("Sending Data to DataImportDao - updateCentreDetails");
//			updatedCentre = dataImportDao.updateCentreDetails(masterSheetSubmitDto);
//		} else {
//			LOGGER.debug("In ELSE -- When Centre Id does not exist, hence inserting the centre id");
//			LOGGER.debug("Sending request to DAO to insert new centreDetais");
//			centerInserted = dataImportDao.insertCentreDetails(userEmail, masterSheetSubmitDto);
//			LOGGER.debug("The center Id inserted is" + updatedCentre);
//		}
	
		//Update details of a batch
		batchStatus = dataImportDao.updateBatchDetails(masterSheetSubmitDto);
		// To insert the employer if batch update was successful and 
				if ((masterSheetSubmitDto.getEmployerName() != null || masterSheetSubmitDto.getEmployerName() != null) && batchStatus > 0) {

					int checkEmployer = employerDao.employerExists(masterSheetSubmitDto.getBatchId().toString(), userEmail);
					if (checkEmployer == 0) {
						LOGGER.debug("In IF -- When Employer does not exist for this entered batch");
						LOGGER.debug("Sending request to employerDao - to insert new employer");
						status = employerDao.insertEmployer(masterSheetSubmitDto.getEmployerName(),
								masterSheetSubmitDto.getEmployerNumber(), masterSheetSubmitDto.getBatchId().toString(),
								userEmail);
						LOGGER.debug("Status of Employer insertion " + status);
					} else if (checkEmployer == 1) {
						LOGGER.debug("In ELSE-IF -- When Employer does exist");
						LOGGER.debug("Sending request to employerDao - to update employer details");
						employerStatus = employerDao.updateEmployer(masterSheetSubmitDto.getEmployerName(),
								masterSheetSubmitDto.getEmployerNumber(), masterSheetSubmitDto.getBatchId().toString(),
								userEmail);
						LOGGER.debug("Status of Employer updation " + status);
					}
				}
		LOGGER.debug("Status of batch update is : " + batchStatus);
		LOGGER.debug("Status of employer update/insert is : "+employerStatus );
		if(batchStatus > 0 && employerStatus > 0)
		{
			status = 1;
		}
		return status;
	}

	/**
	 * This method returns the name of the training partner for the given userEmail
	 * @param userEmail 
	 */
//	public String getTrainingPartnerName(String userEmail,String municipality)
//	{
//		LOGGER.debug("Request recieved to get training partner name for username :" + userEmail);
//		if(userEmail==null)
//		{
//			LOGGER.error("UserEmail in method getTrainingPartnerName is null");
//			LOGGER.error("Returning Null");
//			return null;
//		}
//		try
//		{
//			LOGGER.debug("In try block to get TP Name for user : " + userEmail);
//			LOGGER.debug("Calling method to get name of training partner with userName : " + userEmail);
//			String trainingPartnerName = dataImportDao.getTrainingPartnerName(userEmail);
//			LOGGER.debug("Sending request to getNumberOfBatches method in service to get Number of Batches");
//			return getNumberOfBatches(userEmail,trainingPartnerName,municipality);
//			
//		}
//		catch(Exception e)
//		{
//			LOGGER.error("An exception occured while fetching training partner name : "+ e);
//			return null;
//		}
//		
//	}

	
	
//	/**
//	 * This method generates number of batches and also generates the unique batchID
//	 * @param userEmail
//	 * @param trainingPartnerName
//	 * @return
//	 */
//	public String getNumberOfBatches(String userEmail, String trainingPartnerName, String municipality) {
//		// TODO Auto-generated method stub
//		
//		LOGGER.debug("Batch to be created for municipality: " +municipality);
//		LOGGER.debug("Username recieved in method getNumberOfBatches is : " + userEmail + " trainingPartnerName " + trainingPartnerName);
//		LOGGER.debug("Sending params to dao to get the number of batches for email : " + userEmail);
//		Integer count = dataImportDao.getNumberOfBatches(userEmail) ;
//		LOGGER.debug("The number of batches are : " + count);
//		Integer latestBatch = count + 1; //the latest batch to be generated
//			LOGGER.debug("The number of batches for userEmail : " + userEmail + " are : " + count);
//			LOGGER.debug("Creating unique format for batch id ");
//			LOGGER.debug("Creating batch id for municipality : " + municipality);
//			LOGGER.debug("Calling String Utility method to get the unique name for training partner");
//			String[] nameArray = stringUtility.splitBySpace(trainingPartnerName);	
//			LOGGER.debug("Sending request to utility method to get the first character for each splitted string");
//			String uniqueName = stringUtility.getFirstLetter(nameArray);
//			
//			//Gets the Training partner unique names
//			
//			LOGGER.debug("The unique name of the user is : " + uniqueName);
//			LOGGER.debug("Calling method to get unique name for municipality");
//			LOGGER.debug("Calling method to split name ");
//			
//			String[] municipalityNameArray = stringUtility.splitBySpace(municipality);
//			LOGGER.debug("Sending request to string utility to get unique municipality name");
//			
//			String uniqueMunicipalityName = stringUtility.getUniqueMunicipalityName(municipalityNameArray);
//			String uniqueBatchId = uniqueName+uniqueMunicipalityName+latestBatch;
//			
//			LOGGER.debug("The unique batchId for TP with username : " + userEmail + " is : " + uniqueBatchId);
//			LOGGER.debug("Sending unique batch id ,user email and municipality to Tp to insert into batch table");
//			Integer batchIdInsertStatus = dataImportDao.insertBatchId(userEmail, uniqueBatchId, municipality);
//			
//			if(batchIdInsertStatus==1)
//			{
//				LOGGER.debug("Batch id inserted successfully");
//				return uniqueBatchId;
//			}
//			else
//			{
//				LOGGER.error("Cannot insert batch id" );
//				return null;
//			}
//	
//	}
}