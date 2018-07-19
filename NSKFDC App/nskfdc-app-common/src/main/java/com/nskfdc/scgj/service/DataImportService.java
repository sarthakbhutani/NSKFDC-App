package com.nskfdc.scgj.service;

import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.nskfdc.scgj.dao.DataImportDao;
import com.nskfdc.scgj.dto.BatchDto;
import com.nskfdc.scgj.dto.DownloadFinalMasterSheetDto;
import java.io.File;
import java.io.*;
import java.util.*;
import org.springframework.core.io.ClassPathResource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class DataImportService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DataImportService.class);
	
	String outputFile;
	
	@Autowired
	private DataImportDao importHistoryDao;
	
	
/*--------------Method to Import Master Sheet----------------*/
	
	public void importMasterSheet() {
		
	//write LOGGER here
		
		try {
			//write LOGGER here		
			//write your logic here & change return type
			importHistoryDao.importMasterSheet();
			
		} 
		catch (Exception e) {
			
			//write LOGGER here
			//return the default value, it can be null
		}
	}
	
	


/*---------------Method to create Batch----------------------------*/

	
	public int getGenerateBatchService(String userEmail){
		
		LOGGER.debug("Request received from Controller to create batch for email id: " + userEmail);
		
		LOGGER.debug("In Import Service to create batch for email id: " + userEmail );
		
		try{
			
			LOGGER.debug("In try block to generate batch for training partner with email id: " + userEmail);
			
			return importHistoryDao.generateBatchDao(userEmail);
		
		}
		
		catch(DataAccessException d) {
		
			LOGGER.error("DataAccessException in service to create Batch" + d);
			
			return -1;
		}
		
		catch(Exception e) {
			
			LOGGER.error("An error occurred while creating the Batch"+ e);
			
			return -1;
		}
	}
	
/*-------------------------Method to Download Final Master Sheet---------------------------*/
	
	public String downloadMasterSheetService(String userEmail) {
		
		LOGGER.debug("Request received from controller");
		LOGGER.debug("In Download Final Master Sheet Service");
		
		try {
				
			LOGGER.debug("In try block of Download Final Master Sheet Service");
				
			Collection<DownloadFinalMasterSheetDto> downloadMasterSheetInformation = importHistoryDao.downloadMasterSheetDao(userEmail);
			
			if (CollectionUtils.isNotEmpty(downloadMasterSheetInformation))
				{	
					
					LOGGER.debug("Creating object of JRBean Collection Data Source ");					
					
					JRBeanCollectionDataSource masterSheetBeans = new JRBeanCollectionDataSource(downloadMasterSheetInformation);
				
					/* Map to hold Jasper Report Parameters */
				
					LOGGER.debug("Creating Map to hold Jasper Report Parameters ");					
				
					Map<String,Object> parameters=new HashMap<String,Object>();					
				
					parameters.put("DataSource",masterSheetBeans);
				
					LOGGER.debug("Creating object of Class Path Resource ");					
				
					ClassPathResource resource = new ClassPathResource("/static/FinalMasterSheet.jasper");				           
			    
					String userHomeDirectory = System.getProperty("user.home");
			        				   
					LOGGER.debug("Getting input stream");				        
			    
					InputStream inputStream = resource.getInputStream();				    
			    
					LOGGER.debug("Input Stream successfully generated");			    
		        
					LOGGER.debug("Creating the jrprint file..");
		        
					JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
		        
					LOGGER.debug("Successfuly created the jrprint file >> " + printFileName);    
		           
		        if (printFileName != null) {
		         
		                /*------------------------- In Excel---------------------------------------------*/
		                
		        		LOGGER.debug("Exporting the file to excel..");
		                
		                JRXlsxExporter exporter = new JRXlsxExporter();
		                
		                exporter.setExporterInput(new SimpleExporterInput(printFileName));
		                
		                outputFile=userHomeDirectory + File.separatorChar + "FinalMasterSheet.xlsx";
		               
		                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
		                SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
		                configuration.setDetectCellType(true);
		                configuration.setCollapseRowSpan(false);
		                exporter.setConfiguration(configuration);
		                exporter.exportReport();
		                			                
		                LOGGER.debug("Successfully Generated excel..");               
				                
		            } else {
		                LOGGER.debug("jrprint file is empty..");
		            }

		            LOGGER.debug("Excel Sheet generated successfully....!!!");
		            
				}
			else {
					outputFile=null;
					LOGGER.debug("Collection is null");
				}
		        
		}catch (Exception e) {
            LOGGER.debug("Exception caught, error in downloading Final Master Sheet" + e);
            
        }
		        return outputFile;
	}

	
	
	
	
	
	
	public Collection<BatchDto> getBatchDetail(String userEmail){
    try {
			
			return importHistoryDao.getBatchDetail(userEmail);
		}
    catch (Exception e) {
		
			LOGGER.debug("An Exception occured while fetching batch id for email " + userEmail + e);
			return null;
		}
	}


		
  //Author: Sagun Saluja

public Integer getTotalTargets(String userEmail){
	
	LOGGER.debug("Request received from Control to get Total Targets");
	LOGGER.debug("In CandidatesTrained Service, to get Total Targets ");
	
	try {
		
		LOGGER.debug("In try block to get Total Targets");
		return importHistoryDao.getTotalTargets(userEmail);
	}
	catch (Exception e) {
	
		LOGGER.error("An error occurred while getting the Total Targets"+ e);
		return null;
	}
}

public Integer getTargetAchieved(String userEmail){
	
	LOGGER.debug("Request received from Control to get Target Achieved");
	LOGGER.debug("In CandidatesTrained Service, to get Target Achieved ");
	
	try {
		
		LOGGER.debug("In try block to get Target Achieved");
		return importHistoryDao.getTargetAchieved(userEmail);
	}
	catch (Exception e) {
	
		LOGGER.error("An error occurred while getting the Target Achieved"+ e);
		return null;
	}
}
public Integer getRemainingTargets(String userEmail){
	
	LOGGER.debug("Request received from Control to get Remaining Targets");
	LOGGER.debug("In CandidatesTrained Service, to get Remaining Targets ");
	
	try {
		
		LOGGER.debug("In try block to get Remaining Targets");
		return importHistoryDao.getRemainingTargets(userEmail);
	}
	catch (Exception e) {
	
		LOGGER.error("An error occurred while getting the Remaining Targets"+ e);
		return null;
	}
}
public Integer getFinancialYear(String userEmail){
	
	LOGGER.debug("Request received from Control to get FinancialYear");
	LOGGER.debug("In CandidatesTrained Service, to get FinancialYear ");
	
	try {
		
		LOGGER.debug("In try block to get Remaining Targets");
		return importHistoryDao.ShowFinancialYear(userEmail);
	}
	catch (Exception e) {
	
		LOGGER.error("An error occurred while getting the FinancialYear"+ e);
		return null;
	}
}
}
