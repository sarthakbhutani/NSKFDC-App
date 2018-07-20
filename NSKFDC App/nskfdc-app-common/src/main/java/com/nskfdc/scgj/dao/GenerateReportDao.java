package com.nskfdc.scgj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.GenerateReportConfig;
import com.nskfdc.scgj.dto.*;

@Repository
public class GenerateReportDao extends AbstractTransactionalDao {
	
	/* Creating object of GenerateOccupationCertificateReportRowmapper */
	private static final GenerateOccupationCertificateReportRowmapper generateOccupationCertificateReportRowMapper = new GenerateOccupationCertificateReportRowmapper();
	
	/* Creating object of GenerateAttendanceSheetReportRowmapper */
	private static final GenerateAttendanceSheetRowmapper generateAttendanceSheetRowMapper = new GenerateAttendanceSheetRowmapper();
	
	/* Creating object of GenerateNSKFDCExcelSheetReportRowmapper */
	private static final GenerateNSKFDCExcelSheetRowmapper generateNSKFDCExcelSheetRowMapper = new GenerateNSKFDCExcelSheetRowmapper();
	
	/* Creating object of GenerateSDMSExcelSheetReportRowmapper */
	private static final GenerateSDMSExcelSheetRowmapper generateSDMSExcelSheetRowMapper = new GenerateSDMSExcelSheetRowmapper();
	
	/* Creating object of GetRecordsForAuditTableRowMapper */
	private static final GetRecordsForAuditTableRowMapper getRecordsForAuditTableRowMapper = new GetRecordsForAuditTableRowMapper();
	
	/* Creating object of GenerateMinutesOfSelectionRowmapper */
	private static final GenerateMinutesOfSelectionRowmapper generateMinutesOfSelectionRowMapper = new GenerateMinutesOfSelectionRowmapper();
	
	/* Creating object of BatchIdRowmapper */
	private static final BatchIdRowmapper batchIdRowmapper= new BatchIdRowmapper();
	
	@Autowired
	private GenerateReportConfig generateReportConfig;
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateReportDao.class);
	
	public Collection<GetBatchIdDto> getBatchId(String userEmail){
	     
		 Map<String, Object> parameters = new HashMap<>();
		 parameters.put("userEmail",userEmail);
		 
		 LOGGER.debug("Request received from Service");
		 LOGGER.debug("In GetBatchIdDao, to get Batch Ids' for Training Partner");
		
		try{
			
			LOGGER.debug("In try block");
			LOGGER.debug("Execute query to get batch ids for Training Partner");
			return  getJdbcTemplate().query(generateReportConfig.getShowBatchId(),parameters, batchIdRowmapper);
		}
		catch(Exception e){
			
			LOGGER.error("An error occurred while getting the training partner details for Training Partner");
			return null;
		}
		}
	
	public Collection<GenerateOccupationCertificateReportDto> generateOccupationCertificateReport(String batchId,String userEmail) {
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In Generate Occupation Certificate Dao");
		
		try {
			
			LOGGER.debug("In try block of Generate Occupation Certificate Dao");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("batchId",batchId);
			parameters.put("userEmail", userEmail);
			
			
			return getJdbcTemplate().query(generateReportConfig.getShowOccupationCertificateReportDetails(), parameters ,generateOccupationCertificateReportRowMapper);
			
		}catch(Exception e) {
			
			LOGGER.debug("In catch block of Generate Occupation Certificate Dao"+e);
			return null;
		}
	}	
	
	public Collection<GenerateAttendanceSheetDto> generateAttendanceSheet(String batchId,String userEmail) {
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In Generate Attendance Sheet Dao");
		
		try {
			
			LOGGER.debug("In try block of Generate Attendance Sheet Dao");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("batchId",batchId);
			parameters.put("userEmail", userEmail);
			
			return getJdbcTemplate().query(generateReportConfig.getShowAttendanceSheetDetails(), parameters ,generateAttendanceSheetRowMapper);
			
		}catch(Exception e) {
		
			LOGGER.error("In catch block of Generate Attendance Sheet Dao"+e);
			return null;
		}	
	}
	
	public Collection<GenerateNSKFDCExcelSheetDto> generateNSKFDCExcelSheet(String batchId,String userEmail) {
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In Generate NSKFDC Excel Sheet Dao");
		
		try {
			
			LOGGER.debug("In try block of Generate NSKFDC Excel Sheet Dao");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("batchId",batchId);
			parameters.put("userEmail", userEmail);
			return getJdbcTemplate().query(generateReportConfig.getShowNSKFDCExcelSheet(), parameters ,generateNSKFDCExcelSheetRowMapper);
			
		}catch(Exception e) {
			
			LOGGER.error("In catch block of Generate NSKFDC Excel Sheet Dao"+e);
			return null;
		}	
	}
	
	public Collection<GenerateSDMSExcelSheetDto> generateSDMSExcelSheet(String batchId,String userEmail) {
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In Generate NSKFDC Excel Sheet Dao");
		
		try {
			
			LOGGER.debug("In try block of Generate NSKFDC Excel Sheet Dao");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("batchId",batchId);
			parameters.put("userEmail", userEmail);
			return getJdbcTemplate().query(generateReportConfig.getShowSDMSExcelSheet(), parameters ,generateSDMSExcelSheetRowMapper);
			
		}catch(Exception e) {
			
			LOGGER.error("In catch block of Generate NSKFDC Excel Sheet Dao"+e);
			return null;
		}	
	}
	
	public Collection<GenerateMinutesOfSelectionDto> generateMinutesOfSelection(String batchId,String userEmail/*String jobRole,String trainingPartnerName,String sectorSkillCouncil,String centreCity*/) {

		LOGGER.debug("Request received from service");
		LOGGER.debug("Generate minutes Of Selection details ");
		
		try {
			
			LOGGER.debug("In try block of Generate Minutes Of Selection  Dao");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("batchId",batchId);
			parameters.put("userEmail",userEmail);		
			
			return getJdbcTemplate().query(generateReportConfig.getShowMinutesOfSelectionMeetingDetails() ,parameters , generateMinutesOfSelectionRowMapper);
			
		}catch(Exception e) {
			
			LOGGER.debug("In catch block of Generate  Minutes Of Selection  Dao"+e);
			return null;
		}
	}
	
	public void updateTableGenerateReports(String generateReportsId,Date generatedOn, String reportType, String batchId ,String userEmail) {
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("generateReportsId", generateReportsId);
		parameters.put("generatedOn",generatedOn);
		parameters.put("reportType",reportType);
		parameters.put("batchId",batchId);
		parameters.put("userEmail",userEmail);
		
		int result;
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In Update Database Dao");
		
		try {
			
			LOGGER.debug("In try block of Update Database Dao");
			result = getJdbcTemplate().update(generateReportConfig.getUpdateGenerateReportsTable(),parameters);
			LOGGER.debug("The result of the query is : " + result);
			
		}catch(Exception e) {
			LOGGER.error("In catch block of Update Database Dao"+e);
		
		}			
	}
	
	public Collection<DisplayAuditTableRecordDto> getRecordsForAuditTable(String userEmail) {
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In Get Records For Audit Table Dao");
		
		try {
			
			LOGGER.debug("In try block of Get Records For Audit Table Dao");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("userEmail", userEmail);
			
			
			return getJdbcTemplate().query(generateReportConfig.getShowAuditTableRecords(), parameters ,getRecordsForAuditTableRowMapper);
			
		}catch(Exception e) {
			
			LOGGER.debug("In catch block of Get Records For Audit Table Dao"+e);
			return null;
		}
	}
	
	private static class BatchIdRowmapper implements RowMapper<GetBatchIdDto>{
		
		@Override
		public GetBatchIdDto mapRow(ResultSet rs, int rowNum)throws SQLException {
			String batchId= rs.getString("batchId");
			return new GetBatchIdDto(batchId);
		}
		
	}
	
	/* Declaring inner class GenerateOccupationCertificateReportRowmapper */
    private static class GenerateOccupationCertificateReportRowmapper implements RowMapper<GenerateOccupationCertificateReportDto>{
	    
		@Override
		public GenerateOccupationCertificateReportDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String gender = rs.getString("gender");
			String age = rs.getString("age");
			String firstNameFather = rs.getString("firstNameFather");	
			String lastNameFather = rs.getString("lastNameFather");
			String aadharCardNumber = rs.getString("aadharCardNumber");	
			String residentialAddress = rs.getString("residentialAddress");
			String workplaceAddress = rs.getString("workplaceAddress");
			
			return new GenerateOccupationCertificateReportDto(firstName,lastName,gender,age,firstNameFather,lastNameFather,aadharCardNumber,residentialAddress,workplaceAddress);	
		}	
    }
    
    /* Declaring inner class GenerateAttendanceSheetRowmapper */
    private static class GenerateAttendanceSheetRowmapper implements RowMapper<GenerateAttendanceSheetDto>{
    
		@Override
		public GenerateAttendanceSheetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String firstNameFather = rs.getString("firstNameFather");	
			String lastNameFather = rs.getString("lastNameFather");
			String mobileNumber = rs.getString("mobileNumber");	
			String batchId = rs.getString("batchId");	
			Date batchStartDate = rs.getDate("batchStartDate");	
			
			return new GenerateAttendanceSheetDto(firstName,lastName,firstNameFather,lastNameFather,mobileNumber,batchId,batchStartDate);	
		}	
    }
    
    /* Declaring inner class GenerateNSKFDCExcelSheetRowmapper */
    private static class GenerateNSKFDCExcelSheetRowmapper implements RowMapper<GenerateNSKFDCExcelSheetDto>{

		@Override
		public GenerateNSKFDCExcelSheetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			int age=rs.getInt("age");
			String gender = rs.getString("gender");
			Date dob = rs.getDate("dob");
			String educationLevel = rs.getString("educationLevel");	
			String firstNameFather = rs.getString("firstNameFather");	
			String lastNameFather = rs.getString("lastNameFather");
			String motherName = rs.getString("motherName");
			String aadharCardNumber = rs.getString("aadharCardNumber");
			String residentialAddress = rs.getString("residentialAddress");
			String mobileNumber=rs.getString("mobileNumber");
			String occupationType = rs.getString("occupationType");
			String msId = rs.getString("msId");
			String idProofType = rs.getString("idProofType");	
			String idProofNumber = rs.getString("idProofNumber");
			String bankName = rs.getString("bankName");
			String ifscCode = rs.getString("ifscCode");	
			String accountNumber = rs.getString("accountNumber");
			String batchId=rs.getString("batchId");
			String trainingPartnerName=rs.getString("trainingPartnerName");
			String relationWithSKMS=rs.getString("relationWithSKMS");
			
			return new GenerateNSKFDCExcelSheetDto(firstName,lastName,age,gender,dob,educationLevel,firstNameFather,
					lastNameFather,motherName,aadharCardNumber,residentialAddress,mobileNumber,occupationType,
					msId,idProofType,idProofNumber,bankName,ifscCode,accountNumber,batchId,trainingPartnerName,relationWithSKMS);	
		}	
    }
    
    /* Declaring inner class GenerateSDMSExcelSheetRowmapper */
    private static class GenerateSDMSExcelSheetRowmapper implements RowMapper<GenerateSDMSExcelSheetDto>{
    	
    	@Override
		public GenerateSDMSExcelSheetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
    		String nsdcRegNumber = rs.getString("nsdcRegNumber");
    		int id=rs.getInt("centreId");
    		String enrollmentNumber = rs.getString("enrollmentNumber");
    		String firstName = rs.getString("firstName");
    		String lastName = rs.getString("lastName");
    		Date dob = rs.getDate("dob");
    		String firstNameFather = rs.getString("firstNameFather");
    		String lastNameFather = rs.getString("lastNameFather");
    		String guardianType = rs.getString("guardianType");
    		String salutation = rs.getString("salutation");
    		String gender = rs.getString("gender");
    		String state = rs.getString("state");
    		String district = rs.getString("district");
    		String mobileNumber = rs.getString("mobileNumber");
    		String educationLevel = rs.getString("educationLevel");
    		String sectorSkillCouncil = rs.getString("sectorSkillCouncil");
    		String jobRole = rs.getString("jobRole");
    		Date batchStartDate = rs.getDate("batchStartDate");
    		Date batchEndDate = rs.getDate("batchEndDate");
    		Date assessmentDate = rs.getDate("assessmentDate");
    		String employerName = rs.getString("employerName");
    		String employerContactNumber = rs.getString("employerContactNumber");
    		String employmentType = rs.getString("employmentType");
    		String aadharCardNumber = rs.getString("aadharCardNumber");
    		String disabilityType = rs.getString("disabilityType");
    		
    		return new GenerateSDMSExcelSheetDto(nsdcRegNumber,id,enrollmentNumber,firstName,lastName,dob,firstNameFather,
    				 lastNameFather,guardianType,salutation,gender,state,district,mobileNumber,educationLevel,sectorSkillCouncil,jobRole,
    				 batchStartDate,batchEndDate, assessmentDate,employerName,employerContactNumber,employmentType,aadharCardNumber,disabilityType);
    	}	
    }	
    
    /* Declaring inner class minutes Of Selection Committee Meeting Rowmapper */
    private static class GenerateMinutesOfSelectionRowmapper implements RowMapper<GenerateMinutesOfSelectionDto>{
    	
    	@Override
    	public GenerateMinutesOfSelectionDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		String selectionCommitteeDate=rs.getString("selectionCommitteeDate");
    		String jobRole = rs.getString("jobRole");
    		String trainingPartnerName = rs.getString("trainingPartnerName");
    		String sectorSkillCouncil=rs.getString("sectorSkillCouncil");
    		String centreCity=rs.getString("centreCity");
    		
    		return new GenerateMinutesOfSelectionDto(selectionCommitteeDate,jobRole,trainingPartnerName,sectorSkillCouncil,centreCity);
	
    	}
    }
    				/* Declaring inner class GetRecordsForAuditTableRowMapper */
    private static class GetRecordsForAuditTableRowMapper implements RowMapper<DisplayAuditTableRecordDto>{
    	
    	@Override
    	public DisplayAuditTableRecordDto mapRow(ResultSet rs, int rowNum) throws SQLException{
    		
    		String batchId = rs.getString("batchId");
    		String reportType = rs.getString("reportType");
			Date generatedOn = rs.getDate("generatedOn");
			String generatedBy = rs.getString("trainingPartnerName");	
			
    		return new DisplayAuditTableRecordDto(batchId, reportType,generatedOn, generatedBy);
    	}
    }
    
}
