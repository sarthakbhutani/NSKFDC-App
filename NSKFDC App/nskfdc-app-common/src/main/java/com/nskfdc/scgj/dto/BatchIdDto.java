package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class BatchIdDto extends BaseDto{

	
	private String batchId;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public BatchIdDto(String batchId ){
		super();
		this.batchId=batchId;
	}
	public BatchIdDto(){
		super();
	}
	
	
	
}
