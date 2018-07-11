package com.nskfdc.scgj.service;

import java.io.File;
import java.text.*;
import java.io.*;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.GenerateReportDao;
import com.nskfdc.scgj.dto.GenerateAttendanceSheetDto;
import com.nskfdc.scgj.dto.GenerateOccupationCertificateReportDto;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class GenerateReportService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateReportService.class);
	
	@Autowired
	private GenerateReportDao generateReportDao;
	
	int success=0;
	Date date=new Date();
	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	Calendar c=Calendar.getInstance();
	String hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY)); 
	String minute = Integer.toString(c.get(Calendar.MINUTE));
	String second = Integer.toString(c.get(Calendar.SECOND));
	
	public int generateOccupationCertificateReport(String batchId, String trainingPartnerEmail) {
		
		LOGGER.debug("Request received from controller");
		LOGGER.debug("In Generate Occupation Certificate Service");
		
			try {	
				
				LOGGER.debug("In try block of Generate Occupation Certificate Service");
				
				Collection<GenerateOccupationCertificateReportDto> occupationReportDetails = generateReportDao.generateOccupationCertificateReport(batchId,trainingPartnerEmail);
				
				LOGGER.debug("Create object of JRBean Collection Data Source ");
				JRBeanCollectionDataSource occupationCertificateReportBeans = new JRBeanCollectionDataSource(occupationReportDetails);
				
				/* Map to hold Jasper Report Parameters */
				LOGGER.debug("Create Map to hold Jasper Report Parameters ");
				Map<String,Object> parameters=new HashMap<String,Object>();
				parameters.put("DataSource",occupationCertificateReportBeans);
				
				 /*------------------------- In PDF---------------------------------------------*/       
		         
				LOGGER.debug("Create object of Class Path Resource ");
			    ClassPathResource resource=new ClassPathResource("/static/OccupationReport.jasper");
			    String userHomeDirectory = System.getProperty("user.home");
			    
			    String outputFile = userHomeDirectory + File.separatorChar + "Downloads" + File.separatorChar + "OccupationReport "+df.format(date)+" "+hour+"-"+minute+"-"+second+".pdf";
			    LOGGER.debug("THE OUTPUT FILE IS IN" +userHomeDirectory+"in ---------"+ outputFile);
		        
		        LOGGER.debug("Getting input stream");
			    InputStream inputStream = resource.getInputStream();
		        
			    try {
		            JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
		            OutputStream outputStream = new FileOutputStream(new File(outputFile));

		            if (printFileName != null && CollectionUtils.isNotEmpty(occupationReportDetails)) {
		            	
		            	LOGGER.debug("Creating the jrprint file..");
		                JasperExportManager.exportReportToPdfStream(printFileName, outputStream);
		                LOGGER.debug("Successfully created the jrprint file >> " + printFileName);
		                success = 1;       
		                LOGGER.debug("PDF generated successfully..!!");
		                
		            } else {
		                success = -1;
		                LOGGER.debug("jrprint file is empty..");
		            }
		            
		            outputStream.close();
		        } catch (JRException e) { 
		        	LOGGER.error("Exception caught, unable to create pdf"+e);
		        }	
			} catch (Exception e) {
	        	LOGGER.error("In catch block of Generate Occupation Certificate Service"+e);
			}
			return success;
		}

    public int generateAttendanceSheet(String batchId, String trainingPartnerEmail) {
		
			try {
				
				LOGGER.debug("In try block of Generate Attendance Sheet Service");
				Collection<GenerateAttendanceSheetDto> attendanceSheetDetails = generateReportDao.generateAttendanceSheet(batchId,trainingPartnerEmail);
				
				LOGGER.debug("Create object of JRBean Collection Data Source ");
				JRBeanCollectionDataSource attendanceSheetBeans = new JRBeanCollectionDataSource(attendanceSheetDetails);
				
				/* Map to hold Jasper Report Parameters */
				
				LOGGER.debug("Create Map to hold Jasper Report Parameters ");
				Map<String,Object> parameters=new HashMap<String,Object>();
				parameters.put("DataSource",attendanceSheetBeans);
				
				 /*------------------------- In PDF---------------------------------------------*/       
		        
				LOGGER.debug("Create object of Class Path Resource ");
			    ClassPathResource resource=new ClassPathResource("/static/AttendanceSheet.jasper");
			    String userHomeDirectory = System.getProperty("user.home");
			    
			    String outputFile = userHomeDirectory + File.separatorChar + "Downloads" + File.separatorChar + "AttendanceSheet "+df.format(date)+" "+hour+"-"+minute+"-"+second+".pdf";
		        LOGGER.debug("THE OUTPUT FILE IS IN" +userHomeDirectory+"in ---------"+ outputFile);
		        
		        LOGGER.debug("Getting input stream");
			    InputStream inputStream = resource.getInputStream();
		        try {
		        	
		        	LOGGER.debug("Creating the jrprint file..");
		            JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
		            LOGGER.debug("Successfuly created the jrprint file >> " + printFileName);
		            OutputStream outputStream = new FileOutputStream(new File(outputFile));

		            if (printFileName != null && CollectionUtils.isNotEmpty(attendanceSheetDetails)) {
		            	LOGGER.debug("Creating the jrprint file..");
		                JasperExportManager.exportReportToPdfStream(printFileName, outputStream);
		                LOGGER.debug("Successfully created the jrprint file >> " + printFileName);
		                success = 1;              
		                LOGGER.debug("PDF generated successfully..!!");
		                
		            } else {
		                success = -1;
		                LOGGER.debug("jrprint file is empty..");
		            }
		            outputStream.close();
		        } catch (JRException e) {
		        	LOGGER.error("Exception caught, unable to create pdf"+e);
		        }	
			} catch (Exception e) {
				LOGGER.error("In catch block of Generate Attendance Sheet Service"+e);
			}
			return success;
		}		
}
