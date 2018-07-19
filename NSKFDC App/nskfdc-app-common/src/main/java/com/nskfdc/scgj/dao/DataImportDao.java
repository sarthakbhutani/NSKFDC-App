package com.nskfdc.scgj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.DataImportConfig;
import com.nskfdc.scgj.dto.GetBatchDetailsDto;
import com.nskfdc.scgj.dto.FinancialDto;
import com.nskfdc.scgj.dto.BatchDto;
import com.nskfdc.scgj.dto.DownloadFinalMasterSheetDto;


@Repository
public class DataImportDao extends AbstractTransactionalDao{
	
	/*  Object of Master Sheet RowMapper */
	private static final GenerateMasterSheetRowmapper generateMasterSheetRowMapper = new GenerateMasterSheetRowmapper();
	
	@Autowired
	private DataImportConfig dataImportConfig;
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DataImportDao.class);
	
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

public Collection<GetBatchDetailsDto> BatchDetails(String userEmail,String batchId) {
	
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
				
				return  getJdbcTemplate().query(dataImportConfig.getBatchDetails(),parameters,BatchDetailsRM);

				
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




}






