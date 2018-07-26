package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="updateTargets",locations = "classpath:sql/updateTargets.yml")
public class UpdateTargetsConfig {

    private String updateTargets;
    private String updatedTargetDetails;

    public String getUpdatedTargetDetails() {
        return updatedTargetDetails;
    }

    public void setUpdatedTargetDetails(String updatedTargetDetails) {
        this.updatedTargetDetails = updatedTargetDetails;
    }

    public String getUpdateTargets() {
        return updateTargets;
    }

    public void setUpdateTargets(String updateTargets) {
        this.updateTargets = updateTargets;
    }
}
