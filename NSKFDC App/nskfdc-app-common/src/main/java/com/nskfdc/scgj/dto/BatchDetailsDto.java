//package com.nskfdc.scgj.dto;
//import java.util.Date;
//
//import com.nskfdc.scgj.common.BaseDto;
//
//public class BatchDetailsDto  extends BaseDto{
//
//		/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private int batchId;
//		private int centreId;
//		private String state;
//		private String centreCity;
//		private String municipality;
//		private String WardType;
//		private String WardNumber;
//		private Date selectionCommitteeDate;
//		private String principalTrainerName;
//		private Date batchStartDate;
//		private Date batchEndDate;
//		private Date assessmentDate;
//		private Date medicalExamDate;
//		/**
//		 * Parameterized Constructor
//		 * @param centreId
//		 * @param state
//		 * @param centreCity
//		 * @param municipality
//		 * @param wardType
//		 * @param wardNumber
//		 * @param selectionCommitteeDate
//		 * @param principalTrainerName
//		 * @param batchStartDate
//		 * @param batchEndDate
//		 * @param assessmentDate
//		 * @param medicalExamDate
//		 * @param employerName
//		 * @param employerContactNumber
//		 */
//		public BatchDetailsDto(int centreId, String state, String centreCity,
//				String municipality, String wardType, String wardNumber,
//				Date selectionCommitteeDate, String principalTrainerName,
//				Date batchStartDate, Date batchEndDate, Date assessmentDate,
//				Date medicalExamDate, String employerName,
//				Long employerContactNumber) {
//			super();
//			this.centreId = centreId;
//			this.state = state;
//			this.centreCity = centreCity;
//			this.municipality = municipality;
//			WardType = wardType;
//			WardNumber = wardNumber;
//			this.selectionCommitteeDate = selectionCommitteeDate;
//			this.principalTrainerName = principalTrainerName;
//			this.batchStartDate = batchStartDate;
//			this.batchEndDate = batchEndDate;
//			this.assessmentDate = assessmentDate;
//			this.medicalExamDate = medicalExamDate;
//			this.employerName = employerName;
//			this.employerContactNumber = employerContactNumber;
//		}
//		private String employerName;
//		private Long employerContactNumber;
//		public int getCentreId() {
//			return centreId;
//		}
//		public void setCentreId(int centreId) {
//			this.centreId = centreId;
//		}
//		public String getState() {
//			return state;
//		}
//		public void setState(String state) {
//			this.state = state;
//		}
//		public String getCentreCity() {
//			return centreCity;
//		}
//		public void setCentreCity(String centreCity) {
//			this.centreCity = centreCity;
//		}
//		public String getMunicipality() {
//			return municipality;
//		}
//		public void setMunicipality(String municipality) {
//			this.municipality = municipality;
//		}
//		public String getWardType() {
//			return WardType;
//		}
//		public void setWardType(String wardType) {
//			WardType = wardType;
//		}
//		public String getWardNumber() {
//			return WardNumber;
//		}
//		public void setWardNumber(String wardNumber) {
//			WardNumber = wardNumber;
//		}
//		public Date getSelectionCommitteeDate() {
//			return selectionCommitteeDate;
//		}
//		public void setSelectionCommitteeDate(Date selectionCommitteeDate) {
//			this.selectionCommitteeDate = selectionCommitteeDate;
//		}
//		public String getPrincipalTrainerName() {
//			return principalTrainerName;
//		}
//		public void setPrincipalTrainerName(String principalTrainerName) {
//			this.principalTrainerName = principalTrainerName;
//		}
//		public Date getBatchStartDate() {
//			return batchStartDate;
//		}
//		public void setBatchStartDate(Date batchStartDate) {
//			this.batchStartDate = batchStartDate;
//		}
//		public Date getBatchEndDate() {
//			return batchEndDate;
//		}
//		public void setBatchEndDate(Date batchEndDate) {
//			this.batchEndDate = batchEndDate;
//		}
//		public Date getAssessmentDate() {
//			return assessmentDate;
//		}
//		public void setAssessmentDate(Date assessmentDate) {
//			this.assessmentDate = assessmentDate;
//		}
//		public Date getMedicalExamDate() {
//			return medicalExamDate;
//		}
//		public void setMedicalExamDate(Date medicalExamDate) {
//			this.medicalExamDate = medicalExamDate;
//		}
//		public String getEmployerName() {
//			return employerName;
//		}
//		public void setEmployerName(String employerName) {
//			this.employerName = employerName;
//		}
//		public Long getEmployerContactNumber() {
//			return employerContactNumber;
//		}
//		public void setEmployerContactNumber(Long employerContactNumber) {
//			this.employerContactNumber = employerContactNumber;
//		}
//		public BatchDetailsDto() {
//			super();
//		}
//		public int getBatchId() {
//			return batchId;
//		}
//		public void setBatchId(int batchId) {
//			this.batchId = batchId;
//		}
//		
//		
//		}
//
//
