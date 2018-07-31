package com.nskfdc.scgj.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nskfdc.scgj.common.ReadApplicationConstants;
import com.nskfdc.scgj.dao.UploadDocumentsDao;
import com.nskfdc.scgj.dto.BatchDto;
import com.nskfdc.scgj.dto.BatchIdDto;
import com.nskfdc.scgj.dto.UploadDocumentsDto;

@Service
public class UploadDocumentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadDocumentService.class);

	@Autowired
	private UploadDocumentsDao uploadDocumentsDao;
	
	@Autowired
	private ReadApplicationConstants readApplicationConstants;
	
	public String saveUploadedDocument(MultipartFile file, String fileType, String batchId, String batchNo, String userEmail) {
		
		LOGGER.debug("Request Received from Controller");
		LOGGER.debug("In UploadDocumentService - saveUploadedDocument");
		LOGGER.debug("Parameters Received from front end are - 'file': "+file+" 'fileType': "+fileType+" 'batchId':"+batchId+" 'batchNo': "+batchNo);
		LOGGER.debug("Initializing the default path for uploading documents");
		String UPLOADED_FOLDER = readApplicationConstants.getSaveDocumentsAtLocation();
		LOGGER.debug("Successfully Initialized");
		String pathOfUploadedFile="";
		String fileNameReceived="";
		String fileNameToBeSaved="";
			
			try {
				LOGGER.debug("In TRY block");	

				 fileNameReceived=file.getOriginalFilename();
		           int indexOfDot=fileNameReceived.indexOf(".");
		           
		           String[] fileNameArray={fileNameReceived.substring(0, indexOfDot),
		        		   fileNameReceived.substring(indexOfDot)};
		                      
		           fileNameToBeSaved=fileNameArray[0]+ batchId +fileNameArray[1];
		           if(!fileNameArray[1].equals(".pdf, .xls"))
		           {
		        	   LOGGER.debug("Inside IF block");
		        	   LOGGER.error("Uploaded file is not in PDF or XLS format");
		        	   LOGGER.debug("Returning ERROR message string : Kindly Upload a .pdf or .xls file");
		        	   return "Kindly Upload a .pdf or .xls file";
		           }
		           
		           Path path = Paths.get(UPLOADED_FOLDER +fileNameToBeSaved);
		           LOGGER.debug("Initializing the full path with file name for uploading documents");
		           pathOfUploadedFile=UPLOADED_FOLDER+fileNameToBeSaved;
		           LOGGER.debug("Path is - "+pathOfUploadedFile);
		           LOGGER.debug("Creating File object");
		           File checkFileExistence = new File(pathOfUploadedFile);
		           LOGGER.debug("Successfully Created and Initialized");
		           LOGGER.debug("Checking if file already exists");
		           if(checkFileExistence.exists() && !checkFileExistence.isDirectory()) 
		           {
		        	   LOGGER.debug("Creating File object for deleting file");
		            File deleteUploadedFile = new File(pathOfUploadedFile);
		            LOGGER.debug("Successfully Created and Initialized");
		            LOGGER.debug("Initializing delete request");
		            deleteUploadedFile.delete();
		            LOGGER.debug("Successfully Deleted");
		           }
		           
		           LOGGER.debug("Creating byte array object to write file");
		           byte[] bytes = file.getBytes();
		           LOGGER.debug("Successfully Created and Initialized");
		           try{
		        	   LOGGER.debug("Inside TRY block");
		        	   LOGGER.debug("Writing file");
		           Files.write(path, bytes);
		           LOGGER.debug("Exiting TRY block");
		           return "";
		           }
		           catch(Exception e)
		           {
		        	   LOGGER.debug("In CATCH block");
		    			LOGGER.error("ERROR: Encountered an Exception - "+e);
		    			e.printStackTrace();
		        	   return "Error Saving file on Local Machine.Try Again later ";
		           }
		           
		    	}
		    	catch (Exception e) {
		    	       e.printStackTrace();
		    	       LOGGER.debug("Creating File object for deleting file");
		    	       File deleteUploadedFile = new File(pathOfUploadedFile);
		    	       LOGGER.debug("Initializing delete request");
		    	       deleteUploadedFile.delete();
		    	       LOGGER.debug("Successfully Deleted");
		    	       return "Error uploading Zip File. Kindly try again.";
		    	   }
			
			
//				try {
//					LOGGER.debug("Inside TRY block");
//					LOGGER.debug("Creating Date object");
//					Date date=new Date(System.currentTimeMillis());
//					LOGGER.debug("Successfully created and initialized");
//					LOGGER.debug("Creating HashMap object");
//					Map<String,Object> uploadedFileInfo= new HashMap<String, Object>();
//					LOGGER.debug("Successfully created");
//					LOGGER.debug("Inserting data into HashMap created");
//					uploadedFileInfo.put("finalBatchReportPath",pathOfUploadedFile);
//					uploadedFileInfo.put("batchId",batchId);
//					uploadedFileInfo.put("dateUploaded",date);
//					uploadedFileInfo.put("userEmail",userEmail);
//				} catch (Exception e) {
//					
//				}
//				
			
				
			
	}
			


		public Collection<UploadDocumentsDto> getSearchedDocument(String batchId, String userEmail){
		//write LOGGER here
		LOGGER.debug("Request received from Controller");
		LOGGER.debug("In getSearchedDocumentService");
			
			try {
			
				LOGGER.debug("In try block of UploadDocumentService to get search results for batch Id: " + batchId);
				return uploadDocumentsDao.getSearchedDocument(batchId, userEmail);
				
			} catch (Exception e) {
				
				LOGGER.debug("An error occurred while getting the Document details for UploadDocumentsService"+ e);
				LOGGER.debug("Return NULL");
				return null;
				
			}
		}		
		public List<BatchDto> getBatchDetails(String userEmail){
			LOGGER.debug("Request received from upload documents Controller to get batch id corresponding to user : " + userEmail);
			
			
			try {
				LOGGER.debug("In try block of upload documents service to get batch id " + userEmail);
				return uploadDocumentsDao.getBatchDetail(userEmail);
				
			}
			catch(Exception d) {
				
				LOGGER.error("DataAccessException in service to create Batch" + d);
				
				return null;
			}

			
		}
		public int scgjBatchIdField(String batchId, String scgjBatchId) {
			
			LOGGER.debug("Request received from controller");
			LOGGER.debug("In Scgj Details Service");
			
				try {	
					
					LOGGER.debug("In try block of Scgj Details Service");
					
				return uploadDocumentsDao.scgjBatchIdField(batchId, scgjBatchId);
				
					
					
			         
				}
				catch(Exception e){
				return 0;	
				}
		}
		public int BatchIdField(String batchId) {
			
			LOGGER.debug("Request received from controller");
			LOGGER.debug("In Scgj Details Service");
			
				try {	
					
					LOGGER.debug("In try block of Scgj Details Service");
					
				return uploadDocumentsDao.BatchIdField(batchId);
				
					
					
			         
				}
				catch(Exception e){
				return 0;	
				}
		}
		
	

}