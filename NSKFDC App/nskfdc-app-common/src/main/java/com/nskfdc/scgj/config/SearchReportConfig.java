package com.nskfdc.scgj.config;

 import org.springframework.boot.context.properties.ConfigurationProperties;
 import org.springframework.stereotype.Component;
   @Component
   @ConfigurationProperties(prefix="searchReportQuery",locations="classpath:sql/searchReport.yml")
    public class SearchReportConfig {
            private String showReport;

			public String getShowReport() {
				return showReport;
			}

			public void setShowReport(String showReport) {
				this.showReport = showReport;
			}
            
                        }
