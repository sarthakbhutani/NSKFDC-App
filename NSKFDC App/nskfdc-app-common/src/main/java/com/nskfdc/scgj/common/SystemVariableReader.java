package com.nskfdc.scgj.common;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SystemVariableReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemVariableReader.class);
	public String readSystemVariable(String key)
	{
		LOGGER.debug("Finding path for key : "+ key);
		Map<String, String> env = System.getenv();
		return env.get(key);
	}
}
