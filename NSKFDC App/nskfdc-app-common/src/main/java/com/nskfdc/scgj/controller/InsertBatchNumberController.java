/**
 * 
 */


package com.nskfdc.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.common.Privilege;
import com.nskfdc.scgj.dto.InsertBatchNumberDto;
import com.nskfdc.scgj.service.InsertBatchNumberService;

@RestController
public class InsertBatchNumberController {

	@Autowired
	private InsertBatchNumberService insertBatchNumberService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InsertBatchNumberController.class);

	@Privilege(value= {"TP"})
	@RequestMapping("/insertBatchNumber")
	public Integer insertScgjBatchNumber(@RequestParam("batchId")String batchId,@RequestParam("scgjBatchNumber")String scgjBatchNumber)
	{
		LOGGER.debug("Request recieved from front end to insert SCGJ Batch Number for batch id : " + batchId);
		LOGGER.debug("Sending parameters to service");
		return insertBatchNumberService.insertScgjBatchNumber(batchId, scgjBatchNumber);
	
	}
	
	@Privilege(value= {"TP"})
	@RequestMapping("/updateBatchNumber")
	public Integer updateExistingScgjBatchNumber(@RequestParam("batchId")String batchId,@RequestParam("scgjBatchNumber")String scgjBatchNumber)
	{
		LOGGER.debug("Request recieved from front end to update SCGJ Batch Number for batch id : " + batchId);
		LOGGER.debug("Sending parameters to service");
		return insertBatchNumberService.updateExistingScgjBatchNumber(batchId, scgjBatchNumber);
	
	}
	
	@Privilege(value= {"TP"})
	@RequestMapping("/showDetails")
	public Collection<InsertBatchNumberDto> showBatchNumberDetails(@RequestParam("batchId")String batchId)
	{
		LOGGER.debug("Request recieved from front end to show details of batch number for batch id : " + batchId);
		LOGGER.debug("Sending request to service with parameter batchId : " + batchId);
		return insertBatchNumberService.showBatchNumberDetails(batchId);
				
	}
	
}
