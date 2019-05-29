package com.nskfdc.scgj.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;

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

import com.nskfdc.scgj.common.FileUtility;
import com.nskfdc.scgj.common.Privilege;
import com.nskfdc.scgj.common.SessionUserUtility;
import com.nskfdc.scgj.dto.GetBatchIdDto;
import com.nskfdc.scgj.dto.UploadDocumentsDto;
import com.nskfdc.scgj.service.GenerateReportService;
import com.nskfdc.scgj.service.UploadDocumentService;

@RestController
public class UploadDocumentsController {
	private static final Logger LOGGER= LoggerFactory.getLogger(UploadDocumentsController.class);

	@Autowired
	private UploadDocumentService uploadDocumentService;

	@Autowired
	private SessionUserUtility sessionUserUtility;

	@Autowired
	private GenerateReportService generateReportService;
	int status = -25;
	@Autowired
	private FileUtility fileUtility;

	/**
	 * 
	 * @param batchId
	 * @param scgjBatchNumber
	 * @param file
	 * @param fileType
	 * @Description this method checks if the batch ID and Batch Number matches and then uploads the file if the match is done 
	 */

	@Privilege(value= {"TP"})
	@RequestMapping(value="/uploadFile",method=RequestMethod.POST,consumes=MediaType.ALL_VALUE)
	public String checkBatchNumberandBatchId(@RequestParam("batchId") String batchId, @RequestParam("scgjBatchNumber") String scgjBatchNumber, @RequestParam("file") MultipartFile file,String fileType)
	{

		LOGGER.debug("Request received from frontend to upload file for batchId : " + batchId);

		try {

			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();

			LOGGER.debug("In try block of Controller to check if the batch Id and SCGJ batch number matches");


			if("finalBatchReport".equals(fileType))
			{
				LOGGER.debug("File Type is final batch report : " + fileType);
				//To check batch number against the batch Id
				LOGGER.debug("The scgj batch Number is : " + scgjBatchNumber);
				int status = uploadDocumentService.scgjBatchIdField(batchId,scgjBatchNumber);

				if(status == 1)
				{
					//When search successful 
					LOGGER.debug("The SCGJ batch number and batch id matched");
					LOGGER.debug("Sending request to service to upload the folder");
					return uploadDocumentService.uploadFileService(file, batchId, fileType, userEmail);
				}
				else
				{
					//when search failed
					LOGGER.debug("The BatchId and SCGJBatchNumber does not match");
					return "SCGJ Batch Number against the Batch ID does not match";
				}
			}

			else
			{
				LOGGER.debug("File type is: " + fileType);

				return uploadDocumentService.uploadFileService(file, batchId, fileType, userEmail);
			}

		}
		catch(Exception e) {
			LOGGER.error("An Exception occured in Upload Documents Controller while uploading the file : " +e);
			return "File cannot be uploaded";
		}

	}

	/**
	 * Search Documents based on batch Id
	 * @param batchId
	 * @return Collection of object to type Upload documents table
	 */
	@Privilege(value= {"TP"})
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

	/**
	 * Method to download zip file
	 * @param id
	 * @param response
	 */
	@Privilege(value= {"TP"})
	@RequestMapping("/downloadZipFileService")
	public void DownloadDoc(@RequestParam("batchId") String id, HttpServletResponse response){
		try {
			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
			LOGGER.debug("Trying to find path of Zip");

			String zipPath = uploadDocumentService.createZipFile(id,userEmail);	
			if(zipPath !=  null)
			{
				LOGGER.debug("Path of zip is :"+ zipPath);
				LOGGER.debug("Trying to download : "  + zipPath + id);
				LOGGER.debug("Path of zip file" + zipPath + " and batchID:" +id);
				File file = new File(zipPath);

				LOGGER.debug("Setting the content type of response");
				response.setContentType("application/zip");

				LOGGER.debug("Setting the header of response as attachment along with the filename");
				response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

				LOGGER.debug("Creating buffered input stream to read file from : "+ zipPath);
				BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));

				LOGGER.debug("Creating buffered output stream to append zip file in the response ");
				BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());  

				byte[] buffer = new byte[1024];
				int bytesRead = 0;
				while ((bytesRead = inStrem.read(buffer)) != -1)
				{
					outStream.write(buffer, 0, bytesRead);
				}

				outStream.flush();
				inStrem.close();

				//Delete File
				fileUtility.deleteFile(zipPath);
			}
			else
			{
				LOGGER.error("The path of Zip to be downloaded is empty");
			}

		}
		catch(Exception e) {

			LOGGER.debug("An error occurred : " + e);

		}
	}



	/**
	 * List of batch for a training Partner
	 * @return
	 */
	@Privilege(value= {"TP"})
	@RequestMapping("/getBatchIdDet")
	public Collection<GetBatchIdDto> getBatchIdDetails(){
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In upload Controller");
		String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
		LOGGER.debug("The username retreived from session is : " + userEmail);
		try{

			LOGGER.debug("In try block of upload documents" + userEmail);
			LOGGER.debug("Sending request to uploadservice to fetch batch ID for user with username" + userEmail);

			return generateReportService.getBatchDetails(userEmail);
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
	@Privilege(value= {"TP"})
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


