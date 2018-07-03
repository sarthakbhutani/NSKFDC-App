package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="login",locations="classpath:sql/login.yml")
public class LoginConfigSql {

	
	
}
