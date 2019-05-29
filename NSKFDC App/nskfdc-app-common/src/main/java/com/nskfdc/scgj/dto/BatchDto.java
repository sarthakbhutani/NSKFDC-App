package com.nskfdc.scgj.dto;
import com.nskfdc.scgj.common.BaseDto;

	public class BatchDto extends BaseDto{
    
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String BatchId;

		public String getBatchId() {
			return BatchId;
		}

		public void setBatchId(String batchId) {
			BatchId = batchId;
		}

		public BatchDto(String batchId) {
			super();
			BatchId = batchId;
		}

		public BatchDto() {
			super();
		}
		

	}
