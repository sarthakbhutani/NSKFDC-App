package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="login",locations="classpath:sql/login.yml")

public class LoginConfigSql {
		
		private String checkUserSql;
		private String getValidUserDetails;
		
		public String getCheckUserSql() {
			return checkUserSql;
		}
		public void setCheckUserSql(String checkUserSql) {
			this.checkUserSql = checkUserSql;
		}
		public String getGetValidUserDetails() {
			return getValidUserDetails;
		}
		public void setGetValidUserDetails(String getValidUserDetails) {
			this.getValidUserDetails = getValidUserDetails;
		}


	}



	
	
	

