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
	/*private static final SearchReportRowmapper SearchReport_RowMapper = new SearchReportRowmapper();*/
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a DAO Method that gets the batch Ids of the particular SCGJ Batch Number which is being passed as parameters. 
	 *@return A Collection of the Batch Ids corresponding to the particular SCGJ Batch Number.
	 
	 **/
	
	public Collection<GetBatchIdDto> getBatchId(String userEmail){
     
		 LOGGER.debug("Request received from Service");
		 LOGGER.debug("In GENERATE BATCH REPORT DAO, to get Batch Ids' for logged in Training Partner");
		
		 Map<String, Object> parameters = new HashMap<>();
		 LOGGER.debug("Inserting user Email in the parameters");
		 parameters.put("userEmail",userEmail);
	 
	try{
		
		LOGGER.debug("TRYING -- getBatchId");
		LOGGER.debug("Execute query to get batch ids for Training Partner");
		return  getJdbcTemplate().query(generateBatchReportConfig.getShowBatchId(),parameters, batchIdRowmapper);
	}
	catch(Exception e){
		LOGGER.error("CATCHING -- EXCEPTION Handled in - getBatchId");
		LOGGER.error("Exception is" + e);
		return null;
	}
	}
	
	public Collection<LocationDetailsDto> getLocationDetails(String batchId)
	{
		LOGGER.debug("Request Received from GenerateBatchReportService");
		LOGGER.debug("In GENERATE BATCH REPORT DAO, to get Location Details for Final Batch Report");
		Map<String,Object> parameters = new HashMap<>();
		LOGGER.debug("Inserting batchId in parameters");
		parameters.put("batchId", batchId);
		try{
		
		LOGGER.debug("TRYING -- getLocationDetails ");
		LOGGER.debug("Execute query to get Location Details for entered batchId");
		return getJdbcTemplate().query(generateBatchReportConfig.getShowLocationDetails(),parameters, locationDetailsRowmapper);
		}
		catch(Exception e){
			LOGGER.error("CATCHING -- EXCEPTION handled in getLocationDetails");
			LOGGER.error("Exception while getting the loaction Details");
			LOGGER.error("Exception is"+ e);
			return null;
		}
	}
	
	public Collection<TrainingDetailsDto> getTrainingDetails(String batchId){
		LOGGER.error("Request received from GenerateBatchReportService");
		LOGGER.debug("In GENERATE BATCH REPORT DAO, to get Training details for Final Batch Report");
		Map<String,Object> param=new HashMap<>();
		
		LOGGER.debug("Inserting batchId in parameters");
		param.put("batchId",batchId);
		try{
			
			LOGGER.debug("TRYING -- getTrainingDetails");
			LOGGER.debug("Execute query to get Training Partner details for entered Batch Id");
			return getJdbcTemplate().query(generateBatchReportConfig.getShowTrainingDetails(),param,trainingDetailsRowmapper);
		}
		catch(Exception e){
			LOGGER.error("CATCHING -- EXCEPTION handled in getTrainingDetails");
			LOGGER.error("Exception while getting Training Details");
			LOGGER.error("Exception is " + e);
			return null;
		}
	}
	
	public Collection<CandidateDetailsDto> getCandidateDetails(String batchId){
		
		LOGGER.debug("Request received from GenerateBatchReportService");
		LOGGER.debug("In GENERATE BATCH REPORT DAO, to get Candidate details for Final Batch Report");
		Map<String,Object> param=new HashMap<>();
		LOGGER.debug("Inserting batch Id in parameter");
		param.put("batchId", batchId);
		try{
			LOGGER.debug("TRYING -- getCandidateDetails");
			LOGGER.debug("Execute query to get Candidate Details for entered Batch Id");
			return getJdbcTemplate().query(generateBatchReportConfig.getShowCandidateDetails(),param,candiateDetailsRowmapper);
		}
		catch (Exception e) {
			LOGGER.error("CATCHING -- EXCEPTION handled in getCandidateDetails");
			LOGGER.error("Exception while getting Candidate Details for enetered Batch Id");
			LOGGER.error("Exception is " + e);
			return null;
		}
	}
	
	/**
	 
	 *@author Shivangi Singh
	 *@description This method is a DAO Method that generates the Reports of particular batchId entered.
	 
	 
	 **/
	
	
	
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a RowMapper that gets the batch Ids of the particular SCGJ Batch Number which is being passed as parameters from the database, maps it to a Batch Id column and sends it to the DTO to set values and make a collection. 
	 *@return  Batch Ids corresponding to the particular SCGJ Batch Number to the GetBatchIdDto Parameterized Constructor.
	 
	 **/	
	private static class BatchIdRowmapper implements RowMapper<GetBatchIdDto>{

		
		@Override
		public GetBatchIdDto mapRow(ResultSet rs, int rowNum)throws SQLException {
			
			String batchId= rs.getString("batchId");
			
			return new GetBatchIdDto(batchId);
		}
		
	}
	
	/**
	 *@description This is the Rowmapper getting Location Details for Entered Batch Id
	 * 
	 **/
	private static class LocationDetailsRowmapper implements RowMapper<LocationDetailsDto>
	{
		@Override
		public LocationDetailsDto mapRow(ResultSet rs,int rowNum)throws SQLException{
			String state=rs.getString("centreState");
			LOGGER.debug("state" + state);
			String city=rs.getString("centreCity");
			String municipalCorporation=rs.getString("municipality");
			String ward=rs.getString("wardType");
			String scgjBatchNumber=rs.getString("scgjBatchNumber");
			String us=rs.getString("dataSheetForSDDMS");
			String uploadStatus="NO";
			if("1".equals("us"))
			{
			uploadStatus="Yes";
			}
			return new LocationDetailsDto(state, city, municipalCorporation, ward, scgjBatchNumber, uploadStatus);
		}
	}
	
	
	/**
	 *@description This is the Rowmapper getting Training Details for Entered Batch Id
	 * 
	 **/
	
	private static class TrainingDetailsRowmapper implements RowMapper<TrainingDetailsDto>{
		
		
		
		@Override
		public TrainingDetailsDto mapRow(ResultSet rs,int rowNum)throws SQLException{
			String dateOfScreeningCommittee=rs.getString("selectionCommitteeDate");
			String startDateOfTraining=rs.getString("batchStartDate");
			String endDateOfTraining=rs.getString("batchEndDate");
			String trainingPartner=rs.getString("trainingPartnerName");
			String prinicipalTrainer=rs.getString("principalTrainerName");
			String candidatesRegistered=rs.getString("count(enrollmentNumber)");
			String candidatesAssessed=rs.getString("candidateAssessed");
			String candidatesPassed=rs.getString("candidatePassed");
			String dateOfMedicalExamination=rs.getString("medicalExamDate");
			String candidatesMedicallyExamined=rs.getString("medicalExamConducted");
			String payoutToCandidates=" ";
			String participantHandbook="Given to all candidates";
			
			return new TrainingDetailsDto(dateOfScreeningCommittee, startDateOfTraining, endDateOfTraining, trainingPartner, prinicipalTrainer, candidatesRegistered, candidatesAssessed, candidatesPassed, dateOfMedicalExamination, candidatesMedicallyExamined, payoutToCandidates, participantHandbook);
		}
	}
	
	/**
	 *@description This is the Rowmapper getting Candidate Details for Entered Batch Id
	 * 
	 **/
	private static class CandiateDetailsRowmapper implements RowMapper<CandidateDetailsDto>{
		
		@Override
		public CandidateDetailsDto mapRow(ResultSet rs,int rowNum)throws SQLException{
			String fn=rs.getString("firstName");
			String ln=rs.getString("lastName");
			String name;
			if(ln!=null) {
			name=fn+" "+ln;
			}
			else {
			name=fn;	
			}
			String aadharNumber=rs.getString("aadharCardNumber");
			String mobileNumber=rs.getString("mobileNumber");
			String candidateNumber=rs.getString("enrollmentNumber");
			String remarks="";
			return new CandidateDetailsDto(name, aadharNumber, mobileNumber, candidateNumber, remarks);
		}
	}
	
	
	public String showScgjbatchNumber(String batchId) {
		
		LOGGER.debug("Request recieved from service to get scgj batch number for batch id : " + batchId);
		try {
		
			LOGGER.debug("In Try block of SCGJ Batch Number method to get batch Number ");
			LOGGER.debug("Creating Hashmap of objects");
			Map<String,Object> batchParams = new HashMap<>();
			batchParams.put("batchId", batchId);
			if(batchParams.isEmpty())
			{
				LOGGER.error("The parameters in hashmap at line 270 are null");
				LOGGER.error("Returning null");
				
			}
			LOGGER.debug("Executing query to get batch number for batch id : " + batchId);
			String batchNumber = getJdbcTemplate().queryForObject(generateBatchReportConfig.getShowUpdateBatchNumber(), batchParams, String.class);
			LOGGER.debug("The value of batch number is : " + batchNumber);
			return batchNumber;
		} 
		catch (Exception e)
		{
		
			LOGGER.error("An error occured while fetching the batch number for batch id : " + batchId);
			LOGGER.error("Returning NULL");
			return null;
		}
		
	}
	

	public void updateTableGenerateReports(String generateReportsId, Date date, String reportType, String batchId, String userEmail) {
		LOGGER.debug("Request received from Generate Report Service");
		LOGGER.debug("In Method - updateTableGenerateReports");
		LOGGER.debug("To update the timings of Report Generation in the Database");
		
		Map<String, Object> parameters = new HashMap<>();
		LOGGER.debug("Inserting report Id , Generated On date, Report Type in parameters");
		parameters.put("generateReportsId", generateReportsId);
		parameters.put("generatedOn",date);
		parameters.put("reportType",reportType);
		LOGGER.debug("Inserting batchId, userEmail in parameters");
		parameters.put("batchId",batchId);
		parameters.put("userEmail",userEmail);
		
		int result;
		
		try {
			
			LOGGER.debug("TRYING -- updateTableGenerateReports");
			LOGGER.debug("Executing update quert to update the generated Reports data");
			result = getJdbcTemplate().update(generateBatchReportConfig.getUpdateGenerateReportsTable(),parameters);
			
		}catch(Exception e) {
			LOGGER.error("CATCHING -- EXCEPTION handled while Updating generated report data");
			LOGGER.error("In method - updateTableGenerateReports ");
			LOGGER.error("Exception is "+e);
		
		}	
		
	}
	
	public int checkBatchEndDate(String batchId) {
		LOGGER.debug("Request received at DAO to check if batchEndDate is less than current date");
		LOGGER.debug("In method - checkbatchEndDate");
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("batchId", batchId);
		try {
			
			LOGGER.debug("TRYING --Execute query to check batch End date");
			return getJdbcTemplate().queryForObject(generateBatchReportConfig.getBatchEndDateCheck(), parameter, Integer.class);
		}
		catch(Exception e) {
			LOGGER.debug("CATCHING -- Exception handled in checkBatchEndDate");
			LOGGER.error("Returning status code -297");
			return -297;
			
		}
		
	}
	
}
