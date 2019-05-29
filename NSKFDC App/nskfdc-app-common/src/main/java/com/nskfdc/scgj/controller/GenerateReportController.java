package com.nskfdc.scgj.controller;

import java.io.*;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.common.Privilege;
import com.nskfdc.scgj.common.SessionUserUtility;
import com.nskfdc.scgj.dto.DisplayAuditTableRecordDto;
import com.nskfdc.scgj.dto.GetBatchIdDto;
import com.nskfdc.scgj.service.GenerateReportService;

@RestController
public class GenerateReportController {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateReportController.class);
	
	@Autowired
	private GenerateReportService generateReportService;
	
	@Autowired
	private SessionUserUtility sessionUserUtility;
	
	String userEmail,report;
	
	@Privilege(value= {"TP"})
	@RequestMapping("/getBatchIdDetailsForGenerateReport")
	public Collection<GetBatchIdDto> getBatchIdDetails(){
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In GetBatchId Controller");
				
		try {
			userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
			LOGGER.debug("In try block to get training partner details for Training Partner");
			LOGGER.debug("Sending request to Service");
			return generateReportService.getBatchDetails(userEmail);
			
		} catch (Exception e) {
			LOGGER.error("An error occurred while getting the training partner details for Training Partner");
			return null;
		}
	}
	@Privilege(value= {"TP"})
	@RequestMapping(value="generateOccupationCertificateReport",  method = RequestMethod.GET)
	public void generateOccupationCertificateReport(@RequestParam("batchId") String batchId,HttpServletResponse response) {
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In Generate Occupation Certificate Controller");
		
		try {
			
			LOGGER.debug("In try block of Generate Occupation Certificate Controller");
			
			userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
			report = generateReportService.generateOccupationCertificateReport(batchId,userEmail);
			
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
		    
		    	LOGGER.debug("Pdf Generated successfully");
		    
		    	outStream.flush();
		    	inStrem.close();
		    	
		    	//Delete file
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
				LOGGER.error("Report not generated");
			}
		}catch(Exception e) {
			LOGGER.error("In catch block of Generate Occupation Certificate Controller"+e);
		}
	}

	@Privilege(value= {"TP"})
	@RequestMapping(value="generateAttendanceSheet", method = RequestMethod.GET)
	public void generateAttendanceSheet(@RequestParam("batchId") String batchId, HttpServletResponse response) {
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In Generate Attendance Sheet Controller");
		
		try {		
			
			LOGGER.debug("In try block of Generate Attendance Sheet Controller");
			
			userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
			report = generateReportService.generateAttendanceSheet(batchId,userEmail);	
			
			if(report!=null) {
			
				File file = new File(report);
				response.setContentType("application/pdf");
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
		    	
		    	//Delete file
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
				LOGGER.error("Report not generated");
			}
		}catch(Exception e) {
			LOGGER.error("In catch block of Generate Attendance Sheet Controller"+e);
		}
		
	}
	
	@Privilege(value= {"TP"})
	@RequestMapping("/generateNSKFDCSheet")
	public void generateNSKFDCExcelSheet(@RequestParam("batchId") String batchId, HttpServletResponse response) {
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In Generate NSKFDC Excel Sheet Controller");
		
		try {
				
			LOGGER.debug("In try block of Generate NSKFDC Excel Sheet Controller");
			
			userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
			report=generateReportService.generateNSKFDCExcelSheet(batchId,userEmail);	
			
			if(report!=null) {
				
				File file = new File(report);
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
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
				//Deleting file
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
				LOGGER.error("Report not generated");
			}
		} catch (Exception e) {
			LOGGER.error("In catch block of Generate NSKFDC Excel Sheet Controller"+e);
		}
		
	}
	
	@Privilege(value= {"TP"})
	@RequestMapping("/generateSDMSSheet")
	public void generateSDMSExcelSheet(@RequestParam("batchId") String batchId, HttpServletResponse response) {
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In Generate SDMS Excel Sheet Controller");
		
		try {
				
			LOGGER.debug("In try block of Generate SDMS Excel Sheet Controller");
			
			userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
			report=generateReportService.generateSDMSExcelSheet(batchId,userEmail);	

			if(report!=null) {
				
				File file = new File(report);
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
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
				boolean deleteFile = file.delete();
				//Deleting file
		    	if(deleteFile)
		    	{
		    		LOGGER.debug("Deleting temp file"+ deleteFile);
		    	}
		    	else
		    	{
		    		LOGGER.debug("Could not delete file" + deleteFile);
		    	}
				
			} else {
				LOGGER.error("Report not generated");
			}
		    
		} catch (Exception e) {
			LOGGER.error("In catch block of Generate SDMS Excel Sheet Controller"+e);
		}
	
	}

	@Privilege(value= {"TP"})
    @RequestMapping(value = "/generateMinutesOfSelectionCommitteeDetails", method = RequestMethod.GET)
	public void generateMinutesOfSelection(@RequestParam("batchId") String batchId, HttpServletResponse response) {
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In minutes of selection controller");
		
		try {
				
			LOGGER.debug("In try block of minutes of selection Controller");
			
			userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
			report=generateReportService.generateMinutesOfSelection(batchId,userEmail);	

			if(report!=null) {
				
				File file = new File(report);
				response.setContentType("application/pdf");
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
				
				//Delete file
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
			LOGGER.error("Report not generated for minutes of selection bcz report is null");
			}
		    
		} catch (Exception e) {
			LOGGER.error("In catch block of minutes of selection Controller"+e);
		}
	
	}
	
	@Privilege(value= {"TP"})
	@RequestMapping("/getAuditTableRecords")
	public Collection<DisplayAuditTableRecordDto> getRecordsForAuditTable() {
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In Get Records For Audit Table Controller");
		
		try {		
			
			LOGGER.debug("In try block of Get Records For Audit Table Controller");
			
			userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
			return generateReportService.getRecordsForAuditTable(userEmail);	
			
		}catch(Exception e) {
			LOGGER.error("In catch block of Get Records For Audit Table Controller"+e);
			return null;
		}
	}
	
}
