package com.nskfdc.scgj.dto;
import com.nskfdc.scgj.common.BaseDto;


public class CandidatesTrainedInLast6Months extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @author Shivanshu Garg
	 * @description Initialising data members to get  total number of candidates trained in last 6 months
	 */
	
	private String month;
	private int count;
	
	public String getmonth() {
		return month;
	}
	public void setmonth(String month) {
		this.month = month;
	}
	
	public int getcount() {
		return count;
	}
	public void setcount(int count) {
		this.count = count;
	}
	
	/** @author Shivanshu Garg
	 * @description Parameterised constructor to map result set of candidates trained in last 6 months
	 *
	 **/
	

	public CandidatesTrainedInLast6Months() {
		super();
	
	}
	
	

	public CandidatesTrainedInLast6Months(String month, int count) {
		super();
		
		this.month = month;
		this.count = count;
	}
	

	
	
}

