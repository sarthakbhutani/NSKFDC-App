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
import com.nskfdc.scgj.dto.GenerateAttendanceSheetDto;
import com.nskfdc.scgj.dto.GenerateOccupationCertificateReportDto;

@Repository
public class GenerateReportDao extends AbstractTransactionalDao {
	
	/* Creating object of Generate Occupation Certificate Report Rowmapper */
	private static final GenerateOccupationCertificateReportRowmapper generateOccupationCertificateReportRowMapper = new GenerateOccupationCertificateReportRowmapper();
	
	/* Creating object of Generate Attendance Sheet Report Rowmapper */
	private static final GenerateAttendanceSheetRowmapper generateAttendanceSheetRowMapper = new GenerateAttendanceSheetRowmapper();
	
	@Autowired
	private GenerateReportConfig generateReportConfig;
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateReportDao.class);
	
	public Collection<GenerateOccupationCertificateReportDto> generateOccupationCertificateReport(String batchId) {
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In Generate Occupation Certificate Dao");
		
		try {
			
			LOGGER.debug("In try block of Generate Occupation Certificate Dao");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("batchId",batchId);
			return getJdbcTemplate().query(generateReportConfig.getShowOccupationCertificateReportDetails(), parameters ,generateOccupationCertificateReportRowMapper);
			
		}catch(Exception e) {
			
			LOGGER.debug("In catch block of Generate Occupation Certificate Dao"+e);
			return null;
		}
	}	
	
	public Collection<GenerateAttendanceSheetDto> generateAttendanceSheet(String batchId) {
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("In Generate Attendance Sheet Dao");
		
		try {
			
			LOGGER.debug("In try block of Generate Attendance Sheet Dao");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("batchId",batchId);
			return getJdbcTemplate().query(generateReportConfig.getShowAttendanceSheetDetails(), parameters ,generateAttendanceSheetRowMapper);
			
		}catch(Exception e) {
		
			LOGGER.debug("In catch block of Generate Attendance Sheet Dao"+e);
			return null;
		}	
	}
	
	/* Declaring inner class Generate Occupation Certificate Report Rowmapper */
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
    
    /* Declaring inner class Generate Attendance Sheet Rowmapper */
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
    
}
