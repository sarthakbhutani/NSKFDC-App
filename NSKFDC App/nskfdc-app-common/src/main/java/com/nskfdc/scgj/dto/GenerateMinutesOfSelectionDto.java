package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;


public class GenerateMinutesOfSelectionDto extends BaseDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jobRole;
	private String trainingPartnerName;
	private String sectorSkillCouncil;
	private String centreCity;
	private String selectionCommitteeDate;
	
	public String getSelectionCommitteeDate() {
		return selectionCommitteeDate;
	}
	public void setSelectionCommitteeDate(String selectionCommitteeDate) {
		this.selectionCommitteeDate = selectionCommitteeDate;
	}


	public String getJobRole() {
		return jobRole;
	}
	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}


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


	public String getCentreCity() {
		return centreCity;
	}
	public void setCentreCity(String centreCity) {
		this.centreCity = centreCity;
	}

	/**
	 * Parameterized Constructor
	 * @param selectionCommitteeDate
	 * @param jobRole
	 * @param trainingPartnerName
	 * @param sectorSkillCouncil
	 * @param centreCity
	 */
	public  GenerateMinutesOfSelectionDto(String selectionCommitteeDate,String jobRole,String trainingPartnerName,String sectorSkillCouncil,String centreCity){

		super();
		
		this.selectionCommitteeDate=selectionCommitteeDate;
		this.jobRole=jobRole;
		this.trainingPartnerName=trainingPartnerName;
		this.sectorSkillCouncil=sectorSkillCouncil;
		this.centreCity=centreCity;
		
	}
	
	/**
	 * Default Constructor
	 */
	public GenerateMinutesOfSelectionDto() {
		super();
	}
	

	
}
