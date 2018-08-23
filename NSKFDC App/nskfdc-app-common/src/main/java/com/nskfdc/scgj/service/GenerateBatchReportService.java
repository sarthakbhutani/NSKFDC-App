package com.nskfdc.scgj.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nskfdc.scgj.common.ReadApplicationConstants;
import com.nskfdc.scgj.common.SystemVariableReader;
import com.nskfdc.scgj.dao.GenerateBatchReportDao;
import com.nskfdc.scgj.dto.CandidateDetailsDto;
import com.nskfdc.scgj.dto.GetBatchIdDto;
import com.nskfdc.scgj.dto.LocationDetailsDto;
import com.nskfdc.scgj.dto.TrainingDetailsDto;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.FileResolver;


@Service
public class GenerateBatchReportService {

	@Autowired
	private GenerateBatchReportDao generateBatchReportDao;
	@Autowired
	private ServletContext context;
	
	@Autowired
	private SystemVariableReader systemVariableReader;
	@Autowired
	private ReadApplicationConstants readApplicationConstants;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GenerateBatchReportService.class);

	int success = 0;
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	Calendar c = Calendar.getInstance();
	String hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
	String minute = Integer.toString(c.get(Calendar.MINUTE));
	String second = Integer.toString(c.get(Calendar.SECOND));
	String outputFile;

	/**
	 * 
	 * @author Samridhi Srivastava
	 * @description This method is a Service Method that gets the batch Ids of
	 *              the particular SCGJ Batch Number which is being passed as
	 *              parameters.
	 * @return A Collection of the Batch Ids corresponding to the particular
	 *         SCGJ Batch Number.
	 **/
	public Collection<GetBatchIdDto> getBatchDetails(String userEmail)
	 {
		LOGGER.debug("Request received from Controller");
		LOGGER.debug("In Get Batch Id Service, to get Batch Ids' for Training Partner");

		try {

			LOGGER.debug("TRYING -- To get batch Id of Training Partner");
			LOGGER.debug("Sending request to Dao - getBatchId");
			return generateBatchReportDao.getBatchId(userEmail);
		} catch (Exception e) {
			LOGGER.error("CATCHING -- Exception in GEnerate Batch Report Service");
			LOGGER.error("An error occurred while getting batch Id for Training Partner");
			LOGGER.error("Excetion Handled is " + e);
			return null;
		}
	}
	
		
	

	/**
	 * Method to get Location details corresponding to BatchId
	 * @param batchId
	 * @return
	 */
	public Collection<LocationDetailsDto> locationDetails(String batchId) {

		LOGGER.debug("In Generate Batch Report Service");
		LOGGER.debug("To get Location Details - locationDetails");
		try {
			LOGGER.debug("TRYING -- to get location details");
			LOGGER.debug("Sending request to Generate Batch Report DAO");
			return generateBatchReportDao.getLocationDetails(batchId);
		} catch (Exception e) {
			LOGGER.error("CATCHING -- Exception handled while getting Location Details");
			LOGGER.error("In method locationDetails");
			LOGGER.error("An exception is" + e);
			return null;
		}
	}

	/**
	 * Method to get details of training partners based on batch Id
	 * @param batchId
	 * @return
	 */
	public Collection<TrainingDetailsDto> trainingDetails(String batchId) {

		LOGGER.debug("In Generate Batch Report Service");
		LOGGER.debug("To get details of Training of entered Batch - trainingDetails");
		try {
			LOGGER.debug("TRYING -- to get training details");
			LOGGER.debug("Sending request to Generate Batch Report DAO");
			return generateBatchReportDao.getTrainingDetails(batchId);
		} catch (Exception e) {
			LOGGER.error("CATCHING -- EXCEPTION handled while getting training Details");
			LOGGER.error("In method - trainingDetails");
			LOGGER.error("An exception is" + e);
			return null;
		}
	}

	/**
	 * Method to get details of Candidates based on batch Id
	 * @param batchId
	 * @return
	 */
	public Collection<CandidateDetailsDto> candidateDetails(String batchId) {
		LOGGER.debug("In Generate Batch Service");
		LOGGER.debug("Method - candidateDetails");

		try {
			LOGGER.debug("TRYING -- To get candidate Details of entered Batch Id");
			LOGGER.debug("Sending the request to generateBatchReportDao - getCandidateDetails");
			return generateBatchReportDao.getCandidateDetails(batchId);
		} catch (Exception e) {
			LOGGER.error("CATCHING -- EXCEPTION Handled in candidateDetails");
			LOGGER.error("An exception is" + e);
			return null;
		}
	}

	/**
	 * Method to generate Final Batch report
	 * @param batchId
	 * @param batchnumber
	 * @param userEmail
	 * @param paths
	 * @param serverPath 
	 * @return
	 */
	public String generateBatchReport(String batchId, String batchnumber,String userEmail, String[] paths){
		LOGGER.debug("Request received from controller");
		LOGGER.debug("In Generate Batch Report Service");
		LOGGER.debug("To generate Final Batch Report PDF - generateBatchReport");
		
		try{
			LOGGER.debug("TRYING -- Final Batch Report PDF Generation");
			LOGGER.debug("1. Insert SCGJ Batch Number in the Database");
			LOGGER.debug("Sending request to Generate Batch Report DAO - insertSCGJBatchNumber");
			LOGGER.debug("SCGJ Batch Number Inserted Successfully");
			String sourceCodeDir =System.getProperty("user.dir");
			String userHomeDir = System.getProperty("user.home");
			
			LOGGER.debug("Sending request to generateBatchReportDao to get Location Details");
			Collection<LocationDetailsDto> locationDetails= generateBatchReportDao.getLocationDetails(batchId);
			
			LOGGER.debug("Sending request to generateBatchReportDao to get Training Details");
			Collection<TrainingDetailsDto> trainingDetails=generateBatchReportDao.getTrainingDetails(batchId);
			
			LOGGER.debug("Sending request to generateBatchReportDao to get Candidate Details");
			Collection<CandidateDetailsDto> candidateDetails=generateBatchReportDao.getCandidateDetails(batchId);
			
			LOGGER.debug("Create object of JRBean Collection Data Source -- locationDetails");
			JRBeanCollectionDataSource locationDetailsBeans = new JRBeanCollectionDataSource(locationDetails);
			
			LOGGER.debug("Create object of JRBean Collection Data Source -- trainingDetails");
			JRBeanCollectionDataSource trainingDetailsBeans = new JRBeanCollectionDataSource(trainingDetails);
			
			LOGGER.debug("Create object of JRBean Collection Data Source -- candidateDetails");
			JRBeanCollectionDataSource candidateDetailsBeans = new JRBeanCollectionDataSource(candidateDetails);
			FileResolver fileResolver = new FileResolver() {

				 @Override
				 public File resolveFile(String fileName) {
				  return new File(fileName);
				 }
				};
			LOGGER.debug("Inserting Paths of Images in Objects");
			LOGGER.debug(sourceCodeDir);
			String imagesPath = systemVariableReader.readSystemVariable(readApplicationConstants.getServerPathImages());
            String i1 = imagesPath+ File.separatorChar+"SCGJ logo.png";
            String i2 = imagesPath +File.separatorChar +"nskfdc-logo.png";
			Object i3=paths[0];
			Object i4=paths[1];
			Object i5=paths[2];
			Object i6=paths[3];
			Object i7=paths[4];
			Object i8=paths[5];
			Object i9=paths[6];
			Object i10=paths[7];
			Object i11=paths[8];
			Object i12=paths[9];
			Object i13=paths[10];
			Object i14=paths[11];
			Object i15=paths[12];
			Object i16=paths[13];
			
			/* Map to hold Jasper Report Parameters */
			LOGGER.debug("Create Map to hold Jasper Report Parameters ");
			Map<String,Object> param=new HashMap<String,Object>();
			LOGGER.debug("Inserting dataSource beans in a param - HashMap");
			param.put("LocationDataSource",locationDetailsBeans);
			param.put("TrainingDataSource", trainingDetailsBeans);
			param.put("CandidateDataSource",candidateDetailsBeans);
			LOGGER.debug("Inserting Image objects in a param - HashMap");
			param.put("ImageSource",i1);
			param.put("ImageSource2",i2);
			param.put("Day1Pic1",i3);
			param.put("Day1Pic2",i4);
			param.put("Day2Pic1",i5);
			param.put("Day2Pic2",i6);
			param.put("Day3Pic1",i7);
			param.put("Day3Pic2",i8);
			param.put("Day4Pic1",i9);
			param.put("Day4Pic2",i10);
			param.put("Day5Pic1",i11);
			param.put("Day5Pic2",i12);
            param.put("MedExamPic1",i13);
            param.put("AssessPic1",i14);
            param.put("AssessPic2",i15);
            param.put("Viva",i16);
			param.put("REPORT_FILE_RESOLVER", fileResolver);
			
			LOGGER.debug("Create object of Class Path Resource ");
		    ClassPathResource resource=new ClassPathResource("/static/FinalBatchReport.jasper");
		    
		    //outputFile = userHomeDirectory2 + File.separatorChar + "Downloads" + File.separatorChar + "FinalBatchReport "+df.format(date)+" "+hour+"-"+minute+"-"+second+".pdf";
		    outputFile = userHomeDir + File.separatorChar + "AppData"+File.separatorChar+"Local"+File.separatorChar+"Temp"+File.separatorChar + "FinalBatchReport.pdf";
		    LOGGER.debug("THE OUTPUT FILE Path IS  " + outputFile);
	        
	        LOGGER.debug("Getting input stream");
		    InputStream inputStream = resource.getInputStream();
		    
		   
		    	LOGGER.debug("TRYING -- To Print Jasper Report PDF");
		    	LOGGER.debug("To generate Final Batch Report");

	            //Creating File Stream for Permissions of file
		            		
		            		FileOutputStream finalReportOut = new FileOutputStream(new File(outputFile));
		    	            OutputStream outputStream = finalReportOut;
		    	            LOGGER.debug("Output stream created successfully in Batch Report service");
		    	            
		    	            
		    	            
		    	            
		    	            LOGGER.debug("Values of Locations DTO : "+locationDetails);
		    	            LOGGER.debug("Values of training Details Dto : "+ trainingDetails);
		    	            LOGGER.debug("Values of candidates details : "+ candidateDetails);
		    	            
		    	            //If not null then export file to PDf using template
		    	            if (CollectionUtils.isNotEmpty(candidateDetails)) 
		    	            {
		    	            	LOGGER.debug("Candidates details are present");
		    	            	if(CollectionUtils.isNotEmpty(locationDetails))
		    	            	{
		    	            		LOGGER.debug("Location Details are present");
		    	            		if(CollectionUtils.isNotEmpty(trainingDetails) )
		    	            		{
		    	            			LOGGER.debug("Training details are present");
		    	            			LOGGER.debug("Trying to fill Report using JasperFillManager");
		    	            			JasperPrint printFileName = JasperFillManager.fillReport(inputStream,param, new JREmptyDataSource());
		    	            			LOGGER.debug("Output received from Jasper fill manager is " + printFileName);
		    	            			if(printFileName != null)
		    	            			{
		    		    	            	LOGGER.debug("IN IF -- to export the Final Batch Report -- jrprint file..");
		    		    	                JasperExportManager.exportReportToPdfStream(printFileName, outputStream);
		    		    	                LOGGER.debug("Successfully created the jrprint file for Final Batch Report " );
		    		    	                success = 1;       
		    		    	                LOGGER.debug("PDF of Final Batch Report is generated successfully..!!");
		    	            			}
		    	            			else
		    	            			{
		    	            				LOGGER.debug("Candiadtes Details are empty");
		    	            				success = -1;
		    		    	                LOGGER.debug("IN ELSE -- When PDF file is empty");
		    		    	                LOGGER.debug("Final batch Report -- jrprint file is empty..");
		    	            			}
		    	            		}
		    	            		else
		    	            		{
		    	            			LOGGER.debug("Training Details are empty");
		    	            		}
		    	            	}
		    	            	else
		    	            	{
		    	            		LOGGER.debug("Location Details are null, can not create Final batch report");
		    	            	}
		    	            			    	                
		    	            } 
		    	            else 
		    	            {
		    	                LOGGER.debug("Candidates Details are empty");
		    	            }
		    	            outputStream.close();
			           // }
			
			//To add a row in generated reports for Audit table
			String reportType="Final Batch Report";
			String generateReportsId="FBR"+batchId+userEmail;
			if(success==1) 
			{
	        	LOGGER.debug("IN IF -- When PDF generated successfully.");
	        	LOGGER.debug("Updating Database table records having details regarding printing PDF");
	        	LOGGER.debug("Sending Request to DAO - updateTableGenerateReports");
	        	generateBatchReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,userEmail);		            		            	
	        }
			
		}
		
		catch (JRException e)
		{ 
			LOGGER.error("CATCHING -- JRE Exception ");
	       	LOGGER.error("Exception is in method - generateBatchReport i.e. : "+e);
	       	outputFile = null;
		}
		catch (Exception e) 
		{
			LOGGER.error("CATCHING -- Exception handled while performing PDF Generation for Final Batch Report");
    		LOGGER.error("Exception is in generateBatchReport"+e);
    		outputFile = null;
    	}
		
		return outputFile;
	}

	public int embeddimages(MultipartFile file) {
		LOGGER.debug("In embeddimages method - in Generate Batch Report Service");
		return 0;
	}
	
	/**
	 * This method fetches the batch number for the batch id
	 * @param batchId
	 * @return batch Number
	 */
	public String showScgjbatchNumber(String batchId)
	{
		LOGGER.debug("Request recieved from controller to get batch number for batch Id : " + batchId);
		LOGGER.debug("Sending request to DAO with parameters : " + batchId);
		return generateBatchReportDao.showScgjbatchNumber(batchId);
	}
}
