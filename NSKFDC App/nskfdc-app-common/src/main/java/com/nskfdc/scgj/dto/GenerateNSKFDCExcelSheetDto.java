package com.nskfdc.scgj.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.nskfdc.scgj.common.BaseDto;

public class GenerateNSKFDCExcelSheetDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public void setName(String name) {
		this.name = name;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	private String name;
	private int age;
	private String gender;
	private Date date_dob;
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
	private String relationWithSKMS;
	
	Calendar c=Calendar.getInstance();
	DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
	
	
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
		this.dob = df.format(date_dob);
	}
	public Date getDate_dob() {
		return date_dob;
	}
	public void setDate_dob(Date dob) {
		this.date_dob = dob;
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
	public String getRelationWithSKMS() {
		return relationWithSKMS;
	}
	public void setRelationWithSKMS(String relationWithSKMS) {
		this.relationWithSKMS = relationWithSKMS;
	}
	/**
	 * Parameterized Constructor
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param gender
	 * @param dob
	 * @param educationLevel
	 * @param firstNameFather
	 * @param lastNameFather
	 * @param motherName
	 * @param aadharCardNumber
	 * @param residentialAddress
	 * @param mobileNumber
	 * @param occupationType
	 * @param msId
	 * @param idProofType
	 * @param idProofNumber
	 * @param bankName
	 * @param ifscCode
	 * @param accountNumber
	 * @param batchId
	 * @param trainingPartnerName
	 * @param relationWithSKMS
	 */
	public GenerateNSKFDCExcelSheetDto(String firstName, String lastName,int age,String gender,Date dob,String educationLevel,String firstNameFather, String lastNameFather,
			String motherName,String aadharCardNumber,String residentialAddress,String mobileNumber,String occupationType,String msId,
			String idProofType,String idProofNumber,String bankName,String ifscCode,String accountNumber, String batchId, String trainingPartnerName,String relationWithSKMS) 
	{
		
		if(lastName!=null)
			name = firstName+" "+lastName;
		else
			name = firstName;
		
		if(firstNameFather!=null && lastNameFather!=null) {
			fatherName = firstNameFather+" "+lastNameFather;
		}else if(firstNameFather!=null) {
			fatherName=firstNameFather;
		}else if(lastNameFather!=null) {
			fatherName=lastNameFather;
		}else {
		fatherName=null;
		}
		
		
		
		this.age = age;
		this.gender = gender;
		this.date_dob = dob;
		this.dob = df.format(date_dob);
		this.educationLevel = educationLevel;
		
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
		this.relationWithSKMS=relationWithSKMS;
	}
	/**
	 * Default Constructor
	 */
	public GenerateNSKFDCExcelSheetDto() {
		super();
	}
	
	
}
