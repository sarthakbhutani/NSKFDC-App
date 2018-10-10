package com.nskfdc.scgj.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.common.FileUtility;
import com.nskfdc.scgj.common.Privilege;
import com.nskfdc.scgj.common.SessionUserUtility;
import com.nskfdc.scgj.dto.ViewDocumentDto;
import com.nskfdc.scgj.service.ViewDocumentService;

@RestController
public class ViewDocumentController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ViewDocumentController.class);

	@Autowired
	private SessionUserUtility sessionUserUtility;
	private String userEmail;

	@Autowired
	private FileUtility fileUtility;
	
	@Autowired
	private ViewDocumentService viewDocumentService;

	/**
	 * Method to get training partner details based on a batch Id
	 * 
	 * @param tpName
	 * @param batchId
	 * @return
	 */
	@Privilege(value= {"scgj"})
	@RequestMapping("/getTrainingPartnerDetailForSearchbatchId")
	public Collection<ViewDocumentDto> getTrainingPartnerDetailForSearchbatchId(
			@RequestParam("tpName") String tpName,
			@RequestParam("batchId") String batchId) {

		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In  ViewDocument Controller");

		try {
			userEmail = sessionUserUtility.getSessionMangementfromSession()
					.getUsername();
			LOGGER.debug("In try block to get training partner details for Search");
			LOGGER.debug("Sending request to service");
			return viewDocumentService.getViewTrainingPartnerDetailForBatchId(
					tpName, batchId);

		} catch (Exception e) {

			LOGGER.debug("An error occurred while getting the training partner details for Search"
					+ e);
			return null;
		}

	}

	/**
	 * Method to get details of training Partner based on SCGJ Batch number
	 * 
	 * @param tpName
	 * @param scgjBtNumber
	 * @return
	 */
	@Privilege(value= {"scgj"})
	@RequestMapping("/getTrainingPartnerDetailForSearchscgjBtNumber")
	public Collection<ViewDocumentDto> getTrainingPartnerDetailForSearchscgjBtNumber(
			@RequestParam("tpName") String tpName,
			@RequestParam("scgjBtNumber") String scgjBtNumber) {

		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In  ViewDocument Controller");

		try {
			LOGGER.debug("In try block to get training partner details for Search");
			LOGGER.debug("Sending request to service");
			return viewDocumentService
					.getViewTrainingPartnerDetailForscgjBtNumber(tpName,
							scgjBtNumber);

		} catch (Exception e) {

			LOGGER.debug("An error occurred while getting the training partner details for Search"
					+ e);
			return null;
		}
	}

	
	@Privilege(value= {"scgj"})
	@RequestMapping("/downloadZipFileForBatchId")
	public void DownloadZipFileForBatchId(
			@RequestParam("tpName") String tpName,
			@RequestParam("batchId") String batchId,
			HttpServletResponse response) {
		try {
				
				LOGGER.debug("Trying to find path of Zip");

				String zipPath = viewDocumentService.createZipFileForTPandBatchId(tpName,batchId);	
				if(zipPath !=  null)
				{
					LOGGER.debug("Path of zip is :"+ zipPath);
					LOGGER.debug("Trying to download : "  + zipPath);
					LOGGER.debug("Path of zip file" + zipPath + "for training partner : "+ tpName + "and batchID:" +batchId);
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
	 * Method to download zip with training partner name and scgj batch number
	 * as parameter
	 * 
	 * @param tpName
	 * @param scgjBtNumber
	 * @param response
	 */
	@Privilege(value= {"scgj"})
	@RequestMapping("/downloadZipFileForSearchscgjBtNumber")
	public void DownloadZipFileForSearchscgjBtNumber(
			@RequestParam("tpName") String tpName,
			@RequestParam("scgjBtNumber") String scgjBtNumber,
			HttpServletResponse response) {
		try {
			
			LOGGER.debug("Trying to find path of Zip");

			String zipPath = viewDocumentService.createZipFileForTPandBatchNumber(tpName,scgjBtNumber);	
			if(zipPath !=  null)
			{
				LOGGER.debug("Path of zip is :"+ zipPath);
				LOGGER.debug("Trying to download : "  + zipPath);
				LOGGER.debug("Path of zip file" + zipPath + " training partner : "+ tpName +"and batch Number:" +scgjBtNumber);
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

}
