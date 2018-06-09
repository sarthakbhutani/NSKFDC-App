package com.nskfdc.scgj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.service.ImportService;

@RestController
public class ImportController {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(ImportController.class);

	
	@Autowired
	private ImportService importHistoryService;
	
	@RequestMapping("/importMasterSheet")
	public void importMasterSheet() {
		
		//write LOGGER here
			
			try {
				//write LOGGER here		
				//change return type
				importHistoryService.importMasterSheet();
				
			} catch (Exception e) {
				
				//write LOGGER here
				//return the default value, it can be null
			}
		}
	
	@RequestMapping("/getImportHistory")
	public void getImportHistory() {
		
		//write LOGGER here
			
			try {
				//write LOGGER here		
				// change return type
				importHistoryService.getImportHistory();
				
			} catch (Exception e) {
				
				//write LOGGER here
				//return the default value, it can be null
			}
		}
	
	@RequestMapping("/searchMasterSheet")
	public void searchMasterSheet() {
		
		//write LOGGER here
			
			try {
				//write LOGGER here		
				// change return type
				importHistoryService.getSearchedMasterSheet();
				
			} catch (Exception e) {
				
				//write LOGGER here
				//return the default value, it can be null
			}
		}

}
