package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class DownloadFinalMasterSheetDto extends BaseDto {
	
	private String trainingPartnerName;
	private String sectorSkillCouncil;
	private String jobRole;
	private String nsdcRegNumber;
	private String batchId;

	
	public String getTrainingPartnerName() {
		return trainingPartnerName;
	}
	public void setTrainingPartnerName(String trainingPartnerName) {
		this.trainingPartnerName = trainingPartnerName;
	}
	public String getSectorSkillCouncil() {
		return sectorSkillCouncil;
	}
	public void setSectorSkillCouncil(String sectorSkillCouncil) {
		this.sectorSkillCouncil = sectorSkillCouncil;
	}
	public String getJobRole() {
		return jobRole;
	}
	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}
	public String getNsdcRegNumber() {
		return nsdcRegNumber;
	}
	public void setNsdcRegNumber(String nsdcRegNumber) {
		this.nsdcRegNumber = nsdcRegNumber;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	/*------------------------------CONSTRUCTOR--------------------------------*/
	
	public DownloadFinalMasterSheetDto(String trainingPartnerName, String sectorSkillCouncil,String jobRole,String nsdcRegNumber,String batchId){
	
	this.trainingPartnerName = trainingPartnerName;
	this.sectorSkillCouncil = sectorSkillCouncil;
	this.jobRole = jobRole;
	this.nsdcRegNumber = nsdcRegNumber;
	this.batchId = batchId;
	
	}
	
}
