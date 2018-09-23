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

	/* Object of Master Sheet RowMapper */
	private static final GenerateMasterSheetRowmapper generateMasterSheetRowMapper = new GenerateMasterSheetRowmapper();
	private static final GenerateCandidateSheetRowmapper generateCandidateSheetRowMapper = new GenerateCandidateSheetRowmapper();

	@Autowired
	private DataImportConfig dataImportConfig;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DataImportDao.class);

	/*-------- Excel Sheet Import Method --------------*/

	public Integer masterSheetImport(
			ArrayList<MasterSheetImportDto> candidateDetails, String batchId) {
		int i = 0;
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
			while (itr.hasNext()) {
				// Checking existence of candidate in database using enrollment
				// number
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
				parameters.put("medicalExaminationConducted", candidateDetails.get(i).getMedicalExaminationConducted());
				parameters.put("relationWithSKMS", candidateDetails.get(i).getRelationWithSKMS());
				parameters.put("batchId", batchId);

				if (checkCandidateExistence == 0) {
					LOGGER.debug("TRYING -- Inserting candidate details");
					LOGGER.debug("Executing query to insert candidate details");
					candidateInsertStatus = getJdbcTemplate().update(dataImportConfig.getImportCandidate(),parameters);
					Long bankaccountNumber = candidateDetails.get(i).getAccountNumber();
					if(bankaccountNumber!=null && bankaccountNumber!=0) {
						Map<String, Object> bankparams = new HashMap<>();
						LOGGER.debug("In IF -- When bank account number is received from frontend");
						bankparams.put("accountNumber", bankaccountNumber);
						LOGGER.debug("Executing query to check if bank account number exists");
						bankExistence = getJdbcTemplate().queryForObject(dataImportConfig.getCheckBankExistence(), bankparams, Integer.class);
						
						if(bankExistence == 0) {
							LOGGER.debug("In IF -- When bank Account number does not exists");
							Map<String, Object> updatedParams = new HashMap<>();
							updatedParams.put("accountNumber", candidateDetails.get(i).getAccountNumber());
							updatedParams.put("ifscCode",candidateDetails.get(i).getIfscCode());
							updatedParams.put("bankName",candidateDetails.get(i).getBankName());
							updatedParams.put("enrollmentNumber",candidateDetails.get(i).getEnrollmentNumber());
							LOGGER.debug("Executing insert query for new candidate bank detail while importing excel sheet");
							return getJdbcTemplate().update(dataImportConfig.getImportBankDetails(),updatedParams);
							
						}
						else if(bankExistence == 1) {
							LOGGER.debug("In ELSE -- When bank Account number does exist");
							Map<String, Object> updatedParams = new HashMap<>();
							updatedParams.put("accountNumber", candidateDetails.get(i).getAccountNumber());
							updatedParams.put("ifscCode",candidateDetails.get(i).getIfscCode());
							updatedParams.put("bankName",candidateDetails.get(i).getBankName());
							updatedParams.put("enrollmentNumber",candidateDetails.get(i).getEnrollmentNumber());
							LOGGER.debug("Executing update query for existing candidate bank detail while importing excel sheet");
							return getJdbcTemplate().update(dataImportConfig.getUpdateExistingBankDetails(),updatedParams);
						}
						else {
							LOGGER.debug("IN ELSE -- When Bank account existence results unexpected value");
							return -1;
						}
						
						
					}
					else {
						return 1;
					}
					
					
				}

				else if (checkCandidateExistence == 1) {

					LOGGER.debug("TRYING -- Updating candidate details");
					LOGGER.debug("Executing query to update candidate details");
					candidateInsertStatus = getJdbcTemplate().update(dataImportConfig.getUpdateExistingDetails(),parameters);
					Long bankaccountNumber = candidateDetails.get(i).getAccountNumber();
					if(bankaccountNumber!=null && bankaccountNumber!=0) {
						Map<String, Object> bankparams = new HashMap<>();
						LOGGER.debug("In IF -- When bank account number is received from frontend");
						bankparams.put("accountNumber", bankaccountNumber);
						LOGGER.debug("Executing query to check if bank account number exists");
						bankExistence = getJdbcTemplate().queryForObject(dataImportConfig.getCheckBankExistence(), bankparams, Integer.class);
						
						if(bankExistence == 0) {
							LOGGER.debug("In IF -- When bank Account number does not exists");
							Map<String, Object> updatedParams = new HashMap<>();
							updatedParams.put("accountNumber", candidateDetails.get(i).getAccountNumber());
							updatedParams.put("ifscCode",candidateDetails.get(i).getIfscCode());
							updatedParams.put("bankName",candidateDetails.get(i).getBankName());
							updatedParams.put("enrollmentNumber",candidateDetails.get(i).getEnrollmentNumber());
							LOGGER.debug("Executing insert query for new candidate bank while importing excel sheet");
							return getJdbcTemplate().update(dataImportConfig.getImportBankDetails(),updatedParams);
							
						}
						else if(bankExistence == 1) {
							LOGGER.debug("In ELSE -- When bank Account number does exist");
							Map<String, Object> updatedParams = new HashMap<>();
							updatedParams.put("accountNumber", candidateDetails.get(i).getAccountNumber());
							updatedParams.put("ifscCode",candidateDetails.get(i).getIfscCode());
							updatedParams.put("bankName",candidateDetails.get(i).getBankName());
							updatedParams.put("enrollmentNumber",candidateDetails.get(i).getEnrollmentNumber());
							LOGGER.debug("Executing update query for existing candidate bank while importing excel sheet");
							return getJdbcTemplate().update(dataImportConfig.getUpdateExistingBankDetails(),updatedParams);
						}
						else {
							LOGGER.debug("IN ELSE -- When Bank account existence results unexpected value");
							return -1;
						}
						
						
					}
					
					else {
						return 1;
					}
					
					
				
				}
				i++;

			}
			LOGGER.debug("Returning insert status for Excel Sheet import"+ candidateInsertStatus);
			return candidateInsertStatus;
		} catch (Exception e) {
			LOGGER.error("CATCHING -- Exception while inserting the Excel sheet");
			LOGGER.error("In masterSheetImport" + e);
			LOGGER.error("Returning -25");
			return -25;
		}

	}
	
	
	
	
	
	

	/*--------------- Download Master Sheet Code -------------------- */

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

	/*------------------------------RowMapper to download Master/Candidate Sheet-----------------------------*/
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
			 long   mobileNumber = rs.getLong("mobileNumber");
			 String educationQualification = rs.getString("educationLevel");
			 String state = rs.getString("state");
			 String district = rs.getString("district");
			 long  adhaarCardNumber = rs.getLong("aadharCardNumber");
			 String idProofType = rs.getString("idProofType");
			 String idProofNumber= rs.getString("idProofNumber");
			 String disabilityType = rs.getString("disabilityType");
			 int age = rs.getInt("age");
			 String bankName = rs.getString("bankName");
			 String ifscCode = rs.getString("ifscCode");
			 String workplaceAddress = rs.getString("workplaceAddress");
			 long accountNumber = rs.getLong("accountNumber");
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

	private static class BatchDetailsRowmapper implements
			RowMapper<BatchImportDto> {

		@Override
		public BatchImportDto mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			int centreId = rs.getInt("centreId");
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

			return new BatchImportDto(batchStartDate, batchEndDate,
					assessmentDate, medicalExamDate, selectionCommitteeDate,
					municipality, wardType, employerContactNumber, wardNumber,
					principalTrainerName, centreId, employerName, state,
					centreCity);

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







	public Integer getNsdcRegNumber(String userEmail) {
		// TODO Auto-generated method stub
		
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

}
