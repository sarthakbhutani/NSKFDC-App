package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class ScgjBatchNumberDto extends BaseDto{

	private static final long serialVersionUID = 1L;
	private String scgjBatchNumber;

	public String getScgjBatchNumber() {
		return scgjBatchNumber;
	}

	public void setScgjBatchNumber(String scgjBatchNumber) {
		this.scgjBatchNumber = scgjBatchNumber;
	}

	public ScgjBatchNumberDto(String scgjBatchNumber) {
		super();
		this.scgjBatchNumber = scgjBatchNumber;
	}

	public ScgjBatchNumberDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
