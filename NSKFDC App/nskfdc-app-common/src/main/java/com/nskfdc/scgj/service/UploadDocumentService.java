package com.nskfdc.scgj.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nskfdc.scgj.common.FileUtility;
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
	private FileUtility fileUtility;
	
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
	

	/**
	 * Method to find out searched documents
	 * @param batchId
	 * @param userEmail
	 * @return
	 */
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
		
		

		/**
		 * Method to find batch number for a batchId
		 * @param batchId
		 * @param scgjBatchId
		 * @return
		 */
		public int scgjBatchIdField(String batchId, String scgjBatchId) {
			
			LOGGER.debug("Request received from controller to UploadDocumentService");
			LOGGER.debug("In scgjBatchIdField - To check existence of batchId & scgjBatchNumber for batch id :" +batchId+ " scgjBatchNumber" + scgjBatchId);
			
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
		/**
		 * 
		 * @param batchId
		 * @return
		 */
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
		
		/**
		 * Method to create zip file on a location
		 * @param batchId
		 * @param userEmail
		 * @return path of zip file created
		 */
		public String createZipFile (String batchId, String userEmail)
		{
			String zipPath = null;
			Collection<UploadDocumentsDto> searchResult = uploadDocumentsDao.getSearchedDocument(batchId, userEmail);
			Collection<String> filesForZip = new ArrayList<String>();
			if (searchResult.size() > 1)
			{
				LOGGER.error("Can not download file, There are more than one search results for batch Id : "+batchId+ " and user email : " +userEmail );
			}
			else
			{
				for(UploadDocumentsDto item : searchResult)
				{
					 if(item.getFinalBatchReportPath()!=null){
						  LOGGER.debug("Adding path of Final Batch report to the lists of files to be zipped");
						  filesForZip.add(item.getFinalBatchReportPath());
					  }
					  if(item.getOccupationCertificatePath()!=null){
						  LOGGER.debug("Adding path of Occupation Certificate to the lists of files to be zipped");
						  filesForZip.add(item.getOccupationCertificatePath());
					  }
					  if(item.getMinuteOfSelectionCommitteePath()!=null){
						  LOGGER.debug("Adding path of minutes of selection committee to the lists of files to be zipped");
						  filesForZip.add(item.getMinuteOfSelectionCommitteePath());
					  }
					  if(item.getDataSheetForSDMSPath()!=null){
						  LOGGER.debug("Adding path of Data sheet for SDMS to the lists of files to be zipped");
						  filesForZip.add(item.getDataSheetForSDMSPath());
					  }
					  if(item.getDataSheetForNSKFCPath()!=null){
						  LOGGER.debug("Adding path of Data sheet for NSKFDC to the lists of files to be zipped");
						  filesForZip.add(item.getDataSheetForNSKFCPath());
					  }
					  if(item.getAttendanceSheetPath()!=null){
						  LOGGER.debug("Adding path of attendance sheet to the lists of files to be zipped");
						  filesForZip.add(item.getAttendanceSheetPath());
					  }
					
					
				}
			}
			String zipLocationRead = readApplicationConstants.getSaveTempZip();  
			zipPath = fileUtility.createZip(filesForZip,batchId,zipLocationRead);
			return zipPath;
		}
		
		/**
		 * Method to save uploaded file
		 * @param batchId
		 * @param file
		 * @param pathToFolder
		 * @param userEmail
		 * @param fileType
		 * @return path of File saved
		 * @throws IOException
		 */
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
				String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				LOGGER.debug("The extension of file is : " + extension);
				String fileName = fileType+separator+ batchId + "." + extension;
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