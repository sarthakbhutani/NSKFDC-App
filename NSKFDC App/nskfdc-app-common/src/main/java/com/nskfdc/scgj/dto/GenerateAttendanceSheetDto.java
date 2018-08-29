package com.nskfdc.scgj.dto;

import java.text.*;
import java.util.*;
import com.nskfdc.scgj.common.BaseDto;

public class GenerateAttendanceSheetDto extends BaseDto{
	
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
	public void setStringdate1(String stringdate1) {
		this.stringdate1 = stringdate1;
	}
	public void setStringdate2(String stringdate2) {
		this.stringdate2 = stringdate2;
	}
	public void setStringdate3(String stringdate3) {
		this.stringdate3 = stringdate3;
	}
	public void setStringdate4(String stringdate4) {
		this.stringdate4 = stringdate4;
	}
	public void setStringdate5(String stringdate5) {
		this.stringdate5 = stringdate5;
	}

	private String name;
	private String fatherName;
	private String mobileNumber;
	private String batchId;
	private String stringdate1,stringdate2,stringdate3,stringdate4,stringdate5;
	
	Calendar c=Calendar.getInstance();
	DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
	
	public String getName() {
		return name;
	}
	public void setName(String firstName, String lastName) {
		this.name = firstName+" "+lastName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String firstNameFather, String lastNameFather) {
		fatherName = firstNameFather+" "+lastNameFather;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getStringdate1() {
		return stringdate1;
	}
	public void setStringdate1(Date batchStartDate) {
		c.setTime(batchStartDate);
		stringdate1=df.format(batchStartDate);
	}
	public String getStringdate2() {
		return stringdate2;
	}
	public void setStringdate2() {
		c.add(Calendar.DATE,1);
		stringdate2=df.format(c.getTime());
	}
	public String getStringdate3() {
		return stringdate3;
	}
	public void setStringdate3() {
		c.add(Calendar.DATE,1);
		stringdate3=df.format(c.getTime());
	}
	public String getStringdate4() {
		return stringdate4;
	}
	public void setStringdate4() {
		c.add(Calendar.DATE,1);
		stringdate4=df.format(c.getTime());
	}
	public String getStringdate5() {
		return stringdate5;
	}
	public void setStringdate5() {
		c.add(Calendar.DATE,1);
		stringdate5=df.format(c.getTime());
	}
	
	/**
	 * Parameterized Constructor
	 * @param firstName
	 * @param lastName
	 * @param firstNameFather
	 * @param lastNameFather
	 * @param mobileNumber
	 * @param batchId
	 * @param batchStartDate
	 */
	public GenerateAttendanceSheetDto(String firstName, String lastName,String firstNameFather, String lastNameFather,String mobileNumber,String batchId,Date batchStartDate)
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
			fatherName=lastName;
		}else {
		fatherName=null;
		}
		
		this.mobileNumber = mobileNumber;
		this.batchId = batchId;
		setStringdate1(batchStartDate);
		setStringdate2();
		setStringdate3();
		setStringdate4();
		setStringdate5();
	}
	public GenerateAttendanceSheetDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
