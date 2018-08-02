package com.nskfdc.scgj.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
//import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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
import com.nskfdc.scgj.dto.CandidateDetailsDto;
import com.nskfdc.scgj.dto.GetBatchIdDto;
import com.nskfdc.scgj.dto.LocationDetailsDto;
//import com.nskfdc.scgj.dto.SearchReportDto;
import com.nskfdc.scgj.dto.TrainingDetailsDto;
import com.nskfdc.scgj.service.GenerateBatchReportService;

@RestController
public class GenerateBatchReportController {

	@Autowired
	public GenerateBatchReportService generateBatchReportService;
	
	@Autowired
	private SessionUserUtility sessionUserUtility;
	
	private String userEmail;
	private String Paths[] = new String[20];
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateBatchReportController.class);
	
	
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a Controller Method that maps the request for getting batch Ids of the particular SCGJ Batch Number. 
	 *@return A Collection of the Batch Ids corresponding to the particular SCGJ Batch Number.
	 
	 **/
	
	@RequestMapping("/getBatchIdDetails")
	public Collection<GetBatchIdDto> getBatchIdDetails(){
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In GenerateBatchReportController");
		LOGGER.debug("To get batch Id for logged in user - getBatchIdDetails");
		
		try {
			LOGGER.debug("Getting User Email from Session");
			userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
			LOGGER.debug("TRYING -- getBatchIdDetails");
			LOGGER.debug("Sending request to GENERATE_BATCH_REPORT_SERVICE");
			LOGGER.debug("Method - getBatchDetails");
			return generateBatchReportService.getBatchDetails(userEmail);
			
		} catch (Exception e) {
			LOGGER.error("CATCHING -- Exception Handled in GenerateBatchReportController");
			LOGGER.error("In method getBatchIdDetails");
			LOGGER.error("An exception is "+e);
			return null;
		}
	}
	@RequestMapping("/getLocationDetails")
	public Collection<LocationDetailsDto> locationDetails(@RequestParam ("batchId") String batchId){
		
		LOGGER.debug("In GenerateBatchReportController");
		LOGGER.debug("To get Location Details");
		
		try{ 
			LOGGER.debug("TRYING -- locationDetails");
			LOGGER.debug("Sending request to GENERATE_BATCH_REPORT_SERVICE");
			LOGGER.debug("Method - locationDetails");
			return generateBatchReportService.locationDetails(batchId);
		}
		catch (Exception e) {
			LOGGER.error("CATCHING -- Exception Handled in GenerateBatchReportController");
			LOGGER.error("In method locationDetails");
			LOGGER.error("An exception is "+e);
			return null;
		}
	}
	
	
	
	
	@RequestMapping("/getTrainingDetails")
	public Collection<TrainingDetailsDto> trainingDetails(@RequestParam ("batchId") String batchId){
		
			LOGGER.debug("In Generate Batch Report Controller");
			LOGGER.debug("To get Training Details respective to Batch Id entered");
		
		try{
			LOGGER.debug("TRYING -- method trainingDetails");
			LOGGER.debug("Sending request to generateBatchReportService");
			LOGGER.debug("Method - trainingDetails");
			return generateBatchReportService.trainingDetails(batchId);
		}
		catch (Exception e) {
			LOGGER.error("CATCHING -- Exception Handled in GenerateBatchReportController");
			LOGGER.error("In method trainingDetails");
			LOGGER.error("An exception is "+e);
			return null;
		}
		
	}
	
	
	
	@RequestMapping("/getCandidateDetails")
	public Collection<CandidateDetailsDto> candidateDetails(@RequestParam ("batchId") String batchId){
		try{
			return generateBatchReportService.candidateDetails(batchId);
		}
		catch (Exception e) {
			return null;
		}
	}
	@RequestMapping("/embedimages")
	public int embeddimages(@RequestParam ("file") MultipartFile file){
		
		LOGGER.debug("In GenerateBatchReportController");
		LOGGER.debug("In the controller -- embeddimages");
		
		try{
			LOGGER.debug("TRYING -- to embed images");
			LOGGER.debug("Sending request to service - embeddimages");
			return generateBatchReportService.embeddimages(file);
		}
		catch(Exception e){
			LOGGER.error("CATCHING -- Exception Handled in GenerateBatchReportController");
			LOGGER.error("In method embeddimages");
			LOGGER.error("An exception is "+e);
			return -1;
		}
	}
	
	@RequestMapping(value="/generateBatchReport" ,method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public void batchReport(@RequestParam ("batchId") String batchId,@RequestParam ("batchnumber") String batchnumber,@RequestParam("file") MultipartFile[] files,HttpServletResponse response){
		
		LOGGER.debug("In BATCH REPORT GENERATE CONTROLLER");
		LOGGER.debug("In the method - batchReport");
		LOGGER.debug("To generate the final batch report based on BatchId & batchNumber");
		String report;
		File dir = null;
		userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
		try{
			
					LOGGER.debug("TRYING -- to get final batch Report");
					LOGGER.debug("Checkin if the files are receieved from the frontend");
					if (files.length ==0)
						return ;
					
					LOGGER.debug("Calling a FOR loop");
					String message = "";
					for (int i = 0; i <files.length; i++) {
						MultipartFile file =  files[i];
						try {
							LOGGER.debug("Try Block -- In For Loop");
							LOGGER.debug("Read the Files and Write the file ina temporary Location\"");
							File convFile = new File(file.getOriginalFilename());
						    convFile.createNewFile();
						    FileOutputStream fos = new FileOutputStream(convFile);
						    fos.write(file.getBytes());
						    fos.close();
						    
							
							// Creating the directory to store file
							String rootPath = System.getProperty("user.home");
							dir = new File(rootPath +File.separator +"Documents"+File.separator + "tmpFiles"+batchId);
							if (!dir.exists())
								dir.mkdirs();
							
							
							// Create the file on server
							File serverFile = new File(dir.getAbsolutePath()
									+ File.separator + "p"+i+".jpg");
							
							BufferedImage img=ImageIO.read(convFile);
							
					        ImageIO.write(img, "jpg", serverFile);
					        
							
							Paths[i]=serverFile.getAbsolutePath();

							LOGGER.debug("Server File Location is="+ serverFile.getAbsolutePath());

							message = message + "You successfully uploaded file=" ;
						} catch (Exception e) {
							LOGGER.error("CATCHING Exception while executing For Loop");
							LOGGER.error("Error in Saving File" + e);
						}
					}
				
				report= generateBatchReportService.generateBatchReport(batchId,batchnumber,userEmail,Paths);
				if(report!=null) {
					
				
				LOGGER.debug("Creating object of File");
				File file = new File(report);
			
				LOGGER.debug("Setting Content Type and Header");
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
		    
				LOGGER.debug("Creating object of BufferedInputStream, BufferedOutputStream");
		    	BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
		    	BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());  
		    	byte[] buffer = new byte[1024];
		    	int bytesRead = 0;
		    	while ((bytesRead = inStrem.read(buffer)) != -1) {
		    		outStream.write(buffer, 0, bytesRead);
		    	}
		    
		    	LOGGER.debug("Pdf of Final Batch Report is Generated successfully");
		    	outStream.flush();
		    	inStrem.close();
		    	
		    	
			} else {
				LOGGER.debug("Path not found");
			}
					
		}
		
		catch (Exception e) {
			
			LOGGER.debug("Error Occurred"+e);
			
		}
		
		try {
			LOGGER.debug("Deleting the Directory where Temporary Files were stored");
			FileUtils.deleteDirectory(dir);
		} catch (IOException e) {
			LOGGER.debug("Exception occured while deleting the Temporary Files directory of Final Batch Report" + e);
		}
	}
	
	 
	
	
	
	
	
}
