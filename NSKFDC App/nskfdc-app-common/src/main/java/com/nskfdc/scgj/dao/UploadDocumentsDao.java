package com.nskfdc.scgj.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.UploadDocumentConfig;

import com.nskfdc.scgj.dto.BatchDto;

//import com.nskfdc.scgj.common.POCZipMultipleFile;
//import com.nskfdc.scgj.config.UploadDocumentsConfig;
import com.nskfdc.scgj.dto.UploadDocumentsDto;

@Repository	
public class UploadDocumentsDao extends AbstractTransactionalDao{
	static final Logger LOGGER= LoggerFactory.getLogger(UploadDocumentsDao.class);   
	private static final UploadDocumentsRowMapper uploadDocumentsRowMapper = new UploadDocumentsRowMapper();
	private static final BATCHRowmapper getBatchIdRowMapper = new BATCHRowmapper();
	static StringBuilder s2 = new StringBuilder("");

	@Autowired
	private UploadDocumentConfig uploadDocumentsConfig;
	
//	@Autowired
//	private static  ReadApplicationConstants readApplicationConstantsObj;
	
	public void saveUploadedDocument() {
		//write Logger here
		
				try {
					
					//write Logger here 
					//write code here and change the return type
				}
				catch(Exception e) {
					
					// write Logger
					// return null;
				}
				
				
			}
	
	
	public Collection<UploadDocumentsDto> getSearchedDocument(String batchId, String userEmail) {
		//write Logger here
		LOGGER.debug("Request received from Service");
		LOGGER.debug("In POCuploadDao, to populate the grid-ui" + batchId);
		
				try {
					
					LOGGER.debug("In Try Block of UploadDocumentsDao" + batchId);
					LOGGER.debug("Creating hashmap of objects");
					Map<String,Object> parameters = new HashMap<>();
					LOGGER.debug("Inserting parameters into the hashmap " + batchId+"email:"+userEmail);
					parameters.put("batchId",batchId); //add, tpEmailId later  
					parameters.put("userEmail", userEmail);
					LOGGER.debug("The parameter inserted in hashmap are : " + parameters.get("batchId"));
					LOGGER.debug("The query after inserting the parameters is : " + uploadDocumentsConfig.getUploadDocumentsQuery());
					return  getJdbcTemplate().query(uploadDocumentsConfig.getUploadDocumentsQuery(),parameters,uploadDocumentsRowMapper);

					
				}
				catch(Exception e) {
					LOGGER.error("In catch block of POCuploadDao");
					LOGGER.error("Error occured in POCuploadDao with exception" + e);
					return null;
				}
				
				
			}
	public List<BatchDto> getBatchDetail(String userEmail){
		LOGGER.debug("Request received from UploadService to get batch Id");
		LOGGER.debug("In UploadDocumentsDao" + userEmail);
		Map<String, Object> parameters = new HashMap<>();
		LOGGER.debug("Generating hashmap of parameters");
		parameters.put("userEmail",userEmail);
		LOGGER.debug("The username put into hashmap is : " + parameters.get(userEmail));
		if(parameters.isEmpty())
		{
			LOGGER.error("Null Parameter");
		}
		
try {
			
	LOGGER.debug("In try block of upload documents dao to get batch ID");
	return getJdbcTemplate().query(uploadDocumentsConfig.getShowBatchIdDetails(),parameters,getBatchIdRowMapper );
			
		}


		
catch(Exception e) {
			
	LOGGER.error("In Catch Block");
			
	LOGGER.error("An error occured while fetching the batch ID " + e);
			
	return null;
}
	}
	
	
//	public Collection<BatchDto> showBatchIdDetails(String userEmail){
//		
//		try {
//			
//			Map<String,Object> parameters = new HashMap<>();
//			parameters.put("userEmail", userEmail);
//		
//			return getJdbcTemplate().query(uploadDocumentsConfig.getShowBatchIdDetails(),parameters, BATCH_RowMapper);
//			
//		} catch (Exception e) {
//			
//			LOGGER.debug("An Exception occured while fetching batch id for email " + userEmail + e);
//			return null;
//			
//		}
//		
//	}

	

	private static class BATCHRowmapper implements RowMapper<BatchDto>{
		
		@Override
		public BatchDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			int batchId = rs.getInt("batchId");
			return new BatchDto(batchId);
			
		}
	}

	
	public int scgjBatchIdField(String batchId,String scgjBatchNumber) {
		
		LOGGER.debug("Request received from scgj service");
		LOGGER.debug("In scgj Dao");
		
		try {
			
			LOGGER.debug("In try block of scgj Dao");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("batchId",batchId);
			parameters.put("scgjBatchNumber", scgjBatchNumber);
			
			
			return getJdbcTemplate().queryForObject(uploadDocumentsConfig.getShowScgjDetails(), parameters,Integer.class);
			
		}catch(Exception e) {
			
			LOGGER.debug("In catch block of scgj Dao"+e);
			return 0;
		}
	}	
	public int BatchIdField(String batchId) {
		
		LOGGER.debug("Request received from scgj service");
		LOGGER.debug("In scgj Dao");
		
		try {
			
			LOGGER.debug("In try block of scgj Dao");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("batchId",batchId);
			return getJdbcTemplate().queryForObject(uploadDocumentsConfig.getBatchidDetails(), parameters,Integer.class);
			
		}catch(Exception e) {
			
			LOGGER.debug("In catch block of scgj Dao"+e);
			return 0;
		}
	}	



//bhutano

	static class UploadDocumentsRowMapper implements RowMapper<UploadDocumentsDto>{
		@Override
		public UploadDocumentsDto mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			  int batchId = rs.getInt("batchId");
			  String dateUploaded = rs.getString("dateUploaded");
			  int finalBatchReport = rs.getInt("finalBatchReport");
			  int occupationCertificate = rs.getInt("occupationCertificate");
			  int minuteOfSelectionCommittee = rs.getInt("minuteOfSelectionCommittee");
			  int dataSheetForSDDMS = rs.getInt("dataSheetForSDDMS");
			  int dataSheetForNSKFC = rs.getInt("dataSheetForNSKFC");
			  int attendanceSheet = rs.getInt("attendanceSheet");
			  String finalBatchReportPath = rs.getString("finalBatchReportPath");
			  String occupationCertificatePath = rs.getString("occupatioCertificatePath");
			  String minuteOfSelectionCommitteePath = rs.getString("minuteOfSelectionCommitteePath");
			  String dataSheetForSDMSPath = rs.getString("dataSheetForSDMSPath");
			  String dataSheetForNSKFCPath = rs.getString("dataSheetForNSKFCPath");
			  String attendanceSheetPath = rs.getString("attendanceSheetPath");
			  LOGGER.debug("IN DAO, checking for values"+batchId);
	
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
			  
			  if(s2!=null){
			  s2.setLength(s2.length() - 2);
			  }
			  ArrayList<String> files = new ArrayList<String>();
				

				//condition to handle file paths::
			  if(finalBatchReportPath!=null){
				  files.add(finalBatchReportPath);
			  }
			  if(occupationCertificatePath!=null){
				  files.add(occupationCertificatePath);
			  }
			  if(minuteOfSelectionCommitteePath!=null){
				  files.add(minuteOfSelectionCommitteePath);
			  }
			  if(dataSheetForSDMSPath!=null){ //change to not equal to::CONDITION
//				  files.add("D:/sarthak/testZIp/test1.txt");  //prototype
				  files.add(dataSheetForSDMSPath);
			  }
			  if(dataSheetForNSKFCPath!=null){
				  files.add(dataSheetForNSKFCPath);
			  }
			  if(attendanceSheetPath!=null){
				  files.add(attendanceSheetPath);
			  }
			  LOGGER.debug("In try block  BEFORE ZIP data for Search Document Functionality");
				
			  String zipFileLink = " ";

			  if(s2!=null){
				  LOGGER.debug("STRING NOT NULL");
			  }else{
				  LOGGER.debug("STRING NULL");
			  }
			  //String zipLocationRead = readApplicationConstantsObj.getCreateZipFileAtLocation();  //should be actual thing
			  String zipLocationRead = System.getProperty("user.dir");  //getting working directory
			  LOGGER.debug("the current working directory is " + zipLocationRead);
			  if(s2!=null){
				   File folder = new File(zipLocationRead);
				  LOGGER.debug(zipLocationRead);
				  if(!folder.exists()){
					  if(folder.mkdirs() || folder.canWrite())
					  {
						  LOGGER.debug("FOLDER CREATED TO SAVE THE ZIP FILE");
						   zipFileLink = zipLocationRead+batchId + ".zip";
						  FileOutputStream fileOutputStream = null;
					        ZipOutputStream zipOut = null;
					        FileInputStream fileInputStream = null;
					        try {
					        	LOGGER.debug("In try block of ZipFIleCreation");
					            fileOutputStream = new FileOutputStream(zipFileLink);
					            zipOut = new ZipOutputStream(new BufferedOutputStream(fileOutputStream));
					            for(String filePath:files){
					                File input = new File(filePath);
					                fileInputStream = new FileInputStream(input);
					                ZipEntry zipEntry = new ZipEntry(input.getName());
					                LOGGER.debug("Zipping the file: "+input.getName());
					                zipOut.putNextEntry(zipEntry);
					                byte[] tmp = new byte[4*1024];
					                int size = 0;
					                while((size = fileInputStream.read(tmp)) != -1){
					                    zipOut.write(tmp, 0, size);
					                }
					                zipOut.flush();
					                fileInputStream.close();
					            }
					            zipOut.close();
					            LOGGER.debug("Done... Zipped the files...");
					        } catch (FileNotFoundException e) {
					            // TODO Auto-generated catch block
					            e.printStackTrace();
					        } catch (IOException e) {
					            // TODO Auto-generated catch block
					            e.printStackTrace();
					        } finally{
					            try{
					                if(fileOutputStream != null) fileOutputStream.close();
					            } catch(Exception ex){
					                 
					            }
					        }
					  }
					  else{
						  LOGGER.debug("FAILED TO WRITE THE FOLDER at location: " + zipLocationRead);
					  }
					  
					  
				  }else{
					  zipFileLink = zipLocationRead+batchId + ".zip";
					  FileOutputStream fileOutputStream = null;
				        ZipOutputStream zipOut = null;
				        FileInputStream fileInputStream = null;
				        try {
				        	LOGGER.debug("In try block of ZipFIleCreation");
				            fileOutputStream = new FileOutputStream(zipFileLink);
				            zipOut = new ZipOutputStream(new BufferedOutputStream(fileOutputStream));
				            for(String filePath:files){
				                File input = new File(filePath);
				                fileInputStream = new FileInputStream(input);
				                ZipEntry zipEntry = new ZipEntry(input.getName());
				                LOGGER.debug("Zipping the file: "+input.getName());
				                zipOut.putNextEntry(zipEntry);
				                byte[] tmp = new byte[4*1024];
				                int size = 0;
				                while((size = fileInputStream.read(tmp)) != -1){
				                    zipOut.write(tmp, 0, size);
				                }
				                zipOut.flush();
				                fileInputStream.close();
				            }
				            zipOut.close();
				            LOGGER.debug("Done... Zipped the files...");
				        } catch (FileNotFoundException e) {
				            // TODO Auto-generated catch block
				            e.printStackTrace();
				        } catch (IOException e) {
				            // TODO Auto-generated catch block
				            e.printStackTrace();
				        } finally{
				            try{
				                if(fileOutputStream != null) fileOutputStream.close();
				            } catch(Exception ex){
				                 
				            }
				        }
				  }
				  
			
			return new UploadDocumentsDto(batchId,dateUploaded,s2,zipFileLink);

				}
			  else{
				  return null;
			  }
		}
	}
}
