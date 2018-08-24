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
	
	/**
	 * Method to save file and update paths in database
	 * @param file
	 * @param batchId
	 * @param fileType
	 * @param userEmail
	 * @return messages
	 */
	public String uploadFileService(MultipartFile file, String batchId, String fileType,String userEmail)
	{	
		LOGGER.debug("Request received from Controller to UploadDocumentService");
		LOGGER.debug("In method - uploadFileService");
		LOGGER.debug("To upload the documents");
		
		try
		{
			LOGGER.debug("TRYING -- to upload the files");
			LOGGER.debug("Saving the files at a physical location");
			String filePath = saveFile(batchId,file,readApplicationConstants.getSaveDocumentsAtLocation(), userEmail,fileType);
			LOGGER.debug("File saved at location : " + filePath);
			LOGGER.debug("Sending request to UploadDocumentsDao - uploadDocuments");
			return uploadDocumentsDao.uploadDocuments(batchId, userEmail, fileType, filePath);

		}
		catch(Exception e)
		{
			LOGGER.error("CATCHING -- Exception handled while saving the file in the System");
			LOGGER.error("In UploadDocumentService - uploadFileService" + e);	
			LOGGER.error("Returning string 'File cannot be uploaded'");
			return "File cannot be uploaded";
		}
		
	
	}
	

		public Collection<UploadDocumentsDto> getSearchedDocument(String batchId, String userEmail){
		
		LOGGER.debug("Request received from Controller");
		LOGGER.debug("In UploadDocumentService - getSearchedDocumentService");
		LOGGER.debug("To get the documents against batchId & userEmail");
			
			try {
				LOGGER.debug("TRYING -- getSearchedDocument - To get documents for searched BatchId of TP");
				LOGGER.debug("Sending request to uploadDocumentsDao - getSearchedDocument");
				return uploadDocumentsDao.getSearchedDocument(batchId, userEmail);
				
			} catch (Exception e) {
				
				LOGGER.error("CATCHING -- Exception handled while getting documents uploaded against entered batchId of TP");
				LOGGER.error("In UploadDocumentService - getSearchedDocument");
				LOGGER.error("Exception is "+ e);;
				LOGGER.error("Returning NULL");
				return null;
				
			}
		}	
		

		public int scgjBatchIdField(String batchId, String scgjBatchId) {
			
			LOGGER.debug("Request received from controller to UploadDocumentService");
			LOGGER.debug("In scgjBatchIdField - To check existence of batchId & scgjBatchNumber");
			
				try {	
					
					LOGGER.debug("TRYING -- To check the existence of batchId & batchNumber");
					LOGGER.debug("Sending request to uploadDocumentsDao - scgjBatchIdField ");
				    return uploadDocumentsDao.scgjBatchIdField(batchId, scgjBatchId);

				}
				catch(Exception e)
				{
					LOGGER.error("CATCHING -- Exception handled while checking the Existence of batchId & scgjBatchNumber");
					LOGGER.error("In UploadDocumentService - scgjBatchIdField");
					LOGGER.error("Exception is "+ e);
					LOGGER.error("Returning status code -25");		
				    return  - 25;	
				}
		}
		
		public int BatchIdField(String batchId) {
			
			LOGGER.debug("Request received from controller");
			LOGGER.debug("In UploadDocumentService - BatchIdField");
			
				try {
					LOGGER.debug("TRYING -- To check the existence of entered BatchId");
					LOGGER.debug("Sending request to uploadDocumentsDao - BatchIdField");
					return uploadDocumentsDao.BatchIdField(batchId);			         
				}
				catch(Exception e){
					LOGGER.error("CATCHING -- Exception handled while checking the Existence of batchId");
					LOGGER.error("In UploadDocumentService - BatchIdField");
					LOGGER.error("Exception is "+ e);
					LOGGER.error("Returning status code 0");
					return 0;	
				}
		}
		
		
		private String saveFile(String batchId, MultipartFile file, String pathToFolder, String userEmail,String fileType) throws IOException
		{
			LOGGER.debug("Request received to save File");
			LOGGER.debug("In UploadDocumentService - saveFile");
			String pathOfUploadedFile = "";
			String pathTillBatchId="";
			int folderCreated = 0;
			
			if(!file.isEmpty())
			{
				LOGGER.debug("File is not empty");
				String separator = "-";
				String fileName = fileType+ file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
				pathTillBatchId = pathToFolder + userEmail + "//" +batchId + "//";
				
				LOGGER.debug("Creating a new Folder at"+ pathTillBatchId);
				LOGGER.debug("File name to be saved is "+ fileName);
				File folder = new File(pathTillBatchId);
				
				if(!folder.exists())
				{
					LOGGER.debug("Folder already exists");
					if(folder.mkdirs() || folder.canWrite())
					{
						LOGGER.debug("In IF -- When folder can be a directory OR folder has the write permissions");
						folderCreated = 1;
						LOGGER.debug("Folder Created");
						LOGGER.debug("File name is : " + fileName);
					}
					else
					{
						LOGGER.debug("In ELSE -- When folder cannot be made as directory or cannot be updated");
						folderCreated = -1;
						LOGGER.debug("Could not create or update the given directory");
					}
				}
					
				    byte[] bytes = file.getBytes();
					String fileNameToBeSaved = fileName;	
			        Path path = Paths.get(pathTillBatchId + fileNameToBeSaved);
			        LOGGER.debug("The path of the file : " + path);
			        
			        pathOfUploadedFile = path.toAbsolutePath().toString();
					LOGGER.debug("Path to absolute file is : " + pathOfUploadedFile);
					
					Files.write(path, bytes);
				
				
				
			}
			LOGGER.debug("Returning path of File");
			return pathOfUploadedFile;
			
		}
		
	

}