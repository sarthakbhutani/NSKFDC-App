package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class GenerateOccupationCertificateReportDto extends BaseDto{
     
	private String name;
	private String gender;
	private String age;
	private String fatherName;
	private String aadharCardNumber;
	private String residentialAddress;
	private String workplaceAddress;
	
	public String getName() {
		return name;
	}
	public void setName(String firstName, String lastName) {
		this.name = firstName+" "+lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String firstNameFather, String lastNameFather) {
		fatherName = firstNameFather+" "+lastNameFather;
	}
	public String getAadharCardNumber() {
		return aadharCardNumber;
	}
	public void setAadharCardNumber(String aadharCardNumber) {
		this.aadharCardNumber = aadharCardNumber;
	}
	public String getWorkplaceAddress() {
		return workplaceAddress;
	}
	public void setWorkplaceAddress(String workplaceAddress) {
		this.workplaceAddress = workplaceAddress;
	}
	public String getResidentialAddress() {
		return residentialAddress;
	}
	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	/**
	 * Parameterized Constructor
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param age
	 * @param firstNameFather
	 * @param lastNameFather
	 * @param aadharCardNumber
	 * @param residentialAddress
	 * @param workplaceAddress
	 */
	public GenerateOccupationCertificateReportDto(String firstName, String lastName,String gender, String age, String firstNameFather, String lastNameFather, String aadharCardNumber,	String residentialAddress, String workplaceAddress) {
		
		name = firstName+" "+lastName;
		this.gender = gender;
		this.age = age;
		fatherName = firstNameFather+" "+lastNameFather;
		this.aadharCardNumber = aadharCardNumber;
		this.workplaceAddress = workplaceAddress;
		this.residentialAddress = residentialAddress;
	}
	
	/**
	 * Default Constructor
	 */
	public GenerateOccupationCertificateReportDto() {
		super();
	}
	
	
	
}
