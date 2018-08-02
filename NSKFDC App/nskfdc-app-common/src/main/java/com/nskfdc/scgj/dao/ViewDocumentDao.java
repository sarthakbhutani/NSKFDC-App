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
import com.nskfdc.scgj.config.ViewDocumentConfig;
import com.nskfdc.scgj.dto.UploadDocumentsDto;
import com.nskfdc.scgj.dto.ViewDocumentDto;

@Repository
public class ViewDocumentDao extends AbstractTransactionalDao {

private static final Logger LOGGER= LoggerFactory.getLogger(ViewDocumentDao.class);
	
	private static final ViewDocumentRowmapper viewDocument_RowMapper = new ViewDocumentRowmapper();
	private static final ViewDocumentSCGJNumberRowmapper viewDocumentSCGJNumber_RowMapper = new ViewDocumentSCGJNumberRowmapper();
	
	@Autowired
	private ViewDocumentConfig viewDocumentConfig;
		
	public Collection<ViewDocumentDto> getViewTrainingPartnerDetailForBatchId(String tpName,  String batchId){

		LOGGER.debug("Request received from Service to ViewDocumentsDao");
		LOGGER.debug("In getViewTrainingPartnerDetailForBatchId");
		LOGGER.debug("To get Document details of TP for entered BatchId");
	
	
	try {
			LOGGER.debug("TRYING -- To get the TP Document Details for entered BatchId");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting the trainingPartnerName & scgjBatchNumber in parameters");
			parameters.put("trainingPartnerName",tpName); 
			parameters.put("batchId",batchId);
			LOGGER.debug("Execute query to get training partner documents for Search");
			LOGGER.debug("For enterd BatchId" + batchId);
			return getJdbcTemplate().query(viewDocumentConfig.getShowTrainingPartnerDetailsForDownloadusingBatchId(),parameters, viewDocument_RowMapper);
			
		} catch (Exception e) {
			
			LOGGER.error("CATCHING -- Exception handled while getting the TP documents for entered batchId ");
			LOGGER.error("In ViewDocumentDao - getViewTrainingPartnerDetailForBatchId");
			LOGGER.error("Exception is "+ e);
			LOGGER.error("Returning null");
			return null;
			
		}
		
	}
	
	public Collection< ViewDocumentDto> getViewTrainingPartnerDetailForSearchscgjBtNumber(String tpName, String scgjBtNumber){

			LOGGER.debug("Request received from Service to ViewDocumentsDao");
			LOGGER.debug("In getViewTrainingPartnerDetailForSearchscgjBtNumber");
			LOGGER.debug("To get Document details of TP for entered SCGJBatchNumber");
		
		
		try {
			LOGGER.debug("TRYING -- To get the TP Document Details for entered SCGJBatchNumber");
			Map<String, Object> parameters = new HashMap<>();
			LOGGER.debug("Inserting the trainingPartnerName & scgjBatchNumber in parameters");
			parameters.put("trainingPartnerName",tpName); 
			parameters.put("scgjBatchNumber",scgjBtNumber);
			LOGGER.debug("Execute query to get training partner documents for Search");
			LOGGER.debug("For enterd SCGJBatchNumber"+scgjBtNumber);
			return getJdbcTemplate().query(viewDocumentConfig.getShowTrainingPartnerDetailsForDownloadusingBatchNumber(),parameters, viewDocumentSCGJNumber_RowMapper);
			
		} catch (Exception e) {
			
			LOGGER.error("CATCHING -- Exception handled while getting the TP documents for entered SCGJBatchNumber ");
			LOGGER.error("In ViewDocumentDao - getViewTrainingPartnerDetailForSearchscgjBtNumber");
			LOGGER.error("Exception is "+ e);
			LOGGER.error("Returning null");
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
            			
            		
            			LOGGER.debug("In ViewDocumentRowmapper");
          			  
          			  if(s2!=null){
          				  LOGGER.debug("In IF -- When STRING NOT NULL");
          				  s2.setLength(0);
          			  }else{
          				  LOGGER.debug("In ELSE -- When STRING NULL");
          			  }
          			
          			  //MANIPULATION FOR FILE 
          			  LOGGER.debug("ViewDocumentRowmapper VARIABLE DECLARATION");
          			 LOGGER.debug("ViewDocumentRowmapper for FinalBatchReport VARIABLE DECLARATION");
          			  if(finalBatchReport==1){
          				  s2.append("Final Batch Report, ");
          			  }
          			  LOGGER.debug("In ViewDocumentRowmapper occupationCertificate VARIABLE DECLARATION");
          			  if(occupationCertificate==1){
          				  s2.append("Occupation Certificate, ");
          			  }
          			  LOGGER.debug("In ViewDocumentRowmapper MinutesOfSelectionCommittee VARIABLE DECLARATION");
          			  if(minuteOfSelectionCommittee==1){
          				  s2.append("Signed Minute Of Selection Committee, ");
          			  }
          			  LOGGER.debug("In ViewDocumentRowmapper DataSheetForSDDMS VARIABLE DECLARATION");
          			  if(dataSheetForSDDMS==1){
          				  s2.append("Data Sheet For SDDMS, ");
          			  }
          			  LOGGER.debug("In ViewDocumentRowmapper DataSheetForNSKFC VARIABLE DECLARATION");
          			  if(dataSheetForNSKFC==1){
          				  s2.append("Data Sheet For NSKFC, ");
          			  }
          			  LOGGER.debug("In ViewDocumentRowmapper AttendanceSheet VARIABLE DECLARATION");
          			  if(attendanceSheet==1){
          				  s2.append("Attendance Sheet, ");
          			  }
          			  
          			  if(s2!=null){
          			  s2.setLength(s2.length() - 2);
          			  }
          			  ArrayList<String> files = new ArrayList<String>();
          				

          			  LOGGER.debug("Condition to store File Paths");
          			  if(finalBatchReportPath!=null){
          				  LOGGER.debug("In IF -- When Final Batch Report Path is not NULL");
          				  files.add(finalBatchReportPath);
          			  }
          			  if(occupatioCertificatePath!=null){
          				  LOGGER.debug("In IF -- When occupation Certificate Path is not NULL");
          				  files.add(occupatioCertificatePath);
          			  }
          			  if(minuteOfSelectionCommitteePath!=null){
          				  LOGGER.debug("In IF -- When minute Of Selection Committee Path is not NULL");
          				  files.add(minuteOfSelectionCommitteePath);
          			  }
          			  if(dataSheetForSDMSPath!=null){
          				LOGGER.debug("In IF -- When dataSheetForSDMSPath is not NULL");
          				  files.add(dataSheetForSDMSPath);
          			  }
          			  if(dataSheetForNSKFCPath!=null){
          				  LOGGER.debug("In IF -- When dataSheetForNSKFCPath is not NULL");
          				  files.add(dataSheetForNSKFCPath);
          			  }
          			  if(attendanceSheetPath!=null){
          				  LOGGER.debug("In IF -- When attendanceSheetPath is not NULL");
          				  files.add(attendanceSheetPath);
          			  }
          			  
          				
          			  String zipFileLink = " ";

          			  if(s2!=null){
          				  LOGGER.debug("In IF -- When STRING Is NOT NULL");
          			  }else{
          				  LOGGER.debug("In ELSE -- When STRING is NULL");
          			  }
          			  LOGGER.debug("Getting Local Working Directory");
          			  String zipLocationRead = System.getProperty("user.dir");  
       
          			  LOGGER.debug("The current working directory is " + zipLocationRead);
          			  if(s2!=null){
          				  LOGGER.debug("In IF -- When Paths of Files is Not NULL");
          				  LOGGER.debug("Creating Folder at current Working Directory");
          				  File folder = new File(zipLocationRead);
	          				 
	          				  if(!folder.exists()){
	          					  LOGGER.debug("In IF -- When Folder does not exists");
          					  if(folder.mkdirs() || folder.canWrite())
          					  {
          						LOGGER.debug("In IF -- When Folder can be made directory & Folder can be updated");
          						LOGGER.debug("FOLDER CREATED TO SAVE THE ZIP FILE");
          						zipFileLink = zipLocationRead +"/" + batchId + ".zip";
          						LOGGER.debug("Zipped file location" + zipFileLink);
          						FileOutputStream fileOutputStream = null;
      					        ZipOutputStream zipOut = null;
      					        FileInputStream fileInputStream = null;
      					        try {
          					        	LOGGER.debug("TRYING -- To create Zip file");
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
          					            LOGGER.debug("Zipped the files successfully");   					            
          					        } catch (FileNotFoundException e) {
          					        	LOGGER.error("CATCHING -- Exception handled  while Zipping the files");
          					        	LOGGER.error("In ViewDocumentDao - ViewDocumentRowmapper");
          					            LOGGER.error("File not found exception :" +e);
          					        } catch (IOException e) {
          					        	LOGGER.error("CATCHING -- IOException handled  while Zipping the files");
          					        	LOGGER.error("In ViewDocumentDao - ViewDocumentRowmapper");
          					            LOGGER.error(" Exception is :" +e);
          					        } finally{
          					            try{
          					            	LOGGER.debug("TRYING -- Closing the fileOutputStream");
          					                if(fileOutputStream != null) fileOutputStream.close();
          					            } catch(Exception ex){
          					            	LOGGER.error("CATCHING -- Exception handled  while Closing the fileOutputStream");
              					        	LOGGER.error("In ViewDocumentDao - ViewDocumentRowmapper");
              					            LOGGER.error("Exception is :" +ex);
          					            }
          					        }
          					  }
          					  else{
          						  LOGGER.debug("In ELSE -- When Folder cannot be made directory & Folder cannot be updated");
          						  LOGGER.debug("FAILED TO WRITE THE FOLDER at location: " + zipLocationRead);
          					  }
          					  
          				  }else{
          					  LOGGER.debug("In ELSE -- When Folder exists");
          					  zipFileLink = zipLocationRead +"/" + batchId + ".zip";
          					  LOGGER.debug("Location of Zipped File is :"+zipFileLink);
          					  FileOutputStream fileOutputStream = null;
          				      ZipOutputStream zipOut = null;
          				      FileInputStream fileInputStream = null;
          				      try {
          				        	LOGGER.debug("TRYING -- To create a ZIP File");
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
          				          LOGGER.debug("Zipped the files successfully");   					            
          				        } catch (FileNotFoundException e) {
          				        	LOGGER.error("CATCHING -- Exception handled  while Zipping the files");
      					        	LOGGER.error("In ViewDocumentDao - ViewDocumentRowmapper");
      					            LOGGER.error("File not found exception :" +e);
          				        } catch (IOException e) {
          				        	LOGGER.error("CATCHING -- IOException handled  while Zipping the files");
      					        	LOGGER.error("In ViewDocumentDao - ViewDocumentRowmapper");
      					            LOGGER.error(" Exception is :" +e);
          				        } finally{
          				            try{
          				            	LOGGER.debug("TRYING -- Closing the fileOutputStream");
          				                if(fileOutputStream != null) fileOutputStream.close();
          				            } catch(Exception ex){
          				            	LOGGER.error("CATCHING -- Exception handled  while Closing the fileOutputStream");
          					        	LOGGER.error("In ViewDocumentDao - ViewDocumentRowmapper");
          					            LOGGER.error("Exception is :" +ex);  
          				            }
          				        }
          				  }
          				  
          			
          			return new ViewDocumentDto(batchId,trainingPartnerName,uplodedOn,zipFileLink);

          				}
          			  else{
          				  LOGGER.debug("When no file path is Appended - ViewDocumentRowmapper");
          				  LOGGER.debug("Returning NULL");
          				  return null;
          			  }
          		}
          	}
            	
            	
            	
            	private static class ViewDocumentSCGJNumberRowmapper implements RowMapper<ViewDocumentDto> {
            		@Override
            		public ViewDocumentDto mapRow(ResultSet rs, int rownum)throws SQLException {
            			
            			Integer batchId = rs.getInt("batchId");
            			String scgjBatchNumber= rs.getString("scgjBatchNumber");
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
            			
            			LOGGER.debug("In ViewDocumentSCGJNumberRowmapper");	  
          			  
          			  if(s2!=null){
          				  LOGGER.debug("In IF -- When STRING to store filesPath variables NOT NULL");
          				  LOGGER.debug("Set String length to 0");
          				  s2.setLength(0);
          			  }else{
          				LOGGER.debug("In ELSE -- When STRING is NULL");
          			  }
          			  
          			//MANIPULATION FOR FILE 
          			  LOGGER.debug("In ViewDocumentSCGJNumberRowmapper VARIABLE DECLARATION");
          			  LOGGER.debug("ViewDocumentSCGJNumberRowmapper for FinalBatchReport VARIABLE DECLARATION");
          			  if(finalBatchReport==1){
          				  s2.append("Final Batch Report, ");
          			  }
          			  LOGGER.debug("In ViewDocumentSCGJNumberRowmapper for occupationCertificate VARIABLE DECLARATION");
          			  if(occupationCertificate==1){
          				  s2.append("Occupation Certificate, ");
          			  }
          			  LOGGER.debug("In ViewDocumentSCGJNumberRowmapper MinutesOfSelectionCommittee VARIABLE DECLARATION");
          			  if(minuteOfSelectionCommittee==1){
          				  s2.append("Signed Minute Of Selection Committee, ");
          			  }
          			LOGGER.debug("In ViewDocumentSCGJNumberRowmapper DataSheetForSDDMS VARIABLE DECLARATION");
          			  if(dataSheetForSDDMS==1){
          				  s2.append("Data Sheet For SDDMS, ");
          			  }
          			LOGGER.debug("In ViewDocumentSCGJNumberRowmapper DataSheetForNSKFC VARIABLE DECLARATION");
          			  if(dataSheetForNSKFC==1){
          				  s2.append("Data Sheet For NSKFC, ");
          			  }
          			LOGGER.debug("In ViewDocumentSCGJNumberRowmapper AttendanceSheet VARIABLE DECLARATION");
          			  if(attendanceSheet==1){
          				  s2.append("Attendance Sheet, ");
          			  }
          			  
          			  if(s2!=null){
          			LOGGER.debug("In IF -- When STRING NOT NULL");
          			  s2.setLength(s2.length() - 2);
          			  }
          			  ArrayList<String> files = new ArrayList<String>();
          				

          			LOGGER.debug("Condition to store File Paths");
          			  if(finalBatchReportPath!=null){
          				LOGGER.debug("In IF -- When Final Batch Report Path is not NULL");
          				  files.add(finalBatchReportPath);
          			  }
          			  if(occupatioCertificatePath!=null){
          				LOGGER.debug("In IF -- When occupation Certificate Path is not NULL");
          				  files.add(occupatioCertificatePath);
          			  }
          			  if(minuteOfSelectionCommitteePath!=null){
          				LOGGER.debug("In IF -- When minute Of Selection Committee Path is not NULL");
          				  files.add(minuteOfSelectionCommitteePath);
          			  }
          			  if(dataSheetForSDMSPath!=null){
          				LOGGER.debug("In IF -- When dataSheetForSDMSPath is not NULL");
          				  files.add(dataSheetForSDMSPath);
          			  }
          			  if(dataSheetForNSKFCPath!=null){
          				  LOGGER.debug("In IF -- When dataSheetForNSKFCPath is not NULL");
          				  files.add(dataSheetForNSKFCPath);
          			  }
          			  if(attendanceSheetPath!=null){
          				LOGGER.debug("In IF -- When attendanceSheetPath is not NULL");
          				  files.add(attendanceSheetPath);
          			  }
          			  
          				
          			  String zipFileLink = " ";

          			  if(s2!=null){
          				LOGGER.debug("In IF -- When STRING Is NOT NULL");
          			  }else{
         				LOGGER.debug("In ELSE -- When STRING is NULL");
          			  }
          			  
          			  LOGGER.debug("Getting Local Working Directory");
          			  String zipLocationRead = System.getProperty("user.dir");  //getting working directory
       
          			  LOGGER.debug("The current working directory is " + zipLocationRead);
          			  if(s2!=null){
          				 LOGGER.debug("In IF -- When Paths of Files is Not NULL");
         				  LOGGER.debug("Creating Folder at current Working Directory");
          				  File folder = new File(zipLocationRead);
          				  if(!folder.exists()){
          					LOGGER.debug("In IF -- When Folder does not exist");
          					  if(folder.mkdirs() || folder.canWrite())
          					  {
          						LOGGER.debug("In IF -- When Folder can be made directory & Folder can be updated");
          						  LOGGER.debug("FOLDER CREATED TO SAVE THE ZIP FILE");
          						  zipFileLink = zipLocationRead +"/" + scgjBatchNumber + ".zip";
          						LOGGER.debug("Zipped file location" + zipFileLink);
          						  FileOutputStream fileOutputStream = null;
          					        ZipOutputStream zipOut = null;
          					        FileInputStream fileInputStream = null;
          					        try {
          					        	LOGGER.debug("TRYING -- To create Zip file");
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
          					          LOGGER.debug("Zipped the files successfully");   					              					            
          					        } catch (FileNotFoundException e) {
          					        	LOGGER.error("CATCHING -- Exception handled  while Zipping the files");
          					        	LOGGER.error("In ViewDocumentDao - ViewDocumentSCGJNumberRowmapper");
          					            LOGGER.error("File not found exception :" +e);
          					        } catch (IOException e) {
          					        	LOGGER.error("CATCHING -- IOException handled  while Zipping the files");
          					        	LOGGER.error("In ViewDocumentDao - ViewDocumentSCGJNumberRowmapper");
          					            LOGGER.error(" Exception is :" +e);
          					        } finally{
          					            try{
          					            	LOGGER.debug("TRYING -- Closing the fileOutputStream");
          					                if(fileOutputStream != null) fileOutputStream.close();
          					            } catch(Exception ex){
          					            	LOGGER.error("CATCHING -- Exception handled  while Closing the fileOutputStream");
              					        	LOGGER.error("In ViewDocumentDao - ViewDocumentSCGJNumberRowmapper");
              					            LOGGER.error("Exception is :" +ex);
          					            }
          					        }
          					  }
          					  else{
          						 LOGGER.debug("In ELSE -- When Folder cannot be made directory & Folder cannot be updated");
         						  LOGGER.debug("FAILED TO WRITE THE FOLDER at location: " + zipLocationRead);
          					  }
          					  
          				  }else{
          					  LOGGER.debug("In ELSE -- When Folder exist");
          					  zipFileLink = zipLocationRead +"/" + scgjBatchNumber + ".zip";
          					  FileOutputStream fileOutputStream = null;
          				        ZipOutputStream zipOut = null;
          				        FileInputStream fileInputStream = null;
          				        try {
          				        	LOGGER.debug("TRYING -- To create a ZIP File");
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
          				          LOGGER.debug("Zipped the files successfully");   					            
          				        } catch (FileNotFoundException e) {
          				        	LOGGER.error("CATCHING -- FileNotFoundException handled while zipping the files");
          				        	LOGGER.error("In ViewDocumentsDao - ViewDocumentSCGJNumberRowmapper");
          				        	LOGGER.error("File not found exception :" +e);
          				        } catch (IOException e) {
          				            LOGGER.error("CATCHING -- IOException handled while zipping the files");
          				          LOGGER.error("In ViewDocumentsDao - ViewDocumentSCGJNumberRowmapper");
    				                LOGGER.error("Exception is "+e);
          				        } finally{
          				            try{
          				            	LOGGER.debug("TRYING -- Closing the fileOutputStream");
          				            	if(fileOutputStream != null) fileOutputStream.close();
          				            } catch(Exception ex){
          				                LOGGER.error("CATCHING -- Exception while closing the FileOutputStream"); 
          				                LOGGER.error("In ViewDocumentsDao - ViewDocumentSCGJNumberRowmapper");
          				                LOGGER.error("Exception is "+ex);
          				            }
          				        }
          				  }
          				  
          			
          			return new ViewDocumentDto(batchId,trainingPartnerName,uplodedOn,zipFileLink);
          				}
          			  else{
          				  LOGGER.debug("In ELSE -- When no File is zipped");
          				  LOGGER.debug("s2 is empty");
          				  LOGGER.debug("Returning NULL");
          				  return null;
          			  }
          		}
          	}
          }
