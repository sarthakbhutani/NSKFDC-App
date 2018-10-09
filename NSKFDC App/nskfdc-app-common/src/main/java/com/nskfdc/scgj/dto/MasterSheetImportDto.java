package com.nskfdc.scgj.dto;

import java.util.Date;

import com.nskfdc.scgj.common.BaseDto;

public class MasterSheetImportDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String enrollmentNumber;
	private String salutation;
	private String firstName;
	private String lastName;
	private String gender;
	private Long   mobileNumber;
	private String educationQualification;
	private String state;
	private String district;
	private Long  adhaarCardNumber;
	private String idProofType;
	private String idProofNumber;
	private String disabilityType=null;
	private Integer age;
	private String bankName;
	private String ifscCode;
	private String workplaceAddress;
	private Long accountNumber;
	private String relationWithSKMS;
	private Date dob;
	private String guardianType=null;
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
		if(lastName.isEmpty()) 
			this.lastName = null;
		
		else
			this.lastName = lastName;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public String getEducationQualification() {
		return educationQualification;
	}
	public void setEducationQualification(String educationQualification) {
		if(educationQualification.isEmpty())
			this.educationQualification=null;
		else
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
		if(district.isEmpty())
			this.district=null;
		else
			this.district = district;
	}
	
	public Long getAdhaarCardNumber() {
		return adhaarCardNumber;
	}
	public void setAdhaarCardNumber(Long adhaarCardNumber) {
		this.adhaarCardNumber = adhaarCardNumber;
	}
	
	public String getIdProofType() {
		return idProofType;
	}
	public void setIdProofType(String idProofType) {
		if(idProofType.isEmpty())
			this.idProofType=null;
		else
			this.idProofType = idProofType;
	}
	
	public String getIdProofNumber() {
		return idProofNumber;
	}
	public void setIdProofNumber(String idProofNumber) {
		if(idProofNumber.isEmpty())
			this.idProofNumber=null;
		else
			this.idProofNumber = idProofNumber;
	}
	
	public String getDisabilityType() {
		return disabilityType;
	}
	public void setDisabilityType(String disabilityType) {
		if(disabilityType.isEmpty())
			this.disabilityType=null;
		else
			this.disabilityType = disabilityType;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		if(bankName.isEmpty())
			this.bankName=null;
		else	
			this.bankName = bankName;
	}
	
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		if(ifscCode.isEmpty())
			this.ifscCode=null;
		else
			this.ifscCode = ifscCode;
	}
	
	public String getWorkplaceAddress() {
		return workplaceAddress;
	}
	public void setWorkplaceAddress(String workplaceAddress) {
		if(workplaceAddress.isEmpty())
			this.workplaceAddress=null;
		else
			this.workplaceAddress = workplaceAddress;
	}
	
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getRelationWithSKMS() {
		return relationWithSKMS;
	}
	public void setRelationWithSKMS(String relationWithSKMS) {
		if(relationWithSKMS.isEmpty())
			this.relationWithSKMS=null;
		else
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
		if(guardianType.isEmpty())
			this.guardianType=null;
		else
			this.guardianType = guardianType;
	}
	
	public String getFirstNameFather() {
		return firstNameFather;
	}
	public void setFirstNameFather(String firstNameFather) {
		if(firstNameFather.isEmpty())
			this.firstNameFather=null;
		else
			this.firstNameFather = firstNameFather;
	}
	
	public String getLastNameFather() {
		return lastNameFather;
	}
	public void setLastNameFather(String lastNameFather) {
		if(lastNameFather.isEmpty())
			this.lastNameFather=null;
		else
			this.lastNameFather = lastNameFather;
	}
	
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		if(motherName.isEmpty())
			this.motherName=null;
		else
			this.motherName = motherName;
	}
	
	public String getResidentialAddress() {
		return residentialAddress;
	}
	public void setResidentialAddress(String residentialAddress) {
		if(residentialAddress.isEmpty())
			this.residentialAddress=null;
		else
			this.residentialAddress = residentialAddress;
	}
	
	public String getMsId() {
		return msId;
	}
	public void setMsId(String msId) {
		if(msId.isEmpty())
			this.msId=null;
		else
			this.msId = msId;
	}
	
	public String getOccupationType() {
		return occupationType;
	}
	public void setOccupationType(String occupationType) {
		if(occupationType.isEmpty())
			this.occupationType=null;
		else
			this.occupationType = occupationType;
	}
	
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		if(employmentType.isEmpty())
			this.employmentType=null;
		else
			this.employmentType = employmentType;
	}
	
	public String getAssessmentResult() {
		return assessmentResult;
	}
	public void setAssessmentResult(String assessmentResult) {
		if(assessmentResult.isEmpty())
			this.assessmentResult=null;
		else
			this.assessmentResult = assessmentResult;
	}
	
	public String getMedicalExaminationConducted() {
		return medicalExaminationConducted;
	}
	public void setMedicalExaminationConducted(String medicalExaminationConducted) {
		if(medicalExaminationConducted.isEmpty())
			this.medicalExaminationConducted=null;
		else
			this.medicalExaminationConducted = medicalExaminationConducted;
	}
	
	
	public MasterSheetImportDto(String enrollmentNumber, String salutation, String firstName, String lastName,
			String gender, Long mobileNumber, String educationQualification, String state, String district,
			Long adhaarCardNumber, String idProofType, String idProofNumber, String disabilityType, Integer age,
			String bankName, String ifscCode, String workplaceAddress, Long accountNumber, String relationWithSKMS,
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
	}
	public MasterSheetImportDto(String enrollmentNumber) {
		super();
		this.enrollmentNumber = enrollmentNumber;
	}
	
	
}