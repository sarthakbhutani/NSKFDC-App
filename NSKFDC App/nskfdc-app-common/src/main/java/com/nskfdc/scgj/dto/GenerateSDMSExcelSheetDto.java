package com.nskfdc.scgj.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.nskfdc.scgj.common.BaseDto;

public class GenerateSDMSExcelSheetDto extends BaseDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nsdcRegNumber;
	private int id;
	private String enrollmentNumber;
	public Calendar getC() {
		return c;
	}
	public void setC(Calendar c) {
		this.c = c;
	}
	public DateFormat getDf() {
		return df;
	}
	public void setDf(DateFormat df) {
		this.df = df;
	}
	public void setBatchStartDate(String batchStartDate) {
		this.batchStartDate = batchStartDate;
	}
	public void setBatchEndDate(String batchEndDate) {
		this.batchEndDate = batchEndDate;
	}
	public void setAssessmentDate(String assessmentDate) {
		this.assessmentDate = assessmentDate;
	}


	private String firstName;
	private String lastName;
	private Date date_dob;
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
	private Date date_batchStartDate;
	private Date date_batchEndDate;
	private Date date_assessmentDate;
	private String batchStartDate;
	private String batchEndDate;
	private String assessmentDate;
	private String employerName;
	private String employerContactNumber;
	private String employmentType;
	private String aadharCardNumber;
	private String disabilityType;
	
	Calendar c=Calendar.getInstance();
	DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
	
	
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
	public Date getDate_dob() {
		return date_dob;
	}
	public void setDate_dob(Date dob) {
		this.date_dob = dob;
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
	public void setBatchStartDate() {
		this.batchStartDate = df.format(date_batchStartDate);
	}
	public String getBatchEndDate() {
		return batchEndDate;
	}
	public void setBatchEndDate() {
		this.batchEndDate = df.format(date_batchEndDate);
	}
	public String getAssessmentDate() {
		return assessmentDate;
	}
	public void setAssessmentDate() {
		this.assessmentDate = df.format(date_assessmentDate);
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
	public String getAadharCardNumber() {
		return aadharCardNumber;
	}
	public void setAadharCardNumber(String aadharCardNumber) {
		this.aadharCardNumber = aadharCardNumber;
	}
	public String getDisabilityType() {
		return disabilityType;
	}
	public void setDisabilityType(String disabilityType) {
		this.disabilityType = disabilityType;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = df.format(date_dob);
	}
	public Date getDate_batchStartDate() {
		return date_batchStartDate;
	}
	public void setDate_batchStartDate(Date batchStartDate) {
		this.date_batchStartDate = batchStartDate;
	}
	public Date getDate_batchEndDate() {
		return date_batchEndDate;
	}
	public void setDate_batchEndDate(Date batchEndDate) {
		this.date_batchEndDate = batchEndDate;
	}
	public Date getDate_assessmentDate() {
		return date_assessmentDate;
	}
	public void setDate_assessmentDate(Date assessmentDate) {
		this.date_assessmentDate = assessmentDate;
	}
	
	
	public GenerateSDMSExcelSheetDto(String nsdcRegNumber, int id, String enrollmentNumber, String firstName,
			String lastName, Date dob, String firstNameFather, String lastNameFather, String guardianType,
			String salutation, String gender, String state, String district, String mobileNumber, String educationLevel,
			String sectorSkillCouncil, String jobRole, Date batchStartDate, Date batchEndDate,
			Date assessmentDate, String employerName, String employerContactNumber, String employmentType,
			String aadharCardNumber, String disabilityType) {
		super();
		this.nsdcRegNumber = nsdcRegNumber;
		this.id = id;
		this.enrollmentNumber = enrollmentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.date_dob = dob;
		this.dob = df.format(date_dob);
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
		this.setDate_batchStartDate(batchStartDate);
		this.batchStartDate = df.format(date_batchStartDate);
		this.setDate_batchEndDate(batchEndDate);
		this.batchEndDate = df.format(date_batchEndDate);
		this.setDate_assessmentDate(assessmentDate);
		this.assessmentDate = df.format(date_assessmentDate);
		this.employerName = employerName;
		this.employerContactNumber = employerContactNumber;
		this.employmentType = employmentType;
		this.aadharCardNumber = aadharCardNumber;
		this.disabilityType = disabilityType;
	}

}
