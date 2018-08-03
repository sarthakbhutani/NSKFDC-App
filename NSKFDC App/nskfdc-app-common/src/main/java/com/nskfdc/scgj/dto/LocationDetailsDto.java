package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class LocationDetailsDto extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String state;
	private String city;
	private String municipalCorporation;
	private String ward;
	private String scgjBatchNumber;
	private String uploadStatus;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getMunicipalCorporation() {
		return municipalCorporation;
	}
	public void setMunicipalCorporation(String municipalCorporation) {
		this.municipalCorporation = municipalCorporation;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getScgjBatchNumber() {
		return scgjBatchNumber;
	}
	public void setScgjBatchNumber(String scgjBatchNumber) {
		this.scgjBatchNumber = scgjBatchNumber;
	}
	public String getUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public LocationDetailsDto(String state, String city,
			String municipalCorporation, String ward, String scgjBatchNumber,
			String uploadStatus) {
		this.state = state;
		this.city = city;
		this.municipalCorporation = municipalCorporation;
		this.ward = ward;
		this.scgjBatchNumber = scgjBatchNumber;
		this.uploadStatus = uploadStatus;
	}
	public LocationDetailsDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
