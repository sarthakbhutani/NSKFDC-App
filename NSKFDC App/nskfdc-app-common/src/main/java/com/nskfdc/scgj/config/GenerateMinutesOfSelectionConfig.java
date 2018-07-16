package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="generateSheet",locations="classpath:sql/MinutesSelectionCommitte.yml")
public class GenerateMinutesOfSelectionConfig {
	
	private String showMinutesOfSelectionMeetingDetails;

	public String getShowMinutesOfSelectionMeetingDetails() {
		return showMinutesOfSelectionMeetingDetails;
	}

	public void setShowMinutesOfSelectionMeetingDetails(
			String showMinutesOfSelectionMeetingDetails) {
		this.showMinutesOfSelectionMeetingDetails = showMinutesOfSelectionMeetingDetails;
	}

}
