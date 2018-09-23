package com.nskfdc.scgj.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nskfdc.scgj.common.Privilege;
import com.nskfdc.scgj.common.SessionUserUtility;
import com.nskfdc.scgj.dto.BatchImportDto;
import com.nskfdc.scgj.dto.GetBatchIdDto;
import com.nskfdc.scgj.dto.MasterSheetSubmitDto;
import com.nskfdc.scgj.service.DataImportService;
import com.nskfdc.scgj.service.GenerateReportService;

@RestController
public class DataImportController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DataImportController.class);
	@Autowired
	private SessionUserUtility sessionUserUtility;
	@Autowired
	private GenerateReportService generateReportService;

	@Autowired
	private DataImportService dataImportService;

	/*---------- Master Sheet Import --------------*/
	@Privilege(value= {"TP"})
	@RequestMapping(value = "/importMasterSheet", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public String masterSheetImport(@RequestParam("file") MultipartFile file,
			@RequestParam("batchId") String batchId) throws FileNotFoundException {
		LOGGER.debug("In Data import controller to read excel file");
		try {
			String userEmail = sessionUserUtility.getSessionMangementfromSession()
					.getUsername();
			if (file == null) {
				LOGGER.debug("Null File in controller");
				return "File is null";
			}  else
				return dataImportService.masterSheetImport(file, batchId,userEmail);
		}

		catch (Exception e) {
			LOGGER.error("Exception is " + e);
			return "File cannot be uploaded";
		}
	}


	/**
	 * Method to get details of a batch
	 * 
	 * @param batchId
	 * @return object of GetBatchDetailsDto
	 */
	@Privilege(value= {"TP"})
	@RequestMapping("/get-batch-details")
	public BatchImportDto getBatchDetails(String batchId) {
		String userEmail = sessionUserUtility.getSessionMangementfromSession()
				.getUsername();
		LOGGER.debug("Trying to get batch details for batch:" + batchId
				+ "of user : " + userEmail);
		return dataImportService.BatchDetails(userEmail, batchId);
	}

	/**
	 * Method to get Total targets assigned to training partner
	 * 
	 * @return
	 */
	@Privilege(value= {"TP"})
	@RequestMapping("/getTotalTargets")
	public Integer getTotalTargets() {
                     String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
                     LOGGER.debug("Request received from frontend to get Total Targets");
                     LOGGER.debug("In get Total Targets Controller");
                     
                     try {
                           
                           LOGGER.debug("In try block to get Total Targets");
                           LOGGER.debug("Sending request to service");
                           return dataImportService.getTotalTargets(userEmail);
                           
                           }
                     catch(Exception e) {
                           
                           LOGGER.error("An exception occurred while getting the Total Targets" + e);
                           return null;
                           
                     }
              }

	/**
	 * Method to get targets achieved by training partner to display in panel
	 * 
	 * @return
	 */
	@Privilege(value= {"TP"})
	@RequestMapping("/getTargetAchieved")
	public Integer getTargetAchieveds() {
		String userEmail = sessionUserUtility.getSessionMangementfromSession()
				.getUsername();
		LOGGER.debug("Request received from frontend to get Target Achieved");
		LOGGER.debug("In get Target Achieved Controller");

		try {

			LOGGER.debug("In try block to get Target Achieved");
			LOGGER.debug("Sending request to service");
			return dataImportService.getTargetAchieved(userEmail);

		} catch (Exception e) {

			LOGGER.error("An exception occurred while getting the Target Achieved"
					+ e);
			return null;

		}
	}

	/**
	 * Method to get remaining targets of training partners to display in panel
	 * 
	 * @return
	 */
	@Privilege(value= {"TP"})
	@RequestMapping("/getRemainingTargets")
	public Integer getRemainingTargets() {
		String userEmail = sessionUserUtility.getSessionMangementfromSession()
				.getUsername();
		LOGGER.debug("Request received from frontend to get Remaining Target");
		LOGGER.debug("In get Remaining Target Controller");

		try {

			LOGGER.debug("In try block to get Remaining Target");
			LOGGER.debug("Sending request to service");
			return dataImportService.getRemainingTargets(userEmail);

		} catch (Exception e) {

			LOGGER.error("An exception occurred while getting the Target Achieved"
					+ e);
			return null;

		}
	}

	/**
	 * Method to get Financial year
	 * 
	 * @return
	 */
	@Privilege(value= {"TP"})
	@RequestMapping("/getFinancialYear")
	public Integer getFinancialYear() {
		String userEmail = sessionUserUtility.getSessionMangementfromSession()
				.getUsername();
		LOGGER.debug("Request received from frontend to get FinancialYear");
		LOGGER.debug("In get Remaining Target Controller");

		try {

			LOGGER.debug("In try block to get FinancialYear");
			LOGGER.debug("Sending request to service");
			return dataImportService.getFinancialYear(userEmail);

		} catch (Exception e) {

			LOGGER.error("An exception occurred while getting the FinancialYear"
					+ e);
			return null;

		}
	}

	/**
	 * Method to download master sheet
	 * 
	 * @param response
	 */
	@Privilege(value= {"TP"})
	@RequestMapping("/downloadFinalMasterSheet")
	public void downloadMasterSheetController(@RequestParam("batchId") String batchId,HttpServletResponse response) {

		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In downloadMasterSheetController");

		try {

			LOGGER.debug("In try block of generate Master Sheet Controller");

			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
			String report = dataImportService.downloadMasterSheetService(userEmail,batchId);

			if (report != null) {

				LOGGER.debug("Creating object of File");
				File file = new File(report);

				LOGGER.debug("Setting Content Type and Header");
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + file.getName());

				LOGGER.debug("Creating object of BufferedInputStream, BufferedOutputStream");
				BufferedInputStream inStrem = new BufferedInputStream(
						new FileInputStream(file));
				BufferedOutputStream outStream = new BufferedOutputStream(
						response.getOutputStream());
				byte[] buffer = new byte[1024];
				int bytesRead = 0;
				while ((bytesRead = inStrem.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				LOGGER.debug("Excel Sheet Generated successfully");

				outStream.flush();
				inStrem.close();
				boolean deleteFile = file.delete();
		    	if(deleteFile)
		    	{
		    		LOGGER.debug("Deleting temp file"+ deleteFile);
		    	}
		    	else
		    	{
		    		LOGGER.debug("Could not delete file" + deleteFile);
		    	}
			} else {
				LOGGER.debug("Path not found");
			}
		} catch (Exception e) {
			LOGGER.error("In catch block of downloadMasterSheetController" + e);
		}
	}

	/**
	 * Method to generate Batch on the button click by training Partner
	 * 
	 * @return
	 */
	
	@Privilege(value= {"TP"})
	@RequestMapping("/generateBatch/{municipality}")
	public GetBatchIdDto generateBatchController(@PathVariable("municipality") String municipality)
	{
		String userEmail = sessionUserUtility.getSessionMangementfromSession()
				.getUsername();
		LOGGER.debug("Request received from frontend to create batch for email id : "
				+ userEmail);
		LOGGER.debug("The municipality for which batch should be generated is: " + municipality);
		LOGGER.debug("In Import Controller to create batch for email id: "
				+ userEmail);
		try {

			String batchId = dataImportService.getGenerateBatchService(userEmail,municipality);
			LOGGER.debug("The batch id generated for training partner is : " + batchId);
			GetBatchIdDto getBatchIdDto= new GetBatchIdDto (batchId);
			return getBatchIdDto;

		}

		catch (DataAccessException d) {

			LOGGER.error("DataAccessException in controller to create Batch"
					+ d);

			return null;
		}

		catch (Exception e) {

			LOGGER.error("An error occurred while creating Batch" + e);

			return null;
		}

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	/**
	 * Method to get Batch Id for training partner based on its email
	 * @return
	 */
	@Privilege(value= {"TP"})
	@RequestMapping("/getBatchIdfortrainer")
	public Collection<GetBatchIdDto> getBatchDetail() {
		String userEmail = sessionUserUtility.getSessionMangementfromSession()
				.getUsername();
		LOGGER.debug("Request received from frontend to show batch ID");
		try {
			return generateReportService.getBatchDetails(userEmail);

		} catch (Exception e) {

			return null;

		}
	}

	/**
	 * Method to submit data on submit button click
	 * @param masterSheetSubmitDto object
	 * @return
	 */
	@Privilege(value= {"TP"})
	@RequestMapping(value = "/submitBatchDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public int submitBatchDetails(
			@RequestBody MasterSheetSubmitDto masterSheetSubmitDto) {
		String userEmail = sessionUserUtility.getSessionMangementfromSession()
				.getUsername();
		return dataImportService.submitBatchDetails(userEmail,
				masterSheetSubmitDto);
	}

}
