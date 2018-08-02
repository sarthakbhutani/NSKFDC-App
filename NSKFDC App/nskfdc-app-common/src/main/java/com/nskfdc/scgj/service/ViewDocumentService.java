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

}
