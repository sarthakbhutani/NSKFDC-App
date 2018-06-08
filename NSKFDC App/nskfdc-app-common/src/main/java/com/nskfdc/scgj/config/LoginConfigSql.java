package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="login",locations="classpath:sql/login.yml")
public class LoginConfigSql {

	private String checkUserSql;
	private String getUserName;
	
	public String getGetUserName() {
		return getUserName;
	}

	public void setGetUserName(String getUserName) {
		this.getUserName = getUserName;
	}

	public String getCheckUserSql() {
		return checkUserSql;
	}

	public void setCheckUserSql(String checkUserSql) {
		this.checkUserSql = checkUserSql;
	}
	
	
}
