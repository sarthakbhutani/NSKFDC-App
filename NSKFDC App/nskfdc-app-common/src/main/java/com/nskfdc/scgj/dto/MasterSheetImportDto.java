package com.nskfdc.scgj.dto;

import java.util.Date;

import com.nskfdc.scgj.common.BaseDto;

public class MasterSheetImportDto extends BaseDto{

	private String enrollmentNumber;
	private String salutation;
	private String firstName;
	private String lastName;
	private String gender;
	private long    mobileNumber;
	private String educationQualification;
	private String state;
	private String district;
	private long  adhaarCardNumber;
	private String idProofType;
	private String idProofNumber;
	private String disabilityType;
	private int age;
	private String bankName;
	private String ifscCode;
	private String workplaceAddress;
	private long accountNumber;
	private String relationWithSKMS;
	private Date dob;
	private String guardianType;
	private String firstNameFather;
	private String lastNameFather;
	private String motherName;
	private String residentialAddress;
	private String msId;
	private String occupationType;
	private String employmentType;
	private String assessmentResult;
	private String medicalExaminationConducted;

	
	/*---- Getters and Setters --- */
	
	
	public String getEnrollmentNumber() {
		return enrollmentNumber;
	}
	public void setEnrollmentNumber(String enrollmentNumber) {
		this.enrollmentNumber = enrollmentNumber;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEducationQualification() {
		return educationQualification;
	}
	public void setEducationQualification(String educationQualification) {
		this.educationQualification = educationQualification;
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
	public long getAdhaarCardNumber() {
		return adhaarCardNumber;
	}
	public void setAdhaarCardNumber(long adhaarCardNumber) {
		this.adhaarCardNumber = adhaarCardNumber;
	}
	public String getIdProofType() {
		return idProofType;
	}
	public void setIdProofType(String idProofType) {
		this.idProofType = idProofType;
	}
	public String getIdProofNumber() {
		return idProofNumber;
	}
	public void setIdProofNumber(String idProofNumber) {
		this.idProofNumber = idProofNumber;
	}
	public String getDisabilityType() {
		return disabilityType;
	}
	public void setDisabilityType(String disabilityType) {
		this.disabilityType = disabilityType;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getWorkplaceAddress() {
		return workplaceAddress;
	}
	public void setWorkplaceAddress(String workplaceAddress) {
		this.workplaceAddress = workplaceAddress;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getRelationWithSKMS() {
		return relationWithSKMS;
	}
	public void setRelationWithSKMS(String relationWithSKMS) {
		this.relationWithSKMS = relationWithSKMS;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getGuardianType() {
		return guardianType;
	}
	public void setGuardianType(String guardianType) {
		this.guardianType = guardianType;
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
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getResidentialAddress() {
		return residentialAddress;
	}
	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}
	public String getMsId() {
		return msId;
	}
	public void setMsId(String msId) {
		this.msId = msId;
	}
	public String getOccupationType() {
		return occupationType;
	}
	public void setOccupationType(String occupationType) {
		this.occupationType = occupationType;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	public String getAssessmentResult() {
		return assessmentResult;
	}
	public void setAssessmentResult(String assessmentResult) {
		this.assessmentResult = assessmentResult;
	}
	public String getMedicalExaminationConducted() {
		return medicalExaminationConducted;
	}
	public void setMedicalExaminationConducted(String medicalExaminationConducted) {
		this.medicalExaminationConducted = medicalExaminationConducted;
	}
	
	/*----- Constructor -------- */
	
	public MasterSheetImportDto(String enrollmentNumber, String salutation, String firstName, String lastName,
			String gender, long mobileNumber, String educationQualification, String state, String district,
			long adhaarCardNumber, String idProofType, String idProofNumber, String disabilityType, int age,
			String bankName, String ifscCode, String workplaceAddress, long accountNumber, String relationWithSKMS,
			Date dob, String guardianType, String firstNameFather, String lastNameFather, String motherName,
			String residentialAddress, String msId, String occupationType, String employmentType,
			String assessmentResult, String medicalExaminationConducted) {
		super();
		this.enrollmentNumber = enrollmentNumber;
		this.salutation = salutation;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.mobileNumber = mobileNumber;
		this.educationQualification = educationQualification;
		this.state = state;
		this.district = district;
		this.adhaarCardNumber = adhaarCardNumber;
		this.idProofType = idProofType;
		this.idProofNumber = idProofNumber;
		this.disabilityType = disabilityType;
		this.age = age;
		this.bankName = bankName;
		this.ifscCode = ifscCode;
		this.workplaceAddress = workplaceAddress;
		this.accountNumber = accountNumber;
		this.relationWithSKMS = relationWithSKMS;
		this.dob = dob;
		this.guardianType = guardianType;
		this.firstNameFather = firstNameFather;
		this.lastNameFather = lastNameFather;
		this.motherName = motherName;
		this.residentialAddress = residentialAddress;
		this.msId = msId;
		this.occupationType = occupationType;
		this.employmentType = employmentType;
		this.assessmentResult = assessmentResult;
		this.medicalExaminationConducted = medicalExaminationConducted;
	}
	public MasterSheetImportDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}