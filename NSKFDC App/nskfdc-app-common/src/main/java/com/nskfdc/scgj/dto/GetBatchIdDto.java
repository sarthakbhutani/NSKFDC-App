package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class GetBatchIdDto extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String batchId;
	
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a Getter Method to receive batchIds. 
	 *@return  The Batch Ids of the Training Partner.
	 
	 **/
	
	public String getBatchId() {
		return batchId;
	}
	
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a Setter Method to set value to batch Id. 
	 *@return  Nil.
	 
	 **/
	
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a Parameterized Constructor that gets the batch Ids of the particular SCGJ Batch Number from the Rowmapper,and creates a collection of the Batch Ids. 
	 *@return Nil
	 
	 **/
	
	public GetBatchIdDto(String batchId) {
		
		this.batchId = batchId;
	}
	
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a Non-Parameterized Constructor. 
	 *@return Nil.
	 
	 **/
	
	public GetBatchIdDto(){
		super();
	}
	
}
