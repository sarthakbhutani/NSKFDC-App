package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class GenerateCredentialDto extends BaseDto{

	private String trainingPartnerName;
	private String nsdcRegNumber;
	private String userEmail;
	private String password;
	private String jobRole;
	private String sectorSkillCouncil;
	private String targets;
	
	public String getTrainingPartnerName() {
		return trainingPartnerName;
	}
	public void setTrainingPartnerName(String trainingPartnerName) {
		this.trainingPartnerName = trainingPartnerName;
	}
	public String getNsdcRegNumber() {
		return nsdcRegNumber;
	}
	public void setNsdcRegNumber(String nsdcRegNumber) {
		this.nsdcRegNumber = nsdcRegNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getJobRole() {
		return jobRole;
	}
	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}
	public String getSectorSkillCouncil() {
		return sectorSkillCouncil;
	}
	public void setSectorSkillCouncil(String sectorSkillCouncil) {
		this.sectorSkillCouncil = sectorSkillCouncil;
	}
	public String getTargets() {
		return targets;
	}
	public void setTargets(String targets) {
		this.targets = targets;
	}

	
	public GenerateCredentialDto(String trainingPartnerName,String nsdcRegNumber, String userEmail, String password,
			String jobRole, String sectorSkillCouncil, String targets) {
		super();

		this.trainingPartnerName = trainingPartnerName;
		this.nsdcRegNumber = nsdcRegNumber;
		this.userEmail = userEmail;
		this.password = password;
		this.jobRole = jobRole;
		this.sectorSkillCouncil = sectorSkillCouncil;
		this.targets = targets;
	}
	public GenerateCredentialDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
