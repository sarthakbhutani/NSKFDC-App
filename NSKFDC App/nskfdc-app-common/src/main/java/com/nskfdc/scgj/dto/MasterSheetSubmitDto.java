package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class MasterSheetSubmitDto extends BaseDto{

	private String batchId;
	private String wardType;
	private String wardNumber;
	private String centreId;
	private String state;
	private String city;
	private String municipality;
	private String selectionCommitteeDate;
	private String trainerName;
	private String batchStartDate;
	private String batchEndDate;
	private String assessmentDate;
	private String medicalExamDate;
	private String employerName;
	private String employerNumber;
	
	
	
	
	
	
	public MasterSheetSubmitDto() {
		super();
	}






	public MasterSheetSubmitDto(String batchId, String wardType, String wardNumber, String centreId, String state,
			String city, String municipality, String selectionCommitteeDate, String trainerName, String batchStartDate,
			String batchEndDate, String assessmentDate, String medicalExamDate, String employerName,
			String employerNumber) {
		super();
		this.batchId = batchId;
		this.wardType = wardType;
		this.centreId = centreId;
		this.state = state;
		this.city = city;
		this.municipality = municipality;
		this.selectionCommitteeDate = selectionCommitteeDate;
		this.trainerName = trainerName;
		this.batchStartDate = batchStartDate;
		this.batchEndDate = batchEndDate;
		this.assessmentDate = assessmentDate;
		this.medicalExamDate = medicalExamDate;
		this.employerName = employerName;
		this.employerNumber = employerNumber;
		
		

	}






	public String getBatchId() {
		return batchId;
	}






	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}






	public String getWardType() {
		return wardType;
	}






	public void setWardType(String wardType) {
		this.wardType = wardType;
	}






	public String getWardNumber() {
		return wardNumber;
	}






	public void setWardNumber(String wardNumber) {
		
			this.wardNumber = wardNumber;
	}






	public String getCentreId() {
		return centreId;
	}






	public void setCentreId(String centreId) {
		this.centreId = centreId;
	}






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






	public String getMunicipality() {
		return municipality;
	}






	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}






	public String getSelectionCommitteeDate() {
		return selectionCommitteeDate;
	}






	public void setSelectionCommitteeDate(String selectionCommitteeDate) {
		this.selectionCommitteeDate = selectionCommitteeDate;
	}






	public String getTrainerName() {
		return trainerName;
	}






	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}






	public String getBatchStartDate() {
		return batchStartDate;
	}






	public void setBatchStartDate(String batchStartDate) {
		this.batchStartDate = batchStartDate;
	}






	public String getBatchEndDate() {
		return batchEndDate;
	}






	public void setBatchEndDate(String batchEndDate) {
		this.batchEndDate = batchEndDate;
	}






	public String getAssessmentDate() {
		return assessmentDate;
	}






	public void setAssessmentDate(String assessmentDate) {
		this.assessmentDate = assessmentDate;
	}






	public String getMedicalExamDate() {
		return medicalExamDate;
	}






	public void setMedicalExamDate(String medicalExamDate) {
		this.medicalExamDate = medicalExamDate;
	}






	public String getEmployerName() {
		return employerName;
	}






	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}






	public String getEmployerNumber() {
		return employerNumber;
	}






	public void setEmployerNumber(String employerNumber) {
		this.employerNumber = employerNumber;
	}
	
	
	
	
	
}
