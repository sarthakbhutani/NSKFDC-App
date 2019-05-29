package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class GenerateCredentialDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trainingPartnerName;
	private String nsdcRegNumber;
	private String userEmail;
	private String password;
	private String jobRole;
	private String sectorSkillCouncil;
	private int targets;
	
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
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public int getTargets() {
		return targets;
	}
	public void setTargets(int targets) {
		this.targets = targets;
	}
	
	/**
	 * Parameterized Constructor
	 * @param trainingPartnerName
	 * @param nsdcRegNumber
	 * @param userEmail
	 * @param password
	 * @param jobRole
	 * @param sectorSkillCouncil
	 * @param targets
	 */
	public GenerateCredentialDto(String trainingPartnerName, String nsdcRegNumber, String userEmail, String password,
			String jobRole, String sectorSkillCouncil, int targets) {
		super();
		this.trainingPartnerName = trainingPartnerName;
		this.nsdcRegNumber = nsdcRegNumber;
		this.userEmail = userEmail;
		this.password = password;
		this.jobRole = jobRole;
		this.sectorSkillCouncil = sectorSkillCouncil;
		this.targets = targets;
	}
	/**
	 * Default Constructor
	 */
	public GenerateCredentialDto() {
		super();
	}
	
		
}
