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
	

	/**
	 * /* Creating object of GenerateOccupationCertificateReportRowmapper */
	private static final GenerateOccupationCertificateReportRowmapper generateOccupationCertificateReportRowMapper = new GenerateOccupationCertificateReportRowmapper();
	
	/**
	 * /* Creating object of GenerateAttendanceSheetReportRowmapper */
	private static final GenerateAttendanceSheetRowmapper generateAttendanceSheetRowMapper = new GenerateAttendanceSheetRowmapper();
	
	/**
	 * /* Creating object of GenerateNSKFDCExcelSheetReportRowmapper */
	private static final GenerateNSKFDCExcelSheetRowmapper generateNSKFDCExcelSheetRowMapper = new GenerateNSKFDCExcelSheetRowmapper();
	
	/**
	 * /* Creating object of GenerateSDMSExcelSheetReportRowmapper */
	private static final GenerateSDMSExcelSheetRowmapper generateSDMSExcelSheetRowMapper = new GenerateSDMSExcelSheetRowmapper();
	
	/**
	 * /* Creating object of GetRecordsForAuditTableRowMapper */
	private static final GetRecordsForAuditTableRowMapper getRecordsForAuditTableRowMapper = new GetRecordsForAuditTableRowMapper();
	
	/**
	 * /* Creating object of GenerateMinutesOfSelectionRowmapper */
	private static final GenerateMinutesOfSelectionRowmapper generateMinutesOfSelectionRowMapper = new GenerateMinutesOfSelectionRowmapper();
	
	/**
	 * /* Creating object of BatchIdRowmapper */
	private static final BatchIdRowmapper batchIdRowmapper= new BatchIdRowmapper();
	
	@Autowired
	private GenerateReportConfig generateReportConfig;
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateReportDao.class);
	
	/**
	 * Get batch all batch Ids for a TP based on user email
	 * @param userEmail email of logged in TP
	 * @return collection of BatchIds
	 */
	public Collection<GetBatchIdDto> getBatchId(String userEmail){
	    
		LOGGER.debug("Request received from Service");
		LOGGER.debug("In method getBatchId, to get Batch Id for logged in TP");
		LOGGER.debug("Inserting userEmail in parameter for SQL Query");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userEmail",userEmail);
		 
		
		try{
			
			LOGGER.debug("TRYING -- To get all the batch Id of TP");
			LOGGER.debug("Execute query to get batch ids for Training Partner");
			return  getJdbcTemplate().query(generateReportConfig.getShowBatchId(),parameters, batchIdRowmapper);
		}
		catch(Exception e){
			
			LOGGER.error("CATCHING -- Exception handled while getting batch Id for TP");
			LOGGER.error("In GenerateReportDao - getBatchId");	
			LOGGER.error("Exception is"+e);
			return null;
		}
		}
	
	/**
	 * Method to generate data for Occupation certificate based on batch Id and userEmail
	 * @param batchId batch id for which occupation certifiacte needs to be generated
	 * @param userEmail email of logged in user
	 * @return
	 */
	public Collection<GenerateOccupationCertificateReportDto> generateOccupationCertificateReport(String batchId,String userEmail) {
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In GenerateReportDao - generateOccupationCertificateReport");
		
		try {
			
			LOGGER.debug("TRYING -- To get details for Occupation Certificate Report");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting batchId, userEmail in parameters");
			parameters.put("batchId",batchId);
			parameters.put("userEmail", userEmail);
			
			LOGGER.debug("Executing query to get Occupation certificate Details");
			return getJdbcTemplate().query(generateReportConfig.getShowOccupationCertificateReportDetails(), parameters ,generateOccupationCertificateReportRowMapper);
			
		}catch(Exception e) {
			
			LOGGER.error("CATCHING -- Exception handled while getting OC report details");
			LOGGER.error("In GenerateReportDao - generateOccupationCertificateReport");	
			LOGGER.error("Exception is"+e);
			LOGGER.error("Returning null");
			return null;
		}
	}	
	
	/**
	 * Method to generate attendance sheet for a training partner based on email and batch Id
	 * @param batchId for which attendance sheet needs to be generated
	 * @param userEmail email of logged in TP
	 * @return collection of data to be passed to jasper template of attendance sheet
	 */
	public Collection<GenerateAttendanceSheetDto> generateAttendanceSheet(String batchId,String userEmail) {
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("To get Attendance Sheet details - generateAttendanceSheet");
		
		try {
			
			LOGGER.debug("TRYING -- In GenerateReportDao - generateAttendanceSheet");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting batchId & userEmail in parameters");
			parameters.put("batchId",batchId);
			parameters.put("userEmail", userEmail);
			LOGGER.debug("Executing query to get Occupation certificate Details");
			return getJdbcTemplate().query(generateReportConfig.getShowAttendanceSheetDetails(), parameters ,generateAttendanceSheetRowMapper);
			
		}catch(Exception e) {
		
			LOGGER.error("CATCHING -- Exception handled while getting Attendance Sheet details");
			LOGGER.error("In GenerateReportDao - generateAttendanceSheet");	
			LOGGER.error("Exception is"+e);
			LOGGER.error("Returning null");
			return null;
		}	
	}
	/**
	 * Method to generate NSKFDC excel sheet based on batch Id and useremail
	 * @param batchId for which NSKFDC excel sheet
	 * @param userEmail emial of logged in user
	 * @return Collection of Data to be passed to jasper template of NSKFDC
	 */
	public Collection<GenerateNSKFDCExcelSheetDto> generateNSKFDCExcelSheet(String batchId,String userEmail) {
		
		LOGGER.debug("Request received from service to GenerateReportDao");
		LOGGER.debug("To get NSKFDC Excel Sheet details - generateNSKFDCExcelSheet");
		
		try {
			
			LOGGER.debug("TRYING -- In GenerateReportDao - generateNSKFDCExcelSheet");			
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting batchId & userEmail in parameters");
			parameters.put("batchId",batchId);
			parameters.put("userEmail", userEmail);
			LOGGER.debug("Executing query to get NSKFDC Excel Sheet Details");
			return getJdbcTemplate().query(generateReportConfig.getShowNSKFDCExcelSheet(), parameters ,generateNSKFDCExcelSheetRowMapper);
			
		}catch(Exception e) {
			LOGGER.error("CATCHING -- Exception handled while getting NSKFDC Excel Sheet details");
			LOGGER.error("In GenerateReportDao - generateNSKFDCExcelSheet");	
			LOGGER.error("Exception is"+e);
			LOGGER.error("Returning null");
			return null;
		}	
	}
	
	/**
	 * Method to return data to be used while generation of Excel SDMS Excel sheet based on batchId and user email
	 * @param batchId for which SDMS sheet needs to be generated
	 * @param userEmail of logged in user
	 * @return collection of data to be sent to jasper report template
	 */
	public Collection<GenerateSDMSExcelSheetDto> generateSDMSExcelSheet(String batchId,String userEmail) {
		
		LOGGER.debug("Request received from service to GenerateReportDao");
		LOGGER.debug("To get SDMS Excel Sheet Details - generateSDMSExcelSheet");
		
		try {
			
			LOGGER.debug("TRYING -- In GenerateReportDao - generateSDMSExcelSheet");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting batchId & userEmail in parameters");
			parameters.put("batchId",batchId);
			parameters.put("userEmail", userEmail);
			LOGGER.debug("Executing query to get SDMS Excel sheet Details");
			return getJdbcTemplate().query(generateReportConfig.getShowSDMSExcelSheet(), parameters ,generateSDMSExcelSheetRowMapper);
			
		}catch(Exception e) {
			
			LOGGER.error("CATCHING -- Exception handled while getting SDMS Excel Sheet details");
			LOGGER.error("In GenerateReportDao - generateSDMSExcelSheet");	
			LOGGER.error("Exception is"+e);
			LOGGER.error("Returning null");
			return null;
		}	
	}
	
	/**
	 * Method to get data for generating Minutes of selection committee based on batch id and email
	 * @param batchId for which minutes of selection committee needs to be generated
	 * @param userEmail email of logged in user
	 * @return
	 */
	public Collection<GenerateMinutesOfSelectionDto> generateMinutesOfSelection(String batchId,String userEmail) {

		LOGGER.debug("Request received from service to GenerateReportDao");
		LOGGER.debug("To get Minutes of Selection committee Details - generateMinutesOfSelection");
		
		try {
			
			LOGGER.debug("TRYING -- In GenerateReportDao - generateMinutesOfSelection");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting batchId & userEmail in parameters");
			parameters.put("batchId",batchId);
			parameters.put("userEmail",userEmail);
			LOGGER.debug("Executing query to get Minutes of Selection Committee Details");
			return getJdbcTemplate().query(generateReportConfig.getShowMinutesOfSelectionMeetingDetails() ,parameters , generateMinutesOfSelectionRowMapper);
			
		}catch(Exception e) {
			
			LOGGER.error("CATCHING -- Exception handled while getting Minutes of Selection Committee details");
			LOGGER.error("In GenerateReportDao - generateMinutesOfSelection");	
			LOGGER.error("Exception is"+e);
			LOGGER.error("Returning null");
			return null;
		}
	}
	
	/**
	 * Method to insert the mark the fields for generated reports of a batch and user
	 * @param generateReportsId id of report generated
	 * @param generatedOn date of generation
	 * @param reportType type of report
	 * @param batchId for which the row needs to be filled
	 * @param userEmail email of logged in user
	 */
	public void updateTableGenerateReports(String generateReportsId,Date generatedOn, String reportType, String batchId ,String userEmail) {
		
		LOGGER.debug("Request received from service to GenerateReportDao");
		LOGGER.debug("To Update records in the generateReportTable for the recently generated report");
		LOGGER.debug("In method - updateTableGenerateReports");
		
		int result;
		Map<String, Object> parameters = new HashMap<>();
		
		try {
		LOGGER.debug("Inserting generateReportsId, generatedOn, reportType, batchId & userEmail in parameters");
		parameters.put("generateReportsId", generateReportsId);
		parameters.put("generatedOn",generatedOn);
		parameters.put("reportType",reportType);
		parameters.put("batchId",batchId);
		parameters.put("userEmail",userEmail);
		
		LOGGER.debug("Executing query to update record of generated report");		
		result = getJdbcTemplate().update(generateReportConfig.getUpdateGenerateReportsTable(),parameters);
			
		}catch(Exception e) {

			LOGGER.error("CATCHING -- Exception handled while updating record of generated report");
			LOGGER.error("In GenerateReportDao - updateTableGenerateReports");	
			LOGGER.error("Exception is"+e);
		}			
	}
	
	/**
	 * Method to return data for audit table of an logged in user
	 * @param userEmail email of logged in user
	 * @return collection of Audit table
	 */
	public Collection<DisplayAuditTableRecordDto> getRecordsForAuditTable(String userEmail) {
		
		LOGGER.debug("Request received from service to GenerateReportDao");
		LOGGER.debug("To Get Records For Audit Table - getRecordsForAuditTable");
		LOGGER.debug("Having records of generated reports by logged in TP");
		
		try {
			
			LOGGER.debug("TRYING -- To get records for Audit Table");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting userEmail in parameters");
			parameters.put("userEmail", userEmail);
			LOGGER.debug("Executing query to get generated report record for TP");
			return getJdbcTemplate().query(generateReportConfig.getShowAuditTableRecords(), parameters ,getRecordsForAuditTableRowMapper);
			
		}catch(Exception e) {
			
			LOGGER.error("CATCHING -- Exception handled while getting record of generated report for logged in TP");
			LOGGER.error("In GenerateReportDao - getRecordsForAuditTable");	
			LOGGER.error("Exception is"+e);
			return null;
		}
	}
	
	/**
	 * Row mapper class for Batch Id
	 * @author Ruchi
	 *
	 */
	private static class BatchIdRowmapper implements RowMapper<GetBatchIdDto>{
		
		@Override
		public GetBatchIdDto mapRow(ResultSet rs, int rowNum)throws SQLException {
			String batchId= rs.getString("batchId");
			return new GetBatchIdDto(batchId);
		}
		
	}
	
	/**
	 * Row mapper class for occupation certificate
	 * @author Ruchi
	 *
	 */
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
    
    /**
     * Row mapper class for generate attendance sheet 
     * @author Ruchi
     *
     */
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
    
    /**
     * Row mapper class for NSKFDC sheet generation
     * @author Ruchi
     *
     */
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
			String hiredByMc = rs.getString("hiredByMc");
			String outsourcedEmployerName = rs.getString("outsourcedEmployerName");
			String outsourcedEmployerContact = rs.getString("outsourcedEmployerContact");
			String monthlySalary = rs.getString("monthlySalary");
			String pfOrEsiProvided = rs.getString("pfOrEsiProvided");
			String municipal = rs.getString("municipality");
			
			return new GenerateNSKFDCExcelSheetDto(firstName,lastName,age,gender,dob,educationLevel,firstNameFather,
					lastNameFather,motherName,aadharCardNumber,residentialAddress,mobileNumber,occupationType,
					msId,idProofType,idProofNumber,bankName,ifscCode,accountNumber,batchId,trainingPartnerName,relationWithSKMS, hiredByMc,
					 outsourcedEmployerName, outsourcedEmployerContact, monthlySalary, pfOrEsiProvided, municipal);	
		}	
    }
    
    /**
     * Row mapper class for SDMS sheet generation
     * @author Ruchi
     *
     */
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
    
    /**
     * Row mapper class for minutes of selection committee
     * @author Ruchi
     *
     */
    private static class GenerateMinutesOfSelectionRowmapper implements RowMapper<GenerateMinutesOfSelectionDto>{
    	
    	@Override
    	public GenerateMinutesOfSelectionDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		String selectionCommitteeDate=rs.getString("selectionCommitteeDate");
    		String jobRole = rs.getString("jobRole");
    		String trainingPartnerName = rs.getString("trainingPartnerName");
    		String sectorSkillCouncil=rs.getString("sectorSkillCouncil");
    		String centreCity=rs.getString("centreCity");
    		String municipality=rs.getString("municipality");

   			return new GenerateMinutesOfSelectionDto(selectionCommitteeDate,jobRole,trainingPartnerName,sectorSkillCouncil,centreCity,municipality);	
    		
	
    	}
    }

    /**
     * Row mapper class for Audit table
     * @author Ruchi
     *
     */
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
