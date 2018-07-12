package com.nskfdc.scgj.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.GenerateBatchReportConfig;
import com.nskfdc.scgj.dto.GetBatchIdDto;

@Repository
public class GenerateBatchReportDao extends AbstractTransactionalDao {
	
	@Autowired
	private GenerateBatchReportConfig generateBatchReportConfig;
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateBatchReportDao.class);
	private static final BatchIdRowmapper batchIdRowmapper= new BatchIdRowmapper();
	
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a DAO Method that gets the batch Ids of the particular SCGJ Batch Number which is being passed as parameters. 
	 *@return A Collection of the Batch Ids corresponding to the particular SCGJ Batch Number.
	 
	 **/
	
	public Collection<GetBatchIdDto> getBatchId(String batchNumber){
     
	 Map<String, Object> parameters = new HashMap<>();
	 parameters.put("batchnumber",batchNumber);
	 
	 LOGGER.debug("Request received from Service");
	 LOGGER.debug("In GetBatchIdDao, to get Batch Ids' for Training Partner");
	
	try{
		
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get batch ids for Training Partner");
		return  getJdbcTemplate().query(generateBatchReportConfig.getShowBatchId(),parameters, batchIdRowmapper);
	}
	catch(Exception e){
		
		LOGGER.error("An error occurred while getting the training partner details for Training Partner");
		return null;
	}
	}

	
	private static class BatchIdRowmapper implements RowMapper<GetBatchIdDto>{

		/**
		 
		 *@author Samridhi Srivastava
		 *@description This method is a RowMapper Method that gets the batch Ids of the particular SCGJ Batch Number which is being passed as parameters from the database, maps it to a Batch Id column and sends it to the DTO to set values and make a collection. 
		 *@return  Batch Ids corresponding to the particular SCGJ Batch Number to the GetBatchIdDto Parameterized Constructor.
		 
		 **/
		
		@Override
		public GetBatchIdDto mapRow(ResultSet rs, int rowNum)throws SQLException {
			
			String batchId= rs.getString("batchId");
			
			return new GetBatchIdDto(batchId);
		}
		
	}
	
}
