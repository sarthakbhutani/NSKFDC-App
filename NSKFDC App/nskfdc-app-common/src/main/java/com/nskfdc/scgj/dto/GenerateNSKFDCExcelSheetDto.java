package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class GenerateNSKFDCExcelSheetDto extends BaseDto{

	private String name;
	private int age;
	private String gender;
	private String dob;
	private String educationLevel;
	private String fatherName;
	private String motherName;
	private String aadharCardNumber;
	private String residentialAddress;
	private String mobileNumber;
	private String occupationType;
	private String msId;
	private String idProofType;
	private String idProofNumber;
	private String bankName;
	private String ifscCode;
	private String accountNumber;
	private String batchId;
	private String trainingPartnerName;
	
	public String getName() {
		return name;
	}
	public void setName(String firstName, String lastName) {
		name = firstName+" "+lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String firstNameFather, String lastNameFather) {
		fatherName = firstNameFather+" "+lastNameFather;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getAadharCardNumber() {
		return aadharCardNumber;
	}
	public void setAadharCardNumber(String aadharCardNumber) {
		this.aadharCardNumber = aadharCardNumber;
	}
	public String getResidentialAddress() {
		return residentialAddress;
	}
	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getOccupationType() {
		return occupationType;
	}
	public void setOccupationType(String occupationType) {
		this.occupationType = occupationType;
	}
	public String getMsId() {
		return msId;
	}
	public void setMsId(String msId) {
		this.msId = msId;
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
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getTrainingPartnerName() {
		return trainingPartnerName;
	}
	public void setTrainingPartnerName(String trainingPartnerName) {
		this.trainingPartnerName = trainingPartnerName;
	}
	/*------CONSTRUCTOR-------------*/
	public GenerateNSKFDCExcelSheetDto(String firstName, String lastName,int age,String gender,String dob,String educationLevel,String firstNameFather, String lastNameFather,
			String motherName,String aadharCardNumber,String residentialAddress,String mobileNumber,String occupationType,String msId,
			String idProofType,String idProofNumber,String bankName,String ifscCode,String accountNumber, String batchId, String trainingPartnerName) 
	{
		name = firstName+" "+lastName;
		this.age = age;
		this.gender = gender;
		this.dob = dob;
		this.educationLevel = educationLevel;
		fatherName = firstNameFather+" "+lastNameFather;
		this.motherName = motherName;
		this.aadharCardNumber = aadharCardNumber;
		this.residentialAddress = residentialAddress;
		this.mobileNumber = mobileNumber;
		this.occupationType = occupationType;
		this.msId = msId;
		this.idProofType = idProofType;
		this.idProofNumber = idProofNumber;
		this.bankName = bankName;
		this.ifscCode = ifscCode;
		this.accountNumber = accountNumber;
		this.batchId = batchId;
		this.trainingPartnerName = trainingPartnerName;
	}
}
