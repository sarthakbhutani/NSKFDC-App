package com.nskfdc.scgj.dto;

import java.util.Date;

import com.nskfdc.scgj.common.BaseDto;

public class BatchImportDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long batchId;
	private Date batchStartDate;
	private Date batchEndDate;
	private Date assessmentDate;
	private Date medicalExamDate;
	private Date selectionCommitteeDate;
	private String municipality;
	private String wardType;
	private Long employerContactNumber;
	private String wardNumber;
	private String scgjBatchNumber;
	private String principalTrainerName;
	private String userEmail;
	private Integer centreId;
	private String employerName;
	private String state;
	private String centreCity;
	
	
	public BatchImportDto(Date batchStartDate, Date batchEndDate,
			Date assessmentDate, Date medicalExamDate,
			Date selectionCommitteeDate, String municipality,
			String wardType, Long employerContactNumber, String wardNumber,
			String principalTrainerName, Integer centreId, String employerName,
			String state, String centreCity) {
		
		this.assessmentDate = assessmentDate;
		this.batchEndDate = batchEndDate;
		this.batchStartDate = batchStartDate;
		this.assessmentDate = assessmentDate;
		this.medicalExamDate = medicalExamDate;
		this.selectionCommitteeDate = selectionCommitteeDate;
		this.municipality = municipality;
		this.wardType = wardType;
		this.employerContactNumber = employerContactNumber;
		this.wardNumber = wardNumber;
		this.principalTrainerName = principalTrainerName;
		this.centreId = centreId;
		this.employerName = employerName;
		this.state = state;
		this.centreCity = centreCity;
	}
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	
	public Date getBatchStartDate() {
		return batchStartDate;
	}
	public void setBatchStartDate(Date batchStartDate) {
		this.batchStartDate = batchStartDate;
	}
	public Date getBatchEndDate() {
		return batchEndDate;
	}
	public void setBatchEndDate(Date batchEndDate) {
		this.batchEndDate = batchEndDate;
	}
	public Date getAssessmentDate() {
		return assessmentDate;
	}
	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}
	public Date getMedicalExamDate() {
		return medicalExamDate;
	}
	public void setMedicalExamDate(Date medicalExamDate) {
		this.medicalExamDate = medicalExamDate;
	}
	public Date getSelectionCommitteeDate() {
		return selectionCommitteeDate;
	}
	public void setSelectionCommitteeDate(Date selectionCommitteeDate) {
		this.selectionCommitteeDate = selectionCommitteeDate;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	public String getWardType() {
		return wardType;
	}
	public void setWardType(String wardType) {
		this.wardType = wardType;
	}
	public Long getEmployerContactNumber() {
		return employerContactNumber;
	}
	public void setEmployerContactNumber(Long employerContactNumber) {
		this.employerContactNumber = employerContactNumber;
	}
	public String getWardNumber() {
		return wardNumber;
	}
	public void setWardNumber(String wardNumber) {
		this.wardNumber = wardNumber;
	}
	public String getScgjBatchNumber() {
		return scgjBatchNumber;
	}
	public void setScgjBatchNumber(String scgjBatchNumber) {
		this.scgjBatchNumber = scgjBatchNumber;
	}
	public String getPrincipalTrainerName() {
		return principalTrainerName;
	}
	public void setPrincipalTrainerName(String principalTrainerName) {
		this.principalTrainerName = principalTrainerName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Integer getCentreId() {
		return centreId;
	}
	public void setCentreId(Integer centreId) {
		this.centreId = centreId;
	}
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	public String getState() {
		return state;
	}
	

	public void setState(String state) {
		this.state = state;
	}
	public String getCentreCity() {
		return centreCity;
	}
	public void setCentreCity(String centreCity) {
		this.centreCity = centreCity;
	}

}
