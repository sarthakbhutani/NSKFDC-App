package com.nskfdc.scgj.dto;
import com.nskfdc.scgj.common.BaseDto;

public class SearchReportDto extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String batchId;  
	
	private String userEmail;
	
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public SearchReportDto(String batchId, String userEmail){
		super();
		this.batchId=batchId;
	
		this.userEmail=userEmail;
		
	}
	
}




	












	












	











	

