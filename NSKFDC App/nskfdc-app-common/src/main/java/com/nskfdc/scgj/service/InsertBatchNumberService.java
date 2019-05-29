package com.nskfdc.scgj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.InsertBatchNumberDao;
import com.nskfdc.scgj.dto.InsertBatchNumberDto;

@Service
public class InsertBatchNumberService {

	@Autowired
	private InsertBatchNumberDao insertBatchNumberDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InsertBatchNumberService.class);
	
	public Integer insertScgjBatchNumber(String batchId,String scgjBatchNumber)
	{

		LOGGER.debug("Request recieved from controller to insert batch number for batch id : " + batchId);
		return insertBatchNumberDao.checkBatchNumberExistence(batchId, scgjBatchNumber);
	}
	
	public Integer updateExistingScgjBatchNumber(String batchId,String scgjBatchNumber)
	{
		LOGGER.debug("Request recieved from controller to insert batch number for batch id : " + batchId);
		return insertBatchNumberDao.updateScgjBatchNumber(batchId, scgjBatchNumber);
	}
	
	public Collection<InsertBatchNumberDto> showBatchNumberDetails(String batchId)
	{
		LOGGER.debug("Request recieved from controller to show batch number details for batch id : " + batchId);
		LOGGER.debug("Sending request to dao with parameters - BatchID" + batchId);
		return insertBatchNumberDao.showBatchNumberDetails(batchId);
	}
}
