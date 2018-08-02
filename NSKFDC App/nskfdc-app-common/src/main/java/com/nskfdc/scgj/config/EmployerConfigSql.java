package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="employer",locations="classpath:sql/employer.yml")
public class EmployerConfigSql {
	private String exists;
	private String insert;
	private String update;
	public String getExists() {
		return exists;
	}
	/**
	 * 
	 * @param exists
	 */
	public void setExists(String exists) {
		this.exists = exists;
	}
	public String getInsert() {
		return insert;
	}
	public void setInsert(String insert) {
		this.insert = insert;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}

}
