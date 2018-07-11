package com.nskfdc.scgj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;

import com.nskfdc.scgj.config.UploadDocumentConfig;

import com.nskfdc.scgj.dto.BatchIdDto;

@Repository	
public class UploadDocumentsDao extends AbstractTransactionalDao{

	@Autowired
	private UploadDocumentConfig uploadDocumentConfig;
	private static final BatchIdRowmapper BatchId_RowMapper = new BatchIdRowmapper();
	public void saveUploadedDocument() {
		//write Logger here
		
				try {
					
					//write Logger here 
					//write code here and change the return type
				}catch(Exception e) {
					
					// write Logger
					// return null;
				}
				
				
			}
	
	public void getUploadedHistory() {
		//write Logger here
		
				try {
					
					//write Logger here 
					//write code here and change the return type
				}catch(Exception e) {
					
					// write Logger
					// return null;
				}
				
				
			}
	
	public void getSearchedDocument() {
		//write Logger here
		
				try {
					
					//write Loggerhere 
					//write code here and change the return type
				}catch(Exception e) {
					
					// write Logger
					// return null;
				}
				
				
			}

			
			//write Rowmapper class here
	
	public Collection<BatchIdDto> getBatchDetail(){
		
try {
			
	System.out.println("in dao");
			return getJdbcTemplate().query(uploadDocumentConfig.getShowBatchIdDetails(), BatchId_RowMapper);
			
		} catch (Exception e) {
			
		System.out.println(e);
			return null;
			
		}
	}
private static class BatchIdRowmapper implements RowMapper<BatchIdDto>{
		
		@Override
		public BatchIdDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String batchId = rs.getString("batchId");
						
			
			return new BatchIdDto(batchId);
			
		}
		
		
	}

	
}
