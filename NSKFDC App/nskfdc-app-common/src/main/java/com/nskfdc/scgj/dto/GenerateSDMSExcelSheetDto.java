package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class GenerateSDMSExcelSheetDto extends BaseDto {
	
	private String nsdcRegNumber;
	private int id;
	private String enrollmentNumber;
	private String firstName;
	private String lastName;
	private String dob;
	private String firstNameFather;
	private String lastNameFather;
	private String guardianType;
	private String salutation;
	private String gender;
	private String state;
	private String district;
	private String mobileNumber;
	private String educationLevel;
	private String sectorSkillCouncil;
	private String jobRole;
	private String batchStartDate;
	private String batchEndDate;
	private String assessmentDate;
	private String employerName;
	private String employerContactNumber;
	private String employmentType;
	
	public String getNsdcRegNumber() {
		return nsdcRegNumber;
	}
	public void setNsdcRegNumber(String nsdcRegNumber) {
		this.nsdcRegNumber = nsdcRegNumber;
	}
	public String getEnrollmentNumber() {
		return enrollmentNumber;
	}
	public void setEnrollmentNumber(String enrollmentNumber) {
		this.enrollmentNumber = enrollmentNumber;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getFirstNameFather() {
		return firstNameFather;
	}
	public void setFirstNameFather(String firstNameFather) {
		this.firstNameFather = firstNameFather;
	}
	public String getLastNameFather() {
		return lastNameFather;
	}
	public void setLastNameFather(String lastNameFather) {
		this.lastNameFather = lastNameFather;
	}
	public String getGuardianType() {
		return guardianType;
	}
	public void setGuardianType(String guardianType) {
		this.guardianType = guardianType;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}
	public String getSectorSkillCouncil() {
		return sectorSkillCouncil;
	}
	public void setSectorSkillCouncil(String sectorSkillCouncil) {
		this.sectorSkillCouncil = sectorSkillCouncil;
	}
	public String getJobRole() {
		return jobRole;
	}
	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
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
	
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	public String getEmployerContactNumber() {
		return employerContactNumber;
	}
	public void setEmployerContactNumber(String employerContactNumber) {
		this.employerContactNumber = employerContactNumber;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	public GenerateSDMSExcelSheetDto(String nsdcRegNumber,int id,String enrollmentNumber, String firstName,String lastName,String dob,String firstNameFather,
	 String lastNameFather,String guardianType, String salutation,String gender, String state,String district,String mobileNumber,String educationLevel, 
	 String sectorSkillCouncil,String jobRole,String batchStartDate,String batchEndDate, String assessmentDate,String employerName,String employerContactNumber,String employmentType) {
	
		this.nsdcRegNumber = nsdcRegNumber;
		this.id=id;
		this.enrollmentNumber = enrollmentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.firstNameFather = firstNameFather;
		this.lastNameFather = lastNameFather;
		this.guardianType = guardianType;
		this.salutation = salutation;
		this.gender = gender;
		this.state = state;
		this.district = district;
		this.mobileNumber = mobileNumber;
		this.educationLevel = educationLevel;
		this.sectorSkillCouncil = sectorSkillCouncil;
		this.jobRole = jobRole;
		this.batchStartDate = batchStartDate;
		this.batchEndDate = batchEndDate;
		this.assessmentDate = assessmentDate;
		this.employerName = employerName;
		this.employerContactNumber = employerContactNumber;
		this.employmentType = employmentType;
		
	}
	

}
