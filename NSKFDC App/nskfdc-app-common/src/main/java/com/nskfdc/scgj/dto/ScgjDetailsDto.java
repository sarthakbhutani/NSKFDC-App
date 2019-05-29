package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class ScgjDetailsDto extends BaseDto{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ScgjDetailsDto(String batchId, String scgjBatchNumber) {
		super();
		this.batchId = batchId;
		this.scgjBatchNumber = scgjBatchNumber;
	}
	private String batchId;
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getScgjId() {
		return scgjBatchNumber;
	}
	public void setScgjId(String scgjBatchNumber) {
		this.scgjBatchNumber = scgjBatchNumber;
	}
	private String scgjBatchNumber;
	public ScgjDetailsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
