package com.nskfdc.scgj.dto;
import java.util.Date;
import com.nskfdc.scgj.common.BaseDto;

public class BatchDetailsDto  extends BaseDto{

		int centreId;
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
		Long employerContactNumber;
		
		
				

				public int getCentreId() {
			return centreId;
		}




		public void setCentreId(int centreId) {
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




				public BatchDetailsDto(int centreId,String state,String cntreCity,String municipality,Date selectionCommitteeDate,String principalTrainerName,Date batchStartDate,Date batchEndDate,Date assessmentDate,Date medicalExamDate,String employerName,Long employerContactNumber) {
			super();
			this.centreId = centreId;
			this.state = state;
			this.centreCity = centreCity;
			this.municipality = municipality;
			this.selectionCommitteeDate = selectionCommitteeDate ; 
			this.principalTrainerName = principalTrainerName;
			this.batchStartDate = batchStartDate;
			this.batchEndDate = batchEndDate;
			this.assessmentDate = assessmentDate;
			this.medicalExamDate = medicalExamDate;
			this.employerName = employerName;
			this.employerContactNumber = employerContactNumber;
		}
	}


