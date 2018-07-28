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

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;;

@Service
public class GenerateBatchReportService {
	
	@Autowired
	private GenerateBatchReportDao generateBatchReportDao;
	
	private Logger LOGGER = LoggerFactory.getLogger(GenerateBatchReportService.class);
	
	int success=0;
	Date date=new Date();
	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	Calendar c=Calendar.getInstance();
	String hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY)); 
	String minute = Integer.toString(c.get(Calendar.MINUTE));
	String second = Integer.toString(c.get(Calendar.SECOND));
	String outputFile;
	
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a Service Method that gets the batch Ids of the particular SCGJ Batch Number which is being passed as parameters. 
	 *@return A Collection of the Batch Ids corresponding to the particular SCGJ Batch Number.
	 
	 **/
	
	public Collection<GetBatchIdDto> getBatchDetails(String userEmail){
		LOGGER.debug("Request received from Controller");
		LOGGER.debug("In Get Batch Id Service, to get Batch Ids' for Training Partner");
		
	try{
		
		LOGGER.debug("In try block to get training partner details for Training Partner");
		LOGGER.debug("Sending request to Dao");
		return generateBatchReportDao.getBatchId(userEmail);
	}
	catch(Exception e){
		
		LOGGER.error("An error occurred while getting the training partner details for Training Partner");
		return null;
	}
  }
	public Collection<LocationDetailsDto> locationDetails(String batchId){
		
		try{
			return generateBatchReportDao.getLocationDetails(batchId);
		}
		catch(Exception e){
			return null;
		}
	}
	public Collection<TrainingDetailsDto> trainingDetails(String batchId){
			
			try{
				return generateBatchReportDao.getTrainingDetails(batchId);
			}
			catch(Exception e){
				return null;
			}
		}
	public Collection<CandidateDetailsDto> candidateDetails(String batchId){
		
		try{
			return generateBatchReportDao.getCandidateDetails(batchId);
		}
		catch(Exception e){
			return null;
		}
	}
	public String generateBatchReport(String batchId, String batchnumber,String userEmail, String[] paths){
		LOGGER.debug("Request received from controller");
		LOGGER.debug("In Generate Batch Report Service");
		
		try{
			LOGGER.debug("In try block of Generate Batch Report Service");
			int insert=generateBatchReportDao.insertSCGJBatchNumber(batchId,batchnumber,userEmail);
			LOGGER.debug(""+insert);
			if(insert!=-1)
			{
			String userHomeDirectory =System.getProperty("user.dir");
			String userHomeDirectory2 = System.getProperty("user.home");
			Collection<LocationDetailsDto> locationDetails= generateBatchReportDao.getLocationDetails(batchId);
			Collection<TrainingDetailsDto> trainingDetails=generateBatchReportDao.getTrainingDetails(batchId);
			Collection<CandidateDetailsDto> candidateDetails=generateBatchReportDao.getCandidateDetails(batchId);
			
			LOGGER.debug("Create object of JRBean Collection Data Source ");
			JRBeanCollectionDataSource locationDetailsBeans = new JRBeanCollectionDataSource(locationDetails);
			JRBeanCollectionDataSource trainingDetailsBeans = new JRBeanCollectionDataSource(trainingDetails);
			JRBeanCollectionDataSource candidateDetailsBeans = new JRBeanCollectionDataSource(candidateDetails);
			
			Object i1=userHomeDirectory+File.separatorChar+"src"+File.separatorChar+"main"+File.separatorChar+"resources"+File.separatorChar+"static"+File.separatorChar+"images"+File.separatorChar+"SCGJ logo.png";
			Object i2=userHomeDirectory+File.separatorChar+"src"+File.separatorChar+"main"+File.separatorChar+"resources"+File.separatorChar+"static"+File.separatorChar+"images"+File.separatorChar+"nskfdc-logo.png";
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
			param.put("LocationDataSource",locationDetailsBeans);
			param.put("TrainingDataSource", trainingDetailsBeans);
			param.put("CandidateDataSource",candidateDetailsBeans);

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
			param.put("MedExamPic2",i14);
			param.put("Assessment",i15);
			param.put("Viva",i16);
			
			LOGGER.debug("Create object of Class Path Resource ");
		    ClassPathResource resource=new ClassPathResource("/static/FinalBatchReport.jasper");
		    
		    
		    outputFile = userHomeDirectory2 + File.separatorChar + "Downloads" + File.separatorChar + "FinalBatchReport "+df.format(date)+" "+hour+"-"+minute+"-"+second+".pdf";
		    LOGGER.debug("THE OUTPUT FILE IS IN " +userHomeDirectory2+"in ---------"+ outputFile);
	        
	        LOGGER.debug("Getting input stream");
		    InputStream inputStream = resource.getInputStream();
		    
		    try {
	            JasperPrint printFileName = JasperFillManager.fillReport(inputStream,param, new JREmptyDataSource());
	            OutputStream outputStream = new FileOutputStream(new File(outputFile));
			
	            if (printFileName != null && CollectionUtils.isNotEmpty(locationDetails)&& CollectionUtils.isNotEmpty(trainingDetails) && CollectionUtils.isNotEmpty(candidateDetails)) {
	            	
	            	LOGGER.debug("Creating the jrprint file..");
	                JasperExportManager.exportReportToPdfStream(printFileName, outputStream);
	                LOGGER.debug("Successfully created the jrprint file >> " );
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
			}

	} catch (Exception e) {
    	LOGGER.error("In catch block of Final Batch Report Service"+e);
	}
		String reportType="Final Batch Report";
		String generateReportsId="FBR"+batchId+userEmail;
if(success==1) {
        	
        	LOGGER.debug("Updating Database....");
        	generateBatchReportDao.updateTableGenerateReports(generateReportsId,date,reportType,batchId,userEmail);		            		            	
        }
	return outputFile;
	}
	public int embeddimages(MultipartFile file) {
		
		return 0;
	}
	
}

