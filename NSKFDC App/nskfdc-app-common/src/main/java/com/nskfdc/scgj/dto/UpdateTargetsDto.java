package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class UpdateTargetsDto extends BaseDto {

    private String nsdcRegNumber;
    private String trainingPartnerName;
    private int targets;

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

    public UpdateTargetsDto(String trainingPartnerName, int targets) {
        this.trainingPartnerName = trainingPartnerName;
        this.targets = targets;
    }

    public void setTargets(int targets) {
        this.targets = targets;
    }

    public UpdateTargetsDto(String nsdcRegNumber, String trainingPartnerName, int targets) {
        this.nsdcRegNumber = nsdcRegNumber;
        this.trainingPartnerName = trainingPartnerName;
        this.targets = targets;
    }

    public UpdateTargetsDto() {
        super();
    }
}
