package com.nskfdc.scgj.dto;

import java.text.*;
import java.util.*;
import com.nskfdc.scgj.common.BaseDto;

public class GenerateAttendanceSheetDto extends BaseDto{
	
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
	
	/*------CONSTRUCTOR-------------*/
	public GenerateAttendanceSheetDto(String firstName, String lastName,String firstNameFather, String lastNameFather,String mobileNumber,String batchId,Date batchStartDate)
	{
		name = firstName+" "+lastName;
		fatherName = firstNameFather+" "+lastNameFather;
		this.mobileNumber = mobileNumber;
		this.batchId = batchId;
		setStringdate1(batchStartDate);
		setStringdate2();
		setStringdate3();
		setStringdate4();
		setStringdate5();
	}
}
