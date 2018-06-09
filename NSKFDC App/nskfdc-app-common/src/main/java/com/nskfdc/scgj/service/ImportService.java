package com.nskfdc.scgj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.ImportDao;

@Service
public class ImportService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(ImportService.class);
	
	@Autowired
	private ImportDao importHistoryDao;
	
	
/*--------------Method to Import Master Sheet----------------*/
	
	public void importMasterSheet() {
		
	//write LOGGER here
		
		try {
			//write LOGGER here		
			//write your logic here & change return type
			importHistoryDao.importMasterSheet();
			
		} catch (Exception e) {
			
			//write LOGGER here
			//return the default value, it can be null
		}
	}
	
	
	
	
/*--------------Method to get Import History----------------*/
	
	public void getImportHistory() {
		
	//write LOGGER here
		
		try {
			//write LOGGER here		
			//write your logic here & change return type
			importHistoryDao.getImportHistory();
			
		} catch (Exception e) {
			
			//write LOGGER here
			//return the default value, it can be null
		}
	}
	
	
	
	
/*--------------Method to get searched Master sheet----------------*/
	
	public void getSearchedMasterSheet() {
		
	//write LOGGER here
		
		try {
			//write LOGGER here		
			//write your logic here & change return type
			importHistoryDao.getSearchedMasterSheet();
			
		} catch (Exception e) {
			
			//write LOGGER here
			//return the default value, it can be null
		}
	}

}
