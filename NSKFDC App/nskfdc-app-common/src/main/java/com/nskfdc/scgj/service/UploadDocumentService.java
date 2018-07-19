package com.nskfdc.scgj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.UploadDocumentsDao;
import com.nskfdc.scgj.dto.BatchIdDto;
import com.nskfdc.scgj.dto.UploadDocumentsDto;

@Service
public class UploadDocumentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadDocumentService.class);

	@Autowired
	private UploadDocumentsDao uploadDocumentsDao;
	
	public void saveUploadedDocument() {
		
		//write LOGGER here
			
			try {
				//write LOGGER here		
				//write your logic here & change return type
				uploadDocumentsDao.saveUploadedDocument();
				
			} catch (Exception e) {
				
				//write LOGGER here
				//return the default value, it can be null
			}
		}


		public Collection<UploadDocumentsDto> getSearchedDocument(String batchId, String userEmail){
		//write LOGGER here
		LOGGER.debug("Request received from Controller");
		LOGGER.debug("In getSearchedDocumentService");
			
			try {
			
				LOGGER.debug("In try block of UploadDocumentService to get search results for batch Id: " + batchId);
				return uploadDocumentsDao.getSearchedDocument(batchId, userEmail);
				
			} catch (Exception e) {
				
				LOGGER.debug("An error occurred while getting the Document details for UploadDocumentsService"+ e);
				LOGGER.debug("Return NULL");
				return null;
				
			}
		}		
		public int getBatchDetails(String userEmail){
			LOGGER.debug("Request received from upload documents Controller to upload documents service"+ userEmail);
			
			
			try {
				LOGGER.debug("In try block of upload documents service"+ userEmail);
				return uploadDocumentsDao.getBatchDetail(userEmail);
				
			}
			catch(Exception d) {
				
				LOGGER.error("DataAccessException in service to create Batch" + d);
				
				return -1;
			}

			
		}
		public int scgjBatchIdField(String batchId, String scgjBatchId) {
			
			LOGGER.debug("Request received from controller");
			LOGGER.debug("In Scgj Details Service");
			
				try {	
					
					LOGGER.debug("In try block of Scgj Details Service");
					
				return uploadDocumentsDao.scgjBatchIdField(batchId, scgjBatchId);
				
					
					
			         
				}
				catch(Exception e){
				return 0;	
				}
		}
		public int BatchIdField(String batchId) {
			
			LOGGER.debug("Request received from controller");
			LOGGER.debug("In Scgj Details Service");
			
				try {	
					
					LOGGER.debug("In try block of Scgj Details Service");
					
				return uploadDocumentsDao.BatchIdField(batchId);
				
					
					
			         
				}
				catch(Exception e){
				return 0;	
				}
		}
		
	
}
