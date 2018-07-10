package com.nskfdc.scgj.dto;
import com.nskfdc.scgj.common.BaseDto;

public class StateDetailsDto extends BaseDto{
	
	/**
	 * @author Shivanshu Garg
	 * @description Initialising data members to get  total number of candidates trained in last 6 months
	 */
	private String states;
	private int centers;

	
	
	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public int getCenters() {
		return centers;
	}

	public void setCenters(int centers) {
		this.centers = centers;
	}
	
	
	
	
	/** @author Shivanshu Garg
	 * @description Parameterised constructor to map result set of candidates trained in last 6 months
	 *
	 **/
	

	public StateDetailsDto() {
		super();
	
	}
	
	

	public StateDetailsDto(String states, int centers) {
		super();
		this.states = states;
		this.centers = centers;
		
	}

	
	
}

