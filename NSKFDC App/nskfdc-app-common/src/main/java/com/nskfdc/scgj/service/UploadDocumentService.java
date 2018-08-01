package com.nskfdc.scgj.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nskfdc.scgj.common.ReadApplicationConstants;
import com.nskfdc.scgj.dao.UploadDocumentsDao;
import com.nskfdc.scgj.dto.BatchDto;
import com.nskfdc.scgj.dto.UploadDocumentsDto;

@Service
public class UploadDocumentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadDocumentService.class);

	@Autowired
	private UploadDocumentsDao uploadDocumentsDao;
	
	@Autowired
	private ReadApplicationConstants readApplicationConstants;
	
	public String uploadFileService(MultipartFile file, String batchId,String fileType,String userEmail)
	{		
		String filePath = "";
		
		try
		{
			filePath = saveFile(batchId,file,readApplicationConstants.getSaveDocumentsAtLocation());
			LOGGER.debug("The path of file is : " + filePath);
			return uploadDocumentsDao.uploadDocuments(batchId, userEmail, fileType, filePath);

		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while saving file : " + e);	
			return "File cannot be uploaded";
		}
		
		
	
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
					
					LOGGER.debug("In try block to check the existence of batchID against the batchNumber");
				    return uploadDocumentsDao.scgjBatchIdField(batchId, scgjBatchId);

				}
				catch(Exception e)
				{
					LOGGER.error("An exception occured while checking the existence of batchID against the batchNumber :" + e);
				    return  - 25;	
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
		
		
		private String saveFile(String batchId, MultipartFile file, String pathToFolder) throws IOException
		{
			String pathOfUploadedFile = "";
			String pathTillBatchId="";
			int folderCreated = 0;
			
			if(!file.isEmpty())
			{
				
				String fileName = file.getOriginalFilename();
				pathTillBatchId = pathToFolder + batchId + "//";
				
				File folder = new File(pathTillBatchId);
				
				if(!folder.exists())
				{
					if(folder.mkdirs() || folder.canWrite())
					{
						folderCreated = 1;
						LOGGER.debug("Folder Created");
						LOGGER.debug("File name is : " + fileName);
					}
					else
					{
						folderCreated = -1;
						LOGGER.debug("Could not create or update the given directory");
					}
				}
					
				    byte[] bytes = file.getBytes();
					String fileNameToBeSaved = fileName;	
			        Path path = Paths.get(pathTillBatchId + fileNameToBeSaved);
			        LOGGER.debug("The path is : " + path);
			        
			        pathOfUploadedFile = path.toAbsolutePath().toString();
					LOGGER.debug("Path to absolute file is : " + pathOfUploadedFile);
					
					Files.write(path, bytes);
				
				
				
			}
			return pathOfUploadedFile;
			
		}
		
	

}