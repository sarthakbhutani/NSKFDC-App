package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="updateTargets",locations = "classpath:sql/updateTargets.yml")
public class UpdateTargetsConfig {

    private String updateTargets;
    private String updatedTargetDetails;
    private String checkUserExistence;
    
    
    

    public String getCheckUserExistence() {
		return checkUserExistence;
	}

	public void setCheckUserExistence(String checkUserExistence) {
		this.checkUserExistence = checkUserExistence;
	}

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
