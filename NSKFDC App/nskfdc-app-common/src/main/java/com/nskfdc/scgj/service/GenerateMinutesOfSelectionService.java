package com.nskfdc.scgj.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


//import com.nskfdc.scgj.controller.GenerateMinutesOfSelectionController;
import com.nskfdc.scgj.dao.GenerateMinutesOfSelectionDao;
import com.nskfdc.scgj.dto.GenerateMinutesOfSelectionDto;


 
@Service
public class GenerateMinutesOfSelectionService {
	private static final Logger LOGGER=LoggerFactory.getLogger(GenerateMinutesOfSelectionService.class);

	
	
	int success = 0;
	
	@Autowired
	private GenerateMinutesOfSelectionDao generateminutesofselectiondao;
	
	public int generateMinutesOfSelection(String jobRole, String trainingPartnerName,String sectorSkillCouncil,String centreCity) {
		
		LOGGER.debug("Request received from controller");
		LOGGER.debug("In Generate Minutes Of Selection Service");
		
			try {	
				
				LOGGER.debug("In try block of Generate Minutes Of Selection Committee Service");
				
			 Collection<GenerateMinutesOfSelectionDto> generateMinuteofSelection=generateminutesofselectiondao.generateMinutesOfSelection( /*jobRole, trainingPartnerName,sectorSkillCouncil, centreCity*/);
				
						
				
				LOGGER.debug("Create object of JRBean Collection Data Source ");
				
				JRBeanCollectionDataSource minutesOfSelectionReportBeans = new JRBeanCollectionDataSource(generateMinuteofSelection);
				
				/* Map to hold Jasper Report Parameters */
				LOGGER.debug("Create Map to hold Jasper Report Parameters ");
				Map<String,Object> parameters=new HashMap<String,Object>();
				parameters.put("DataSource",minutesOfSelectionReportBeans);
				 
				 /*------------------------- In PDF---------------------------------------------*/       
		         
				LOGGER.debug("Create object of Class Path Resource ");
			    ClassPathResource resource=new ClassPathResource("/static/minutesOfSelectionCommittee.jasper");
			    String userHomeDirectory = System.getProperty("user.home");
			    
			    String outputFile = userHomeDirectory + File.separatorChar + "Downloads" + File.separatorChar + "MinutesOfSelectionCommittee.pdf";
			    LOGGER.debug("THE OUTPUT FILE IS IN" +userHomeDirectory+"in ---------"+ outputFile);
		        
		        LOGGER.debug("Getting input stream");
			    InputStream inputStream = resource.getInputStream();
		        
			    try {
		            JasperPrint printFileName = JasperFillManager.fillReport(inputStream, parameters, new JREmptyDataSource());
		            OutputStream outputStream = new FileOutputStream(new File(outputFile));

		            if (printFileName != null && CollectionUtils.isNotEmpty(generateMinuteofSelection)) {
		            	
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
	        	LOGGER.error("In catch block of Generate Minutes Of Selection Committee Service"+e);
			}
			LOGGER.error("Successfully returned");
			return success;
		}
	
			
		}
	


