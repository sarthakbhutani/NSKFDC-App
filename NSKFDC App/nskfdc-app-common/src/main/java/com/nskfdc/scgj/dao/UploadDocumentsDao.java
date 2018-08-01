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

//import com.nskfdc.scgj.config.UploadDocumentsConfig;
import com.nskfdc.scgj.dto.UploadDocumentsDto;

@Repository	
public class UploadDocumentsDao extends AbstractTransactionalDao{
	
	private static final Logger LOGGER= LoggerFactory.getLogger(UploadDocumentsDao.class);   
	
	private static final UploadDocumentsRowMapper uploadDocumentsRowMapper = new UploadDocumentsRowMapper();
	
	private static final BATCHRowmapper getBatchIdRowMapper = new BATCHRowmapper();
	
	static StringBuilder s2 = new StringBuilder("");

	@Autowired
	private UploadDocumentConfig uploadDocumentsConfig;
	
	Integer checkUserDocumentExistence = -25;
	int updateStatus = - 5;
	
	public String uploadDocuments(String batchId,String userEmail,String fileType,String path)
	{
		LOGGER.debug("Received request from service to upload file path into the database");
		LOGGER.debug("Checking Existence of user uploaded documents in the uploaded documents table");
		LOGGER.debug("Creating hashmap of objects");
		
		try
		{
			Map<String,Object> params = new HashMap<>();
			params.put("batchId", batchId);
			params.put("userEmail",userEmail);
			
			LOGGER.debug("Inserted parameters into the hashmap");
			 checkUserDocumentExistence = getJdbcTemplate().queryForObject(uploadDocumentsConfig.getCheckExistence(), params, Integer.class);
			 LOGGER.debug("Existence of user in the uploaded documents table is : " + checkUserDocumentExistence);
			 
			
			 if(checkUserDocumentExistence == 1)
			 {
				 LOGGER.debug("User Documents exists in the database");
				 LOGGER.debug("Updating the path of the documents");
				 return updateDocumentPath(batchId,userEmail,fileType,path);
			 }	
			 	
			 else 
			 {
				 LOGGER.debug("User does not exists in the database");
				 LOGGER.debug("Inserting the batchId and userEmail in uploaded documents table");
				 
				 Integer insertStatus = getJdbcTemplate().update(uploadDocumentsConfig.getInsertUserDetails(), params);
				 
				 if(insertStatus == 1)
				 {
					 LOGGER.debug("batchId and userEmail inserted into table");
					 LOGGER.debug("Sending control to updateDocumentPath to update the path of the documents");
					   return updateDocumentPath(batchId,userEmail,fileType,path);
				 }
				 
				 else
				 {

					 return "Document cannot be uploaded";
				 }
				 
				 
			 }	

		 } 
		catch(Exception e)
				{
					LOGGER.debug("An Exception occured while uploading documents in the database " + e);
					return "Document cannot be uploaded";
				}
		
		
 	}
	

	
	/**
	 * Update Document Method
	 * @param batchId
	 * @param userEmail
	 * @param fileType
	 * @param path
	 * @return String which is tells the status of update of file path
	 */
	
	private String updateDocumentPath(String batchId, String userEmail, String fileType, String path) 
	{
	   
		LOGGER.debug("In method updateDocumentPath to update the path of the document");
		LOGGER.debug("Creating hashmap of objects");
		Map<String,Object>updateParams = new HashMap<>();
		
		 switch (fileType) 
		 {
		 
		 case "occupationCertificate":
			 
			 LOGGER.debug("The document is occupation certificate");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 LOGGER.debug("Inserting query into the JDBC Template");
			 try
			 {
				 LOGGER.debug("In try block of upload occupationCertificate");
				  updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateOccupationCertificatePath(), updateParams);
	
					 if(updateStatus < 1)
					 {
						 return "File cannot be uplaoded, Please try again"; 
					 }
					 	
			 }
			 catch(Exception e)
			 {
				 LOGGER.error("An Exception occured while updating path of occupation certificate " + e);
				 return "Cannot upload the occupation certificate";
			 }
			 
			 					break;
		 			
		 case "attendanceSheet":
			 
			 LOGGER.debug("The type of file to be uploaded is attendance sheet");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 try
			 {
				 LOGGER.debug("Inserted the path for attendance sheet in hashmap");
				 updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateAttendanceSheetPath(), updateParams);
				 
				 if(updateStatus < 1)
				 {
					 LOGGER.debug("Attendance Sheet path is not updated");
					 return "File cannot be uploaded";
				 }
	
			 }
			 catch(Exception e)
			 {
				 LOGGER.error("An Exception occured while uplaoding the attendance sheet");
				 return "File cannot be uplaoded";
			 }
			 			
			 						break;
		 
		 case "NSKFDCSheet":
			 
			 LOGGER.debug("The received file is NSKFDC Sheet");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 
			 try
			 {
			 updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateNSKFDCSheet(), updateParams);
	
				 if(updateStatus < 1)
				 {
					 LOGGER.debug("NSKFDC Sheet path cannot be updated");
					 return "File cannot be uploaded";
				 }
			 }
			 catch(Exception e)
			 {
				 LOGGER.debug("Exception occured while uplaoding file for NSKFDC Sheet " + e);
				 return "File cannot be uploaded";
			 }
		 
			 						break;
		
		case "SDMSSheet" :
			
			LOGGER.debug("The document is SDMS Sheet");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 try
			 {
			 updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateSDMSSheet(), updateParams);
	
				 if(updateStatus < 1)
				 {
					 LOGGER.debug("SDMS Sheet path cannot be updated");
					 return "File cannot be uploaded";
				 }

			 }
			 catch(Exception e)
			 {
				 LOGGER.debug("Exception occured while uplaoding file for NSKFDC Sheet " + e);
				 return "File cannot be uploaded";
			 }	
			 			break;
			
		case "selectionCommitteeMeeting" :
			
			LOGGER.debug("The document is minutes of selection committee meeting");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 
			 try
			 {
			 updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateMinuteOfSelectionCommittee(), updateParams);
	
				 if(updateStatus < 1)
				 {
					 LOGGER.debug("Selection Committee Meeting path cannot be updated");
					 return "File cannot be uploaded";
				 }

			 }
			 catch(Exception e)
			 {
				 LOGGER.debug("Exception occured while uplaoding file for Selection Committee Meeting " + e);
				 return "File cannot be uploaded";
			 }	
			 					break;
			 					
		case "finalBatchReport":
			
			LOGGER.debug("The document is final batch report");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 
			 try
			 {
			 updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateFinalBatchReportPath(), updateParams);
	
				 if(updateStatus < 1)
				 {
					 LOGGER.debug("Final Batch Report path cannot be updated");
					 return "File cannot be uploaded";
				 }

			 }
			 catch(Exception e)
			 {
				 LOGGER.debug("Exception occured while uplaoding file for final batch Report " + e);
				 return "File cannot be uploaded";
			 }	
 
		 }
		
		
		 return "File Uploaded Successfully";
	}
	
	public int insertInUploadedDocument(Map<String , Object> recordToInsert)
	{
		LOGGER.debug("Request Received from Service");
		LOGGER.debug("In DataImportCertificateDao - insertDataInUploadedDocument");
		LOGGER.debug("Parameters Received from Service are  - HashMap 'recordToInsert'");
				   	
		LOGGER.debug("Inserting data in UploadedDocument Table");
		LOGGER.debug("Executing SQL query and returning response");
        return getJdbcTemplate().update(uploadDocumentsConfig.getInsertIntoUploadDocument(), recordToInsert);	
	}
	
	
	
	public Collection<UploadDocumentsDto> getSearchedDocument(String batchId, String userEmail) {
		//write Logger here
		LOGGER.debug("Request received from Service");
		LOGGER.debug("In UploadDocumentsuploadDao, to populate the grid-ui" + batchId);
		
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
					LOGGER.error("In catch block of UploadDocumentsuploadDao");
					LOGGER.error("Error occured in UploadDocumentsuploadDao with exception" + e);
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
	
			  
			  if(s2!=null){
				  LOGGER.debug("STRING NOT NULL");
				  s2.setLength(0);
			  }else{
				  LOGGER.debug("STRING NULL");
			  }
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
			  if(dataSheetForSDMSPath!=null){
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
  // THE ABOVE LINE NEEDS TO CHANGED AS IT NEEDS TO PICK THE DYNAMIC PATH FOR STORING ZIP FILE.
  //PRAVEK SIR WILL BE IMPLEMENTING THOSE CHANGES

			  LOGGER.debug("the current working directory is " + zipLocationRead);
			  if(s2!=null){
				   File folder = new File(zipLocationRead);
				  LOGGER.debug(zipLocationRead);
				  if(!folder.exists()){
					  if(folder.mkdirs() || folder.canWrite())
					  {
						  LOGGER.debug("FOLDER CREATED TO SAVE THE ZIP FILE");
						  zipFileLink = zipLocationRead +"/" + batchId + ".zip";
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
//					            e.printStackTrace();
					            LOGGER.debug("File not found exception :" +e);
					        } catch (IOException e) {
					            // TODO Auto-generated catch block
//					            e.printStackTrace();
					        	LOGGER.debug("Input output exception :" +e);
					        } finally{
					            try{
					                if(fileOutputStream != null) fileOutputStream.close();
					            } catch(Exception ex){
					            	LOGGER.debug("Error"+ex);
					            }
					        }
					  }
					  else{
						  LOGGER.debug("FAILED TO WRITE THE FOLDER at location: " + zipLocationRead);
					  }
					  
				  }else{
					  zipFileLink = zipLocationRead +"/" + batchId + ".zip";
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
				            LOGGER.debug("Done... Zipped the files... at location:" + zipFileLink);
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
