package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="login",locations="classpath:sql/login.yml")

public class LoginConfigSql {
		
		private String checkUserSql;
		private String getValidUserDetails;
		private String getNameOfUser;
		
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
		public String getGetNameOfUser() {
			return getNameOfUser;
		}
		public void setGetNameOfUser(String getNameOfUser) {
			this.getNameOfUser = getNameOfUser;
		}


	}



	
	
	

