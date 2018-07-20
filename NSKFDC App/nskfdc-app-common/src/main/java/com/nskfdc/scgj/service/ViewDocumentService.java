package com.nskfdc.scgj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.ViewDocumentDao;
import com.nskfdc.scgj.dto.ViewDocumentDto;

@Service
public class ViewDocumentService {

private static final Logger LOGGER = LoggerFactory.getLogger(ViewDocumentService.class);
	
	@Autowired
	private ViewDocumentDao viewDocumentDao;
	
public Collection<ViewDocumentDto> getViewTrainingPartnerDetailForBatchId(String tpName, String batchId){
			LOGGER.debug("Request received from Control");
		LOGGER.debug("In POC training Partner Service, to get training Partner details for search");
		
		try {
			LOGGER.debug("In try block to get training partner details");
			return viewDocumentDao.getViewTrainingPartnerDetailForBatchId(tpName,batchId);
		} catch (Exception e) {
		
			LOGGER.debug("An error occurred while getting the training partner details for Search"+ e);
			return null;
		}
	}

public Collection<ViewDocumentDto> getViewTrainingPartnerDetailForscgjBtNumber(String tpName ,String scgjBtNumber){
	LOGGER.debug("Request received from Control");
LOGGER.debug("In POC training Partner Service, to get training Partner details for search");

try {
	LOGGER.debug("In try block to get training partner details");
	return viewDocumentDao.getViewTrainingPartnerDetailForSearchscgjBtNumber( tpName, scgjBtNumber);
} catch (Exception e) {

	LOGGER.debug("An error occurred while getting the training partner details for Search"+ e);
	return null;
}
}

}
