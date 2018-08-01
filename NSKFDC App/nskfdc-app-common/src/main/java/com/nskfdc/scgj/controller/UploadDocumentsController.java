package com.nskfdc.scgj.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nskfdc.scgj.common.SessionUserUtility;
import com.nskfdc.scgj.dto.BatchDto;
import com.nskfdc.scgj.dto.UploadDocumentsDto;
import com.nskfdc.scgj.service.UploadDocumentService;

@RestController
public class UploadDocumentsController {
	private static final Logger LOGGER= LoggerFactory.getLogger(UploadDocumentsController.class);

	@Autowired
	private UploadDocumentService uploadDocumentService;
	
	@Autowired
	private SessionUserUtility sessionUserUtility;
	
	int status = -25;

	
	/**
	 * 
	 * @param batchId
	 * @param scgjBatchNumber
	 * @param file
	 * @param fileType
	 * @Description this method checks if the batch ID and Batch Numbre matches and then uploads the file if the match is done 
	 */

	 @RequestMapping(value="/uploadFile",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
	 public String checkBatchNumberandBatchId(@RequestParam("batchId") String batchId, @RequestParam("scgjBatchNumber") String scgjBatchNumber, @RequestParam("file") MultipartFile file,String fileType ) {
	 		
	 		LOGGER.debug("Request received from frontend");
	 		LOGGER.debug("In Scgj Controller");
	 		
	 		try {
	 			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
	 			
	 			LOGGER.debug("In try block of Controller to check if the batch Id and SCGJ batch number matches");
	 			
	 			int status = uploadDocumentService.scgjBatchIdField(batchId,scgjBatchNumber);
	 			
	 			if(status == 1)
	 			{
	 				LOGGER.debug("The SCGJ batch number and batch id matched");
	 				LOGGER.debug("Sending request to service to upload the folder");
	 				return uploadDocumentService.uploadFileService(file, batchId, fileType, userEmail);
	 			}
	 			else
	 			{
	 				LOGGER.debug("The BatchId and SCGJBatchNumber does not match");
	 				return "SCGJ Batch Number against the Batch ID does not match";
	 			}
	 			
	 		}catch(Exception e) {
	 			LOGGER.error("In catch block of Scgj Controller"+e);
	 			return "File cannot be uploaded";
	 		}
	 		
	 	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	@RequestMapping("/searchDocument")
	public Collection<UploadDocumentsDto> searchDocument(@RequestParam("batchId") String batchId){
		
	
try {
	String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
			LOGGER.debug("In try block ");
			LOGGER.debug("Sending request to service to get documents uploaded by id :" + batchId);	
			return uploadDocumentService.getSearchedDocument(batchId,userEmail);
		}
		catch(Exception e) {	
			LOGGER.debug("An error occurred while searching for uploaded documents" + e);
			LOGGER.debug("Returning NULL!");
			return null;
			
		}	
	
	}
	
	//adding my own:
	@RequestMapping("/downloadZipFileService")
	public void DownloadDoc(@RequestParam("batchId") String id, HttpServletResponse response){
		LOGGER.debug("In block DOWNLOAD");
		try {
			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
			String zipFileLink="j";
			LOGGER.debug("In try block DOWNLOAD"  + zipFileLink + id);
			int count=0;
			//CHANGE HERE!!!!!!
			Collection<UploadDocumentsDto> oldCollection = uploadDocumentService.getSearchedDocument(id,userEmail);
			LOGGER.debug("In try block ");
				Iterator<UploadDocumentsDto> iterator = oldCollection.iterator();
				while (iterator.hasNext()) {
				count++;
				if(count==4)
				zipFileLink = iterator.next().getZipFileLink();
				System.out.print(zipFileLink);
				LOGGER.debug("After try" + zipFileLink + id);
				}
				
				System.out.print(zipFileLink);
				LOGGER.debug("Checking for zip value" + zipFileLink + "batchID:" +id);
				File file = new File(zipFileLink);
			    response.setContentType("application/zip");
			   
			    response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
			    BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
			    BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());  
			    byte[] buffer = new byte[1024];
			    int bytesRead = 0;
			    while ((bytesRead = inStrem.read(buffer)) != -1) {
			        outStream.write(buffer, 0, bytesRead);
			    }
			    outStream.flush();
			    inStrem.close();
		}catch(Exception e) {
			
			LOGGER.debug("An error occurred" + e);
//			return;
			
		}
		}
	@RequestMapping("/getBatchIdDet")
	public List<BatchDto> getBatchIdDetails(){
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In upload Controller");
		String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
		LOGGER.debug("The username retreived from session is : " + userEmail);
		try{
			
			LOGGER.debug("In try block of upload documents" + userEmail);
			LOGGER.debug("Sending request to uploadservice to fetch batch ID for user with username" + userEmail);
			
			return uploadDocumentService.getBatchDetails(userEmail);
		}
		
		 catch(Exception e) {
		
			 LOGGER.error("An error occurred while sending Batch" + e);
				
			 return null;
		 }
	}
	
	 /**
	  * 
	  * @param batchId
	  * @return
	  */
	 @RequestMapping("/getBatchDetails")
	 public int scgjDetails(@RequestParam("batchId") String batchId) {
	 		
	 		LOGGER.debug("Request received from frontend");
	 		LOGGER.debug("In Scgj Controller");
	 		
	 		try {
	 			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
	 			
	 			LOGGER.debug("In try block of Scgj Controller");
	 			return uploadDocumentService.BatchIdField(batchId);	
	 			
	 		}catch(Exception e) {
	 			LOGGER.error("In catch block of Scgj Controller"+e);
	 			return 0;
	 		}
	 		
	 	}
	

}

	
