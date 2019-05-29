package com.nskfdc.scgj.dto;
import java.util.Date;

import com.nskfdc.scgj.common.BaseDto;

public class GetBatchDetailsDto  extends BaseDto{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		Integer centreId;
		String state;
		String centreCity;
		String municipality;
		Date selectionCommitteeDate;
		String principalTrainerName;
		Date batchStartDate;
		Date batchEndDate;
		Date assessmentDate;
		Date medicalExamDate;
		String employerName;
		String wardNumber;
		String wardType;
		public String getCentreCity() {
			return centreCity;
		}




		public void setCentreCity(String centreCity) {
			this.centreCity = centreCity;
		}




		public String getWardNumber() {
			return wardNumber;
		}




		public void setWardNumber(String wardNumber) {
			this.wardNumber = wardNumber;
		}




		public String getWardType() {
			return wardType;
		}




		public void setWardType(String wardType) {
			this.wardType = wardType;
		}




		Long employerContactNumber;
		
		
				

				public Integer getCentreId() {
			return centreId;
		}




		public void setCentreId(Integer centreId) {
			this.centreId = centreId;
		}




		public String getState() {
			return state;
		}




		public void setState(String state) {
			this.state = state;
		}




		public String getcentreCity() {
			return centreCity;
		}




		public void setcentreCity(String centreCity) {
			this.centreCity = centreCity;
		}




		public String getMunicipality() {
			return municipality;
		}




		public void setMunicipality(String municipality) {
			this.municipality = municipality;
		}




		public Date getSelectionCommitteeDate() {
			return selectionCommitteeDate;
		}




		public void setSelectionCommitteeDate(Date selectionCommitteeDate) {
			this.selectionCommitteeDate = selectionCommitteeDate;
		}




		public String getPrincipalTrainerName() {
			return principalTrainerName;
		}




		public void setPrincipalTrainerName(String principalTrainerName) {
			this.principalTrainerName = principalTrainerName;
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




		public String getEmployerName() {
			return employerName;
		}




		public void setEmployerName(String employerName) {
			this.employerName = employerName;
		}




		public Long getEmployerContactNumber() {
			return employerContactNumber;
		}




		public void setEmployerContactNumber(Long employerContactNumber) {
			this.employerContactNumber = employerContactNumber;
		}




		public GetBatchDetailsDto(Integer centreId, String state, String centreCity, String municipality,
				Date selectionCommitteeDate, String principalTrainerName, Date batchStartDate, Date batchEndDate,
				Date assessmentDate, Date medicalExamDate, String employerName, String wardNumber, String wardType,
				Long employerContactNumber) {
			super();
			this.centreId = centreId;
			this.state = state;
			this.centreCity = centreCity;
			this.municipality = municipality;
			this.selectionCommitteeDate = selectionCommitteeDate;
			this.principalTrainerName = principalTrainerName;
			this.batchStartDate = batchStartDate;
			this.batchEndDate = batchEndDate;
			this.assessmentDate = assessmentDate;
			this.medicalExamDate = medicalExamDate;
			this.employerName = employerName;
			this.wardNumber = wardNumber;
			this.wardType = wardType;
			this.employerContactNumber = employerContactNumber;
		}




		public GetBatchDetailsDto() {
			super();
		}
		




			




			
	}


