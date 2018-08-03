package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

import java.util.Date;

public class UpdateTargetsDto extends BaseDto {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nsdcRegNumber;
    private String trainingPartnerName;
    private int targets;
    private Date targetApprovalDate;


    public String getNsdcRegNumber() {
        return nsdcRegNumber;
    }

    public void setNsdcRegNumber(String nsdcRegNumber) {
        this.nsdcRegNumber = nsdcRegNumber;
    }

    public String getTrainingPartnerName() {
        return trainingPartnerName;
    }

    public void setTrainingPartnerName(String trainingPartnerName) {
        this.trainingPartnerName = trainingPartnerName;
    }

    public int getTargets() {
        return targets;
    }

    public void setTargets(int targets) {
        this.targets = targets;
    }

    public Date getTargetApprovalDate() {
        return targetApprovalDate;
    }

    public void setTargetApprovalDate(Date targetApprovalDate) {
        this.targetApprovalDate = targetApprovalDate;
    }

    public UpdateTargetsDto(String nsdcRegNumber, String trainingPartnerName, int targets, Date targetApprovalDate) {
        this.nsdcRegNumber = nsdcRegNumber;
        this.trainingPartnerName = trainingPartnerName;
        this.targets = targets;
        this.targetApprovalDate = targetApprovalDate;
    }

    public UpdateTargetsDto() {
        super();
    }
}
