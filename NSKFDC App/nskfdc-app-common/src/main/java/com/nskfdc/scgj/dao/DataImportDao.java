package com.nskfdc.scgj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.DataImportConfig;
import com.nskfdc.scgj.dto.BatchImportDto;
import com.nskfdc.scgj.dto.DownloadFinalMasterSheetDto;
import com.nskfdc.scgj.dto.FinancialDto;
import com.nskfdc.scgj.dto.MasterSheetImportDto;
import com.nskfdc.scgj.dto.MasterSheetSubmitDto;

@Repository
public class DataImportDao extends AbstractTransactionalDao {

	/**
	 * /* Object of Master Sheet RowMapper */
	private static final GenerateMasterSheetRowmapper generateMasterSheetRowMapper = new GenerateMasterSheetRowmapper();
	private static final GenerateCandidateSheetRowmapper generateCandidateSheetRowMapper = new GenerateCandidateSheetRowmapper();

	@Autowired
	private DataImportConfig dataImportConfig;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DataImportDao.class);

	/**
	 * Method that reads each value of cell of uploaded excel
	 * @param candidateDetails
	 * @param batchId
	 * @return Object of Integer class which holds the status of reading excel
	 */

	public Integer masterSheetImport(ArrayList<MasterSheetImportDto> candidateDetails, String batchId) {
		int i = 0;
		boolean flag=true;
		LOGGER.debug("Received array list of all the columns read from excel sheet for batchId "
				+ batchId);
		LOGGER.debug("Creating an Iterator to iterate through the array list elements");
		try {
			LOGGER.debug("Received array list of all the columns read from excel sheet for batchId "
					+ batchId);
			LOGGER.debug("Creating an Iterator to iterate through the array list elements");
			Iterator itr = candidateDetails.iterator();
			LOGGER.debug("Creating hashmap of objects ");
			Map<String, Object> parameters = new HashMap<>();
			Integer checkCandidateExistence = -50;
			Integer candidateInsertStatus = -25;
			Integer bankExistence = -1;

			LOGGER.debug("Inserting candidate details from Excel sheet imported");
			while (itr.hasNext()&&flag) {
				// Checking existence of candidate in database using enrollment number
				parameters.put("enrollmentNumber", candidateDetails.get(i).getEnrollmentNumber());
				LOGGER.debug("Inserting batchId into hashmap to check existence of the user");
				
				checkCandidateExistence = getJdbcTemplate().queryForObject(dataImportConfig.getCheckCandidateExistance(), parameters, Integer.class);
				parameters.put("enrollmentNumber", candidateDetails.get(i).getEnrollmentNumber());
				parameters.put("salutation", candidateDetails.get(i).getSalutation());
				parameters.put("firstName", candidateDetails.get(i).getFirstName());
				parameters.put("lastName", candidateDetails.get(i).getLastName());
				parameters.put("gender", candidateDetails.get(i).getGender());
				parameters.put("mobileNumber", candidateDetails.get(i).getMobileNumber());
				parameters.put("educationLevel", candidateDetails.get(i).getEducationQualification());
				parameters.put("state", candidateDetails.get(i).getState());
				parameters.put("district", candidateDetails.get(i).getDistrict());
				parameters.put("aadharCardNumber", candidateDetails.get(i).getAdhaarCardNumber());
				parameters.put("idProofType", candidateDetails.get(i).getIdProofType());
				parameters.put("idProofNumber", candidateDetails.get(i).getIdProofNumber());
				parameters.put("disabilityType", candidateDetails.get(i).getDisabilityType());
				parameters.put("age", candidateDetails.get(i).getAge());
				parameters.put("dob", candidateDetails.get(i).getDob());
				parameters.put("guardianType", candidateDetails.get(i).getGuardianType());
				parameters.put("firstNameFather", candidateDetails.get(i).getFirstNameFather());
				parameters.put("lastNameFather", candidateDetails.get(i).getLastNameFather());
				parameters.put("motherName", candidateDetails.get(i).getMotherName());
				parameters.put("residentialAddress", candidateDetails.get(i).getResidentialAddress());
				parameters.put("msId", candidateDetails.get(i).getMsId());
				parameters.put("occupationType", candidateDetails.get(i).getOccupationType());
				parameters.put("employmentType", candidateDetails.get(i).getEmploymentType());
				parameters.put("workplaceAddress", candidateDetails.get(i).getWorkplaceAddress());
				parameters.put("assessmentResult", candidateDetails.get(i).getAssessmentResult());
        		LOGGER.debug("The assessment result is : " +candidateDetails.get(i).getAssessmentResult());
				parameters.put("medicalExaminationConducted", candidateDetails.get(i).getMedicalExaminationConducted());
				parameters.put("relationWithSKMS", candidateDetails.get(i).getRelationWithSKMS());
				parameters.put("batchId", batchId);

				
				
				
				if (checkCandidateExistence == 0) {
					
					
					/**
					 * Start the review from this line
					 */
					
					//Checking existence of aadhar number for the new candidate
					
					LOGGER.debug("Checking the existence of unique aadhar in candidate");
					Integer candidateAadharExistence = checkAadharExistence(candidateDetails.get(i).getAdhaarCardNumber(),batchId);
					
					//Start review from here
					
					if(candidateAadharExistence == 0)
					{
						LOGGER.debug("Aadhar card of candidate is unique for batch id : " +batchId);
						LOGGER.debug("Checking the existence of mobile number for batch id: "+batchId);
						LOGGER.debug("Calling method checkMobileNumberExistence to check the existence of mobile number of candidate");
						
						Integer candidateMobileNumberExistence = checkMobileNumberExistence(candidateDetails.get(i).getMobileNumber(),batchId);
						
						if(candidateMobileNumberExistence==1)
						{
							LOGGER.error("Mobile number for candidate already exists");
							LOGGER.error("Sending error code -425 to service");
							flag=false;
							return -425;
						}
						else 
						{
							LOGGER.debug("Mobile number for candidate is unique");
							LOGGER.debug("Inserting the details of the candidates in the database");
							
							
							LOGGER.debug("TRYING -- Inserting candidate details");
							LOGGER.debug("Executing query to insert candidate details");
							candidateInsertStatus = getJdbcTemplate().update(dataImportConfig.getImportCandidate(),parameters);
							LOGGER.debug("Candidate Insert Status is : " +candidateInsertStatus);
							Long bankaccountNumber = candidateDetails.get(i).getAccountNumber();
							
							if(bankaccountNumber!=null && bankaccountNumber!=0) 
							{
								Map<String, Object> bankparams = new HashMap<>();
								LOGGER.debug("In IF -- When bank account number is received from frontend");
								bankparams.put("accountNumber", bankaccountNumber);
								LOGGER.debug("Executing query to check if bank account number exists");
								bankExistence = getJdbcTemplate().queryForObject(dataImportConfig.getCheckBankExistence(), bankparams, Integer.class);
								
								if(bankExistence == 0) {
									LOGGER.debug("In IF -- When bank Account number does not exists");
									try {
											
										Map<String, Object> updatedParams = new HashMap<>();
										updatedParams.put("accountNumber", candidateDetails.get(i).getAccountNumber());
										updatedParams.put("ifscCode",candidateDetails.get(i).getIfscCode());
										updatedParams.put("bankName",candidateDetails.get(i).getBankName());
										updatedParams.put("enrollmentNumber",candidateDetails.get(i).getEnrollmentNumber());
										LOGGER.debug("Executing insert query for new candidate bank detail while importing excel sheet");
										return getJdbcTemplate().update(dataImportConfig.getImportBankDetails(),updatedParams);
									
									}
									
									catch(Exception e)
									{
										LOGGER.error("An exception occured while inserting bank details in the database");
										LOGGER.error("Returning error code -754");
										return -754;
									}
									
								}
								else if(bankExistence == 1) {
									LOGGER.debug("In ELSE -- When bank Account number does exist");
									
									try {
										Map<String, Object> updatedParams = new HashMap<>();
										updatedParams.put("accountNumber", candidateDetails.get(i).getAccountNumber());
										updatedParams.put("ifscCode",candidateDetails.get(i).getIfscCode());
										updatedParams.put("bankName",candidateDetails.get(i).getBankName());
										updatedParams.put("enrollmentNumber",candidateDetails.get(i).getEnrollmentNumber());
										LOGGER.debug("Executing update query for existing candidate bank detail while importing excel sheet");
										return getJdbcTemplate().update(dataImportConfig.getUpdateExistingBankDetails(),updatedParams);
									}
									catch(Exception e)
									{
										LOGGER.error("An exception occured while updating the bank details of the candidates");
										return -363;
									}
									
									
								}
								else 
								{
									LOGGER.debug("IN ELSE -- When Bank account existence results unexpected value");
									flag=false;
									return -1;
								}
								
							}
								else 
								{
									LOGGER.debug("Bank details are not present");
									LOGGER.debug("Returning 1");
									return 1;
								}
			
						}
			
					}
					
					else
					{
						LOGGER.debug("Duplicate value found for aadhar number");
						LOGGER.debug("Returning error code -265");
						flag=false;
						return -265;
					}
					
					//Till here for candidate who is new to the system, first checks the aadhar number and then checks the mobile number of the candidate
	
				}

				else if (checkCandidateExistence == 1) {

					
					LOGGER.debug("Candidate exists in the system");
					LOGGER.debug("Sending request to checkUpdateCandidateAadhar() to check whether the existing candidate has unique aadhar number");
					
					Integer checkAadharNumber = checkUpdateCandidateAadhar(candidateDetails.get(i).getAdhaarCardNumber(), candidateDetails.get(i).getEnrollmentNumber(), batchId);
					
					//If a candidate already exists and wants to update his/her details
					//Review from here
					if(checkAadharNumber==0)
					{
						LOGGER.debug("The aadhar number is unique");
						LOGGER.debug("Checking if the mobile number is unique for batch");
						Integer checkMobileNumber = checkUpdatedMobileNumber(candidateDetails.get(i).getMobileNumber(),candidateDetails.get(i).getEnrollmentNumber(),batchId);
						if(checkMobileNumber == 0)
						{
							LOGGER.debug("Updating the existing details of the user");
							LOGGER.debug("TRYING -- Updating candidate details");
							LOGGER.debug("Executing query to update candidate details");
							candidateInsertStatus = getJdbcTemplate().update(dataImportConfig.getUpdateExistingDetails(),parameters);
							Long bankaccountNumber = candidateDetails.get(i).getAccountNumber();
							
							if(bankaccountNumber!=null && bankaccountNumber!=0) 
							{
								Map<String, Object> bankparams = new HashMap<>();
								LOGGER.debug("In IF -- When bank account number is received from frontend");
								bankparams.put("accountNumber", bankaccountNumber);
								LOGGER.debug("Executing query to check if bank account number exists");
								bankExistence = getJdbcTemplate().queryForObject(dataImportConfig.getCheckBankExistence(), bankparams, Integer.class);
								
								if(bankExistence == 0)
								{
									LOGGER.debug("In IF -- When bank Account number does not exists");
									Map<String, Object> updatedParams = new HashMap<>();
									updatedParams.put("accountNumber", candidateDetails.get(i).getAccountNumber());
									updatedParams.put("ifscCode",candidateDetails.get(i).getIfscCode());
									updatedParams.put("bankName",candidateDetails.get(i).getBankName());
									updatedParams.put("enrollmentNumber",candidateDetails.get(i).getEnrollmentNumber());
									LOGGER.debug("Executing insert query for new candidate bank while importing excel sheet");
									return getJdbcTemplate().update(dataImportConfig.getImportBankDetails(),updatedParams);
									
								}
								else if(bankExistence == 1)
								{
									LOGGER.debug("In ELSE -- When bank Account number does exist");
									Map<String, Object> updatedParams = new HashMap<>();
									updatedParams.put("accountNumber", candidateDetails.get(i).getAccountNumber());
									updatedParams.put("ifscCode",candidateDetails.get(i).getIfscCode());
									updatedParams.put("bankName",candidateDetails.get(i).getBankName());
									updatedParams.put("enrollmentNumber",candidateDetails.get(i).getEnrollmentNumber());
									LOGGER.debug("Executing update query for existing candidate bank while importing excel sheet");
									return getJdbcTemplate().update(dataImportConfig.getUpdateExistingBankDetails(),updatedParams);
								}
								else 
								{
									LOGGER.debug("IN ELSE -- When Bank account existence results unexpected value");
									return -1;
								}
								
								
							}
							
							else {
								LOGGER.debug("No value for bank details, updating rest of the details");
								return 1;
							}
						}
						
						else if(checkMobileNumber==1)
						{
							LOGGER.debug("Duplicate value for mobile number found");
							LOGGER.debug("Returning response code as -989");
							flag=false;
							return -989;
						}
						
						
					}
					 
					else if(checkAadharNumber==1)
					{   //if updated aadhar number that user is trying to insert in the system exists for an enrollment number in the same batch
						LOGGER.debug("Duplicate value found for aadhar number");
						LOGGER.debug("Returning response code -696");
						flag=false;
						return -696;
					}

				}
				
				i++;

			}
		
			//Review till here
			LOGGER.debug("Returning insert status for Excel Sheet import"+ candidateInsertStatus);
			return candidateInsertStatus;
		}
		
		catch(DuplicateKeyException duplicateKeyException)
		{
			LOGGER.error("Duplicate value found");
			LOGGER.error("An exception occured while inserting a composite/primary key" + duplicateKeyException);
			LOGGER.error("Returning error code -42");
			return -42;
		}
		
		
		catch (Exception e) {
			LOGGER.error("CATCHING -- Exception while inserting the Excel sheet");
			LOGGER.error("In masterSheetImport" + e);
			LOGGER.error("Returning -25");
			return -25;
		}
	}
	

	/**
	 * Data for  candidate details sheet
	 * @param userEmail
	 * @param batchId
	 * @return collection of candidate object
	 */
	public Collection<DownloadFinalMasterSheetDto> downloadMasterSheetDao(
			String userEmail, String batchId) {

		LOGGER.debug("Request received from service");
		LOGGER.debug("In downloadMasterSheetDao - to get masterSheet details");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userEmail", userEmail);
		parameters.put("batchId", batchId);

		try {

			LOGGER.debug("TRYING -- to get details for final Master Sheet");
			LOGGER.debug("Executing query to get details for Final Master Sheet");
			return getJdbcTemplate().query(
					dataImportConfig.getDownloadMasterSheet(), parameters,
					generateMasterSheetRowMapper);
		} catch (Exception e) {

			LOGGER.error("CATCHING -- Exception while getting details for final Master Sheet");
			LOGGER.error("In downloadMasterSheetDao" + e);
			LOGGER.error("Returning NULL");

			return null;
		}
	}
	/**
	 * Method to return data for master sheet import based on batchId
	 * @param batchId for which master sheet needs to be generated
	 * @return
	 */
	public Collection<MasterSheetImportDto> candidateSheetDetails(String batchId){
		LOGGER.debug("Request received from Service");
		LOGGER.debug("In candidateSheetDetails Dao - to get details of candidate of selected batchId");
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("batchId", batchId);

		try {

			LOGGER.debug("TRYING -- to get details for Candidate Sheet");
			LOGGER.debug("Executing query to get details for Candidate Sheet");
			return getJdbcTemplate().query(
					dataImportConfig.getCandidateSheet(), parameters, generateCandidateSheetRowMapper);
		} catch (Exception e) {

			LOGGER.error("CATCHING -- Exception while getting details for Candidate Sheet");
			LOGGER.error("In Dao, candidateSheetDetails" + e);
			LOGGER.error("Returning NULL");

			return null;
		}
	}

	/**
	 * Row mapper class for Master sheet
	 * @author Ruchi
	 *
	 */
	private static class GenerateMasterSheetRowmapper implements
			RowMapper<DownloadFinalMasterSheetDto> {

		@Override
		public DownloadFinalMasterSheetDto mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			String trainingPartnerName = rs.getString("trainingPartnerName");
			String sectorSkillCouncil = rs.getString("sectorSkillCouncil");
			String jobRole = rs.getString("jobRole");
			String nsdcRegNumber = rs.getString("nsdcRegNumber");
			String batchId = rs.getString("batchId");

			return new DownloadFinalMasterSheetDto(trainingPartnerName,
					sectorSkillCouncil, jobRole, nsdcRegNumber, batchId);
		}
	}
	/**
	 * Rowmapper class for generating candidate sheet
	 * @author Ruchi
	 *
	 */
	private static class GenerateCandidateSheetRowmapper implements
	RowMapper<MasterSheetImportDto> {

		@Override
		public MasterSheetImportDto mapRow(ResultSet rs, int rowNum)
				throws SQLException {
		
			String enrollmentNumber = rs.getString("enrollmentNumber");
			 String salutation = rs.getString("salutation");
			 String firstName = rs.getString("firstName");
			 String lastName = rs.getString("lastName");
			 String gender = rs.getString("gender");
			 Long   mobileNumber = rs.getLong("mobileNumber");
			 String educationQualification = rs.getString("educationLevel");
			 String state = rs.getString("state");
			 String district = rs.getString("district");
			 Long  adhaarCardNumber = rs.getLong("aadharCardNumber");
			 String idProofType = rs.getString("idProofType");
			 String idProofNumber= rs.getString("idProofNumber");
			 String disabilityType = rs.getString("disabilityType");
			 Integer age = rs.getInt("age");
			 String bankName = rs.getString("bankName");
			 String ifscCode = rs.getString("ifscCode");
			 String workplaceAddress = rs.getString("workplaceAddress");
			 Long accountNumber = rs.getLong("accountNumber");
			 String relationWithSKMS = rs.getString("relationWithSKMS");
			 Date dob = rs.getDate("dob");
			 String guardianType = rs.getString("guardianType");
			 String firstNameFather = rs.getString("firstNameFather");
			 String lastNameFather = rs.getString("lastNameFather");
			 String motherName = rs.getString("motherName");
			 String residentialAddress = rs.getString("residentialAddress");
			 String msId = rs.getString("msId");
			 String occupationType = rs.getString("occupationType");
			 String employmentType = rs.getString("employmentType");
			 String assessmentResult = rs.getString("assessmentResult");
			 String medicalExaminationConducted = rs.getString("medicalExamConducted");
			 
			 return new MasterSheetImportDto(	 enrollmentNumber,  salutation,  firstName,  lastName,
					 gender,  mobileNumber,  educationQualification,  state,  district,
					 adhaarCardNumber,  idProofType,  idProofNumber,  disabilityType,  age,
					 bankName,  ifscCode,  workplaceAddress,  accountNumber,  relationWithSKMS,
					 dob,  guardianType,  firstNameFather,  lastNameFather,  motherName,
					 residentialAddress,  msId,  occupationType,  employmentType,
					 assessmentResult,  medicalExaminationConducted);
			
		}
		}
	

	/**
	 * Method to find out targets assigned to training partner by SCGJ based on user email
	 * @param userEmail email address of logged in user
	 * @return Object of class integer which has value of assigned targets
	 */
	public Integer getTotalTargets(String userEmail) {

		LOGGER.debug("Request received from Service");
		LOGGER.debug("In getTotalTargets, to get Total Targets");

		try {
			String date1;
			String date2;
			LocalDate date = LocalDate.now();
			int year = date.getYear();
			int month = date.getMonthValue();

			if (month >= 4) {
				LOGGER.debug("In if condition, when month is between April & December");
				date1 = Integer.toString(year) + "-4-1";
				date2 = Integer.toString(year + 1) + "-3-31";

			} else {
				LOGGER.debug("In else condition, when month is between January & March");
				date1 = Integer.toString(year - 1) + "-4-1";
				date2 = Integer.toString(year) + "-3-31";

			}

			LOGGER.debug("TRYING -- To get Total targets of logged in TP");

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("userEmail", userEmail);
			parameters.put("date1", date1);
			parameters.put("date2", date2);
			LOGGER.debug("Execute query to get TotalTargets");
			return getJdbcTemplate().queryForObject(
					dataImportConfig.getShowTotalTargets(), parameters,
					Integer.class);

		} catch (EmptyResultDataAccessException re) {
			LOGGER.error("EXCEPTION :" + re);
			return 0;
		} catch (DataAccessException de) {
			LOGGER.error("CATCHING -- Data Access Exception has occured, while getting total Targets");
			LOGGER.error("In DataImportDao - getTotalTargets" + de);
			LOGGER.error("Returning zero");
			return -1;
		}

		catch (Exception e) {
			LOGGER.error("CATCHING -- Exception has occured, while getting total Targets");
			LOGGER.error("In DataImportDao - getTotalTargets" + e);
			LOGGER.error("Returning zero");
			return 0;
		}

	}

	/**
	 * Method to find out targets achieved by training partner
	 * @param userEmail
	 * @return Object of class Integer which has value of remaining targets
	 */
	public Integer getTargetAchieved(String userEmail) {

		LOGGER.debug("Request received from Service");
		LOGGER.debug("In getTargetAchieved, to get Target Achieved");

		try {
			String date1;
			String date2;
			LocalDate date = LocalDate.now();
			int year = date.getYear();
			int month = date.getMonthValue();

			if (month >= 4) {
				LOGGER.debug("In if condition, when month is between April & December");
				date1 = Integer.toString(year) + "-4-1";
				date2 = Integer.toString(year + 1) + "-3-31";

			} else {
				LOGGER.debug("In else condition, when month is between January & March");
				date1 = Integer.toString(year - 1) + "-4-1";
				date2 = Integer.toString(year) + "-3-31";

			}

			LOGGER.debug("TRYING -- to get Acheived targets");

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("userEmail", userEmail);
			parameters.put("date1", date1);
			parameters.put("date2", date2);
			LOGGER.debug("Executing query to get acheived targets of logged in TP");
			return getJdbcTemplate().queryForObject(
					dataImportConfig.getShowTargetAchieved(), parameters,
					Integer.class);
		} catch (EmptyResultDataAccessException re) {
			LOGGER.error("CATCHING -- Data Access Exception has occured, while getting achieved Targets");
			LOGGER.error("In DataImportDao - getTargetAchieved" + re);
			LOGGER.error("Returning zero");
			return 0;
		} catch (DataAccessException de) {
			LOGGER.error("CATCHING -- Data Access Exception has occured, while getting achieved Targets");
			LOGGER.error("In DataImportDao - getTargetAchieved" + de);
			LOGGER.error("Returning zero");
			return 0;
		}

		catch (Exception e) {
			LOGGER.error("CATCHING -- Exception has occured, while getting achieved Targets");
			LOGGER.error("In DataImportDao - getTargetAchieved" + e);
			LOGGER.error("Returning zero");

			return 0;
		}

	}

	/**
	 * Method to find out remaining targets 
	 * @param userEmail
	 * @return integer value for remaining targets
	 */
	public Integer getRemainingTargets(String userEmail) {

		LOGGER.debug("Request received from Service");
		LOGGER.debug("In getRemainingTargets, to get Remaining Target");

		try {
			String date1;
			String date2;
			LocalDate date = LocalDate.now();
			int year = date.getYear();
			int month = date.getMonthValue();

			if (month >= 4) {
				LOGGER.debug("In if condition, when month is between April & December");
				date1 = Integer.toString(year) + "-4-1";
				date2 = Integer.toString(year + 1) + "-3-31";

			} else {
				LOGGER.debug("In else condition, when month is between January & March");
				date1 = Integer.toString(year - 1) + "-4-1";
				date2 = Integer.toString(year) + "-3-31";

			}

			LOGGER.debug("TRYING -- To get remaining targets of logged in tP");

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("userEmail", userEmail);
			parameters.put("date1", date1);
			parameters.put("date2", date2);
			LOGGER.debug("Executing queries to get Remaining Targets");
			int totalTargets = getJdbcTemplate().queryForObject(
					dataImportConfig.getShowTotalTargets(), parameters,
					Integer.class);
			int acheivedTargets = getJdbcTemplate().queryForObject(
					dataImportConfig.getShowTargetAchieved(), parameters,
					Integer.class);
			return (totalTargets - acheivedTargets);
		} catch (EmptyResultDataAccessException de) {

			LOGGER.error("CATCHING --EmptyResultDataAccessException has occured,while getting Remaining Targets ");
			LOGGER.error("In DataImportDao - getRemainingTargets" + de);
			LOGGER.error("Returning zero");
			return 0;
		}

		catch (DataAccessException de) {

			LOGGER.error("CATCHING -- data access exception has occured,while getting Remaining Targets ");
			LOGGER.error("In DataImportDao - getRemainingTargets" + de);
			LOGGER.error("Returning zero");
			return -1;
		}

		catch (Exception e) {

			LOGGER.error("CATCHING -- Exception has occured, while getting Remaining Targets");
			LOGGER.error("In DataImportDao - getRemainingTargets" + e);
			LOGGER.error("Returning zero");
			return -2;
		}

	}

	/**
	 * Method to fetch financial year 
	 * @param userEmail
	 * @return
	 */
	public Integer ShowFinancialYear(String userEmail) {

		LOGGER.debug("Request received from Service to DataImportDao - ShowFinancialYear");
		LOGGER.debug("To get FinancialYear");

		try {

			LOGGER.debug("TRYING -- To get Financial year");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("userEmail", userEmail);
			LOGGER.debug("Execute query to get FinancialYear");
			return getJdbcTemplate().queryForObject(
					dataImportConfig.getShowFinancialYear(), parameters,
					Integer.class);

		} catch (EmptyResultDataAccessException re) {
			LOGGER.error("CATCHING --EmptyResultDataAccessException has occured: "
					+ re);
			LOGGER.error("In DataImportDao - ShowFinancialYear");
			LOGGER.error("Returning zero");
			return 0;
		}

		catch (DataAccessException de) {
			LOGGER.error("CATCHING -- data access exception has occured: " + de);
			LOGGER.error("In DataImportDao - ShowFinancialYear");
			LOGGER.error("Returning zero");
			return -1;
		}

		catch (Exception e) {

			LOGGER.error("CATCHING -- Exception has occured: " + e);
			LOGGER.error("In DataImportDao - ShowFinancialYear");
			LOGGER.error("Returning zero");
			return -2;
		}

	}

	private static final FinancialRowmapper Financial_RowMapper = new FinancialRowmapper();
/**
 * Method to return financial year
 * @return
 */
	public Collection<FinancialDto> FinancialRowmapper() {

		try {
			LOGGER.debug("TRYING -- to get the Financial Year");
			LOGGER.debug("Executing query to get financial year");
			return getJdbcTemplate().query(
					dataImportConfig.getShowFinancialYear(),
					Financial_RowMapper);

		} catch (Exception e) {
			LOGGER.error("CATCHING -- Exception handled while getting financial year");
			LOGGER.error("Exception in DataImportDao - FinancialRowmapper" + e);
			LOGGER.error("Returning null");

			return null;

		}

	}

	/**
	 * Row mapper for financial year
	 * @author Ruchi
	 *
	 */
	private static class FinancialRowmapper implements RowMapper<FinancialDto> {

		@Override
		public FinancialDto mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			String financialYear = rs.getString("FinancialDto");

			return new FinancialDto(financialYear);

		}
	}

	private static final BatchDetailsRowmapper BatchDetailsRM = new BatchDetailsRowmapper();

	/**
	 * Method to get details of Employer, Batch and Center from database
	 * 
	 * @param userEmail
	 * @param batchId
	 * @return
	 */
	public BatchImportDto BatchDetails(String userEmail, String batchId) {

		LOGGER.debug("Request received from Service");
		LOGGER.debug("In DataImportDao - BatchDetails to get details of batch of logged in TP");

		try {

			LOGGER.debug("TRYING to get batch details for selected batchId ");
			LOGGER.debug("Creating hashmap of objects");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting parameters into the hashmap ");
			parameters.put("userEmail", userEmail);
			parameters.put("batchId", batchId);

			LOGGER.debug("Executing query to get batch details for selected batch Id of logged in TP");
			// return getJdbcTemplate().
			return getJdbcTemplate().queryForObject(
					dataImportConfig.getBatchDetails(), parameters,
					BatchDetailsRM);

		} catch (Exception e) {
			LOGGER.error("CATCHING -- Exception handled while getting batch details of selected batchId");
			LOGGER.error("In DataImportDao - BatchDetails " + e);
			LOGGER.error("Returning null");
			return null;
		}

	}

	/**
	 * Row mapper class for batch details
	 * @author Ruchi
	 *
	 */
	private static class BatchDetailsRowmapper implements
			RowMapper<BatchImportDto> {

		@Override
		public BatchImportDto mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			Integer centreId = rs.getInt("centreId");
			String state = rs.getString("centreState");
			String centreCity = rs.getString("centreCity");
			String municipality = rs.getString("municipality");
			Date selectionCommitteeDate = rs.getDate("selectionCommitteeDate");
			String principalTrainerName = rs.getString("principalTrainerName");
			Date batchStartDate = rs.getDate("batchStartDate");
			Date batchEndDate = rs.getDate("batchEndDate");
			Date assessmentDate = rs.getDate("assessmentDate");
			Date medicalExamDate = rs.getDate("medicalExamDate");
			String employerName = rs.getString("employerName");
			String wardType = rs.getString("wardType");
			String wardNumber = rs.getString("wardNumber");
			Long employerContactNumber = rs.getLong("employerContactNumber");

			return new BatchImportDto( batchStartDate, batchEndDate,
					 assessmentDate, medicalExamDate,
					selectionCommitteeDate, municipality,
					wardType, employerContactNumber, wardNumber,
					principalTrainerName, centreId, employerName,
					state, centreCity);

		}

	}

//	/*--------------------------Generate BatchId------------------------------------*/
//
//	public Integer generateBatchDao(String userEmail) {
//
//		LOGGER.debug("Request received from Service , to create new batch for logged in TP");
//
//		LOGGER.debug("In  DataImportDao - generateBatchDao");
//
//		Map<String, Object> parameters = new HashMap<>();
//		LOGGER.debug("Inserting parameters in hashMap");
//		parameters.put("userEmail", userEmail);
//	
//
//		if (parameters.isEmpty()) {
//			LOGGER.debug("In IF -- When HasMap parameters are empty");
//			LOGGER.debug("In DataImportDao - generateBatchDao");
//			LOGGER.error("Null Parameter");
//		}
//
//		try {
//
//			LOGGER.debug("TRYING -- To generate batch");
//			LOGGER.debug("Executing insert query to enter a new batch");
//			Integer result = insert(dataImportConfig.getGenerateBatch(),
//					parameters, "batchId");
//			LOGGER.debug("The result of the query is : " + result);
//			LOGGER.debug("Returning result");
//			return result;
//		}
//
//		catch (EmptyResultDataAccessException e) {
//
//			LOGGER.error("CATCHING -- DataAcessException handled while generating batch");
//			LOGGER.error("In DataImportDao - generateBatchDao " + e);
//			LOGGER.error("Returning -1");
//			return 0;
//		} catch (DataAccessException d) {
//
//			LOGGER.error("CATCHING -- DataAcessException handled while generating batch");
//			LOGGER.error("In DataImportDao - generateBatchDao " + d);
//			LOGGER.error("Returning -1");
//			return -1;
//		}
//
//		catch (Exception e) {
//
//			LOGGER.error("CATCHING -- Exception handled while generating batch");
//			LOGGER.error("In DataImportDao - generateBatchDao " + e);
//			LOGGER.error("Returning -1");
//			return -2;
//		}
//	}


//
//	private static class BATCHRowmapper implements RowMapper<BatchDto> {
//
//		@Override
//		public BatchDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//			String batchId = rs.getString("batchId");
//			return new BatchDto(batchId);
//
//		}
//	}

	/*-----------------------Submit the data in database for respective batch---------------*/

//	public int checkCentreExistence(MasterSheetSubmitDto masterSheetSubmitDto) {
//		LOGGER.debug("Request receive to check centre Id existence");
//		LOGGER.debug("In DataImportDao - checkCentreExistence");
//
//		try {
//			LOGGER.debug("TRYING -- To check existence of entered centre id");
//			Map<String, Object> batchDetailsParameters = new HashMap<>();
//			LOGGER.debug("Inserting parameters to be checked, in HashMap");
//			batchDetailsParameters.put("centreId",
//					masterSheetSubmitDto.getCentreId());
//			LOGGER.debug("Exectuing query to check if entered centreId exists");
//			return getJdbcTemplate().queryForObject(
//					dataImportConfig.getCheckCentreExistence(),
//					batchDetailsParameters, Integer.class);
//		} catch (Exception e) {
//			LOGGER.error("CATCHING -- Exception handled while checking centre existence");
//			LOGGER.error("In DataImportDao - checkCentreExistence " + e);
//			LOGGER.error("Returning status code -1");
//			return -1;
//		}
//	}
//
//	public String insertCentreDetails(String userEmail,
//			MasterSheetSubmitDto masterSheetSubmitDto) {
//		LOGGER.debug("Request receive to insert the centre Details");
//		LOGGER.debug("In DataImportDao - insertCentreDetails");
//
//		try {
//			LOGGER.debug("TRYING -- To insert centre details");
//			Map<String, Object> centreParameters = new HashMap<>();
//			LOGGER.debug("Inserting parameters to be inserted, in HashMap");
//			centreParameters
//					.put("centreId", masterSheetSubmitDto.getCentreId());
//			centreParameters.put("state", masterSheetSubmitDto.getState());
//			centreParameters.put("city", masterSheetSubmitDto.getCity());
//			centreParameters.put("userEmail", userEmail);
//			LOGGER.debug(" Calling method to insert the details of entered centreId into database");
//			return insertString(dataImportConfig.getInsertCentreDetails(),
//					centreParameters, "centerId");
//
//		} catch (DataAccessException e) {
//			LOGGER.error("CATCHING -- Exception handled while inserting centre details");
//			LOGGER.error("In DataImportDao - insertCentreDetails " + e);
//			LOGGER.error("Returning status code -1");
//			return "-1";
//		}
//
//	}
//
//	public int updateCentreDetails(MasterSheetSubmitDto masterSheetSubmitDto) {
//		LOGGER.debug("Request receive to update the centre Details");
//		LOGGER.debug("In DataImportDao - updateCentreDetails");
//
//		try {
//			LOGGER.debug("TRYING -- To update centre details");
//			Map<String, Object> centreParameters = new HashMap<>();
//			LOGGER.debug("Inserting parameters to be updated in HashMap");
//			centreParameters
//					.put("centreId", masterSheetSubmitDto.getCentreId());
//			centreParameters.put("state", masterSheetSubmitDto.getState());
//			centreParameters.put("city", masterSheetSubmitDto.getCity());
//			LOGGER.debug("Executing Update query to update the details of entered centreId");
//			return getJdbcTemplate()
//					.update(dataImportConfig.getUpdateCentreDetails(),
//							centreParameters);
//		} catch (DataAccessException e) {
//			LOGGER.error("CATCHING -- Exception handled while updating centre details");
//			LOGGER.error("In DataImportDao - updateCentreDetails " + e);
//			LOGGER.error("Returning status code -1");
//			return -1;
//		}
//	}

	/**
	 * Update details of batch
	 * @param masterSheetSubmitDto
	 * @return status of success or failure of updating
	 */
	public int updateBatchDetails(MasterSheetSubmitDto masterSheetSubmitDto) {
		LOGGER.debug("Request receive to update the batchDetails");
		LOGGER.debug("In DataImportDao - updateBatchDetails");

		try {
			LOGGER.debug("TRYING -- To update batch details");

			Map<String, Object> batchParameters = new HashMap<>();
			LOGGER.debug("Inserting parameters to be updated in HashMap");
			batchParameters.put("batchStartDate",
					masterSheetSubmitDto.getBatchStartDate());
			batchParameters.put("batchEndDate",
					masterSheetSubmitDto.getBatchEndDate());
			batchParameters.put("assessmentDate",
					masterSheetSubmitDto.getAssessmentDate());
			batchParameters.put("medicalExamDate",
					masterSheetSubmitDto.getMedicalExamDate());
			batchParameters.put("selectionCommitteeDate",
					masterSheetSubmitDto.getSelectionCommitteeDate());
			batchParameters.put("municipality",
					masterSheetSubmitDto.getMunicipality());
			batchParameters.put("wardType", masterSheetSubmitDto.getWardType());
			batchParameters.put("wardNumber",
					masterSheetSubmitDto.getWardNumber());
			batchParameters.put("trainerName",
					masterSheetSubmitDto.getTrainerName());
			batchParameters.put("centreId", masterSheetSubmitDto.getCentreId());
			batchParameters.put("batchId", masterSheetSubmitDto.getBatchId());
			batchParameters.put("centreState", masterSheetSubmitDto.getState());
			batchParameters.put("centreCity", masterSheetSubmitDto.getCity());
			batchParameters.put("employerName", masterSheetSubmitDto.getEmployerName());
			batchParameters.put("employerContactNumber", masterSheetSubmitDto.getEmployerNumber());
			LOGGER.debug("Executing Update query to update the details of selected batchId");
			return getJdbcTemplate().update(
					dataImportConfig.getUpdateBatchDetails(), batchParameters);
		} catch (DataAccessException e) {
			LOGGER.error("CATCHING -- Exception handled while updating batch details");
			LOGGER.error("In DataImportDao - updateBatchDetails " + e);
			LOGGER.error("Returning status code -1");
			return -1;
		}

	}
  /**
      * This method returns the name of the training partner with the userEmail in session
      * @param userEmail
      * @return
      */
	public String getTrainingPartnerName(String userEmail) {
		LOGGER.debug("In DAO to get name of training partner with userEmail : " + userEmail);
		try
		{
			LOGGER.debug("In try block to get name of training partner with userEmail : " + userEmail);
			LOGGER.debug("Creating hash map to insert userEmail into the hashmap");
			Map<String,Object> userParams = new HashMap<>();
			userParams.put("userEmail", userEmail);
			if(userParams.isEmpty())
			{
				LOGGER.error("userParams are null in method getTrainingPartnerName");
				LOGGER.error("Returning NULL");
				return null;
			}
			if(dataImportConfig==null)
			{
				LOGGER.error("Object of config is null");
			}
			LOGGER.debug("Parameters inserted into hashmap ");
			return getJdbcTemplate().queryForObject(dataImportConfig.getShowTpName(), userParams, String.class);
		}
		catch(Exception e)
		{
			LOGGER.error("An Exception occured while fetching the name of TP with userEmail : " + userEmail);
			LOGGER.error("Exception is : " + e);
			LOGGER.error("Returning NULL");
			return null;
		}
		
	}

	/**
	 * This method returns the number of batches for a training partner who is logged into the system
	 * 
	 * @param userEmail
	 * @return
	 */

	public Integer getNumberOfBatches(String userEmail) {	
		LOGGER.debug("In method getNumberOfBatches to get the number of batches for user with email : " + userEmail);
		try {
			
			LOGGER.debug("In try block to get number of batches");
			LOGGER.debug("Creating HASH Map of objects");
			Map<String,Object> params = new HashMap<>();
			params.put("userEmail", userEmail);
			return getJdbcTemplate().queryForObject(dataImportConfig.getNumberOfBatches(), params, Integer.class); 
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while getting the number of batches for training partner : " + e);
			return null;
		}
		
	}

	/**
	 * This method inserts the unique batchID
	 * @param userEmail
	 * @param uniqueBatchId
	 * @return
	 */

	public Integer insertBatchId(String userEmail, String uniqueBatchId,String municipality) {

		LOGGER.debug("the unique batchId recieved from service is : " + uniqueBatchId);
		try
		{
			if(uniqueBatchId==null)
			{
				LOGGER.error("Unique batch id is null");
				return null;
			}
			LOGGER.debug("Inserting unique batch id into batch table");
			LOGGER.debug("Creating hashmap of objects");
			Map<String,Object> uniqueBatchIdParam = new HashMap<>();
			uniqueBatchIdParam.put("userEmail", userEmail);
			uniqueBatchIdParam.put("batchId", uniqueBatchId);
			uniqueBatchIdParam.put("municipality", municipality);
			return getJdbcTemplate().update(dataImportConfig.getInsertBatchId(),uniqueBatchIdParam);
		}
		catch(Exception e)
		{
			LOGGER.error("An error occured while inserting unique batch id : " + e);
			LOGGER.error("Returning NULL");
			return null;
		}
	}
	
	/**
	 * Check existence of candidate before inserting
	 * @param enrollmentNumber
	 * @param batchId
	 * @return
	 */
	public Integer checkCandidate(String enrollmentNumber, String batchId)
	{
		LOGGER.debug("In method checkCandidate to check existence of candidate with enrollment number  :  " + enrollmentNumber + " and batchID : " + batchId);
		LOGGER.debug("Creating hashmap of objects");
		try
		{
			Map<String,Object> checkParams = new HashMap<>();
			checkParams.put("enrollmentNumber", enrollmentNumber);
			checkParams.put("batchId", batchId);
			if(checkParams.isEmpty())
			{
				LOGGER.error("Parameters in method checkCandidate are empty");
				return -399;
			}
			return getJdbcTemplate().queryForObject(dataImportConfig.getCheckCandidate(), checkParams, Integer.class);   
					
		}
		catch(Exception e)
		{
			LOGGER.error("An error occured while checking the candidate with enrollment number : " + enrollmentNumber + " in method checkCandidate" + e);
			LOGGER.error("Returning error code -622");
			return -622;
		}
	}


	/**
	 * Methid to return NSDC registration number
	 * @param userEmail
	 * @return object of class integer that contains value of registration number
	 */
	public Integer getNsdcRegNumber(String userEmail) {
		
		LOGGER.debug("Request recieved to fetch nsdc registration number of training partner with email: " +userEmail);
		LOGGER.debug("Creating hash map of objects");
		try
		{
			Map<String,Object> userEmailParam = new HashMap<>();
			userEmailParam.put("userEmail", userEmail);
			Integer nsdcRegistrationNumber = getJdbcTemplate().queryForObject(dataImportConfig.getNsdcRegNumber(),userEmailParam, Integer.class);
			LOGGER.debug("The NSDC Registration number for user with email : " +userEmail +" is : "+ nsdcRegistrationNumber);
			return nsdcRegistrationNumber;
		}
		catch(Exception e)
		{
			LOGGER.error("An error occured while fetching the nsdc registration number " +e );
			LOGGER.error("Returning NULL");
			return null;
		}
		
	}
	
	public Integer checkAadharExistence(long aadharNumber,String batchId)
	{
		LOGGER.debug("Request recieved in checkAadharExistence method to check the existence of aadharcard for batch id : " +batchId);
		try
		{
			LOGGER.debug("In try block to check existence of aadhar number");
			LOGGER.debug("Creating hashmap of objects");
			Map<String,Object> existence = new HashMap<>();
			existence.put("aadharCardNumber",aadharNumber);
			existence.put("batchId", batchId);
			
			if(existence.isEmpty())
			{
				LOGGER.error("The hashmap is empty");
				return null;
			}
			
			LOGGER.debug("Calling jdbc template method to check the existence of aadhar number");
			
			return getJdbcTemplate().queryForObject(dataImportConfig.getCheckAadharNumberExistence(), existence, Integer.class);
			
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while checking the aadhar number for batch id: " +batchId);
			LOGGER.error("Returning error code -200");
			return -200;
		}
	}

	
	public Integer checkMobileNumberExistence(Long mobileNumber, String batchId)
	{
		LOGGER.debug("In method checkMobileNumberExistence to check the existence of the mobile number");
		try
		{
			LOGGER.debug("In try block to check the existence of mobile number for the batch : "+batchId);
			LOGGER.debug("Creating hashmap of objects to check the existence");
			Map<String,Object>mobileNumberExistence = new HashMap<>();
			mobileNumberExistence.put("mobileNumber", mobileNumber);
			mobileNumberExistence.put("batchId",batchId);
			return getJdbcTemplate().queryForObject(dataImportConfig.getCheckMobileNumberExistence(), mobileNumberExistence, Integer.class);
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while checking the existence of the mobile number of candidate in batch: "+batchId);
			LOGGER.error("Returning NULL");
			return -625;
		}
		
	}
	
	
	public Integer checkUpdateCandidateAadhar(Long aadharCardNumber,String enrollmentNumber, String batchId)
	{
		
		LOGGER.debug("Request recieved to check the existence of aadhar except for enrollment number "+enrollmentNumber+ " in batch : "+batchId);
		try
		{
			LOGGER.debug("In try block, to check existence of aadhar number");
			Map<String,Object>params = new HashMap<>();
			params.put("aadharCardNumber", aadharCardNumber);
			params.put("enrollmentNumber", enrollmentNumber);
			params.put("batchId", batchId);
			LOGGER.debug("Hashmap of objects created");
			return getJdbcTemplate().queryForObject(dataImportConfig.getUpdateCheckAadharNumberExistence(), params, Integer.class);
		}
		catch (Exception e)
		{
			LOGGER.error("An exception occured while checking the existence of the candidate "+e);
			return -55;
			
		}
	}
	
	public Integer checkUpdatedMobileNumber(Long mobileNumber,String enrollmentNumber, String batchId)
	{
		LOGGER.debug("In checkUpdatedMobileNumber()- to check the existence of mobile number in batch:  "+batchId+" except for enrollment number : "+enrollmentNumber);
		try
		{
			LOGGER.debug("In try block, creating hashmap of objects");
			Map<String,Object>parameters = new HashMap<>();
			parameters.put("mobileNumber", mobileNumber);
			parameters.put("enrollmentNumber",enrollmentNumber);
			parameters.put("batchId", batchId);
			LOGGER.debug("Created hashmap of objects");
			return getJdbcTemplate().queryForObject(dataImportConfig.getUpdateCheckMobileNumberExistence(),parameters,Integer.class);
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while checking for mobile number existence");
			LOGGER.error("Returning error code -621");
			return -621;
		}
	}
	
}
