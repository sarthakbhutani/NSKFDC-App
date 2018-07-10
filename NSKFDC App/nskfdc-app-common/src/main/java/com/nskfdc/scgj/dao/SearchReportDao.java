package com.nskfdc.scgj.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.SearchReportConfig;
import com.nskfdc.scgj.dto.SearchReportDto;



@Repository
public class SearchReportDao extends AbstractTransactionalDao{
private static final Logger LOGGER= LoggerFactory.getLogger(SearchReportDao.class);
	
	private static final SearchReportRowmapper SearchReport_RowMapper = new SearchReportRowmapper();
	
	@Autowired
	private SearchReportConfig searchreportconfig;
		
		public Collection<SearchReportDto> getReport(int batchid) {
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put("batchid",batchid);
		
		

		try {  
			
			
			LOGGER.debug("In try block");
			LOGGER.debug("Execute query to get BATCH details ");
			return getJdbcTemplate().query(searchreportconfig.getShowReport(), parameters, SearchReport_RowMapper);
				
				
				
				
				
		
			
		} catch (Exception e) {
			
			LOGGER.debug("In Catch Block");
			LOGGER.debug("An error occured while getting the REPORT" + e);
			return null;
			
		}
		
	}
	
private static class SearchReportRowmapper implements RowMapper<SearchReportDto>{
		
		@Override
		public SearchReportDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			int batchid = rs.getInt("batchid");
			String type = rs.getString("type");
			Date generatedOn = rs.getDate("generatedOn");
			String generatedBy = rs.getString("generatedBy");
			String viewDocument = rs.getString("viewDocument");			
			
			return new SearchReportDto(batchid,type,generatedOn,generatedBy,viewDocument);
			
		}
		
		
	}
	
	
	
	}


