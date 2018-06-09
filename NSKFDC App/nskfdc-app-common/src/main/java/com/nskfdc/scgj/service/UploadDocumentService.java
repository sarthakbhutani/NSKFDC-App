package com.nskfdc.scgj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.UploadDocumentsDao;

@Service
public class UploadDocumentService {

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
	
	
}
