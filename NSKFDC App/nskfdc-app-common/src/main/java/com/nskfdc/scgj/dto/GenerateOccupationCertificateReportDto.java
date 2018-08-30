package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class GenerateOccupationCertificateReportDto extends BaseDto{
     
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String gender;
	private String age;
	public void setName(String name) {
		this.name = name;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

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
		
		if(lastName!=null)
			name = firstName+" "+lastName;
		else
			name = firstName;
		
		this.gender = gender;
		this.age = age;
		
		if(firstNameFather!=null && lastNameFather!=null) {
			fatherName = firstNameFather+" "+lastNameFather;
		}else if(firstNameFather!=null) {
			fatherName=firstNameFather;
		}else if(lastNameFather!=null) {
			fatherName=lastNameFather;
		}else {
			fatherName=null;
		}
		
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
