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
			Integer checkCandidateExistence = - 50;
     		Integer candidateInsertStatus   = - 25;
     
     		LOGGER.debug("Inserting candidate details from Excel sheet imported");
     		while(itr.hasNext())
			{
     			// Checking existence of candidate in database using enrollment number
     			parameters.put("enrollmentNumber", candidateDetails.get(i).getEnrollmentNumber());
				LOGGER.debug("Inserting batchId into hashmap to check existence of the user");
				checkCandidateExistence = getJdbcTemplate().queryForObject(dataImportConfig.getCheckCandidateExistance(), parameters, Integer.class);
				
     			// Put inside if and else block
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
				
				if(checkCandidateExistence == 0)
				{
					 //if the details of the candidate are not present in the database
					
					try
					{
						
						candidateInsertStatus = getJdbcTemplate().update(dataImportConfig.getImportCandidate(), parameters);
					}
					
					catch(Exception e)
					{
						LOGGER.error("An exception occured while inserting new values in the database" + e);
						LOGGER.error("Returning null");
						return null;
					}
					
					if(candidateInsertStatus > 0)
					{
						
						try
						{
							Map<String,Object> params = new HashMap<>();
							params.put("accountNumber", candidateDetails.get(i).getAccountNumber());
							params.put("ifscCode", candidateDetails.get(i).getIfscCode());
							params.put("bankName", candidateDetails.get(i).getBankName());
							params.put("enrollmentNumber", candidateDetails.get(i).getEnrollmentNumber());
							LOGGER.debug("Executing query to uplate bank details of candidate");
							return getJdbcTemplate().update(dataImportConfig.getImportBankDetails(), params);

						}
						catch(Exception e)
						{
							LOGGER.error("CATCHING -- Exception Handled while inserting bank details of candidate from sheet");
							LOGGER.error("An Exception occured at line 120 while inserting new enteries in database");
							LOGGER.error("Exception is"+ e);
							LOGGER.error("Returning null");
							return null;
						}
												
					}
					
				}
				
				else if(checkCandidateExistence == 1)
				{
				
					candidateInsertStatus = getJdbcTemplate().update(dataImportConfig.getUpdateExistingDetails(), parameters);
					if(candidateInsertStatus > 0)
					{
						try
						{
							LOGGER.debug("TRYING -- To update the existing candidate from the sheet");
							Map<String,Object> updatedParams = new HashMap<>();
							updatedParams.put("accountNumber", candidateDetails.get(i).getAccountNumber());
							updatedParams.put("ifscCode", candidateDetails.get(i).getIfscCode());
							updatedParams.put("bankName", candidateDetails.get(i).getBankName());
							updatedParams.put("enrollmentNumber", candidateDetails.get(i).getEnrollmentNumber());
							LOGGER.debug("Executing update query for existing candidate while importing excel sheet");
							return getJdbcTemplate().update(dataImportConfig.getUpdateExistingBankDetails(), updatedParams);
						}
						catch(Exception e)
						{
							LOGGER.error("CATCHING -- Exception while updating import sheet details of candidate");
							LOGGER.error("An exception occured at line 148 while updating the details of candidates" + e);
							return null;
						}
						
					}
				}
				i++;
				
			}
     		LOGGER.debug("Returning insert status for Excel Sheet import");
			return candidateInsertStatus;
		}
		catch(Exception e)
		{
			LOGGER.error("CATCHING -- Exception while inserting the Excel sheet");			
			LOGGER.error("In masterSheetImport" + e);
			LOGGER.error("Returning -25");
			return -25;
		}
			
	}

	/*--------------- Download Master Sheet Code -------------------- */
	
	public Collection<DownloadFinalMasterSheetDto> downloadMasterSheetDao(String userEmail){
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In downloadMasterSheetDao - to get masterSheet details");
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userEmail", userEmail);
		
		try {
			
			LOGGER.debug("TRYING -- to get details for final Master Sheet");
			LOGGER.debug("Executing query to get details for Final Master Sheet");
			return getJdbcTemplate().query(dataImportConfig.getDownloadMasterSheet(), parameters, generateMasterSheetRowMapper);
		}catch(Exception e) {
					
			LOGGER.error("CATCHING -- Exception while getting details for final Master Sheet");			
			LOGGER.error("In downloadMasterSheetDao" + e);
			LOGGER.error("Returning NULL");
					
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
        LOGGER.debug("In getTotalTargets, to get Total Targets");
        
        
        try {
        		String date1;
        		String date2;
        	    LocalDate date = LocalDate.now(); 
        	    int year = date.getYear();
        	    int month= date.getMonthValue();
        	   
        	
        	if(month>=4) {
        		LOGGER.debug("In if condition, when month is between April & December");
        		date1= Integer.toString(year)+"-4-1";
        		date2= Integer.toString(year+1)+"-3-31";
        		
        		
        	}
        	else {
        		LOGGER.debug("In else condition, when month is between January & March");
        		date1= Integer.toString(year-1)+"-4-1";
        		date2= Integer.toString(year)+"-3-31";
        		
        	}
               
               LOGGER.debug("TRYING -- To get Total targets of logged in TP");
               
               Map<String,Object> parameters = new HashMap<> ();
               parameters.put("userEmail", userEmail);
               parameters.put("date1", date1);
               parameters.put("date2", date2);
               LOGGER.debug("Execute query to get TotalTargets");
               return getJdbcTemplate().queryForObject(dataImportConfig.getShowTotalTargets(),parameters,Integer.class);
               
        } 
        catch(DataAccessException de)
        {
        	LOGGER.error("CATCHING -- Data Access Exception has occured, while getting total Targets");
	        LOGGER.error("In DataImportDao - getTotalTargets" + de);
	        LOGGER.error("Returning zero");
               return 0;
        }
        
        catch(Exception e)
        {
        	LOGGER.error("CATCHING -- Exception has occured, while getting total Targets");
	        LOGGER.error("In DataImportDao - getTotalTargets" + e);
	        LOGGER.error("Returning zero");
               return 0;
        }
        
 }

    	
    
    
    
    public Integer getTargetAchieved(String userEmail){
 
    	LOGGER.debug("Request received from Service");
    	LOGGER.debug("In getTargetAchieved, to get Target Achieved");
 
    	try {
    		String date1;
    		String date2;
    	    LocalDate date = LocalDate.now(); 
    	    int year = date.getYear();
    	    int month= date.getMonthValue();
    	   
    	
    	if(month>=4) {
    		LOGGER.debug("In if condition, when month is between April & December");
    		date1= Integer.toString(year)+"-4-1";
    		date2= Integer.toString(year+1)+"-3-31";
    		
    		
    	}
    	else {
    		LOGGER.debug("In else condition, when month is between January & March");
    		date1= Integer.toString(year-1)+"-4-1";
    		date2= Integer.toString(year)+"-3-31";
    		
    	}
           
           LOGGER.debug("TRYING -- to get Acheived targets");
           
           Map<String,Object> parameters = new HashMap<> ();
           parameters.put("userEmail", userEmail);
           parameters.put("date1", date1);
           parameters.put("date2", date2);
           LOGGER.debug("Executing query to get acheived targets of logged in TP");
           return getJdbcTemplate().queryForObject(dataImportConfig.getShowTargetAchieved(),parameters,Integer.class);
        } 
		 catch(DataAccessException de)
		 {
			 LOGGER.error("CATCHING -- Data Access Exception has occured, while getting achieved Targets");
		        LOGGER.error("In DataImportDao - getTargetAchieved" + de);
		        LOGGER.error("Returning zero");
		        return 0;
		 }
		 
		 catch(Exception e)
		 {
			 	LOGGER.error("CATCHING -- Exception has occured, while getting achieved Targets");
		        LOGGER.error("In DataImportDao - getTargetAchieved" + e);
		        LOGGER.error("Returning zero");
		      
		        return 0;
		 }
		 
    }
		public Integer getRemainingTargets(String userEmail){
		 
		 LOGGER.debug("Request received from Service");
		 LOGGER.debug("In getRemainingTargets, to get Remaining Target");
		 
		 
		 try {
     		String date1;
     		String date2;
     	    LocalDate date = LocalDate.now(); 
     	    int year = date.getYear();
     	    int month= date.getMonthValue();
     	   
     	
     	if(month>=4) {
     		LOGGER.debug("In if condition, when month is between April & December");
     		date1= Integer.toString(year)+"-4-1";
     		date2= Integer.toString(year+1)+"-3-31";
     		
     		
     	}
     	else {
     		LOGGER.debug("In else condition, when month is between January & March");
     		date1= Integer.toString(year-1)+"-4-1";
     		date2= Integer.toString(year)+"-3-31";
     		
     	}
            
            LOGGER.debug("TRYING -- To get remaining targets of logged in tP");
            
            Map<String,Object> parameters = new HashMap<> ();
            parameters.put("userEmail", userEmail);
            parameters.put("date1", date1);
            parameters.put("date2", date2);
            LOGGER.debug("Executing queries to get Remaining Targets");
		    int totalTargets = getJdbcTemplate().queryForObject(dataImportConfig.getShowTotalTargets(),parameters,Integer.class);
		    int acheivedTargets=getJdbcTemplate().queryForObject(dataImportConfig.getShowTargetAchieved(),parameters,Integer.class);
		        return (totalTargets-acheivedTargets);
		 } 
		 catch(DataAccessException de)
		 {

		        LOGGER.error("CATCHING -- data access exception has occured,while getting Remaining Targets " );
		        LOGGER.error("In DataImportDao - getRemainingTargets" +de);
		        LOGGER.error("Returning zero");
		        return 0;
		 }
		 
		 catch(Exception e)
		 {

		        LOGGER.error("CATCHING -- Exception has occured, while getting Remaining Targets");
		        LOGGER.error("In DataImportDao - getRemainingTargets" + e);
		        LOGGER.error("Returning zero");
		        return 0;
		 }
	
	}
		
	
	public Integer ShowFinancialYear(String userEmail){
		 
		 LOGGER.debug("Request received from Service to DataImportDao - ShowFinancialYear");
		 LOGGER.debug("To get FinancialYear");
		 
		 
		 try {
		        
		        LOGGER.debug("TRYING -- To get Financial year");
		        Map<String,Object> parameters = new HashMap<> ();
		        parameters.put("userEmail", userEmail);
		        LOGGER.debug("Execute query to get FinancialYear");
		        return getJdbcTemplate().queryForObject(dataImportConfig.getShowFinancialYear(),parameters,Integer.class);
		        
		 } 
		 catch(DataAccessException de)
		 {
		        LOGGER.error("CATCHING -- data access exception has occured: " + de);
		        LOGGER.error("In DataImportDao - ShowFinancialYear");
		        LOGGER.error("Returning zero");
		        return 0;
		 }
		 
		 catch(Exception e)
		 {

		        LOGGER.error("CATCHING -- Exception has occured: " + e);
		        LOGGER.error("In DataImportDao - ShowFinancialYear");
		        LOGGER.error("Returning zero");
		        return 0;
		 }
		 
	}

	
	private static final FinancialRowmapper Financial_RowMapper = new FinancialRowmapper();


	public Collection<FinancialDto> FinancialRowmapper(){
		
		 try {
		        LOGGER.debug("TRYING -- to get the Financial Year");
		        LOGGER.debug("Executing query to get financial year");
		        return getJdbcTemplate().query(dataImportConfig.getShowFinancialYear(), Financial_RowMapper);
		        
		 } catch (Exception e) {
		        LOGGER.error("CATCHING -- Exception handled while getting financial year");
		        LOGGER.error("Exception in DataImportDao - FinancialRowmapper"+e);
		        LOGGER.error("Returning null");
		        
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
	LOGGER.debug("In DataImportDao - BatchDetails to get details of batch of logged in TP" );
	
			try {
				
				LOGGER.debug("TRYING to get batch details for selected batchId ");
				LOGGER.debug("Creating hashmap of objects");
				Map<String,Object> parameters = new HashMap<>();
				LOGGER.debug("Inserting parameters into the hashmap ");
				parameters.put("userEmail",userEmail); 
				parameters.put("batchId",batchId);
				
				LOGGER.debug("Executing query to get batch details for selected batch Id of logged in TP");
				
				return  getJdbcTemplate().queryForObject(dataImportConfig.getBatchDetails(),parameters,BatchDetailsRM);

				
			}
			catch(Exception e) {
				LOGGER.error("CATCHING -- Exception handled while getting batch details of selected batchId");
				LOGGER.error("In DataImportDao - BatchDetails " + e);
				LOGGER.error("Returning null");
				return null;
			}
			
			
		}


	private static class BatchDetailsRowmapper implements RowMapper<GetBatchDetailsDto>{
	
	@Override
	public GetBatchDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
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
		
		return new GetBatchDetailsDto( centreId,  state,  centreCity,  municipality,
				 selectionCommitteeDate,  principalTrainerName,  batchStartDate,  batchEndDate,
				 assessmentDate,  medicalExamDate,  employerName,  wardNumber,  wardType,
				 employerContactNumber);
		
	}
	
	
}


	/*--------------------------Generate BatchId------------------------------------*/

	public Integer generateBatchDao(String userEmail){
				
		LOGGER.debug("Request received from Service , to create new batch for logged in TP");
				
		LOGGER.debug("In  DataImportDao - generateBatchDao");
				
		Map<String, Object> parameters = new HashMap<>();
		LOGGER.debug("Inserting parameters in hashMap");		
		parameters.put("userEmail",userEmail);
				
		if(parameters.isEmpty())
		{
			LOGGER.debug("In IF -- When HasMap parameters are empty");
			LOGGER.debug("In DataImportDao - generateBatchDao");
			LOGGER.error("Null Parameter");
		}
		
		try{
		      
			LOGGER.debug("TRYING -- To generate batch");
			LOGGER.debug("Executing insert query to enter a new batch");
			Integer result = insert(dataImportConfig.getGenerateBatch(),parameters,"batchId");
			LOGGER.debug("The result of the query is : " + result);
			LOGGER.debug("Returning result");	
			return result;
		}
				
		catch(DataAccessException d) {
					
			LOGGER.error("CATCHING -- DataAcessException handled while generating batch");
			LOGGER.error("In DataImportDao - generateBatchDao " + d);
			LOGGER.error("Returning -1");
					
			return -1;
		}
				
		catch(Exception e) {
					
			LOGGER.error("CATCHING -- Exception handled while generating batch");
			LOGGER.error("In DataImportDao - generateBatchDao " + e);
			LOGGER.error("Returning -1");
					
			return -1;
		}
	}

	private static final BATCHRowmapper BATCH_RowMapper = new BATCHRowmapper();
	
	
	public Collection<BatchDto> getBatchDetail(String userEmail){
	
		LOGGER.debug("Request receive to get Batch Ids of TP logged in");
		LOGGER.debug("In DataImportDao - getBatchDetail");
		
		try {
			LOGGER.debug("TRYING -- To get batchId of TP");
			
			Map<String,Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting userEmail into HashMap");
			parameters.put("userEmail", userEmail);
			LOGGER.debug("Executing query to get batchId of TP");
			return getJdbcTemplate().query(dataImportConfig.getshowbatchId(),parameters, BATCH_RowMapper);
			
		} catch (Exception e) {
			
			LOGGER.error("CATCHING -- Exception handled while getting batchId of Logged in TP");
			LOGGER.error("In DataImportDao - getBatchDetail " + e);
			LOGGER.error("Returning null");
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
		LOGGER.debug("Request receive to check centre Id existence");
		LOGGER.debug("In DataImportDao - checkCentreExistence");
		
		try {
			LOGGER.debug("TRYING -- To check existence of entered centre id");
			Map<String, Object> batchDetailsParameters = new HashMap<>();
			LOGGER.debug("Inserting parameters to be checked, in HashMap");
			batchDetailsParameters.put("centreId", masterSheetSubmitDto.getCentreId());
			LOGGER.debug("Exectuing query to check if entered centreId exists");
			return getJdbcTemplate().queryForObject(dataImportConfig.getCheckCentreExistence(), batchDetailsParameters, Integer.class);
		} catch (Exception e) {
			LOGGER.error("CATCHING -- Exception handled while checking centre existence");
			LOGGER.error("In DataImportDao - checkCentreExistence " + e);
			LOGGER.error("Returning status code -1");
			return -1;
		}
	}

	public String insertCentreDetails(String userEmail, MasterSheetSubmitDto masterSheetSubmitDto) {
		LOGGER.debug("Request receive to insert the centre Details");
		LOGGER.debug("In DataImportDao - insertCentreDetails");
		
		try {
			LOGGER.debug("TRYING -- To insert centre details");
			Map<String, Object> centreParameters = new HashMap<>();
			LOGGER.debug("Inserting parameters to be inserted, in HashMap");
			centreParameters.put("centreId", masterSheetSubmitDto.getCentreId());
			centreParameters.put("state", masterSheetSubmitDto.getState());
			centreParameters.put("city", masterSheetSubmitDto.getCity());
			centreParameters.put("userEmail", userEmail);
			LOGGER.debug(" Calling method to insert the details of entered centreId into database");
			return insertString(dataImportConfig.getInsertCentreDetails(), centreParameters, "centerId");
			
		} catch (DataAccessException e) {
			LOGGER.error("CATCHING -- Exception handled while inserting centre details");
			LOGGER.error("In DataImportDao - insertCentreDetails " + e);
			LOGGER.error("Returning status code -1");
			return "-1";
		}
		
	}

	public int updateCentreDetails(MasterSheetSubmitDto masterSheetSubmitDto) {
		LOGGER.debug("Request receive to update the centre Details");
		LOGGER.debug("In DataImportDao - updateCentreDetails");
		
		try {
			LOGGER.debug("TRYING -- To update centre details");
			Map<String, Object> centreParameters = new HashMap<>();
			LOGGER.debug("Inserting parameters to be updated in HashMap");
			centreParameters.put("centreId", masterSheetSubmitDto.getCentreId());
			centreParameters.put("state", masterSheetSubmitDto.getState());
			centreParameters.put("city", masterSheetSubmitDto.getCity());
			LOGGER.debug("Executing Update query to update the details of entered centreId");
			return getJdbcTemplate().update(dataImportConfig.getUpdateCentreDetails(), centreParameters);
		} catch (DataAccessException e) {
			LOGGER.error("CATCHING -- Exception handled while updating centre details");
			LOGGER.error("In DataImportDao - updateCentreDetails " + e);
			LOGGER.error("Returning status code -1");
			return -1;
		}
	}

	public int updateBatchDetails(MasterSheetSubmitDto masterSheetSubmitDto) {
		LOGGER.debug("Request receive to update the batchDetails");
		LOGGER.debug("In DataImportDao - updateBatchDetails");
		
		try {
			LOGGER.debug("TRYING -- To update batch details");
			
			Map<String, Object> batchParameters = new HashMap<>();
			LOGGER.debug("Inserting parameters to be updated in HashMap");
			batchParameters.put("batchStartDate", masterSheetSubmitDto.getBatchStartDate());
			batchParameters.put("batchEndDate", masterSheetSubmitDto.getBatchEndDate());
			batchParameters.put("assessmentDate", masterSheetSubmitDto.getAssessmentDate());
			batchParameters.put("medicalExamDate", masterSheetSubmitDto.getMedicalExamDate());
			batchParameters.put("selectionCommitteeDate", masterSheetSubmitDto.getSelectionCommitteeDate());
			batchParameters.put("municipality", masterSheetSubmitDto.getMunicipality());
			batchParameters.put("wardType", masterSheetSubmitDto.getWardType());
			batchParameters.put("wardNumber", masterSheetSubmitDto.getWardNumber());
			batchParameters.put("trainerName", masterSheetSubmitDto.getTrainerName());
			batchParameters.put("centreId", masterSheetSubmitDto.getCentreId());
			batchParameters.put("batchId", masterSheetSubmitDto.getBatchId());
			LOGGER.debug("Executing Update query to update the details of selected batchId");
			return getJdbcTemplate().update(dataImportConfig.getUpdateBatchDetails(), batchParameters);
		} catch (DataAccessException e) {
			LOGGER.error("CATCHING -- Exception handled while updating batch details");
			LOGGER.error("In DataImportDao - updateBatchDetails " + e);
			LOGGER.error("Returning status code -1");
			return -1;
		}
		
		
		
	}




}






