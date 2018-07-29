package com.nskfdc.scgj.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:ApplicationConstants.properties")
@ConfigurationProperties
public class ReadApplicationConstants {

    private String saveExcelSheetAtLocation;

    public String getSaveExcelSheetAtLocation() {
        return saveExcelSheetAtLocation;
    }

    public void setSaveExcelSheetAtLocation(String saveExcelSheetAtLocation) {
        this.saveExcelSheetAtLocation = saveExcelSheetAtLocation;
    }
}
