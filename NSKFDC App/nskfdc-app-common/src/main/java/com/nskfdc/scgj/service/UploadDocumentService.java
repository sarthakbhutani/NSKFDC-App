package com.nskfdc.scgj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.nskfdc.scgj.dao.UploadDocumentsDao;
import com.nskfdc.scgj.dto.BatchIdDto;

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
	
	public void getUploadHistory() {
		
		//write LOGGER here
			
			try {
				//write LOGGER here		
				//write your logic here & change return type
				uploadDocumentsDao.getUploadedHistory();
				
			} catch (Exception e) {
				
				//write LOGGER here
				//return the default value, it can be null
			}
			
		}
			
	public void getSearchedDocument() {
		
		//write LOGGER here
			
			try {
				//write LOGGER here		
				//write your logic here & change return type
				uploadDocumentsDao.getSearchedDocument();
				
			} catch (Exception e) {
				
				//write LOGGER here
				//return the default value, it can be null
			}
		}		
	
	
	
	public Collection<BatchIdDto> getBatchDetails(){
		LOGGER.debug("Request received from upload documents Controller to upload documents service");
		
		
		try {
			LOGGER.debug("In try block of upload documents service");
			return uploadDocumentsDao.getBatchDetail();
			
		}
		catch(Exception e){
			LOGGER.debug("An error occurred in upload documents service"+ e);
			return null;
		}
		
	}
	
}
