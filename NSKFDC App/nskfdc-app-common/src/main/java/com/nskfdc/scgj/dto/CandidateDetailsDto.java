package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class CandidateDetailsDto extends BaseDto{

	private String name;
	private String aadharNumber;
	private String mobileNumber;
	private String candidateNumber;
	private String remarks;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getCandidateNumber() {
		return candidateNumber;
	}
	public void setCandidateNumber(String candidateNumber) {
		this.candidateNumber = candidateNumber;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public CandidateDetailsDto(String name, String aadharNumber, String mobileNumber, String candidateNumber,
			String remarks) {
		super();
		this.name = name;
		this.aadharNumber = aadharNumber;
		this.mobileNumber = mobileNumber;
		this.candidateNumber = candidateNumber;
		this.remarks = remarks;
	}
	
	
}
