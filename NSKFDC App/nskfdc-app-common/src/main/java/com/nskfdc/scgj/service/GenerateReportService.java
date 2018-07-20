package com.nskfdc.scgj.service;

import java.io.File;
import java.io.*;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import com.nskfdc.scgj.dao.GenerateReportDao;
import com.nskfdc.scgj.dto.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class GenerateReportService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateReportService.class);
	
	@Autowired
	private GenerateReportDao generateReportDao;
	
	int success=0;
	Date date=new Date();
	String reportType;
	String generateReportsId;
	String outputFile;
	
	public Collection<GetBatchIdDto> getBatchDetails(String userEmail){
		LOGGER.debug("Request received from Controller");
		LOGGER.debug("In Get Batch Id Service, to get Batch Ids' for Training Partner");
		
	try{
		
		LOGGER.debug("In try block to get training partner details for Training Partner");
		LOGGER.debug("Sending request to Dao");
		return generateReportDao.getBatchId(userEmail);
	}
	catch(Exception e){
		
		LOGGER.error("An error occurred while getting the training partner details for Training Partner");
		return null;
	}
  }
	
	public String generateOccupationCertificateReport(String batchId, String trainingPartnerEmail) {
		
		LOGGER.debug("Request received from controller");
		LOGGER.debug("In Generate Occupation Certificate Service");
		
			try {	
				
				LOGGER.debug("In try block of Generate Occupation Certificate Service");
				
				Collection<GenerateOccupationCertificateReportDto> occupationReportDetails = generateReportDao.generateOccupationCertificateReport(batchId,trainingPartnerEmail);
	
				if (CollectionUtils.isNotEmpty(occupationReportDetails))
				{	
				
					LOGGER.debug("Create object of JRBean Collection Data Source ");
					JRBeanCollectionDataSource occupationCertificateReportBeans = new JRBeanCollectionDataSource(occupationReportDetails);
				
					/* Map to hold Jasper Report Parameters */
					LOGGER.debug("Create Map to hold Jasper Report Parameters ");
					Map<String,Object> parameters=new HashMap<String,Object>();
					parameters.put("DataSource",occupationCertificateReportBeans);
				
				    /*------------------------------------ In PDF---------------------------------------------*/       
		         
					LOGGER.debug("Create object of Class Path Resource ");
					ClassPathResource resource=new ClassPathResource("/static/OccupationReport.jasper");
					String userHomeDirectory = System.getProperty("user.home");
			    
					outputFile = userHomeDirectory + File.separatorChar + "AppData"+File.separatorChar+"Local"+File.separatorChar+"Temp"+File.separatorChar +"OccupationReport.pdf";
					LOGGER.debug("THE OUTPUT FILE IS IN" +userHomeDirectory+"in ---------"+ outputFile);
		        
					LOGGER.debug("Getting input stream");
					InputStream inputStream = resource.getInputStream();
		        
					try {
						JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
						OutputStream outputStream = new FileOutputStream(new File(outputFile));

						if (printFileName != null) {
		            	
							LOGGER.debug("Creating the jrprint file..");
							JasperExportManager.exportReportToPdfStream(printFileName,outputStream);
							LOGGER.debug("Successfully created the jrprint file >> " + printFileName);
							success = 1;       
							LOGGER.debug("PDF generated successfully..!!");
							
							reportType="Occupation Certificate";
							generateReportsId="OC"+batchId+trainingPartnerEmail;				           
				            LOGGER.debug("Updating Database....");
				            generateReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,trainingPartnerEmail);		            		            	
				            
		                
						} else {
							success = -1;
							LOGGER.debug("jrprint file is empty..");
						}
		            	outputStream.close();
		        	} catch (JRException e) { 
		        		LOGGER.error("Exception caught, unable to create pdf"+e);
		        	}
				} else {
					outputFile=null;
					LOGGER.debug("Collection is null");
				}
			} catch (Exception e) {
	        	LOGGER.error("In catch block of Generate Occupation Certificate Service"+e);
			}
			
			return outputFile;
		}

    public String generateAttendanceSheet(String batchId, String userEmail) {
		
			try {
				
				LOGGER.debug("In try block of Generate Attendance Sheet Service");
				Collection<GenerateAttendanceSheetDto> attendanceSheetDetails = generateReportDao.generateAttendanceSheet(batchId,userEmail);
				
				if (CollectionUtils.isNotEmpty(attendanceSheetDetails))
				{	
				
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
			    
					outputFile = userHomeDirectory + File.separatorChar + "AppData"+ File.separatorChar+"Local"+File.separatorChar+"Temp"+File.separatorChar + "AttendanceSheet.pdf";
					LOGGER.debug("THE OUTPUT FILE IS IN" +userHomeDirectory+"in ---------"+ outputFile);
		        
					LOGGER.debug("Getting input stream");
					InputStream inputStream = resource.getInputStream();
					try {
		        	
						LOGGER.debug("Creating the jrprint file..");
						JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
						LOGGER.debug("Successfully created the jrprint file >> " + printFileName);
						OutputStream outputStream = new FileOutputStream(new File(outputFile));

						if (printFileName != null) {
							LOGGER.debug("Creating the jrprint file..");
							JasperExportManager.exportReportToPdfStream(printFileName, outputStream);
							LOGGER.debug("Successfully created the jrprint file >> " + printFileName);
							success = 1;              
							LOGGER.debug("PDF generated successfully..!!");
							
							reportType="Attendance Sheet";
							generateReportsId="AS"+batchId+userEmail;	
				           	LOGGER.debug("Updating Database....");
				           	generateReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,userEmail);		            		            	
		                
						} else {
							success = -1;
		                	LOGGER.debug("jrprint file is empty..");
						}
						outputStream.close();
					} catch (JRException e) {
						LOGGER.error("Exception caught, unable to create pdf"+e);
					}	
				} else {
					outputFile=null;
					LOGGER.debug("Collection is null");
				}
			} catch (Exception e) {
				LOGGER.error("In catch block of Generate Attendance Sheet Service"+e);
			}            
			
			return outputFile;
		}	
    
    public  String generateNSKFDCExcelSheet(String batchId,String userEmail) {
		
    	LOGGER.debug("Request received from controller");
		LOGGER.debug("In Generate NSKFDC Excel Sheet Service");
    	
    	try {
    			LOGGER.debug("In try block of Generate NSKFDC Excel Sheet Service");
				Collection<GenerateNSKFDCExcelSheetDto> nSKFDCSheetDetails = generateReportDao.generateNSKFDCExcelSheet(batchId,userEmail);
				
				if (CollectionUtils.isNotEmpty(nSKFDCSheetDetails))
				{
					LOGGER.debug("Creating object of JRBean Collection Data Source");
					JRBeanCollectionDataSource nSKFDCSheetBeans = new JRBeanCollectionDataSource(nSKFDCSheetDetails);
				
					/* Map to hold Jasper Report Parameters */
					Map<String,Object> parameters=new HashMap<String,Object>();
					parameters.put("NSKFDCDataSource",nSKFDCSheetBeans);
						                
		        /*------------------------- In Excel---------------------------------------------*/       
		        
					LOGGER.debug("Creating object of Class Path Resource");
					ClassPathResource resource=new ClassPathResource("/static/NSKFDCExcelSheet.jasper");
					String userHomeDirectory = System.getProperty("user.home");
					
					LOGGER.debug("Getting input stream");
					InputStream inputStream = resource.getInputStream();	
					try {
					
						LOGGER.debug("Creating the jrprint file..");
						JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());	
						if (printFileName != null) {
						
							LOGGER.debug("Exporting the file to Excel..");
							JRXlsxExporter exporter = new JRXlsxExporter();
							exporter.setExporterInput(new SimpleExporterInput(printFileName));
							outputFile=userHomeDirectory + File.separatorChar + "AppData"+ File.separatorChar+"Local"+File.separatorChar+"Temp"+File.separatorChar + "NSKFDCSheet.xlsx";
							exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
							SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
							configuration.setDetectCellType(true);
							configuration.setCollapseRowSpan(false);
							exporter.setConfiguration(configuration);
							exporter.exportReport();
						
							success = 1;              
							LOGGER.debug("Excel Sheet generated successfully..!!");
							
							reportType="NSKFDC Excel Sheet";
							generateReportsId="NS"+batchId+userEmail;
					       	LOGGER.debug("Updating Database....");
					       	generateReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,userEmail);		            		            	
					 		                
						}else {
							success = -1;
							LOGGER.debug("jrprint file is empty..");
						}
					
					} catch (JRException e) { 
						LOGGER.debug("Exception caught, Error in generating Excel Sheet"+e);
					}	
				} else {
					outputFile=null;
					LOGGER.debug("Collection is null");
				}	
					
					
		} catch(Exception e) {
			LOGGER.debug("In catch block of Generate NSKFDC Excel Sheet Service"+e);
		}
    	   
    	return outputFile;
    }
    
    public String generateSDMSExcelSheet(String batchId,String userEmail) {
		
    	LOGGER.debug("Request received from controller");
		LOGGER.debug("In Generate SDMS Excel Sheet Service");
    	
    	try {
    			LOGGER.debug("In try block of Generate SDMS Excel Sheet Service");
				Collection<GenerateSDMSExcelSheetDto> sDMSSheetDetails = generateReportDao.generateSDMSExcelSheet(batchId,userEmail);
				
				if (CollectionUtils.isNotEmpty(sDMSSheetDetails))
				{
					
					LOGGER.debug("Creating object of JRBean Collection Data Source");
					JRBeanCollectionDataSource sDMSSheetBeans = new JRBeanCollectionDataSource(sDMSSheetDetails);
				
					/* Map to hold Jasper Report Parameters */
					Map<String,Object> parameters=new HashMap<String,Object>();
					parameters.put("SDMSDataSource",sDMSSheetBeans);
					
					/*------------------------- In Excel---------------------------------------------*/       
		        
					LOGGER.debug("Creating object of Class Path Resource");
					ClassPathResource resource=new ClassPathResource("/static/SDMSExcelSheet.jasper");
					String userHomeDirectory = System.getProperty("user.home");
				
					LOGGER.debug("Getting input stream");
					InputStream inputStream = resource.getInputStream();	
					try {
					
						LOGGER.debug("Creating the jrprint file..");
						JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());	
						if (printFileName != null) {
						
							LOGGER.debug("Exporting the file to Excel..");
							JRXlsxExporter exporter = new JRXlsxExporter();
							exporter.setExporterInput(new SimpleExporterInput(printFileName));
							outputFile=userHomeDirectory + File.separatorChar + "AppData"+ File.separatorChar+"Local"+File.separatorChar+"Temp"+File.separatorChar + "SDMSSheet.xlsx";
							exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
							SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
							configuration.setDetectCellType(true);
							configuration.setCollapseRowSpan(false);
							exporter.setConfiguration(configuration);
							exporter.exportReport();
						
							success = 1;              
							LOGGER.debug("Excel Sheet generated successfully..!!");
							
							reportType="SDMS Excel Sheet";
							generateReportsId="SD"+batchId+userEmail;
							LOGGER.debug("Updating Database....");
					        generateReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,userEmail);
		                
						}else {
		                success = -1;
		                LOGGER.debug("jrprint file is empty..");
						}
					
					} catch (JRException e) { 
						LOGGER.debug("Exception caught, Error in generating Excel Sheet"+e);
					}	
				} else {
					outputFile=null;
					LOGGER.debug("Collection is null");
				}
		} catch(Exception e) {
			LOGGER.debug("In catch block of Generate SDMS Excel Sheet Service"+e);
		}
    			            		            	
    	return outputFile;
    }
    
   public String generateMinutesOfSelection(String batchId, String userEmail) {
		
		LOGGER.debug("Request received from controller");
		LOGGER.debug("In Generate Minutes Of Selection Service");
		
			try {	
				
				LOGGER.debug("In try block of Generate Minutes Of Selection Committee Service");
				
			    Collection<GenerateMinutesOfSelectionDto> generateMinuteofSelection=generateReportDao.generateMinutesOfSelection(batchId,userEmail);
				
			    if (CollectionUtils.isNotEmpty(generateMinuteofSelection))
				{
			    	
			    	LOGGER.debug("Create object of JRBean Collection Data Source ");
			    	JRBeanCollectionDataSource minutesOfSelectionReportBeans = new JRBeanCollectionDataSource(generateMinuteofSelection);
				
			    	/* Map to hold Jasper Report Parameters */
			    	LOGGER.debug("Create Map to hold Jasper Report Parameters ");
			    	Map<String,Object> parameters=new HashMap<String,Object>();
			    	parameters.put("MinutesDataSource",minutesOfSelectionReportBeans);
				 
			    	/*------------------------- In PDF---------------------------------------------*/       
		         
			    	LOGGER.debug("Create object of Class Path Resource ");
			    	ClassPathResource resource=new ClassPathResource("/static/minutesOfSelectioncommittee.jasper");
			    	String userHomeDirectory = System.getProperty("user.home");
			    
			    	outputFile = userHomeDirectory + File.separatorChar + "AppData"+ File.separatorChar+"Local"+File.separatorChar+"Temp"+File.separatorChar + "MinutesOfSelectionCommittee.pdf";
			    	
			    	LOGGER.debug("THE OUTPUT FILE IS IN" +userHomeDirectory+" in ---------"+ outputFile);
		        
			    	LOGGER.debug("Getting input stream");
			    	InputStream inputStream = resource.getInputStream();
		        
			    	try {
			    		JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
			    		OutputStream outputStream = new FileOutputStream(new File(outputFile));

			    		if (printFileName != null) {
		            	
			    			LOGGER.debug("Creating the jrprint file for minutes of selection..");
			    			JasperExportManager.exportReportToPdfStream(printFileName, outputStream);
		                	LOGGER.debug("Successfully created the jrprint file >> " + printFileName);
		                	success = 1;       
		                	LOGGER.debug("PDF generated successfully..!!");
		                
		                	reportType="Minutes Of Selection Committee";
		                	generateReportsId="MSC"+batchId+userEmail;
							LOGGER.debug("Updating Database....");
				        	generateReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,userEmail);
		                
			    		} else {
			    			success = -1;
		                	LOGGER.debug("jrprint file is empty..");
			    		}
		            
			    		outputStream.close();
			    	} catch (JRException e) { 
			    		LOGGER.error("Exception caught, unable to create pdf"+e);
			    	}	
			    
					} else {
						LOGGER.debug("Collection is null");
					}
				} catch (Exception e) {
	        		LOGGER.error("In catch block of Generate Minutes Of Selection Committee Service"+e);
				}
				LOGGER.debug("the output file of minutes selections is "+ outputFile);
				return outputFile;
		}
    
    public Collection<DisplayAuditTableRecordDto> getRecordsForAuditTable(String userEmail) {
    	
    	LOGGER.debug("Request received from controller");
		LOGGER.debug("In Get Records For Audit Table Service");
		
		try {		
			
			LOGGER.debug("In try block of Get Records For Audit Table Service");
			return generateReportDao.getRecordsForAuditTable(userEmail);	
			
		}catch(Exception e) {
			LOGGER.error("In catch block of Get Records For Audit Table Service"+e);
			return null;
		}
    }
}
