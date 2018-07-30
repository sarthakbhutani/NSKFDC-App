package com.nskfdc.scgj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.DataImportConfig;
import com.nskfdc.scgj.dto.BatchDto;
import com.nskfdc.scgj.dto.DownloadFinalMasterSheetDto;
import com.nskfdc.scgj.dto.FinancialDto;
import com.nskfdc.scgj.dto.GetBatchDetailsDto;
import com.nskfdc.scgj.dto.MasterSheetImportDto;
import com.nskfdc.scgj.dto.MasterSheetSubmitDto;


@Repository
public class DataImportDao extends AbstractTransactionalDao{
	
	/*  Object of Master Sheet RowMapper */
	private static final GenerateMasterSheetRowmapper generateMasterSheetRowMapper = new GenerateMasterSheetRowmapper();
	
	@Autowired
	private DataImportConfig dataImportConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataImportDao.class);
	
	/*-------- Excel Sheet Import Method --------------*/
	
	public Integer masterSheetImport(ArrayList<MasterSheetImportDto> candidateDetails,int batchId)
	{
		int i = 0;
		LOGGER.debug("Received array list of all the columns read from excel sheet for batchId " + batchId);
		LOGGER.debug("Creating an Iterator to iterate through the array list elements");
	try
		{
			LOGGER.debug("Received array list of all the columns read from excel sheet for batchId " + batchId);
			LOGGER.debug("Creating an Iterator to iterate through the array list elements");
			
			Iterator itr = candidateDetails.iterator();
			LOGGER.debug("Creating hashmap of objects ");
			Map<String,Object> parameters = new HashMap<>();
			
			Integer candidateInsertStatus = - 25;
			while(itr.hasNext())
			{
				parameters.put("enrollmentNumber", candidateDetails.get(i).getEnrollmentNumber());
				parameters.put("salutation", candidateDetails.get(i).getSalutation());
				parameters.put("firstName", candidateDetails.get(i).getFirstName());
				parameters.put("lastName", candidateDetails.get(i).getLastName());
				parameters.put("gender", candidateDetails.get(i).getGender());
				parameters.put("mobileNumber", candidateDetails.get(i).getMobileNumber());
				parameters.put("educationLevel",candidateDetails.get(i).getEducationQualification());
				parameters.put("state", candidateDetails.get(i).getState());
				parameters.put("district", candidateDetails.get(i).getDistrict());
				parameters.put("aadharCardNumber", candidateDetails.get(i).getAdhaarCardNumber());
				parameters.put("idProofType", candidateDetails.get(i).getIdProofType());
				parameters.put("idProofNumber",candidateDetails.get(i).getIdProofNumber());
				parameters.put("disabilityType", candidateDetails.get(i).getDisabilityType());
				parameters.put("age", candidateDetails.get(i).getAge());
				parameters.put("dob",candidateDetails.get(i).getDob());
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
				
				candidateInsertStatus = getJdbcTemplate().update(dataImportConfig.getImportCandidate(), parameters);
				if(candidateInsertStatus > 0)
				{
					Map<String,Object> params = new HashMap<>();
					params.put("accountNumber", candidateDetails.get(i).getAccountNumber());
					params.put("ifscCode", candidateDetails.get(i).getIfscCode());
					params.put("bankName", candidateDetails.get(i).getBankName());
					params.put("enrollmentNumber", candidateDetails.get(i).getEnrollmentNumber());
					
					return getJdbcTemplate().update(dataImportConfig.getImportBankDetails(), params);
					
				}
				i++;
				
			}
			return candidateInsertStatus;
		}
		catch(Exception e)
		{
			LOGGER.debug("An Exception occured while inserting the sheet details into database : " + e);
			return -25;
		}
			
	}

	/*--------------- Download Master Sheet Code -------------------- */
	
	public Collection<DownloadFinalMasterSheetDto> downloadMasterSheetDao(String userEmail){
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In Generate Occupation Certificate Dao");
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userEmail", userEmail);
		
		try {
			
			LOGGER.debug("In try block to download Final Master Sheet");
			return getJdbcTemplate().query(dataImportConfig.getDownloadMasterSheet(), parameters, generateMasterSheetRowMapper);
		}catch(Exception e) {
					
			LOGGER.error("In Catch Block");			
			LOGGER.error("An error occured while downloading the Final Master Sheet" + e);
					
			return null;
		}
	}
	 
    /*------------------------------RowMapper to download Master Sheet-----------------------------*/
    private static class GenerateMasterSheetRowmapper implements RowMapper<DownloadFinalMasterSheetDto>{
    
		@Override
		public DownloadFinalMasterSheetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						
			String trainingPartnerName = rs.getString("trainingPartnerName");
			String sectorSkillCouncil = rs.getString("sectorSkillCouncil");
			String jobRole = rs.getString("jobRole");
			String nsdcRegNumber = rs.getString("nsdcRegNumber");
			String batchId = rs.getString("batchId");
			
			return new DownloadFinalMasterSheetDto(trainingPartnerName,sectorSkillCouncil,jobRole,nsdcRegNumber,batchId);	
		}	
    }
    
    
    public Integer getTotalTargets(String userEmail){
        
        LOGGER.debug("Request received from Service");
        LOGGER.debug("In Total Targets Dao, to get Total Targets");
        
        
        try {
               
               LOGGER.debug("In try block");
               LOGGER.debug("Execute query to get TotalTargets");
               Map<String,Object> parameters = new HashMap<> ();
               parameters.put("userEmail", userEmail);
               return getJdbcTemplate().queryForObject(dataImportConfig.getShowTotalTargets(),parameters,Integer.class);
               
        } 
        catch(DataAccessException de)
        {
               LOGGER.error("A data access exception has occured: " + de);
               LOGGER.error("Returning zero");
               return null;
        }
        
        catch(Exception e)
        {
               LOGGER.error("An Exception occured: " + e);
               return null;
        }
        
 }

    	public Integer getTargetAchieved(String userEmail){
 
    			LOGGER.debug("Request received from Service");
    			LOGGER.debug("In Target Achieved Dao, to get Target Achieved");
 
 
    			try {
        
        LOGGER.debug("In try block");
        LOGGER.debug("Execute query to get Target Achieved");
        Map<String,Object> parameters = new HashMap<> ();
        parameters.put("userEmail", userEmail);
        return getJdbcTemplate().queryForObject(dataImportConfig.getShowTargetAchieved(),parameters,Integer.class);
        
 } 
 catch(DataAccessException de)
 {
        LOGGER.error("A data access exception has occured: " + de);
        LOGGER.error("Returning zero");
        return null;
 }
 
 catch(Exception e)
 {
        LOGGER.error("An Exception occured: " + e);
        return null;
 }
 
}
public Integer getRemainingTargets(String userEmail){
 
 LOGGER.debug("Request received from Service");
 LOGGER.debug("In Remaining Target Dao, to get Remaining Target");
 
 
 try {
        
        LOGGER.debug("In try block");
        LOGGER.debug("Execute query to get Target Achieved");
        Map<String,Object> parameters = new HashMap<> ();
        parameters.put("userEmail", userEmail);
        return getJdbcTemplate().queryForObject(dataImportConfig.getShowRemainingTargets(),parameters,Integer.class);
        
 } 
 catch(DataAccessException de)
 {
        LOGGER.error("A data access exception has occured: " + de);
        LOGGER.error("Returning zero");
        return null;
 }
 
 catch(Exception e)
 {
        LOGGER.error("An Exception occured: " + e);
        return null;
 }
 
}
public Integer ShowFinancialYear(String userEmail){
 
 LOGGER.debug("Request received from Service");
 LOGGER.debug("In Remaining Target Dao, to get FinancialYear");
 
 
 try {
        
        LOGGER.debug("In try block");
        LOGGER.debug("Execute query to get FinancialYear");
        Map<String,Object> parameters = new HashMap<> ();
        parameters.put("userEmail", userEmail);
        return getJdbcTemplate().queryForObject(dataImportConfig.getShowFinancialYear(),parameters,Integer.class);
        
 } 
 catch(DataAccessException de)
 {
        LOGGER.error("A data access exception has occured: " + de);
        LOGGER.error("Returning zero");
        return null;
 }
 
 catch(Exception e)
 {
        LOGGER.error("An Exception occured: " + e);
        return null;
 }
 
}
private static final FinancialRowmapper Financial_RowMapper = new FinancialRowmapper();


public Collection<FinancialDto> FinancialRowmapper(){
 try {
        
 
        return getJdbcTemplate().query(dataImportConfig.getShowFinancialYear(), Financial_RowMapper);
        
 } catch (Exception e) {
        
        
        return null;
        
 }
 
}

private static class FinancialRowmapper implements RowMapper<FinancialDto>{
 
 @Override
 public FinancialDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        String financialYear = rs.getString("FinancialDto");
        
        return new FinancialDto(financialYear);
        
 }
}
private static final BatchDetailsRowmapper BatchDetailsRM = new BatchDetailsRowmapper();

public GetBatchDetailsDto BatchDetails(String userEmail,String batchId) {
	
	LOGGER.debug("Request received from Service");
	LOGGER.debug("In BatchDetailsDao to get details of batch with userEmail" + userEmail);
	
			try {
				
				LOGGER.debug("In Try Block of BatchDetailsDao" + userEmail);
				LOGGER.debug("Creating hashmap of objects");
				Map<String,Object> parameters = new HashMap<>();
				LOGGER.debug("Inserting parameters into the hashmap " + userEmail);
				parameters.put("userEmail",userEmail); 
				parameters.put("batchId",batchId);
				
				LOGGER.debug("The parameter inserted in hashmap are : " + parameters.get("userEmail"));
				
				return  getJdbcTemplate().queryForObject(dataImportConfig.getBatchDetails(),parameters,BatchDetailsRM);

				
			}
			catch(Exception e) {
				LOGGER.error("In catch block of BatchDetailsDao");
				LOGGER.error("Error occured in BatchDetailsDao with exception" + e);
				return null;
			}
			
			
		}

private static class BatchDetailsRowmapper implements RowMapper<GetBatchDetailsDto>{
	
	@Override
	public GetBatchDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int centreId = rs.getInt("centreId");
		String state = rs.getString("state");
		String centreCity = rs.getString("centreCity");
		String municipality = rs.getString("municipality");
		Date selectionCommitteeDate = rs.getDate("selectionCommitteeDate");
		String principalTrainerName = rs.getString("principalTrainerName");
		Date batchStartDate = rs.getDate("batchStartDate");
		Date batchEndDate = rs.getDate("batchEndDate");
		Date assessmentDate = rs.getDate("assessmentDate");
		Date medicalExamDate = rs.getDate("medicalExamDate");
		String employerName = rs.getString("employerName");
		Long employerContactNumber = rs.getLong("employerContactNumber");
		
		return new GetBatchDetailsDto(centreId,state,centreCity,municipality,selectionCommitteeDate,principalTrainerName,batchStartDate,batchEndDate,assessmentDate,medicalExamDate,employerName,employerContactNumber);
		
	}
	
	
}

/*--------------------------Generate BatchId------------------------------------*/
public Integer generateBatchDao(String userEmail){
				
		LOGGER.debug("Request received from Service");
				
		LOGGER.debug("In  Import Dao, to get create batch for email id:" + userEmail);
				
		Map<String, Object> parameters = new HashMap<>();
				
		parameters.put("userEmail",userEmail);
				
		if(parameters.isEmpty())
		{
			LOGGER.error("Null Parameter");
		}
		
		try{
		        
			Integer result = getJdbcTemplate().update(dataImportConfig.getGenerateBatch(),parameters);
					
			LOGGER.debug("The result of the query is : " + result);
					
			return result;
		}
				
		catch(DataAccessException d) {
					
			LOGGER.error("DataAccessException in Dao" + d);
					
			return -1;
		}
				
		catch(Exception e) {
					
			LOGGER.error("In Catch Block");
					
			LOGGER.error("An error occured while generating the batch" + e);
					
			return -1;
		}
}

private static final BATCHRowmapper BATCH_RowMapper = new BATCHRowmapper();


public Collection<BatchDto> getBatchDetail(String userEmail){

	try {
		
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("userEmail", userEmail);
	
		return getJdbcTemplate().query(dataImportConfig.getshowbatchId(),parameters, BATCH_RowMapper);
		
	} catch (Exception e) {
		
		LOGGER.debug("An Exception occured while fetching batch id for email " + userEmail + e);
		return null;
		
	}
	
}


private static class BATCHRowmapper implements RowMapper<BatchDto>{
	
	@Override
	public BatchDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int batchId = rs.getInt("batchId");
		return new BatchDto(batchId);
		
	}
}






/*-----------------------Submit the data in database for respective batch---------------*/

public int checkCentreExistence(MasterSheetSubmitDto masterSheetSubmitDto) {
	
	try {
		
		LOGGER.debug("In Data Import Dao to check Existence of centre with respective centre Id");
		Map<String, Object> batchDetailsParameters = new HashMap<>();
		batchDetailsParameters.put("centreId", masterSheetSubmitDto.getCentreId());
		
		return getJdbcTemplate().queryForObject(dataImportConfig.getCheckCentreExistence(), batchDetailsParameters, Integer.class);
	} catch (Exception e) {
		LOGGER.debug("Exception handled in checking the existence of centre"+e);
		return -1;
	}
}

public int insertCentreDetails(String userEmail, MasterSheetSubmitDto masterSheetSubmitDto) {
	try {
		
		LOGGER.debug("In Data Import Dao to insert the centre details");
		Map<String, Object> centreParameters = new HashMap<>();
		centreParameters.put("centreId", masterSheetSubmitDto.getCentreId());
		LOGGER.debug("Centre Id is-----------------------"+masterSheetSubmitDto.getCentreId());
		centreParameters.put("state", masterSheetSubmitDto.getState());
		centreParameters.put("city", masterSheetSubmitDto.getCity());
		centreParameters.put("userEmail", userEmail);
		
		return getJdbcTemplate().update(dataImportConfig.getInsertCentreDetails(), centreParameters);
	} catch (DataAccessException e) {
		LOGGER.debug("Exception handled while inserting centre detail"+e);
		return -1;
	}
	
}

public int updateCentreDetails(MasterSheetSubmitDto masterSheetSubmitDto) {
	try {
		LOGGER.debug("In Data Import Dao");
		LOGGER.debug("To update the centre records");
		Map<String, Object> centreParameters = new HashMap<>();
		centreParameters.put("centreId", masterSheetSubmitDto.getCentreId());
		centreParameters.put("state", masterSheetSubmitDto.getState());
		centreParameters.put("city", masterSheetSubmitDto.getCity());
		return getJdbcTemplate().update(dataImportConfig.getUpdateCentreDetails(), centreParameters);
	} catch (DataAccessException e) {
		LOGGER.debug("Exception Handled while updating the centre records"+e);
		return -1;
	}
}

public int updateBatchDetails(MasterSheetSubmitDto masterSheetSubmitDto) {
	try {
		LOGGER.debug("In Data Import Dao");
		LOGGER.debug("To update the batch details");
		Map<String, Object> batchParameters = new HashMap<>();
		batchParameters.put("batchStartDate", masterSheetSubmitDto.getBatchStartDate());
		batchParameters.put("batchEndDate", masterSheetSubmitDto.getBatchEndDate());
		batchParameters.put("assessmentDate", masterSheetSubmitDto.getAssessmentDate());
		batchParameters.put("medicalExamDate", masterSheetSubmitDto.getMedicalExamDate());
		batchParameters.put("selectionCommitteeDate", masterSheetSubmitDto.getSelectionCommitteeDate());
		batchParameters.put("municipality", masterSheetSubmitDto.getMunicipality());
		batchParameters.put("wardType", masterSheetSubmitDto.getWardType());
		batchParameters.put("wardNumber", masterSheetSubmitDto.getWardNumber());
		batchParameters.put("trainerName", masterSheetSubmitDto.getTrainerName());
		batchParameters.put("batchId", masterSheetSubmitDto.getBatchId());
		
		return getJdbcTemplate().update(dataImportConfig.getUpdateBatchDetails(), batchParameters);
	} catch (DataAccessException e) {
		LOGGER.debug("Exception handled while updating the batch details in DataImportDao"+e);
		return -1;
	}
	
	
	
}




}






