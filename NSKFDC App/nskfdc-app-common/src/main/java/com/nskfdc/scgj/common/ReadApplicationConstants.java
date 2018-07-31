package com.nskfdc.scgj.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:ApplicationConstants.properties")
@ConfigurationProperties
public class ReadApplicationConstants {

    private String saveExcelSheetAtLocation;
    private String saveDocumentsAtLocation;

    public String getSaveDocumentsAtLocation() {
		return saveDocumentsAtLocation;
	}

	public void setSaveDocumentsAtLocation(String saveDocumentsAtLocation) {
		this.saveDocumentsAtLocation = saveDocumentsAtLocation;
	}

	public String getSaveExcelSheetAtLocation() {
        return saveExcelSheetAtLocation;
    }

    public void setSaveExcelSheetAtLocation(String saveExcelSheetAtLocation) {
        this.saveExcelSheetAtLocation = saveExcelSheetAtLocation;
    }
}
