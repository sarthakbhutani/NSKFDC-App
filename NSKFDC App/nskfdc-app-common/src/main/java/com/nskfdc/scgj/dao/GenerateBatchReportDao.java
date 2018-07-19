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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.GenerateBatchReportConfig;
import com.nskfdc.scgj.dto.CandidateDetailsDto;
import com.nskfdc.scgj.dto.GetBatchIdDto;
import com.nskfdc.scgj.dto.LocationDetailsDto;
import com.nskfdc.scgj.dto.SearchReportDto;
import com.nskfdc.scgj.dto.TrainingDetailsDto;

@Repository
public class GenerateBatchReportDao extends AbstractTransactionalDao {
	
	@Autowired
	private GenerateBatchReportConfig generateBatchReportConfig;
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateBatchReportDao.class);
	private static final BatchIdRowmapper batchIdRowmapper= new BatchIdRowmapper();
	private static final LocationDetailsRowmapper locationDetailsRowmapper=new LocationDetailsRowmapper();
	private static final TrainingDetailsRowmapper trainingDetailsRowmapper=new TrainingDetailsRowmapper();
	private static final CandiateDetailsRowmapper candiateDetailsRowmapper=new CandiateDetailsRowmapper();
	
	/* Creating object of Generate  Report Rowmapper */
	private static final SearchReportRowmapper SearchReport_RowMapper = new SearchReportRowmapper();
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a DAO Method that gets the batch Ids of the particular SCGJ Batch Number which is being passed as parameters. 
	 *@return A Collection of the Batch Ids corresponding to the particular SCGJ Batch Number.
	 
	 **/
	
	public Collection<GetBatchIdDto> getBatchId(String userEmail){
     
	 Map<String, Object> parameters = new HashMap<>();
	 parameters.put("userEmail",userEmail);
	 
	 LOGGER.debug("Request received from Service");
	 LOGGER.debug("In GetBatchIdDao, to get Batch Ids' for Training Partner");
	
	try{
		
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get batch ids for Training Partner");
		return  getJdbcTemplate().query(generateBatchReportConfig.getShowBatchId(),parameters, batchIdRowmapper);
	}
	catch(Exception e){
		
		LOGGER.error("An error occurred while getting the training partner details for Training Partner");
		return null;
	}
	}
	
	public Collection<LocationDetailsDto> getLocationDetails(String batchId)
	{
		Map<String,Object> parameters=new HashMap<>();
		parameters.put("batchId", batchId);
		try{
		
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get batch ids for Training Partner"+generateBatchReportConfig.getShowLocationDetails());
		return getJdbcTemplate().query(generateBatchReportConfig.getShowLocationDetails(),parameters, locationDetailsRowmapper);
		}
		catch(Exception e){
			LOGGER.error("An error occurred while getting the training partner details for Training Partner");
			return null;
		}
	}
	
	public Collection<TrainingDetailsDto> getTrainingDetails(String batchId){
		Map<String,Object> param=new HashMap<>();
		param.put("batchId",batchId);
		try{
			
			LOGGER.debug("In try block");
			LOGGER.debug("Execute query to get batch ids for Training Partner");
			return getJdbcTemplate().query(generateBatchReportConfig.getShowTrainingDetails(),param,trainingDetailsRowmapper);
		}
		catch(Exception e){
			return null;
		}
	}
	
	public Collection<CandidateDetailsDto> getCandidateDetails(String batchId){
		Map<String,Object> param=new HashMap<>();
		param.put("batchId", batchId);
		try{
			LOGGER.debug("In try block");
			LOGGER.debug("Execute query to get batch ids for Training Partner");
			return getJdbcTemplate().query(generateBatchReportConfig.getShowCandidateDetails(),param,candiateDetailsRowmapper);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	/**
	 
	 *@author Shivangi Singh
	 *@description This method is a DAO Method that generates the Reports of particular batchId entered.
	 
	 
	 **/
	
	
	public Collection<SearchReportDto> getReport(String batchId, String userEmail) {
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put("batchId",batchId);
		parameters.put("userEmail", userEmail);
		
		try {  
			
			LOGGER.debug("In try block");
			LOGGER.debug("Execute query to get BATCH details ");
			return getJdbcTemplate().query(generateBatchReportConfig.getShowReport(), parameters, SearchReport_RowMapper);
	
			
		} catch (Exception e) {
			
			LOGGER.error("In Catch Block");
			LOGGER.error("An error occured while getting the REPORT" + e);
			return null;
			}
	}

	
	private static class BatchIdRowmapper implements RowMapper<GetBatchIdDto>{

		/**
		 
		 *@author Samridhi Srivastava
		 *@description This method is a RowMapper Method that gets the batch Ids of the particular SCGJ Batch Number which is being passed as parameters from the database, maps it to a Batch Id column and sends it to the DTO to set values and make a collection. 
		 *@return  Batch Ids corresponding to the particular SCGJ Batch Number to the GetBatchIdDto Parameterized Constructor.
		 
		 **/
		
		@Override
		public GetBatchIdDto mapRow(ResultSet rs, int rowNum)throws SQLException {
			
			String batchId= rs.getString("batchId");
			
			return new GetBatchIdDto(batchId);
		}
		
	}
	private static class LocationDetailsRowmapper implements RowMapper<LocationDetailsDto>{
		@Override
		public LocationDetailsDto mapRow(ResultSet rs,int rowNum)throws SQLException{
			String state=rs.getString("centreState");
			LOGGER.debug("state"+state);
			String city=rs.getString("centreCity");
			String municipalCorporation=rs.getString("municipality");
			String ward=rs.getString("wardType");
			String scgjBatchNumber=rs.getString("scgjBatchNumber");
			String us=rs.getString("dataSheetForSDDMS");
			String uploadStatus="NO";
			if(us.equals("1"))
			{
			uploadStatus="Yes";
			}
			return new LocationDetailsDto(state,city,municipalCorporation,ward,scgjBatchNumber,uploadStatus);
		}
	}
	private static class TrainingDetailsRowmapper implements RowMapper<TrainingDetailsDto>{
		
		@Override
		public TrainingDetailsDto mapRow(ResultSet rs,int rowNum)throws SQLException{
			String dateOfScreeningCommittee=rs.getString("selectionCommitteeDate");
			String startDateOfTraining=rs.getString("batchStartDate");
			String endDateOfTraining=rs.getString("batchEndDate");
			String trainingPartner=rs.getString("trainingPartnerName");
			String prinicipalTrainer=rs.getString("principalTrainerName");
			String candidatesRegistered=rs.getString("count(*)");
			String candidatesAssessed="NA";
			String candidatesPassed=rs.getString("count(assessmentResult)");
			String dateOfMedicalExamination=rs.getString("medicalExamDate");
			String candidatesMedicallyExamined=rs.getString("count(medicalExamConducted)");
			String payoutToCandidates="NEFT(HDFC)";
			String participantHandbook="Given to all candidates";
			
			return new TrainingDetailsDto(dateOfScreeningCommittee, startDateOfTraining, endDateOfTraining, trainingPartner, prinicipalTrainer, candidatesRegistered, candidatesAssessed, candidatesPassed, dateOfMedicalExamination, candidatesMedicallyExamined, payoutToCandidates, participantHandbook);
		}
	}
	private static class CandiateDetailsRowmapper implements RowMapper<CandidateDetailsDto>{
		
		@Override
		public CandidateDetailsDto mapRow(ResultSet rs,int rowNum)throws SQLException{
			String fn=rs.getString("firstName");
			String ln=rs.getString("lastName");
			String name=fn+" "+ln;
			String aadharNumber=rs.getString("aadharCardNumber");
			String mobileNumber=rs.getString("mobileNumber");
			String candidateNumber=rs.getString("enrollmentNumber");
			String remarks="";
			return new CandidateDetailsDto(name, aadharNumber, mobileNumber, candidateNumber, remarks);
		}
	}
	public int insertSCGJBatchNumber(String batchId,String batchnumber,String userEmail) {
		Map<String,Object> param=new HashMap<>();
		param.put("batchId",batchId );
		param.put("batchNumber", batchnumber);
		param.put("userEmail",userEmail );
		try{
			LOGGER.debug("IN DAO TO INSERT BATCHNUMBER");
			Integer result = getJdbcTemplate().update(generateBatchReportConfig.getShowUpdateBatchNumber(),param);
			return result;
		}
		catch(Exception e){
		return -1;
		}
	}
	
	
	/* Declaring inner class search report Rowmapper */
    private static class SearchReportRowmapper implements RowMapper<SearchReportDto>{
    	
    	/**
		 
		 *@author Shivangi singh
		 *@description This method is a RowMapper Method that gets the reports of the particular batchid entered. 
		 *@return  reports  corresponding to the particular BatchId to the searchreportDto Parameterized Constructor.
		 
		 **/
		
		@Override
		public SearchReportDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String batchId = rs.getString("batchId");
			String userEmail = rs.getString("userEmail");
							
			return new SearchReportDto(batchId,userEmail);
			
		}

	}
	public void updateTableGenerateReports(String generateReportsId, Date date,
			String reportType, String batchId, String userEmail) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("generateReportsId", generateReportsId);
		parameters.put("generatedOn",date);
		parameters.put("reportType",reportType);
		parameters.put("batchId",batchId);
		parameters.put("userEmail",userEmail);
		
int result;
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In  Dao");
		
		try {
			
			LOGGER.debug("In try block of  Dao");
			result = getJdbcTemplate().update(generateBatchReportConfig.getUpdateGenerateReportsTable(),parameters);
			LOGGER.debug("The result of the query is : " + result);
			
		}catch(Exception e) {
			LOGGER.error("In catch block of Update Database Dao"+e);
		
		}	
		
	}
	
}
