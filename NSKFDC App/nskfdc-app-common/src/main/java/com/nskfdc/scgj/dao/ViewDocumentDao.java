package com.nskfdc.scgj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.common.CreateZipFile;
import com.nskfdc.scgj.config.ViewDocumentConfig;
import com.nskfdc.scgj.dto.ViewDocumentDto;

@Repository
public class ViewDocumentDao extends AbstractTransactionalDao {

private static final Logger LOGGER= LoggerFactory.getLogger(ViewDocumentConfig.class);
	
	private static final ViewDocumentRowmapper viewDocument_RowMapper = new ViewDocumentRowmapper();
	
	@Autowired
	private ViewDocumentConfig viewDocumentConfig;
	
	public Collection< ViewDocumentDto>getViewTrainingPartnerDetailForBatchId(String tpName,  String batchId){

		LOGGER.debug("Request received from Service");
		LOGGER.debug("In viewDocumentDao, to get Training Partner Detail for Search");
		
		
		try {
			
			LOGGER.debug("In try block");
			LOGGER.debug("Execute query to get training partner details for Search"+tpName);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("trainingPartnerName",tpName); //to be changed
			parameters.put("batchId",batchId);
			parameters.put("scgjBatchNumber",null);
			return getJdbcTemplate().query(viewDocumentConfig.getShowTrainingPartnerDetailsForBatchId(),parameters,  viewDocument_RowMapper);
			
		} catch (Exception e) {
			
			LOGGER.debug("In Catch Block DAO");
			LOGGER.debug("An error occured while getting the training partner details for Search" + e);
			return null;
			
		}
		
	}
	//second for SCGJ batch number
	public Collection< ViewDocumentDto>getViewTrainingPartnerDetailForSearchscgjBtNumber(String tpName, String scgjBtNumber){

		LOGGER.debug("Request received from Service" + tpName+scgjBtNumber);
		LOGGER.debug("In viewDocumentDao, to get Training Partner Detail for Search");
		
		
		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("trainingPartnerName",tpName); //to be changed
			parameters.put("scgjBatchNumber",scgjBtNumber);
			parameters.put("batchId",null);
			LOGGER.debug("In try block");
			LOGGER.debug("Execute query to get training partner details for Search");
			LOGGER.debug("sql query"+viewDocumentConfig.getShowTrainingPartnerDetailsForBatchId());
			return getJdbcTemplate().query(viewDocumentConfig.getShowTrainingPartnerDetailsForBatchId(),parameters, viewDocument_RowMapper);
			
		} catch (Exception e) {
			
			LOGGER.debug("In Catch Block DAO");
			LOGGER.debug("An error occured while getting the training partner details for Search" + e);
			return null;
			
		}
		
	}
	                static StringBuilder s2 = new StringBuilder("");
            	private static class ViewDocumentRowmapper implements RowMapper<ViewDocumentDto> {
            		@Override
            		public ViewDocumentDto mapRow(ResultSet rs, int rownum)throws SQLException {
            			
            			Integer batchId = rs.getInt("batchId");
            			String trainingPartnerName = rs.getString("trainingPartnerName");
            			String uplodedOn=rs.getString("dateUploaded");
            			Integer finalBatchReport = rs.getInt("finalBatchReport");
            			Integer occupationCertificate = rs.getInt("occupationCertificate");
            			Integer minuteOfSelectionCommittee = rs.getInt("minuteOfSelectionCommittee");
            			Integer dataSheetForSDDMS = rs.getInt("dataSheetForSDDMS");
            			Integer dataSheetForNSKFC = rs.getInt("dataSheetForNSKFC");
            			Integer attendanceSheet = rs.getInt("attendanceSheet");
            			String finalBatchReportPath =rs.getString("finalBatchReportPath"); 
            			String occupatioCertificatePath =rs.getString("occupatioCertificatePath"); 
            			String minuteOfSelectionCommitteePath =rs.getString("minuteOfSelectionCommitteePath");
            			String dataSheetForSDMSPath =rs.getString("dataSheetForSDMSPath"); 
            			String dataSheetForNSKFCPath =rs.getString("dataSheetForNSKFCPath");
            			String attendanceSheetPath =rs.getString("attendanceSheetPath");
            			
            			  
            			//MANIPULATION FOR FILE 
            			  LOGGER.debug("In rowmapper VARIABLE DECLARATION");
            			  
            			  if(finalBatchReport==1){
            				  s2.append("Final Batch Report, ");
            			  }
            			  LOGGER.debug("In rowmapper BEFORE occupationCertificate VARIABLE DECLARATION");
            			  if(occupationCertificate==1){
            			  
            				  s2.append("Occupation Certificate, ");
            			  }
            			  if(minuteOfSelectionCommittee==1){
            				  s2.append("Signed Minute Of Selection Committee, ");
            			  }
            			  if(dataSheetForSDDMS==1){
            				  s2.append("Data Sheet For SDDMS, ");
            			  }
            			  if(dataSheetForNSKFC==1){
            				  s2.append("Data Sheet For NSKFC, ");
            			  }
            			  if(attendanceSheet==1){
            				  s2.append("Attendance Sheet, ");
            			  }
            			  s2.setLength(s2.length() - 2);
            			  
            			  ArrayList<String> files = new ArrayList<String>();
            			  CreateZipFile createZipFile = new  CreateZipFile();
          				
          				
            			  //condition to handle file paths::
            			  if(finalBatchReportPath!=null){
            				  files.add(finalBatchReportPath);
            			  }
            			  if(occupatioCertificatePath!=null){
            				  files.add(occupatioCertificatePath);
            			  }
            			  if(minuteOfSelectionCommitteePath!=null){
            				  files.add(minuteOfSelectionCommitteePath);
            			  }
            			  if(dataSheetForSDMSPath==null){ //change to not equal to::CONDITION
            				  //files.add("D:/sarthak/testZIp/test1.txt");  //prototype
            				  files.add(dataSheetForSDMSPath);
            			  }
            			  if(dataSheetForNSKFCPath!=null){
            				  files.add(dataSheetForNSKFCPath);
            			  }
            			  if(attendanceSheetPath!=null){
            				  files.add(attendanceSheetPath);
            			  }
            			  LOGGER.debug("In try block  BEFORE ZIP data for Search Document Functionality");
            			  files.add("");
            			String zipFileLink ="C:/Users/Bhawana Sharma/Desktop/ziptest.zip";
            			createZipFile.ZipFile(files);
            			
            			return new ViewDocumentDto(batchId, trainingPartnerName,uplodedOn,zipFileLink) ;
            		}
            	}
            	
 
	

}