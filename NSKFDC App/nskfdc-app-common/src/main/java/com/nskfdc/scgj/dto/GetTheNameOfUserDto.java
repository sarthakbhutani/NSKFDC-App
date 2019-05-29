package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class GetTheNameOfUserDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trainingPartnerName;

	public String getTrainingPartnerName() {
		return trainingPartnerName;
	}

	public void setTrainingPartnerName(String trainingPartnerName) {
		this.trainingPartnerName = trainingPartnerName;
	}

	public GetTheNameOfUserDto(String trainingPartnerName) {
		super();
		this.trainingPartnerName = trainingPartnerName;
	}
	
	
}
