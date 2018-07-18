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
import com.nskfdc.scgj.dto.BatchIdDto;
//import com.nskfdc.scgj.common.POCZipMultipleFile;
//import com.nskfdc.scgj.config.UploadDocumentsConfig;
import com.nskfdc.scgj.dto.UploadDocumentsDto;

@Repository	
public class UploadDocumentsDao extends AbstractTransactionalDao{
	static final Logger LOGGER= LoggerFactory.getLogger(UploadDocumentsDao.class);   
	private static final UploadDocumentsRowMapper uploadDocumentsRowMapper = new UploadDocumentsRowMapper();
	private static final BatchIdRowmapper BatchId_RowMapper = new BatchIdRowmapper();
	
	static StringBuilder s2 = new StringBuilder("  ");
	
	@Autowired
	private UploadDocumentConfig uploadDocumentsConfig;
	
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
	public Collection<BatchIdDto> getBatchDetail(){
		LOGGER.debug("Request received from UploadService");
		LOGGER.debug("In UploadDocumentsDao");
		
try {
			
	LOGGER.debug("In try block of upload documents dao");
			return getJdbcTemplate().query(uploadDocumentsConfig.getShowBatchIdDetails(), BatchId_RowMapper);
			
		} catch (Exception e) {
			
			LOGGER.debug("In Catch Block");
			LOGGER.debug("An error occured in upload documents dao" + e);
			return null;
			
		}
	}
private static class BatchIdRowmapper implements RowMapper<BatchIdDto>{
		
		@Override
		public BatchIdDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String batchId = rs.getString("batchId");
						
			
			return new BatchIdDto(batchId);
			
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
			  LOGGER.debug("IN DAO, checking for values"+batchId);
			  //wat
			  
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
		//		POCZipMultipleFile mfe = new POCZipMultipleFile();
				ArrayList<String> files = new ArrayList<String>();

				//condition to handle file paths::
			  if(finalBatchReportPath!=null){
//				  files.add(finalBatchReportPath);
			  }
			  if(occupationCertificatePath!=null){
//				  files.add(occupationCertificatePath);
			  }
			  if(minuteOfSelectionCommitteePath!=null){
//				  files.add(minuteOfSelectionCommitteePath);
			  }
			  if(dataSheetForSDMSPath!=null){ //change to not equal to::CONDITION
				  files.add("D:/sarthak/testZIp/test1.txt");  //prototype
				  //files.add(dataSheetForSDMSPath);
			  }
			  if(dataSheetForNSKFCPath!=null){
//				  files.add(dataSheetForNSKFCPath);
			  }
			  if(attendanceSheetPath!=null){
//				  files.add(attendanceSheetPath);
			  }
			  LOGGER.debug("In try block  BEFORE ZIP data for Search Document Functionality");
				
			 // file path send ::implement later
			  
			  //ZIP FILE!!!::
			  String zipFileLink = "D:/sarthak/testZIp/test1.zip";
			  FileOutputStream fos = null;
		        ZipOutputStream zipOut = null;
		        FileInputStream fis = null;
		        try {
		        	LOGGER.debug("In try block of ZipFIleCreation");
		            fos = new FileOutputStream("D:/sarthak/testZIp/test1.zip");
		            zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
		            for(String filePath:files){
		                File input = new File(filePath);
		                fis = new FileInputStream(input);
		                ZipEntry ze = new ZipEntry(input.getName());
		                System.out.println("Zipping the file: "+input.getName());
		                zipOut.putNextEntry(ze);
		                byte[] tmp = new byte[4*1024];
		                int size = 0;
		                while((size = fis.read(tmp)) != -1){
		                    zipOut.write(tmp, 0, size);
		                }
		                zipOut.flush();
		                fis.close();
		            }
		            zipOut.close();
		            System.out.println("Done... Zipped the files...");
		        } catch (FileNotFoundException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } finally{
		            try{
		                if(fos != null) fos.close();
		            } catch(Exception ex){
		                 
		            }
		        }
			return new UploadDocumentsDto(batchId,dateUploaded,s2,zipFileLink);
//			  UploadDocumentsDto abc = new UploadDocumentsDto(batchId,dateUploaded,s2,zipFileLink);
//			destroy string s2
//			  s2.setLength(0);
//			  LOGGER.debug("s2="+s2);
//			  return abc;
				}
	}
}
