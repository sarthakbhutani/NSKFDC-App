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
import java.util.Calendar;
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
import org.junit.runner.RunWith;
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
	@Autowired
	private StringUtility stringUtility;

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
	public String masterSheetImport(MultipartFile file, String batchId, String userEmail)   {

		LOGGER.debug("Request received from Controller to DataImportService");
		LOGGER.debug("In masterSheetImport - to read Excel sheet ");
		boolean flag = true;
		Integer insertResult = -25;
		boolean rowFlag=true; //turns false if theres an error in the row - the while loop stops
		


		String fileName = "Master Sheet"
				+ file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

		String uploadedFolder = readApplicationConstants.getSaveExcelSheetAtLocation();
		LOGGER.debug("The path to folder where file would be uploaded " + uploadedFolder);
		try
		{
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
			while(rowIterator.hasNext() && rowFlag)
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

						if(stringUtility.splitByChar(enrollmentNumber,"/")!=null && stringUtility.splitByChar(enrollmentNumber,"/").length==2)
						{int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Format for enrollment number is correct , at row :" + rowNumber+" checking if the format contains batch Id");
							if(stringUtility.stringCompare(batchId, stringUtility.splitByChar(enrollmentNumber,"/")[0]))
							{
								LOGGER.debug("The first word in enrollment number is batchId");
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
							else
							{
								LOGGER.error("The first word of enrollment number is not batchId at row : " + rowNumber);
								return "The format of enrollment number is not correct at row "+ rowNumber+" Format should be batch id/number";
							}
							
							
						}
						else
						{
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Incorrect format for enrollment number at row : " + rowNumber);
							return "Incorrect format for enrollment number. at row "+ rowNumber+ "Format should be BatchId/number";
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

				while (cellIterator.hasNext() && flag) 
				{
					Cell cell = cellIterator.next();
					if(cell == null)
					{
						LOGGER.debug("The value of column is null, hence skipping reading it");
					}
					else if (cell.getColumnIndex() == 0)
					{
						
							if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								int rowNumber = row.getRowNum() + 1;
								int columnNumber = cell.getColumnIndex() + 1;
								LOGGER.error("No enrollment number found");
								
								LOGGER.error("The row number is " + rowNumber + " and column Number is : " + columnNumber);
								flag = false;
								return "Please enter Enrollment Number at row number " + " " + rowNumber + " and column number "
								+ columnNumber;
							} 

							else if(cell.getCellType() == Cell.CELL_TYPE_STRING)
							{
								int rowNumber = row.getRowNum() + 1;
								int columnNumber = cell.getColumnIndex() + 1;
								
								if(cell.getStringCellValue()=="" || cell.getStringCellValue()==null)
								{
									LOGGER.error("Null or blank value for enrollment number");
									flag = false;
									return "Enrollment Number cannot be left blank at row: "+rowNumber;
								}
								
								if(stringUtility.splitByChar(cell.getStringCellValue(),"/")!=null && stringUtility.splitByChar(cell.getStringCellValue(),"/").length==2)
								{
									if(stringUtility.stringCompare(batchId, stringUtility.splitByChar(cell.getStringCellValue(),"/")[0]))
									{
										LOGGER.debug("The cell value of enrollment number is : " + cell.getStringCellValue());
										masterSheetImportDto.setEnrollmentNumber(cell.getStringCellValue());
									}
									else
									{
										LOGGER.debug("The first word of enrollment number is not batchId");
										return "The format of enrollment number is not correct at row "+ rowNumber+" Format should be batch id/number";
									}
								}
								else
								{
									LOGGER.debug("The format for enrollment number is not correct");
									return "The format of enrollment number is not correct at row "+ rowNumber+" Format should be batch id/number";
								}
								
								
								
							}
							else
							{
								int rowNumber = row.getRowNum() + 1;
								LOGGER.debug("The enrollment number is of type numeric");
								return "Enrollment number can only be alpha numeric at row " +rowNumber ;
							}
						} 
						else if (cell.getColumnIndex() == 1)
						{
							if ((cell.getCellType() == Cell.CELL_TYPE_BLANK)) 
							{
								LOGGER.debug("Cell is blank");
								LOGGER.debug("Do Nothing");
							}
					
							else if (cell.getCellType() == Cell.CELL_TYPE_STRING)
								{
									if(cell.getStringCellValue()==null|| cell.getStringCellValue().isEmpty())
									{
										LOGGER.debug("Doing nothing, The salutation has null or empty value");
									}
									else
									{
										LOGGER.debug("The salutation is : " + cell.getStringCellValue());
										masterSheetImportDto.setSalutation(cell.getStringCellValue());	
									}
									
								}
				
								else
								{
									int rowNumber = row.getRowNum() + 1;
									LOGGER.error("Not a valid Salutation");
									return "Salutation value at row: "+rowNumber + " is not correct.";
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
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING)
							{
								LOGGER.debug("Checking if the first name is null or empty");
								if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
								{
									int rowNumber = row.getRowNum() + 1;
									LOGGER.error("First Name is Null");
									return "First Name cannot be empty at row: " +rowNumber;
								}
								else
								{
									LOGGER.debug("Reading the value of first name");
									masterSheetImportDto.setFirstName(cell.getStringCellValue());
								}
							}
							else
							{
								LOGGER.error("Invalid format for first name");
								int rowNumber = row.getRowNum() + 1;
								return "Invalid value for first name at row: "+rowNumber;
							}


						}
						else if (cell.getColumnIndex() == 3)
						{
							if ((cell.getCellType()==Cell.CELL_TYPE_BLANK))
							{
								LOGGER.debug("Cell is blank");
								
								
							}	
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING)
								{
									if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
									{
										LOGGER.debug("Cell type is null");
									}
									else
									{
										LOGGER.debug("Last Name of candidate is : " + cell.getStringCellValue());
										masterSheetImportDto.setLastName(cell.getStringCellValue());
										
									}
									
								}
								else 
								{
									int rowNumber = row.getRowNum() + 1;
									LOGGER.error("Not a valid value for last name");
									return "Not a valid value for Last name of candidate at row : "+rowNumber +" Value should have text only"; 
								}
							

						}
						else if (cell.getColumnIndex() == 4) 
						{
							if (cell.getCellType()==Cell.CELL_TYPE_BLANK) 
							{
								LOGGER.debug("Gender is set to null");
								int rowNumber = row.getRowNum() + 1;
								
								LOGGER.error("The row number is " + rowNumber);
								flag = false;
								return "Please enter the gender at row number " + " " + rowNumber;
							}

							else  if(cell.getCellType() == Cell.CELL_TYPE_STRING)
							{
								if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
								{
									LOGGER.debug("Value for Gender is null");
									int rowNumber = row.getRowNum()+1;
									return "Gender cannot be null at row: "+rowNumber;
									
								}
								else if(cell.getStringCellValue().equals("M") || cell.getStringCellValue().equals("F")) 
									{
										LOGGER.debug("The value of Gender is : " + cell.getStringCellValue());
										masterSheetImportDto.setGender(cell.getStringCellValue());
										
									}
									else 
									{
										int rowNumber = row.getRowNum() + 1;
										LOGGER.debug("The invalid value of Gender is : " + cell.getStringCellValue());
										return "Please enter gender value as 'M' or 'F' at" + " " + rowNumber ;
									}

							}
							else
							{
								int rowNumber = row.getRowNum() + 1;
								LOGGER.error("Not a valid value for gender");
								return "Not a valid value for Gender at row: "+rowNumber+ ", value should have text only";
							}
						} 
						else if (cell.getColumnIndex() == 5)
						{
							if((cell.getCellType() == Cell.CELL_TYPE_BLANK))
							{
								LOGGER.debug("Disability type is blank");
							}
								
							else if(cell.getCellType() == Cell.CELL_TYPE_STRING)
								{
									if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
									{
										LOGGER.debug("Cell is null and does not have any value");
									}
									else
									{
									LOGGER.debug("Capturing value of header : Disability Type is : " + cell.getStringCellValue());
									masterSheetImportDto.setDisabilityType(cell.getStringCellValue());
								}
							}
								else
								{
									int rowNumber = row.getRowNum() + 1;
									LOGGER.error("Not a valid value for disability type");
									return "Not a valid value for Disability type at row : "+rowNumber+ ", Value should have text only";
								}
							}
						
						else if (cell.getColumnIndex() == 6) 
						{
							
							LOGGER.debug("Capturing value of header : DateOfBirth ");
							int rowNumber = row.getRowNum() + 1;
							
							if(cell.getCellType()==Cell.CELL_TYPE_STRING)
							{ 
								if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
								{
									LOGGER.error("Cells are string formatted and empty");
									return "Please fill Date of Birth at row : "+rowNumber;
								}
								else
								{
									LOGGER.error("Cells are String formatted");
									return "Please set the format of date of birth as dd-mm-yyyy at row : "+rowNumber;		
								}
								 
							}

							else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
							{
								LOGGER.error("Cells are blank formatted");
								flag = false;
								int columnNumber = cell.getColumnIndex() + 1;
								return "Please insert value in the DOB Column at row : " + rowNumber + " & column number " + columnNumber;
							}
							else if(DateUtil.isCellDateFormatted(cell) == false)
							{
								LOGGER.error("Cells are not date formatted");
								return "Please set the format of date of birth as dd-mm-yyyy at row : "+rowNumber;
							}
							else if(DateUtil.isCellDateFormatted(cell) == true)
							{

								LOGGER.debug("Cells are date formatted");								
								LOGGER.debug("Date is in date format");
								LOGGER.debug("The date of birth is " + cell.getDateCellValue());
								LOGGER.debug("Checking if the date of birth is not greater than current date");
			
								Calendar calendar = Calendar.getInstance();
								int year = calendar.get(Calendar.YEAR);
								int month = calendar.get(Calendar.MONTH);
								Calendar dateOfBirth = Calendar.getInstance();
	
								dateOfBirth.set(cell.getDateCellValue().getYear(), cell.getDateCellValue().getMonth(), cell.getDateCellValue().getDate());
								int dateOfBirthYear = dateOfBirth.get(Calendar.YEAR);
								int dateOfBirthMonth = dateOfBirth.get(Calendar.MONTH);
								
								int age = (year - dateOfBirthYear)-1900;
								
								if(age<18)
								{
									LOGGER.error("Candidate is not 18 years of age");
									return "Candidate must be of 18 years of age at row: "+rowNumber;
								}
								
								else if(dateOfBirth.get(Calendar.MONTH)>calendar.get(Calendar.MONTH) && age == 18)
								{
						
									
									LOGGER.error("Age of candidate is less than 18");
									return "Candidate must be of 18 years of age at row: "+rowNumber;
								}
								
								else if(calendar.get(Calendar.MONTH)==dateOfBirth.get(Calendar.MONTH)&&age>=18)
								{
									
									LOGGER.debug("This month is candidate's birthday month");
									LOGGER.debug("The new age of the candidate is: "+age);
									masterSheetImportDto.setDob(cell.getDateCellValue());
									masterSheetImportDto.setAge(age);
								}
								else if(dateOfBirth.get(Calendar.MONTH)<calendar.get(Calendar.MONTH)&&age>=18)
								{
									
									LOGGER.debug("This is not the birthday month of the candidate");
									LOGGER.debug("Setting the age and date of birth of candidate in the array list");
									masterSheetImportDto.setDob(cell.getDateCellValue());
									masterSheetImportDto.setAge(age);
								}

								else if(dateOfBirth.get(Calendar.MONTH)>calendar.get(Calendar.MONTH) && age>18)
								{
									LOGGER.debug("This is not the birthday month of the candidate");
									LOGGER.debug("The age of the candidate is: "+age);
									age = age-1;
									LOGGER.debug("Setting the age and date of birth of candidate in the array list");
									masterSheetImportDto.setDob(cell.getDateCellValue());
									masterSheetImportDto.setAge(age);
								}
								
							}
							

						}

						else if (cell.getColumnIndex() == 8) 
						{
							if((cell.getCellType()==Cell.CELL_TYPE_BLANK))
							{
								LOGGER.debug("Capturing the value of header : Guardian");
								LOGGER.debug("Cells are blank");
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING)
								{
									if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
									{
										LOGGER.debug("Guardian is null or empty");
									}
									else
									{
										LOGGER.debug("The value of guardian type : " + cell.getStringCellValue());
										masterSheetImportDto.setGuardianType(cell.getStringCellValue());
									}
									
								}
								else
								{
									LOGGER.error("Not a valid value for gaurdian type");
									int rowNumber = row.getRowNum() + 1;
									return "Not a valid value for gaurdian type at row : "+rowNumber + " Value should have text only";
								}
							}

						

						else if (cell.getColumnIndex() == 9) 
						{
							LOGGER.debug("Capturing the value for father/husband first name");
							if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
							{
								LOGGER.error("First Name of father/husband is blank");
								int rowNumber = row.getRowNum() + 1;
								return "Please enter the first name of father/husband at row: "+rowNumber;
							}

							if(cell.getCellType() == Cell.CELL_TYPE_STRING)
							{
								LOGGER.debug("Father/Husband first name is of string type");
								if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
								{
									LOGGER.error("First Name of father/husband null or empty");
									int rowNumber = row.getRowNum()+1;
									return "Please enter first name of father/husband at row: "+rowNumber;
								}
								else 
								{
									LOGGER.debug("Father's first name is : " + cell.getStringCellValue());
									masterSheetImportDto.setFirstNameFather(cell.getStringCellValue());
								}
								
								
							}
							else 
							{
								int rowNumber = row.getRowNum() + 1;
								LOGGER.error("Column type of father/husband does not have a valid value");
								return "Not a valid value for First name of father/husband at row : "+rowNumber+ " Value should have text only";
							}

						}
						else if (cell.getColumnIndex() == 10)
						{

							if((cell.getCellType()==Cell.CELL_TYPE_BLANK))
							{
								LOGGER.debug("Checking the cell type of father's last name");
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING)
								{ 
								
									if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
									{
										LOGGER.debug("Last Name of father is null or empty");
									}
									else
									{
									
										LOGGER.debug("Capturing value of header : Father's Last Name");
										LOGGER.debug("Father's last name is : " + cell.getStringCellValue());
										masterSheetImportDto.setLastNameFather(cell.getStringCellValue());
									}
									
								}
								else
								{
									int rowNumber = row.getRowNum() + 1;
									LOGGER.error("The cell type of father's last name does not have a valid value");
									return "Not a valid value of Father's last name at row number "+rowNumber+ ", value should have text only";
								}
							}

						else if (cell.getColumnIndex() == 11)
						{
							if((cell.getCellType()==Cell.CELL_TYPE_BLANK))
							{
								LOGGER.debug("Mother name is null");
								
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING)
								{
									if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
									{
										LOGGER.debug("Cell is null or empty");
									}
									else
									{
										LOGGER.debug("Capturing value of header : Mother Name");
										LOGGER.debug("Mother's Last Name is : " + cell.getStringCellValue());
										masterSheetImportDto.setMotherName(cell.getStringCellValue());
									}
									
									
								}
								else
								{
									int rowNumber = row.getRowNum() + 1;
									LOGGER.error("The cell type of mother's name does not have a valid value");
									return "Not a valid value for Mother's name column at row number: " +rowNumber+ ", value should have text only";	
								}
						}
					
						else if (cell.getColumnIndex() == 12) 
						{
							if((cell.getCellType()==Cell.CELL_TYPE_BLANK))
							{
								LOGGER.debug("Cell is blank");
							}
								
							else if(cell.getCellType() == Cell.CELL_TYPE_STRING)
							{
								LOGGER.debug("Cell is string type");
								if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
								{
									LOGGER.debug("Cell is string type with null or empty");
								}
								else
								{
									int rowNumber = row.getRowNum()+1;
									LOGGER.error("Invalid format for mobile number");
									return "Invalid format for mobile number at row: "+rowNumber;
								}
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
								{
									long mobileNumber = (long) cell.getNumericCellValue();
									long count = String.valueOf(mobileNumber).length();
									if(count<10)
									{
										int rowNumber = row.getRowNum() + 1;
										LOGGER.error("Mobile Number is less than 10 digits");
										return "Mobile Number cannot be less than 10 digits at row: "+rowNumber;
									}
									else 
									{
										LOGGER.debug("Capturing value of header : Mobile Number ");
										LOGGER.debug("Mobile Number is : " + (long) cell.getNumericCellValue());
										masterSheetImportDto.setMobileNumber((long) cell.getNumericCellValue());

									}
									
								}
								else 
								{
									int rowNumber = row.getRowNum() + 1;
									LOGGER.error("The cell type of mobile number does not have a valid value");
									return "Not a valid value for Mobile number column at row : "+rowNumber+ " value should have digits only";
								}
							}
						
						else if (cell.getColumnIndex() == 13)
						{
							if((cell.getCellType()==Cell.CELL_TYPE_BLANK))
							{
								LOGGER.debug("Cell type is blank");
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING)
								{ 
									if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
									{
										LOGGER.debug("Cell is empty or null");
									}
									else
									{
										LOGGER.debug("Capturing value for header : Education Level");
										LOGGER.debug("Education Level is : " + cell.getStringCellValue());
										masterSheetImportDto.setEducationQualification(cell.getStringCellValue());
									}
									
								}
								else
								{
									int rowNumber = row.getRowNum() + 1;
									LOGGER.error("The cell type of education qualitfication is numeric");
									return "Not a valid  value for Education Qualification at row: "+rowNumber+", It should be a text value ";
								}
							}

						
						else if (cell.getColumnIndex() == 14)
						{
							if((cell.getCellType() == Cell.CELL_TYPE_BLANK))
							{
								LOGGER.debug("Cell type for state is blank");
							}
							
							else if(cell.getCellType() == Cell.CELL_TYPE_STRING) 
								{
									if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
									{
										LOGGER.debug("Cell is null or empty");
									}
									else
									{
										LOGGER.debug("The value of state is : " + cell.getStringCellValue());
										masterSheetImportDto.setState(cell.getStringCellValue());
									}
									
								}
								else
								{
									LOGGER.error("State does not have a valid value");
									int rowNumber = row.getRowNum() + 1;
									int columnNumber = cell.getColumnIndex() + 1;
									LOGGER.error("The row number is " + rowNumber + " and column Number is : " + columnNumber);
									flag = false;
									return "Not a valid value for State column at row "+rowNumber+ ", value should have text only";
								}
							}
						
						else if (cell.getColumnIndex() == 15) 
						{
							if((cell.getCellType()==Cell.CELL_TYPE_BLANK))
							{
								LOGGER.debug("Cell is blank");
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING)
								{
									if(cell.getStringCellValue()==null||cell.getStringCellValue().isEmpty())
									{
										LOGGER.debug("Cell is null or empty");
									}
									else
									{
										LOGGER.debug("Capturing value of header : District");
										LOGGER.debug("The value of district is : " + cell.getStringCellValue());
										masterSheetImportDto.setDistrict(cell.getStringCellValue());
									}
															
								}
								else
								{
									int rowNumber = row.getRowNum() + 1;
									LOGGER.error("The cell type of district does not have valid value");
									return "Not a valid value for District column at row: "+rowNumber+ ", value should have text only";
								}
							}

						
						else if (cell.getColumnIndex() == 16) 
						{
							LOGGER.debug("Reading the column Aadhaar Card Number");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking if Aadhaar number consists value");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								
								LOGGER.error("Aadhar Number does not have any value");
								return "Aadhaar card number cannot be blank at row: "+rowNumber;
								
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="") {
									LOGGER.error("Aadhar Number does not have any value");
									return "Aadhaar card number cannot be blank at row: "+rowNumber;
								}
								else {
									LOGGER.error("Aadhar Number has string value");
									return "Please enter numeric value for Aadhaar card number at row: "+rowNumber;
								}
								
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
								
								if(cell.getNumericCellValue()<=0 ) {
									
									LOGGER.error("Aadhaar Number has numeric value less than or equal to zero"+cell.getNumericCellValue());
									return "Aadhaar card number is invalid at row : "+rowNumber;
								
								}
								else {
									
									long aadharNumber = (long) cell.getNumericCellValue();
									int count = String.valueOf(aadharNumber).length();
									LOGGER.debug("The number of digits in Aadhaar number : " + count);
									
									if(count==12) {
										LOGGER.debug("Aadhaar number is of 12 digits :" + cell.getNumericCellValue());
										masterSheetImportDto.setAdhaarCardNumber((long) cell.getNumericCellValue());
									}
									else {
										LOGGER.error("Aadhaar  number is not of 12digits :"+ cell.getNumericCellValue());
										LOGGER.error("Returing error message to enter 12 digits valid aadhaar number");
										return "Please enter 12 digits Aadhaar card number at row : "+rowNumber ;
									}
									
								}
								
								
							}
							else {
								LOGGER.error("Aadhaar card number is invalid");
								return "Invalid Aadhaar card number at row : "+row;
								
							}
				
						}
					
					
						else if (cell.getColumnIndex() == 17)
						{
							LOGGER.debug("Reading the column id proof type");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of id proof type");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The id proof type is blank, so skipping the insertion of id proof type value");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The id proof type has empty string, so skipping the insertion of id proof type value");
								}
								else {
									LOGGER.debug("The id proof type is string has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setIdProofType(cell.getStringCellValue());
								}
								
							}
							else {
								LOGGER.error("The id proof type is not blank and not String");
								LOGGER.error("Returning message: The id proof type has invalid format");
								return "Id proof type has invalid format at row : "+rowNumber;
								
							}

						}
					
					
						else if (cell.getColumnIndex() == 18)
						{
							LOGGER.debug("Reading the column id proof number");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of id proof number");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The id proof number is blank, so skipping the insertion of id proof number value");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue()=="" || cell.getStringCellValue().isEmpty()  ) {
									LOGGER.debug("The id proof number has empty string, so skipping the insertion of id proof number value");
								}
								else {
									LOGGER.debug("The id proof number is string has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setIdProofNumber(cell.getStringCellValue());
								}
								
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
								if(cell.getNumericCellValue()>0) {
									Long idProofNumber= (long) cell.getNumericCellValue();
									LOGGER.debug("The id proof number is a number, has value :"+ cell.getNumericCellValue());
									masterSheetImportDto.setIdProofNumber(idProofNumber.toString());
								}
								else {
									LOGGER.debug("The id proof number has numeric value less than 1 :" +cell.getNumericCellValue());
								}
							}
							else {
								LOGGER.error("The id proof number is not blank and not String");
								LOGGER.error("Returning message: The id proof number has invalid format");
								return "Id proof number has invalid format at row : "+rowNumber;
								
							}

						}
						else if (cell.getColumnIndex() == 19) 
						{
							LOGGER.debug("Reading the column Occupation / Category");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of Occupation / Category");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The Occupation / Category is blank, so skipping the insertion of Occupation / Category");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The Occupation / Category has empty string, so skipping the insertion of Occupation / Category");
								}
								else {
									LOGGER.debug("The Occupation / Category is string has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setOccupationType(cell.getStringCellValue());
								}
								
							}
							else {
								LOGGER.error("The Occupation / Category is not blank and not String");
								LOGGER.error("Returning message: The Occupation / Category has invalid format");
								return "Occupation / Category has invalid format at row : "+rowNumber;
								
							}

						}
						else if (cell.getColumnIndex() == 20) 
						{
							LOGGER.debug("Reading the column MS ID");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of MS ID");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column MS ID is blank, so skipping the insertion of MS ID");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The MS ID has empty string, so skipping the insertion of MS ID");
								}
								else {
									LOGGER.debug("The MS ID is string has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setMsId(cell.getStringCellValue());
								}
								
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
								if(cell.getNumericCellValue()>0) {
									Long msId = (long) cell.getNumericCellValue();
									LOGGER.debug("The MS Id has value : "+cell.getNumericCellValue());
									masterSheetImportDto.setMsId(msId.toString());
								}
								else {
									LOGGER.debug("MS ID has value less than 1, so skipping the insertion of Monthly Salary");
								}
								
							}
							else {
								LOGGER.error("The MS ID is not blank and not String");
								LOGGER.error("Returning message: The MS ID has invalid format");
								return "MS ID has invalid format at row : "+rowNumber;
								
							}

						}
						else if (cell.getColumnIndex() == 21)
						{
							LOGGER.debug("Reading the column Relation with SK/MS");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of Relation with SK/MS");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column Relation with SK/MS blank, so skipping the insertion of value for Relation with SK/MS");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The Relation with SK/MS has empty string, so skipping the insertion of value for Relation with SK/MS");
								}
								else {
									LOGGER.debug("The Relation with SK/MS is string, has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setRelationWithSKMS(cell.getStringCellValue());
								}
								
							}
							else {
								LOGGER.error("The Relation with SK/MS is not blank and not String");
								LOGGER.error("Returning message: The Relation with SK/MS has invalid format");
								return "Relation with SK/MS has invalid format at row : "+rowNumber;
								
							}

						}
						else if (cell.getColumnIndex() == 22)
						{
							LOGGER.debug("Reading the column Bank Name");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of Bank Name");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column Bank Name is blank, so skipping the insertion of value for Bank Name");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The Bank Name has empty string, so skipping the insertion of value for Bank Name");
								}
								else {
									LOGGER.debug("The Bank Name is string, has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setBankName(cell.getStringCellValue());
								}
								
							}
							else {
								LOGGER.error("The Bank Name is not blank and not String");
								LOGGER.error("Returning message: The Bank Name has invalid format");
								return "Bank Name has invalid format at row : "+rowNumber;
								
							}

						}
						else if (cell.getColumnIndex() == 23)
						{
							LOGGER.debug("Reading the column IFSC Code");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of IFSC Code");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column IFSC Code is blank, so skipping the insertion of value for IFSC Code");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The IFSC Code has empty string, so skipping the insertion of value for IFSC Code");
								}
								else {
									LOGGER.debug("The IFSC Code is string, has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setIfscCode(cell.getStringCellValue());
								}
								
							}
							
							else {
								LOGGER.error("The IFSC Code is not blank and not String");
								LOGGER.error("Returning message: The IFSC Code has invalid format");
								return "IFSC Code has invalid format at row : "+rowNumber;
								
							}

						}
						else if (cell.getColumnIndex() == 24) 
						{
							LOGGER.debug("Reading the column Bank A/C number");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of Bank A/C number");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column Bank A/C number is blank, so skipping the insertion of value for Bank A/C number");
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The Bank A/C number has empty string, so skipping the insertion of Bank A/C number");
								}
								else {
									LOGGER.error("The Bank A/C number has value string :"+ cell.getStringCellValue());
									return "Invalid Bank A/C Number format at row : "+rowNumber+". Please enter numeric value";
								}
								
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
								if(cell.getNumericCellValue()>0) {
									LOGGER.debug("The Bank A/C no. has value : "+cell.getNumericCellValue());
									masterSheetImportDto.setAccountNumber((long) cell.getNumericCellValue());
								}
								else {
									LOGGER.debug("Bank A/C no. has value less than 1, so skipping the insertion of Bank A/C no.");
								}
								
							}
							else {
								LOGGER.error("The Bank A/C number is not blank and not number");
								LOGGER.error("Returning message: The Bank A/C number has invalid format");
								return "Bank A/C number has invalid format at row : "+rowNumber;
								
							}
							
						}

						else if (cell.getColumnIndex() == 25) 
						{
							LOGGER.debug("Reading the cell residential address");
							int rowNumber = row.getRowNum() + 1;
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK)
							{
								
								LOGGER.error("Cell type of residential address is empty");
								return "Please enter residential address of candidate at row: "+rowNumber;
							}
							else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
							{
								
								LOGGER.error("Cell type of residential address is numeric");
								return "Residential address cannot have a numeric value at row: "+rowNumber;

							}
							else if (cell.getCellType() == Cell.CELL_TYPE_STRING)
							{
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.error("Residential address has no value");
									LOGGER.error("Returning message: Please enter residential address of candidate");
									return "Please enter residential address of candidate at row: "+rowNumber;
								}
								else {
									LOGGER.debug("Cell type of residential address is String");
									LOGGER.debug("Capturing value of header : Residential Address : "+cell.getStringCellValue());
									masterSheetImportDto.setResidentialAddress(cell.getStringCellValue());
								}
								
							}
							else
							{
								LOGGER.error("Not a valid value for Cell type of residential address");
								return "Invalid value for Residential address at row : "+rowNumber +" value should have text and digits";
							}
						}
						else if (cell.getColumnIndex() == 26) 
						{
							LOGGER.debug("Reading the cell workplace address");
							int rowNumber = row.getRowNum() + 1;
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK)
							{
								
								LOGGER.error("Cell type of workplace address is empty");
								return "Please enter workplace address of candidate at row: "+rowNumber;
							}
							else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
							{
								LOGGER.error("Cell type of workplace address is numeric");
								return "Workplace address cannot have a numeric value at row: "+rowNumber;

							}
							else if(cell.getCellType() == Cell.CELL_TYPE_STRING)
							{
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.error("Workplace address has no value");
									LOGGER.error("Returning message: Please enter Workplace address of candidate");
									return "Please enter Workplace address of candidate at row: "+rowNumber;
								}
								else {
									LOGGER.debug("Capturing value of header : Workplace Address "+cell.getStringCellValue());
									masterSheetImportDto.setWorkplaceAddress(cell.getStringCellValue());
								}
								
							}
							else
							{
								LOGGER.debug("Not a valid value for Workplace Address");
								return "Invalid value for Workplace address at row: "+rowNumber+ " value should have text and digits";
							}
						}
						else if (cell.getColumnIndex() == 27)
						{
							LOGGER.debug("Reading the column Medical Examination Conducted");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of Medical Examination Conducted");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column Medical Examination Conducted is blank, so skipping the insertion of value for Medical Examination Conducted");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The Medical Examination Conducted has empty string, so skipping the insertion of value for Medical Examination Conducted");
								}
								else {
									LOGGER.debug("The Medical Examination Conducted is string, has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setMedicalExaminationConducted(cell.getStringCellValue());
								}
								
							}
							else {
								LOGGER.error("The Medical Examination Conducted is not blank and not String");
								LOGGER.error("Returning message: The Medical Examination Conducted has invalid format");
								return "Medical Examination Conducted has invalid format at row : "+rowNumber;
								
							}

						}
						else if (cell.getColumnIndex() == 28) 
						{
							LOGGER.debug("Reading the column Assessment Result");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of Assessment Result");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column Assessment Result is blank, so skipping the insertion of value for Assessment Result");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The Assessment Result has empty string, so skipping the insertion of value for Assessment Result");
								}
								else {
									LOGGER.debug("The Assessment Result is string, has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setAssessmentResult(cell.getStringCellValue());
								}
								
							}
							else {
								LOGGER.error("The Assessment Result is not blank and not String");
								LOGGER.error("Returning message: The Assessment Result has invalid format");
								return "Assessment Result has invalid format at row : "+rowNumber;
								
							}

						} 
						else if (cell.getColumnIndex() == 29)
						{
							LOGGER.debug("Reading the column Employment Type");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of Employment Type");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column Employment Type is blank, so skipping the insertion of value for Employment Type");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The Employment Type has empty string, so skipping the insertion of value for Employment Type");
								}
								else {
									LOGGER.debug("The Employment Type is string, has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setEmploymentType(cell.getStringCellValue());
								}
								
							}
							else {
								LOGGER.error("The Employment Type is not blank and not String");
								LOGGER.error("Returning message: The Employment Type has invalid format");
								return "Invalid Employment Type format at row : "+rowNumber;
								
							}

						}
						else if (cell.getColumnIndex() == 30)
						{
							LOGGER.debug("Reading the column Whether hired on ad hoc / contractual basis by the MC directly (Yes/No)");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of Whether hired on ad hoc / contractual basis by the MC directly (Yes/No)");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column Whether hired on ad hoc / contractual basis by the MC directly (Yes/No) is blank, so skipping the insertion of this value");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The Whether hired on ad hoc / contractual basis by the MC directly (Yes/No) has empty string, so skipping the insertion of this value");
								}
								else if(cell.getStringCellValue().length()>3) {
									LOGGER.error("The Whether hired on ad hoc / contractual basis by the MC directly (Yes/No) has more than 3 letter");
									LOGGER.error("Returning error message: Please enter 'Yes' or 'No' for Whether hired on ad hoc / contractual basis by the MC directly ");
									return "Please enter 'Yes' or 'No' for Whether hired on ad hoc / contractual basis by the MC directly at row : "+rowNumber;
								}
								else {
									LOGGER.debug("The Whether hired on ad hoc / contractual basis by the MC directly (Yes/No) is string, has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setHiredByMc(cell.getStringCellValue());
								}
								
							}
							else {
								LOGGER.error("The Whether hired on ad hoc / contractual basis by the MC directly (Yes/No) is not blank and not String");
								LOGGER.error("Returning message: The Whether hired on ad hoc / contractual basis by the MC directly (Yes/No) has invalid format");
								return "Column 'Whether hired on ad hoc' has invalid format at row : "+rowNumber;
								
							}

						}
						else if (cell.getColumnIndex() == 31)
						{
							LOGGER.debug("Reading the column Name of Employer");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the type of column Name of Employer");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column Name of Employer is blank, so skipping the insertion of this value");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The Name of Employer has empty string, so skipping the insertion of this value");
								}
								else {
									LOGGER.debug("The Name of Employer is string, has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setOutsourcedEmployerName(cell.getStringCellValue());
								}
								
							}
							else {
								LOGGER.error("The Name of Employer is not blank and not String");
								LOGGER.error("Returning message: The Name of Employer has invalid format");
								return "Invalid format of Name of Employer at row : "+rowNumber;
								
							}

						}
						else if (cell.getColumnIndex() == 32)
						{
							LOGGER.debug("Reading the column Employer Contact Number");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of Employer Contact Number");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column Employer Contact Number is blank, so skipping the insertion of value for Employer Contact Number");
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The Employer Contact Number has empty string, so skipping the insertion of Employer Contact Number");
								}
								else {
									LOGGER.error("TheEmployer Contact Number has value string :"+ cell.getStringCellValue());
									return "Invalid format Employer Contact Number at row : "+rowNumber+", Please enter numeric value";
								}
								
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
								if(cell.getNumericCellValue()>0) {
									LOGGER.debug("The Employer Contact Number has value : "+cell.getNumericCellValue());
									masterSheetImportDto.setOutsourcedEmployerContact((long) cell.getNumericCellValue());
								}
								else {
									LOGGER.debug("Employer Contact Number has value less than 1, so skipping the insertion of Employer Contact Number");
								}
								
							}
							else {
								LOGGER.error("The Employer Contact Number is not blank and not number");
								LOGGER.error("Returning message: The Employer Contact Number has invalid format");
								return "Invalid format of Employer Contact Number at row : "+rowNumber;
								
							}
							
						}
						else if (cell.getColumnIndex() == 33)
						{
							LOGGER.debug("Reading the column Monthly Salary");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the cell type of Monthly Salary");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column Monthly Salary is blank, so skipping the insertion of value for Monthly Salary");
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The Monthly Salary has empty string, so skipping the insertion of Employer Contact Number");
								}
								else {
									LOGGER.error("The Monthly Salary has value string :"+ cell.getStringCellValue());
									return "Invalid format of Monthly Salary at row : "+rowNumber+". Please enter numeric value";
								}
								
							}
							else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
								if(cell.getNumericCellValue()>0) {
									LOGGER.debug("The Monthly Salary has value : "+cell.getNumericCellValue());
									masterSheetImportDto.setMonthlySalary((long) cell.getNumericCellValue());
								}
								else {
									LOGGER.debug("Monthly Salary has value less than 1, so skipping the insertion of Monthly Salary");
								}
								
							}
							else {
								LOGGER.error("The Monthly Salary is not blank and not number");
								LOGGER.error("Returning message: The Employer Contact Number has invalid format");
								return "Invalid format Employer Contact Number at row : "+rowNumber;
								
							}
							
						}
						else if (cell.getColumnIndex() == 34)
						{
							LOGGER.debug("Reading the column PF/ESI Provided");
							
							int rowNumber = row.getRowNum() + 1;
							LOGGER.debug("Checking the type of column PF/ESI Provided");
							
							if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
								LOGGER.debug("The column PF/ESI Provided is blank, so skipping the insertion of this value");
							}
							
							else if(cell.getCellType()==Cell.CELL_TYPE_STRING) {
								
								if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty() || cell.getStringCellValue()=="" ) {
									LOGGER.debug("The PF/ESI Provided has empty string, so skipping the insertion of this value");
								}
								else if(cell.getStringCellValue().length()>3) {
									LOGGER.error("The PF/ESI Provided(Yes/No) has more than 3 letter");
									LOGGER.error("Returning error message: Please enter 'Yes' or 'No' for PF/ESI Provided");
									return "Please enter 'Yes' or 'No' for PF/ESI Provided at row : "+rowNumber;
								}
								else {
									LOGGER.debug("The PF/ESI Provided is string, has value :"+ cell.getStringCellValue());
									masterSheetImportDto.setPfOrEsiProvided(cell.getStringCellValue());
								}
								
							}
							else {
								LOGGER.error("The PF/ESI Provided is not blank and not String");
								LOGGER.error("Returning message: The PF/ESI Provided has invalid format");
								return "PF/ESI Provided has invalid format at row : "+rowNumber;
								
							}

						}
				}
				candidateDetails.add(masterSheetImportDto);
				insertResult = dataImportDao.masterSheetImport(candidateDetails, batchId);
			    fileStream.close();
			
			if(insertResult==-425)
			{
				LOGGER.debug("Duplicate value of mobile number is present");
				rowFlag=false;
				int rowNumber = row.getRowNum() + 1;
				return "Value of Mobile Number is duplicate for batch id "+batchId+" at row number "+rowNumber;
			}
			else if(insertResult==-696)
			{
				LOGGER.debug("Duplicate value of aadhar card exists while updating");
				rowFlag=false;
				int rowNumber = row.getRowNum() + 1;
				return "Value of Aadhaar Number is duplicate for batch id "+batchId+" at row number "+rowNumber;
			}
			else if(insertResult==-989)
			{
				LOGGER.debug("Duplicate value of mobile number present while updating");
				rowFlag=false;
				int rowNumber = row.getRowNum() + 1;
				return "Value of Mobile Number is duplicate for batch id "+batchId+" at row number "+rowNumber;
			}
			else if(insertResult == -265)
			{
				
				LOGGER.debug("Duplicate aadhar number found");
				rowFlag=false;
				int rowNumber = row.getRowNum() + 1;
				return "Value of Aadhaar Number is duplicate for batch id "+batchId+" at row number "+rowNumber;
			}
			else if (insertResult < 1) {
				LOGGER.debug("In IF -- When insertResult of Excel Sheet is < 1 :" + insertResult);
				LOGGER.debug("Returning message - 'File cannot be uploaded'");
				rowFlag=false;
				return "File cannot be uploaded";
			}

			
		}
			return "File Uploaded Successfully";
			//to be reviewed till here - end of try block
		}
		
		catch(IOException io)
		{
			LOGGER.error("An input output error occured while reading candidate excel sheet. Exception is : "+ io);
			return "An error has occured please contact administrator";
		}
		catch(Exception e)
		{
			LOGGER.error("An input output error occured while reading candidate excel sheet. Exception is : "+ e);
			return "An error has occured please contact administrator";
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
	 * Method to get remaining targets
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
				{
					LOGGER.debug("No Candidate Details available");
					LOGGER.debug("Adding enrollment number to excel sheet");
					Integer batchSize = 1;
					while(batchSize <= 50)
					{
						candidateSheetInformation.add(new MasterSheetImportDto(batchId+"/"+batchSize.toString()));
						batchSize++;
					}
				}
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

		batchStatus = dataImportDao.updateBatchDetails(masterSheetSubmitDto);

		LOGGER.debug("Status of batch update is : " + batchStatus);
		LOGGER.debug("Status of employer update/insert is : "+employerStatus );

		status = 1;
		return status;
	}


}