package com.nskfdc.scgj.service;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.common.FileUtility;
import com.nskfdc.scgj.common.ReadApplicationConstants;
import com.nskfdc.scgj.dao.ViewDocumentDao;
import com.nskfdc.scgj.dto.UploadDocumentsDto;
import com.nskfdc.scgj.dto.ViewDocumentDto;

@Service
public class ViewDocumentService {

private static final Logger LOGGER = LoggerFactory.getLogger(ViewDocumentService.class);
	
	@Autowired
	private ViewDocumentDao viewDocumentDao;
	
	@Autowired
	private FileUtility fileUtility;
	
	@Autowired
	private ReadApplicationConstants readApplicationConstants;
	
	public Collection<ViewDocumentDto> getViewTrainingPartnerDetailForBatchId(String tpName, String batchId){
			LOGGER.debug("Request received from Controller to ViewDocumentService");
			LOGGER.debug("In getViewTrainingPartnerDetailForBatchId - to get TP documents based on its searched BatchId");
		
		try {
			LOGGER.debug("TRYING -- To get uploadedDocuments of TP based on BatchId");
			LOGGER.debug("Sending request to ViewDocumentDao - getViewTrainingPartnerDetailForBatchId");
			return viewDocumentDao.getViewTrainingPartnerDetailForBatchId(tpName,batchId);
			
		} catch (Exception e) {
		
				LOGGER.error("CATCHING -- Exception handled while getting uploadedDocuments of TP based on BatchId");
	            LOGGER.error("In ViewDocumentService - getViewTrainingPartnerDetailForBatchId");
	            LOGGER.error("The Exception is : " + e);
	            LOGGER.error("Returning null");
				return null;
			}
		}

		public Collection<ViewDocumentDto> getViewTrainingPartnerDetailForscgjBtNumber(String tpName ,String scgjBtNumber){
			
			LOGGER.debug("Request received from Controller to ViewDocumentService");
			LOGGER.debug("In getViewTrainingPartnerDetailForscgjBtNumber - to get TP documents based on its searched SCGJBatchNumber");
		
		try {
			LOGGER.debug("TRYING -- To get uploadedDocuments of TP based on SCGJBatchNumber");
			LOGGER.debug("Sending request to ViewDocumentDao - getViewTrainingPartnerDetailForSearchscgjBtNumber");
			return viewDocumentDao.getViewTrainingPartnerDetailForSearchscgjBtNumber( tpName, scgjBtNumber);
		
		} catch (Exception e) {
		
			LOGGER.error("CATCHING -- Exception handled while getting uploadedDocuments of TP based on SCGJBatchNumber");
            LOGGER.error("In ViewDocumentService - getViewTrainingPartnerDetailForscgjBtNumber");
            LOGGER.error("The Exception is : " + e);
            LOGGER.error("Returning null");
			return null;
			}
		}
		
		
		/**
		 * Method to create zip file on a location
		 * @param batchId
		 * @param userEmail
		 * @return path of zip file created
		 */
		public String createZipFileForTPandBatchId(String tpName, String batchId)
		{
			String zipPath = null;
			Collection<ViewDocumentDto> searchResult = viewDocumentDao.getViewTrainingPartnerDetailForBatchId(tpName, batchId);
			Collection<String> filesForZip = new ArrayList<String>();
			if (searchResult.size() > 1)
			{
				LOGGER.error("Can not download file, There are more than one search results for batch Id : "+batchId);
			}
			else
			{
				for(ViewDocumentDto item : searchResult)
				{
					 if(item.getFinalBatchReportPath()!=null){
						  LOGGER.debug("Adding path of Final Batch report to the lists of files to be zipped");
						  filesForZip.add(item.getFinalBatchReportPath());
					  }
					  if(item.getOccupationCertificatePath()!=null){
						  LOGGER.debug("Adding path of Occupation Certificate to the lists of files to be zipped");
						  filesForZip.add(item.getOccupationCertificatePath());
					  }
					  if(item.getMinuteOfSelectionCommitteePath()!=null){
						  LOGGER.debug("Adding path of minutes of selection committee to the lists of files to be zipped");
						  filesForZip.add(item.getMinuteOfSelectionCommitteePath());
					  }
					  if(item.getDataSheetForSDMSPath()!=null){
						  LOGGER.debug("Adding path of Data sheet for SDMS to the lists of files to be zipped");
						  filesForZip.add(item.getDataSheetForSDMSPath());
					  }
					  if(item.getDataSheetForNSKFCPath()!=null){
						  LOGGER.debug("Adding path of Data sheet for NSKFDC to the lists of files to be zipped");
						  filesForZip.add(item.getDataSheetForNSKFCPath());
					  }
					  if(item.getAttendanceSheetPath()!=null){
						  LOGGER.debug("Adding path of attendance sheet to the lists of files to be zipped");
						  filesForZip.add(item.getAttendanceSheetPath());
					  }
					
					
				}
			}
			String zipLocationRead = readApplicationConstants.getSaveTempZip();  
			zipPath = fileUtility.createZip(filesForZip,batchId,zipLocationRead);
			return zipPath;
		}
		
		/**
		 * Method to create zip file on a location
		 * @param batchId
		 * @param userEmail
		 * @return path of zip file created
		 */
		public String createZipFileForTPandBatchNumber(String tpName, String scgjBtNumber)
		{
			String zipPath = null;
			Collection<ViewDocumentDto> searchResult = viewDocumentDao.getViewTrainingPartnerDetailForSearchscgjBtNumber(tpName, scgjBtNumber);
			Collection<String> filesForZip = new ArrayList<String>();
			if (searchResult.size() > 1)
			{
				LOGGER.error("Can not download file, There are more than one search results for batch number : " + scgjBtNumber);
			}
			else
			{
				for(ViewDocumentDto item : searchResult)
				{
					 if(item.getFinalBatchReportPath()!=null){
						  LOGGER.debug("Adding path of Final Batch report to the lists of files to be zipped");
						  filesForZip.add(item.getFinalBatchReportPath());
					  }
					  if(item.getOccupationCertificatePath()!=null){
						  LOGGER.debug("Adding path of Occupation Certificate to the lists of files to be zipped");
						  filesForZip.add(item.getOccupationCertificatePath());
					  }
					  if(item.getMinuteOfSelectionCommitteePath()!=null){
						  LOGGER.debug("Adding path of minutes of selection committee to the lists of files to be zipped");
						  filesForZip.add(item.getMinuteOfSelectionCommitteePath());
					  }
					  if(item.getDataSheetForSDMSPath()!=null){
						  LOGGER.debug("Adding path of Data sheet for SDMS to the lists of files to be zipped");
						  filesForZip.add(item.getDataSheetForSDMSPath());
					  }
					  if(item.getDataSheetForNSKFCPath()!=null){
						  LOGGER.debug("Adding path of Data sheet for NSKFDC to the lists of files to be zipped");
						  filesForZip.add(item.getDataSheetForNSKFCPath());
					  }
					  if(item.getAttendanceSheetPath()!=null){
						  LOGGER.debug("Adding path of attendance sheet to the lists of files to be zipped");
						  filesForZip.add(item.getAttendanceSheetPath());
					  }
					
					
				}
			}
			String zipLocationRead = readApplicationConstants.getSaveTempZip();  
			zipPath = fileUtility.createZip(filesForZip,scgjBtNumber,zipLocationRead);
			return zipPath;
		}

}
