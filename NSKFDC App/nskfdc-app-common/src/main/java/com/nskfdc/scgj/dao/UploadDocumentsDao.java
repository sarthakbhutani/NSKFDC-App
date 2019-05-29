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
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.UploadDocumentConfig;
import com.nskfdc.scgj.dto.UploadDocumentsDto;

@Repository	
public class UploadDocumentsDao extends AbstractTransactionalDao{
	
	private static final Logger LOGGER= LoggerFactory.getLogger(UploadDocumentsDao.class);   
	
	private static final UploadDocumentsRowMapper uploadDocumentsRowMapper = new UploadDocumentsRowMapper();

	@Autowired
	private UploadDocumentConfig uploadDocumentsConfig;
	
	Integer checkUserDocumentExistence = -25;
	int updateStatus = - 5;
	
	/**
	 * Method to upload Documents 
	 * @param batchId
	 * @param userEmail
	 * @param fileType
	 * @param path
	 * @return
	 */
	public String uploadDocuments(String batchId,String userEmail,String fileType,String path)
	{
	
		try
		{
			Map<String,Object> params = new HashMap<>();

			params.put("batchId", batchId);
			params.put("userEmail",userEmail);
			checkUserDocumentExistence = getJdbcTemplate().queryForObject(uploadDocumentsConfig.getCheckExistence(), params, Integer.class);
			LOGGER.debug("Result of checking if the user's documents exists is : " + checkUserDocumentExistence);
			 
			
			 if(checkUserDocumentExistence == 1)
			 {
				 LOGGER.debug("User Documents already exists");
				 return updateDocumentPath(batchId,userEmail,fileType,path);
			 }	
			 	
			 else 
			 {
				 LOGGER.debug("User documents does not exist");
				 LOGGER.debug("Inserting the batchId and userEmail in uploaded documents table");
				 Integer insertStatus = getJdbcTemplate().update(uploadDocumentsConfig.getInsertUserDetails(), params);
				 LOGGER.debug("Status of userEmail & batchId inserted in uploaded documents "+ insertStatus);
				 if(insertStatus == 1)
				 {
					 LOGGER.debug("In IF -- When batchId & userEmail inserted successfully in uploadedDocuments table");
					 LOGGER.debug("Calling mathod - updateDocumentPath, to update the path of the documents");
					 return updateDocumentPath(batchId,userEmail,fileType,path);
				 }
				 
				 else
				 {
					 LOGGER.debug("In ELSE -- When batchId & userEmail in uploaded Documents table");
					 LOGGER.debug("Returning string 'Document cannot be uploaded'");
					 return "Document cannot be uploaded";
				 }
				 
				 
			 }	

		 } 
		catch(Exception e)
				{
					LOGGER.error("CATCHING -- Exception handled while saving paths of uploaded documents");
					LOGGER.error("In UploadDocumentsDao - uploadDocuments ");
					LOGGER.error("Exception is : "+ e);
					LOGGER.error("Returning string 'Document cannot be uploaded'");
					return "Could not update path. Exception occured";
				}
		
		
 	}
	

	
	/**
	 * Update path of documents in upload documents Method
	 * @param batchId
	 * @param userEmail
	 * @param fileType
	 * @param path
	 * @return String which is tells the status of update of file path
	 */
	
	private String updateDocumentPath(String batchId, String userEmail, String fileType, String path) 
	{
	   
		LOGGER.debug("In method - updateDocumentPath, to update the path of the uploaded documents");
		LOGGER.debug("Creating hashmap of objects to pass in the query");
		Map<String,Object>updateParams = new HashMap<>();
		
		LOGGER.debug(" In Switch Case -- For different fileType");
		 switch (fileType) 
		 {
		 
		 case "occupationCertificate":
			 
			 LOGGER.debug("Case - When document is occupation certificate");
			 LOGGER.debug("Inserting batchId, userEmail & path in HashMap");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 
			 try
			 {
				 LOGGER.debug("TRYING -- To upload occupationCertificate");
				 LOGGER.debug("Executing update query to save path for Occupation Certificate");
				  updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateOccupationCertificatePath(), updateParams);
	
					 if(updateStatus < 1)
					 {
						 LOGGER.debug("Could not update path of occupation certificate");
						 LOGGER.debug("Returning message 'File cannot be uplaoded, Please try again'");
						 return "File cannot be uplaoded, Please try again"; 
					 }
					 	
			 }
			 catch(Exception e)
			 {
				 LOGGER.error("CATCHING -- Exception handled while updating Occupation certificate filepath");
				 LOGGER.error("In UploadDocumentsDao - updateDocumentPath");
				 LOGGER.error(" An exception occured while updating path of occupation certificate Exception is :"+ e);
				 LOGGER.error("Returning message 'Cannot upload the occupation certificate'");
				 return "Cannot upload the occupation certificate";
			 }
			break;
		 			
		 case "attendanceSheet":
			 
			 LOGGER.debug("Case - When document is attendance sheet");
			 LOGGER.debug("Inserting batchId, userEmail & path in HashMap");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 try
			 {
				 LOGGER.debug("TRYING -- To upload AttendanceSheet");
				 LOGGER.debug("Executing update query to save path for AttendanceSheet");
				 updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateAttendanceSheetPath(), updateParams);
				 
				 if(updateStatus < 1)
				 {
					 LOGGER.debug("In IF -- When updating status of Attendance Sheet is <1");
					 LOGGER.debug("Returning message 'File cannot be uplaoded'");
					 return "File cannot be uploaded";
				 }
	
			 }
			 catch(Exception e)
			 {
				 LOGGER.error("CATCHING -- Exception handled while updating AttendanceSheet filepath");
				 LOGGER.error("In UploadDocumentsDao - updateDocumentPath");
				 LOGGER.error("Exception is :"+ e);
				 LOGGER.error("Returning message 'File cannot be uplaoded'");
				 return "File cannot be uplaoded";
			 }
			 			
			 break;
		 
		 case "NSKFDCSheet":
			 
			 LOGGER.debug("Case - When document is NSKFDCSheet");
			 LOGGER.debug("Inserting batchId, userEmail & path in HashMap");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 
			 try
			 {
				 LOGGER.debug("TRYING -- To upload NSKFDCSheet");
				 LOGGER.debug("Executing update query to save path for NSKFDCSheet");
			 updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateNSKFDCSheet(), updateParams);
	
				 if(updateStatus < 1)
				 {
					 LOGGER.debug("In IF -- When updating status of NSKFDCSheet is <1");
					 LOGGER.debug("Returning message 'File cannot be uplaoded'");
					 return "File cannot be uploaded";
				 }
			 }
			 catch(Exception e)
			 {
				 LOGGER.error("CATCHING -- Exception handled while updating NSKFDCSheet filepath");
				 LOGGER.error("In UploadDocumentsDao - updateDocumentPath");
				 LOGGER.error("Exception is :"+ e);
				 LOGGER.error("Returning message 'File cannot be uplaoded'");
				 return "File cannot be uploaded";
			 }
		 
			 						break;
		
		case "SDMSSheet" :
			
			 LOGGER.debug("Case - When document is SDMSSheet");
			 LOGGER.debug("Inserting batchId, userEmail & path in HashMap");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 try
			 {
				 LOGGER.debug("TRYING -- To upload SDMSSheet");
				 LOGGER.debug("Executing update query to save path for SDMSSheet");
				 updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateSDMSSheet(), updateParams);
	
				 if(updateStatus < 1)
				 {
					 LOGGER.debug("In IF -- When updating status of SDMSSheet is <1");
					 LOGGER.debug("Returning message 'File cannot be uplaoded'");
					 return "File cannot be uploaded";
				 }

			 }
			 catch(Exception e)
			 {
				 LOGGER.error("CATCHING -- Exception handled while updating SDMSSheet filepath");
				 LOGGER.error("In UploadDocumentsDao - updateDocumentPath");
				 LOGGER.error("Exception is :"+ e);
				 LOGGER.error("Returning message 'File cannot be uplaoded'");
				 return "File cannot be uploaded";
			 }	
			 			break;
			
		case "minutesOfSelectionCommitteeMeeting" :
			
			LOGGER.debug("Case - When document is selectionCommitteeMeeting");
			 LOGGER.debug("Inserting batchId, userEmail & path in HashMap");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 
			 try
			 {
				 LOGGER.debug("TRYING -- To upload selectionCommitteeMeeting");
				 LOGGER.debug("Executing update query to save path for selectionCommitteeMeeting");
				 updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateMinuteOfSelectionCommittee(), updateParams);
	
				 if(updateStatus < 1)
				 {
					 LOGGER.debug("In IF -- When updating status of selectionCommitteeMeeting is <1");
					 LOGGER.debug("Returning message 'File cannot be uplaoded'");
					 return "File cannot be uploaded";
				 }

			 }
			 catch(Exception e)
			 {
				 LOGGER.error("CATCHING -- Exception handled while updating selectionCommitteeMeeting filepath");
				 LOGGER.error("In UploadDocumentsDao - updateDocumentPath");
				 LOGGER.error("Exception is :"+ e);
				 LOGGER.error("Returning message 'File cannot be uplaoded'");
				 return "File cannot be uploaded";
			 }	
			 					break;
			 					
		case "finalBatchReport":
			
			
			LOGGER.debug("Case - When document is finalBatchReport");
			 LOGGER.debug("Inserting batchId, userEmail & path in HashMap");
			 updateParams.put("batchId", batchId);
			 updateParams.put("userEmail", userEmail);
			 updateParams.put("path",path);
			 
			 try
			 {
				 LOGGER.debug("TRYING -- To upload finalBatchReport");
				 LOGGER.debug("Executing update query to save path for finalBatchReport");
			 updateStatus = getJdbcTemplate().update(uploadDocumentsConfig.getUpdateFinalBatchReportPath(), updateParams);
	
				 if(updateStatus < 1)
				 {
					 LOGGER.debug("In IF -- When updating status of finalBatchReport is <1");
					 LOGGER.debug("Returning message 'File cannot be uplaoded'");
					 return "File cannot be uploaded";
				 }

			 }
			 catch(Exception e)
			 {
				 LOGGER.error("CATCHING -- Exception handled while updating finalBatchReport filepath");
				 LOGGER.error("In UploadDocumentsDao - updateDocumentPath");
				 LOGGER.error("Exception is :"+ e);
				 LOGGER.error("Returning message 'File cannot be uplaoded'");
				 return "File cannot be uploaded";
			 }	
 
		 }
		
		
		 return "File Uploaded Successfully";
	}
	
	/**
	 * Method to insert batch Id and user email in Uploaded documents
	 * @param recordToInsert
	 * @return
	 */
	public int insertInUploadedDocument(Map<String , Object> recordToInsert)
	{
		LOGGER.debug("Request Received from Service");
		LOGGER.debug("In DataImportCertificateDao - insertDataInUploadedDocument");
		LOGGER.debug("Parameters Received from Service are  - HashMap 'recordToInsert'");
				   	
		LOGGER.debug("Inserting data in UploadedDocument Table");
		LOGGER.debug("Executing SQL query and returning response");
        return getJdbcTemplate().update(uploadDocumentsConfig.getInsertIntoUploadDocument(), recordToInsert);	
	}
	
	
	/**
	 * Method to search uploaded documents based on batchId and userEmail
	 * @param batchId
	 * @param userEmail
	 * @return Collection of list of objects of Uploaded documents
	 */
	public Collection<UploadDocumentsDto> getSearchedDocument(String batchId, String userEmail) {
		
		LOGGER.debug("Request received from Service - UploadDocumentsDao ");
		LOGGER.debug("In getSearchedDocument, to get documents for searched BatchId of TP" + batchId);
		
				try {
					LOGGER.debug("Trying to get Documents for batchId " + batchId);
					LOGGER.debug("Creating hashmap of objects");
					Map<String,Object> parameters = new HashMap<>();
					LOGGER.debug("Inserting parameters - batchId, userEmail into the hashmap");
					parameters.put("batchId",batchId);  
					parameters.put("userEmail", userEmail);
					LOGGER.debug("Executing query to get searched batchId documents");
					return  getJdbcTemplate().query(uploadDocumentsConfig.getUploadDocumentsQuery(),parameters,uploadDocumentsRowMapper);

					
				}
				catch(Exception e) {
					 
					 LOGGER.error("Exception is :"+ e);
					 LOGGER.error("Returning null");
					return null;
				}
				
				
			}

	
/**
 * Row mappper for batchId from batch details table
 * @author Ruchi
 *
 */


	/**
	 * Method to check if entered batch number exists corresponding to batchId inserted by Training Partner
	 * @param batchId
	 * @param scgjBatchNumber
	 * @return
	 */
	public int scgjBatchIdField(String batchId,String scgjBatchNumber) {
		
		LOGGER.debug("Request received from service to UploadDocumentsDao");
		LOGGER.debug("In scgjBatchIdField - To check existence of entered batchId & scgjBatchNumber");
		try {
			
			LOGGER.debug("TRYING -- To check the existence of batchId & SCGJbatchNumber");
			LOGGER.debug("Creating hash map of objects");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting parameters - batchId & scgjBatchNumber into HashMap");
			parameters.put("batchId",batchId);
			parameters.put("scgjBatchNumber", scgjBatchNumber);
			
			LOGGER.debug("Executing query to check the existence of batchID against the batchNumber");
			return getJdbcTemplate().queryForObject(uploadDocumentsConfig.getShowScgjDetails(), parameters,Integer.class);
			
		}catch(Exception e) {
			
			LOGGER.error("CATCHING -- Exception handled while check the existence of batchID against the entered SCGJbatchNumber");
			LOGGER.error("In UploadDocumentsDao - scgjBatchIdField");
			LOGGER.error("Exception is :"+ e);
			LOGGER.error("Returning status code -35");
			return -35;
		}
	}	
	/**
	 * Method to check existence of BatchId
	 * @param batchId
	 * @return
	 */
	public int BatchIdField(String batchId) {
		
		LOGGER.debug("Request received from service to UploadDocumentsDao");
		LOGGER.debug("In BatchIdField - To check the existence of BatchId");
		
		try {
			
			LOGGER.debug("TRYING -- To check if entered batchId exists");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting batchId in HashMap");
			parameters.put("batchId",batchId);
			LOGGER.debug("Executing query to check existence of batchId in batch table");
			return getJdbcTemplate().queryForObject(uploadDocumentsConfig.getBatchidDetails(), parameters,Integer.class);
			
		}catch(Exception e) {
			
			LOGGER.error("CATCHING -- Exception handled while check the existence of entered batchID");
			LOGGER.error("In UploadDocumentsDao - scgjBatchIdField");
			LOGGER.error("Exception is :"+ e);
			LOGGER.error("Returning status code 0");
			return 0;
		}
	}	



	/**
	 * Row mapper for Uploaded documents table
	 * @author Ruchi
	 *
	 */
	static class UploadDocumentsRowMapper implements RowMapper<UploadDocumentsDto>{
		@Override
		public UploadDocumentsDto mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			  String batchId = rs.getString("batchId");
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
			  StringBuilder documentsUploaded = filesUploadedList(finalBatchReport,occupationCertificate,minuteOfSelectionCommittee,dataSheetForSDDMS,dataSheetForNSKFC,attendanceSheet); 

			  
			return new UploadDocumentsDto(batchId, dateUploaded,
					 finalBatchReport,  occupationCertificate,
					 minuteOfSelectionCommittee,  dataSheetForSDDMS,
					 dataSheetForNSKFC,  attendanceSheet,
					 finalBatchReportPath,  occupationCertificatePath,
					 minuteOfSelectionCommitteePath,
					 dataSheetForSDMSPath, dataSheetForNSKFCPath,
					 attendanceSheetPath, documentsUploaded);

				

		}
	}
	
	/**
	 * Method to return the list of files uploaded
	 * @param finalBatchReport
	 * @param occupationCertificate
	 * @param minuteOfSelectionCommittee
	 * @param dataSheetForSDDMS
	 * @param dataSheetForNSKFC
	 * @param attendanceSheet
	 * @return an Object of Stringbuilder which has comma separated list of uploaded files
	 */
	private static StringBuilder filesUploadedList(int finalBatchReport,int occupationCertificate, int minuteOfSelectionCommittee, int dataSheetForSDDMS,
			  int dataSheetForNSKFC, int attendanceSheet)
	{
		StringBuilder documentsUploaded = new StringBuilder("");
		 
		  LOGGER.debug("UploadDocumentsRowMapper VARIABLE DECLARATION");
		  LOGGER.debug("UploadDocumentsRowMapper for FinalBatchReport VARIABLE DECLARATION");
		  if(finalBatchReport==1){
			  documentsUploaded.append("Final Batch Report, ");
		  }
		  LOGGER.debug("In UploadDocumentsRowMapper occupationCertificate VARIABLE DECLARATION");
		  if(occupationCertificate==1){
		  
			  documentsUploaded.append("Occupation Certificate, ");
		  }
		  LOGGER.debug("In UploadDocumentsRowMapper MinutesOfSelectionCommittee VARIABLE DECLARATION");
		  if(minuteOfSelectionCommittee==1){
			  documentsUploaded.append("Signed Minute Of Selection Committee, ");
		  }
		  LOGGER.debug("In UploadDocumentsRowMapper DataSheetForSDDMS VARIABLE DECLARATION");
		  if(dataSheetForSDDMS==1){
			  documentsUploaded.append("Data Sheet For SDDMS, ");
		  }
		  LOGGER.debug("In UploadDocumentsRowMapper DataSheetForNSKFC VARIABLE DECLARATION");
		  if(dataSheetForNSKFC==1){
			  documentsUploaded.append("Data Sheet For NSKFC, ");
		  }
		  LOGGER.debug("In UploadDocumentsRowMapper AttendanceSheet VARIABLE DECLARATION");
		  if(attendanceSheet==1){
			  documentsUploaded.append("Attendance Sheet, ");
		  }
		  //To remove last space and comma
		  if(documentsUploaded!=null)
		  {
			  documentsUploaded.setLength(documentsUploaded.length() - 2);
		 }
		  return documentsUploaded;
	}

}
