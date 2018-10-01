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
	int generateOC=0;
	int generateNSKFDCReport=0;
	int generateMinutesOfSelectionCommiitteeReport=0;
	
	public Collection<GetBatchIdDto> getBatchDetails(String userEmail){
		LOGGER.debug("Request received from Controller");
		LOGGER.debug("In GenerateReportService - getBatchDetails");
		LOGGER.debug("To get Batch Ids for Logged in Training Partner");
		
	try{
		
		LOGGER.debug("TRYING -- To get batchId for Training Partner");
		LOGGER.debug("Sending request to generateReortDao - getBatchId");
		return generateReportDao.getBatchId(userEmail);
	}
	catch(Exception e){
		
		LOGGER.error("CATCHING -- Exception handled in GenerateReportService");
		LOGGER.error("In method - getBatchDetails , to get batch id of Logged in TP");
		LOGGER.error("Exception is "+e);
		return null;
	}
  }
	
	public String generateOccupationCertificateReport(String batchId, String trainingPartnerEmail) {
		
		LOGGER.debug("Request received from controller - GenerateReportService");
		LOGGER.debug("In Generate Occupation Certificate Service");
		
			try {	
				
				LOGGER.debug("TRYING -- To Generate Occupation Certificate");
				Collection<GenerateOccupationCertificateReportDto> occupationReportDetails = generateReportDao.generateOccupationCertificateReport(batchId,trainingPartnerEmail);
				if (CollectionUtils.isNotEmpty(occupationReportDetails))
				{	
					LOGGER.debug("INSIDE IF-- When collection for occupation certificate is not empty");
				
					
					/**
					 * Calling a for loop to check if all the candidates has all the details for occupation certificate
					 * and there is no candidate having any of the field required for the Occupation certificate is absent
					 * then report should not be generated 
					 * 
					 */
					
					for(GenerateOccupationCertificateReportDto occupationCertificateDetails : occupationReportDetails) {
						generateOC=0;
					
						if(occupationCertificateDetails.getAadharCardNumber() == null || occupationCertificateDetails.getAadharCardNumber().isEmpty() || occupationCertificateDetails.getGender()==null || occupationCertificateDetails.getGender().isEmpty() || occupationCertificateDetails.getFatherName()==null || occupationCertificateDetails.getFatherName().isEmpty() || occupationCertificateDetails.getResidentialAddress()==null || occupationCertificateDetails.getResidentialAddress().isEmpty() || occupationCertificateDetails.getWorkplaceAddress()==null || occupationCertificateDetails.getWorkplaceAddress().isEmpty() || occupationCertificateDetails.getAge()==null || occupationCertificateDetails.getAge().isEmpty() || occupationCertificateDetails.getAge().equals("0")){
							//To check if all the details of candidate for OC Report are present
							generateOC=0;
							break;
						}
					
						else {
							generateOC=1;
							
						}
					}							
					
					
					if(generateOC==1){
					LOGGER.debug("Create object of JRBean Collection Data Source ");
					JRBeanCollectionDataSource occupationCertificateReportBeans = new JRBeanCollectionDataSource(occupationReportDetails);
				
					/* Map to hold Jasper Report Parameters */
					LOGGER.debug("Create Map to hold Jasper Report Parameters ");
					Map<String,Object> parameters=new HashMap<String,Object>();
					parameters.put("DataSource",occupationCertificateReportBeans);
				  
		         
					LOGGER.debug("Create object of Class Path Resource - With OC Template Path");
					ClassPathResource resource=new ClassPathResource("/static/OccupationReport.jasper");
					String userHomeDirectory = System.getProperty("user.home");
					outputFile = userHomeDirectory + File.separatorChar + "AppData"+File.separatorChar+"Local"+File.separatorChar+"Temp"+File.separatorChar +"OccupationReport.pdf";
					LOGGER.debug("THE OUTPUT FILE IS AT"+ outputFile);
		        
					LOGGER.debug("Getting input stream for Occupation Certificate");
					InputStream inputStream = resource.getInputStream();
		        
					try {
						
						JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
						OutputStream outputStream = new FileOutputStream(new File(outputFile));

						if (printFileName != null) {
							LOGGER.debug("In IF -- When printFileName is not NULL");
							LOGGER.debug("Creating the Occupation Centrificate jrprint file..");
							JasperExportManager.exportReportToPdfStream(printFileName,outputStream);
							LOGGER.debug("Successfully created the Occupation Certificate jrprint file >> " + printFileName);
							success = 1;       
							LOGGER.debug("Occupation Centrificate PDF generated successfully..!!");
							reportType="Occupation Certificate";
							generateReportsId="OC"+batchId+trainingPartnerEmail;	
							LOGGER.debug("Sending Request to generateReportDao - updateTableGenerateReports");
				            LOGGER.debug("Updating generateReportTable for this generated PDF");
				            generateReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,trainingPartnerEmail);		            		            	
				            
		                
						} else {
							success = -1;
							LOGGER.debug("In ELSE -- When Occupation Certificate jrprint File is empty");

						}
						
		            	outputStream.close();
		        	} catch (JRException e) { 
		        		LOGGER.error("CATCHING -- Exception handled, while generating PDF of occupation certificate");
		        		LOGGER.error("In GenerateReportService - generateOccupationCertificateReport");
		        		LOGGER.error("Exception caught is"+e);
		        	}
					
					
				}
					
				else {
					outputFile=null;
					LOGGER.debug("Collection for Occupation certificate does not have complete details");
				}					
					
				} else {
					outputFile=null;
					LOGGER.debug("Collection is null");
					LOGGER.debug("In GeneratereportService - generateOccupationCertificateReport");
				}
			} catch (Exception e) {
				LOGGER.error("CATCHING -- Exception handled - GenerateReportService - generateOccupationCertificateReport ");
	        	LOGGER.error("While genertaing Occupation Certificate");
	        	LOGGER.error("Exception is "+ e);
			}
			return outputFile;
		}

    public String generateAttendanceSheet(String batchId, String userEmail) {
		
    	LOGGER.debug("Request received from controller - GenerateReportService");
		LOGGER.debug("In Generate Attendance Sheet Service");
    	
			try {
				LOGGER.debug("TRYING -- To Generate Attendance Sheet");
				Collection<GenerateAttendanceSheetDto> attendanceSheetDetails = generateReportDao.generateAttendanceSheet(batchId,userEmail);
				
				if (CollectionUtils.isNotEmpty(attendanceSheetDetails))
				{	
				
					LOGGER.debug("Create object of JRBean Collection Data Source ");
					JRBeanCollectionDataSource attendanceSheetBeans = new JRBeanCollectionDataSource(attendanceSheetDetails);
				
					/* Map to hold Jasper Report Parameters */
					
					LOGGER.debug("Create Map to hold Jasper Report Parameters -- Attendance Sheet ");
					Map<String,Object> parameters=new HashMap<String,Object>();
					parameters.put("DataSource",attendanceSheetBeans);
				
					       
					
					LOGGER.debug("Create object of Class Path Resource - With Attendance Sheet Template Path");
					ClassPathResource resource=new ClassPathResource("/static/AttendanceSheet.jasper");
					String userHomeDirectory = System.getProperty("user.home");
			    
					outputFile = userHomeDirectory + File.separatorChar + "AppData"+ File.separatorChar+"Local"+File.separatorChar+"Temp"+File.separatorChar + "AttendanceSheet.pdf";
					LOGGER.debug("THE OUTPUT FILE IS AT"+ outputFile);
		        
					LOGGER.debug("Getting input stream for Attendance Sheet");
					InputStream inputStream = resource.getInputStream();
					
					try {
		        	
						LOGGER.debug("TRYING -- Creating PDF for Attendance Sheet");
						JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
						OutputStream outputStream = new FileOutputStream(new File(outputFile));

						if (printFileName != null) {
							LOGGER.debug("In IF -- When printFileName is not Null");
							JasperExportManager.exportReportToPdfStream(printFileName, outputStream);
							success = 1;              
							LOGGER.debug("Attendance Sheet PDF generated successfully..!!");
							
							reportType="Attendance Sheet";
							generateReportsId="AS"+batchId+userEmail;	
				           	LOGGER.debug("Updating Database generateReports Table with this file generation entry");
				           	generateReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,userEmail);		            		            	
		                
						} else {
							LOGGER.debug("In ELSE -- When printFileName is Null");
							success = -1;
		                	LOGGER.debug("Attendance Sheet jrprint file is empty..");
						}
						outputStream.close();
					} catch (JRException e) {
						LOGGER.error("CATCHING -- Exception in GenerateReportService - generateAttendanceSheet");
						LOGGER.error("Exception caught, unable to generate Attendance Sheet pdf");
						LOGGER.error("Exception is "+e);
					}	
				} else {
					LOGGER.debug("In ELSE -- Collection of Attendance Sheet is Empty");
					LOGGER.debug("Assigning OutFile = null");
					outputFile=null;
					LOGGER.debug("Collection is null");
				}
			} catch (Exception e) {
				LOGGER.error("CATCHING -- Exception in generating Attendance Sheet pdf");
				LOGGER.error("In GenerateReportService - generateAttendanceSheet"+e);
			}            
			
			LOGGER.debug("Returning outputFile");
			return outputFile;
		}	
    
    public  String generateNSKFDCExcelSheet(String batchId,String userEmail) {
		
    	LOGGER.debug("Request received from controller to GenerateReportService");
		LOGGER.debug("In Generate NSKFDC Excelsheet");
    	
		try {
				LOGGER.debug("TRYING -- To Generate NSKFDC Excelsheet");
				Collection<GenerateNSKFDCExcelSheetDto> nSKFDCSheetDetails = generateReportDao.generateNSKFDCExcelSheet(batchId,userEmail);
				
				if (CollectionUtils.isNotEmpty(nSKFDCSheetDetails))
				{
					LOGGER.debug("In IF -- When NSKFDCSheetDetails collection is not empty");
					
					// Loop to check details for all the columns in NSKFDC sheet against each candidate are present
					for(GenerateNSKFDCExcelSheetDto nskfdcExcelDetails : nSKFDCSheetDetails) {
						
					if(nskfdcExcelDetails.getName()==null || nskfdcExcelDetails.getName().isEmpty() || nskfdcExcelDetails.getGender()==null || nskfdcExcelDetails.getGender().isEmpty() || nskfdcExcelDetails.getEducationLevel()==null || nskfdcExcelDetails.getEducationLevel().isEmpty() || nskfdcExcelDetails.getFatherName()==null || nskfdcExcelDetails.getFatherName().isEmpty() || nskfdcExcelDetails.getMotherName()==null || nskfdcExcelDetails.getMotherName().isEmpty() || nskfdcExcelDetails.getAadharCardNumber()==null || nskfdcExcelDetails.getAadharCardNumber().isEmpty() || nskfdcExcelDetails.getResidentialAddress()==null || nskfdcExcelDetails.getResidentialAddress().isEmpty() || nskfdcExcelDetails.getMobileNumber()==null || nskfdcExcelDetails.getMobileNumber().isEmpty() || nskfdcExcelDetails.getOccupationType()==null || nskfdcExcelDetails.getOccupationType().isEmpty() || nskfdcExcelDetails.getMsId()==null || nskfdcExcelDetails.getMsId().isEmpty() || nskfdcExcelDetails.getIdProofType()==null || nskfdcExcelDetails.getIdProofType().isEmpty() || nskfdcExcelDetails.getIdProofNumber()==null || nskfdcExcelDetails.getIdProofNumber().isEmpty() || nskfdcExcelDetails.getBankName()==null || nskfdcExcelDetails.getBankName().isEmpty() || nskfdcExcelDetails.getIfscCode()==null || nskfdcExcelDetails.getIfscCode().isEmpty() || nskfdcExcelDetails.getAccountNumber()==null || nskfdcExcelDetails.getAccountNumber().isEmpty() || nskfdcExcelDetails.getBatchId()==null || nskfdcExcelDetails.getBatchId().isEmpty() || nskfdcExcelDetails.getTrainingPartnerName()==null || nskfdcExcelDetails.getTrainingPartnerName().isEmpty() || nskfdcExcelDetails.getRelationWithSKMS()==null || nskfdcExcelDetails.getRelationWithSKMS().isEmpty() || nskfdcExcelDetails.getAge()==0 || nskfdcExcelDetails.getDob()==null || nskfdcExcelDetails.getDob().isEmpty()){
						
						//IF any column for a candidate in a collection is null or empty
						generateNSKFDCReport=0;	
						break;
						}
						else {
							generateNSKFDCReport=1;
						}
					}
					
					
					if(generateNSKFDCReport==1){
					LOGGER.debug("Creating object of JRBean Collection Data Source");
					JRBeanCollectionDataSource nSKFDCSheetBeans = new JRBeanCollectionDataSource(nSKFDCSheetDetails);
				
					/* Map to hold Jasper Report Parameters */
					Map<String,Object> parameters=new HashMap<String,Object>();
					parameters.put("NSKFDCDataSource",nSKFDCSheetBeans);
						                
		 			LOGGER.debug("Creating object of Class Path Resource - With NSKFDC Excelsheet");
					ClassPathResource resource=new ClassPathResource("/static/NSKFDCExcelSheet.jasper");
					String userHomeDirectory = System.getProperty("user.home");
					
					LOGGER.debug("Getting input stream for NSKFDCExcelsheet");
					InputStream inputStream = resource.getInputStream();	
					
					try {
						LOGGER.debug("TRYING -- To create NSKFDC excelSheet using Jasper");
						LOGGER.debug("Creating the NSKFDC Excelsheet jrprint file..");
						JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());	
						
						if (printFileName != null) {
							
							LOGGER.debug("In IF -- When printFileName is not NULL");
							LOGGER.debug("Exporting the NSKFDCfile to Excelsheet");
							JRXlsxExporter exporter = new JRXlsxExporter();
							exporter.setExporterInput(new SimpleExporterInput(printFileName));
							outputFile=userHomeDirectory + File.separatorChar + "AppData"+ File.separatorChar+"Local"+File.separatorChar+"Temp"+File.separatorChar + "NSKFDCSheet.xlsx";
							exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
							SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
							configuration.setRemoveEmptySpaceBetweenRows(true);
							configuration.setDetectCellType(true);
							configuration.setCollapseRowSpan(false);
							exporter.setConfiguration(configuration);
							exporter.exportReport();
						
							success = 1;              
							LOGGER.debug("NSKFDC Excel Sheet generated successfully");
							
							reportType="NSKFDC Excel Sheet";
							generateReportsId="NS"+batchId+userEmail;
					       	LOGGER.debug("Updating generateReportTable for this file generation");
					       	generateReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,userEmail);		            		            	
					 		                
						}else {
							LOGGER.debug("In ELSE -- When printFileName is NULL");
							success = -1;
							LOGGER.debug("NSKFDC Excelsheet jrprint file is empty..");
						}
					
					} catch (JRException e) { 
						LOGGER.error("CATCHING -- Exception handled in GenerateReportService - generateNSKFDCExcelSheet");
						LOGGER.error("While generating NSKFDCExcelSheet"+ e);
					}	
				
				
				}else {
					outputFile=null;
					LOGGER.debug("Collection for NSKFDC Sheet has not complete detail of candidate");
				}
				
				
				} else {
					LOGGER.debug("In ELSE -- Collection of NSKFDC Excel Sheet is Empty");
					LOGGER.debug("Assigning OutFile = null");
					outputFile=null;
					LOGGER.debug("Collection is null");
				}	
					
					
		} catch(Exception e) {
			LOGGER.error("CATCHING -- Exception handled - GenerateReportService - generateNSKFDCExcelSheet ");
        	LOGGER.error("While genertaing NSKFDCExcelSheet");
        	LOGGER.error("Exception is "+ e);
		}
    	   
    	return outputFile;
    }
    
    public String generateSDMSExcelSheet(String batchId,String userEmail) {
		

    	LOGGER.debug("Request received from controller to GenerateReportService");
		LOGGER.debug("In Generate SDMS Excelsheet");
    	
		try {
				LOGGER.debug("TRYING -- To Generate SDMS Excelsheet");
				Collection<GenerateSDMSExcelSheetDto> sDMSSheetDetails = generateReportDao.generateSDMSExcelSheet(batchId,userEmail);
				
				if (CollectionUtils.isNotEmpty(sDMSSheetDetails))
				{
					LOGGER.debug("In IF -- When SDMSSheetDetails collection is not empty");
					LOGGER.debug("Creating object of JRBean Collection Data Source");
					JRBeanCollectionDataSource sDMSSheetBeans = new JRBeanCollectionDataSource(sDMSSheetDetails);
				
					/* Map to hold Jasper Report Parameters */
					Map<String,Object> parameters=new HashMap<String,Object>();
					LOGGER.debug("Inserting collection to insert in PDF");
					parameters.put("SDMSDataSource",sDMSSheetBeans);
					       
		        
					LOGGER.debug("Creating object of Class Path Resource - With SDMS ExcelSheet Template");
					ClassPathResource resource=new ClassPathResource("/static/SDMSExcelSheet.jasper");
					String userHomeDirectory = System.getProperty("user.home");
				
					LOGGER.debug("Getting input stream for SDMSExcelSheet");
					InputStream inputStream = resource.getInputStream();	
					try {
					
						LOGGER.debug("TRYING -- To create SDMS excelSheet using Jasper");
						LOGGER.debug("Creating the SDMS Excelsheet jrprint file..");
						JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());	
						if (printFileName != null) {
							LOGGER.debug("In IF -- When printFileName is not NULL");
							LOGGER.debug("Exporting the NSKFDCfile to Excelsheet");
							JRXlsxExporter exporter = new JRXlsxExporter();
							exporter.setExporterInput(new SimpleExporterInput(printFileName));
							outputFile=userHomeDirectory + File.separatorChar + "AppData"+ File.separatorChar+"Local"+File.separatorChar+"Temp"+File.separatorChar + "SDMSSheet.xlsx";
							exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
							SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
							configuration.setRemoveEmptySpaceBetweenRows(true);
							configuration.setDetectCellType(true);
							configuration.setCollapseRowSpan(false);
							exporter.setConfiguration(configuration);
							exporter.exportReport();
						
							success = 1;              
							LOGGER.debug("SDMS Excel Sheet generated successfully..!!");
							
							reportType="SDMS Excel Sheet";
							generateReportsId="SD"+batchId+userEmail;
							LOGGER.debug("Updating generateReportTable for this file generation");
					        generateReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,userEmail);
		                
						}else {
							LOGGER.debug("In ELSE -- When printFileName is NULL");
							success = -1;
							LOGGER.debug("SDMS Excelsheet jrprint file is empty..");
						}
					
					} catch (JRException e) { 
						LOGGER.error("CATCHING -- Exception handled in GenerateReportService - generateSDMSExcelSheet");
						LOGGER.error("While generating SDMSExcelSheet"+ e);
					}	
				} else {
					LOGGER.debug("In ELSE -- Collection of SDMS Excel Sheet is Empty");
					LOGGER.debug("Assigning OutFile = null");
					outputFile=null;
					LOGGER.debug("Collection is null");
				}
		} catch(Exception e) {
			LOGGER.error("CATCHING -- Exception handled - GenerateReportService - generateSDMSExcelSheet ");
        	LOGGER.error("While genertaing SDMS ExcelSheet");
        	LOGGER.error("Exception is "+ e);
		}
    			            		            	
    	return outputFile;
    }
    
   public String generateMinutesOfSelection(String batchId, String userEmail) {
		
	   LOGGER.debug("Request received from controller to GenerateReportService");
		LOGGER.debug("To - Generate Minutes Of Selection Committee PDF");
   	
		try {
				LOGGER.debug("TRYING -- To Generate Minutes Of Selection Committee PDF");
				
				
			    Collection<GenerateMinutesOfSelectionDto> generateMinuteofSelection=generateReportDao.generateMinutesOfSelection(batchId,userEmail);
				
			    if (CollectionUtils.isNotEmpty(generateMinuteofSelection))
				{
			    	
			    	// Loop to check if all the fields in collection for minutes of selection committee meeting report are present
			    	for(GenerateMinutesOfSelectionDto m : generateMinuteofSelection) {
			    		
			    		if(m.getJobRole()==null || m.getJobRole().isEmpty() || m.getTrainingPartnerName()==null || m.getTrainingPartnerName().isEmpty() || m.getSectorSkillCouncil()==null || m.getSectorSkillCouncil().isEmpty() || m.getCentreCity()==null || m.getCentreCity().isEmpty() || m.getSelectionCommitteeDate()==null || m.getSelectionCommitteeDate().isEmpty() || m.getMunicipality()==null || m.getMunicipality().isEmpty()) {
			    			// IF condition where any of the field is absent for minutes of selection committee report
			    			generateMinutesOfSelectionCommiitteeReport=0;
			    			break;
			    		}
			    		
			    		else {
			    			//Else when all fields are present 
			    			generateMinutesOfSelectionCommiitteeReport=1;
			    		}
			    		
			    	}
			    	
		
			    	if(generateMinutesOfSelectionCommiitteeReport == 1) {
			    	
			    	LOGGER.debug("In IF -- When all fields in Minutes Of Selection Committee collection are present");
			    	LOGGER.debug("Create object of JRBean Collection Data Source ");
			    	JRBeanCollectionDataSource minutesOfSelectionReportBeans = new JRBeanCollectionDataSource(generateMinuteofSelection);
				
			    	/* Map to hold Jasper Report Parameters */
			    	LOGGER.debug("Create Map to hold Jasper Report Parameters ");
			    	Map<String,Object> parameters=new HashMap<String,Object>();
			    	parameters.put("MinutesDataSource",minutesOfSelectionReportBeans);
				        
		         
			    	LOGGER.debug("Create object of Class Path Resource - With Template of Minutes of Selection Committee ");
			    	ClassPathResource resource=new ClassPathResource("/static/minutesOfSelectioncommittee.jasper");
			    	String userHomeDirectory = System.getProperty("user.home");
			    
			    	outputFile = userHomeDirectory + File.separatorChar + "AppData"+ File.separatorChar+"Local"+File.separatorChar+"Temp"+File.separatorChar + "MinutesOfSelectionCommittee.pdf";
			    	
			    	LOGGER.debug("THE OUTPUT FILE IS AT"+ outputFile);
		        
			    	LOGGER.debug("Getting input stream");
			    	InputStream inputStream = resource.getInputStream();
		        
			    	try {
			    		JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
			    		OutputStream outputStream = new FileOutputStream(new File(outputFile));

			    		if (printFileName != null) {
		            	
			    			LOGGER.debug("In IF -- When printFileName is not NULL");
							LOGGER.debug("Creating the Minutes Of Selection Committee PDF jrprint file..");
			    			
			    			JasperExportManager.exportReportToPdfStream(printFileName, outputStream);
		                	LOGGER.debug("Successfully created Minutes Of Selection Committee PDF jrprint file >> " + printFileName);
		                	success = 1;       
		                	LOGGER.debug("PDF of Minutes Of Selection Committee generated successfully..!!");
		                
		                	reportType="Minutes Of Selection Committee";
		                	generateReportsId="MSC"+batchId+userEmail;
		                	LOGGER.debug("Sending Request to generateReportDao - updateTableGenerateReports");
				            LOGGER.debug("Updating generateReportTable for this generated PDF");
				        	generateReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,userEmail);
		                
			    		} else {
			    			success = -1;
			    			LOGGER.debug("In ELSE -- When Minutes Of Selection Committee jrprint File is empty");
			    		}
		            
			    		outputStream.close();
			    	} catch (JRException e) { 
			    		LOGGER.error("CATCHING -- Exception handled, while generating PDF of Minutes Of Selection Committee");
		        		LOGGER.error("In GenerateReportService - generateMinutesOfSelection");
		        		LOGGER.error("Exception caught is "+e);
			    	}	
			    
			    	
				}else {
					outputFile=null;
					LOGGER.debug("Report cannot be generated because all fields are not present ");
				}
			    	
					} else {
						outputFile=null;
						LOGGER.debug("Collection is null");
						LOGGER.debug("In GeneratereportService - generateMinutesOfSelection");
					}
				} catch (Exception e) {
					LOGGER.error("CATCHING -- Exception handled - GenerateReportService - generateMinutesOfSelection ");
		        	LOGGER.error("While genertaing Minutes Of Selection Committee PDF");
		        	LOGGER.error("Exception is "+ e);
				}
				
				return outputFile;
		}
    
    public Collection<DisplayAuditTableRecordDto> getRecordsForAuditTable(String userEmail) {
    	
    	LOGGER.debug("Request received from controller");
		LOGGER.debug("In Get Records For Audit Table");
		LOGGER.debug("Having generated Reports record");
		
		try {		
			LOGGER.debug("TRYING -- GenerateReportService - getRecordsForAuditTable");
			LOGGER.debug("Sending request to generateReportDao - getRecordsForAuditTable");
			return generateReportDao.getRecordsForAuditTable(userEmail);	
			
		}catch(Exception e) {
			LOGGER.error("CATCHING -- Exception in GenerateReportService - getRecordsForAuditTable");
			LOGGER.error("While getting details of generated reports by logged in TP");
			LOGGER.error("Exception is " + e);
			return null;
		}
    }
}
