package com.nskfdc.scgj.dto;
import com.nskfdc.scgj.common.BaseDto;

	public class BatchDto extends BaseDto{
    
		private int BatchId;

		public int getBatchId() {
			return BatchId;
		}

		public void setBatchId(int batchId) {
			BatchId = batchId;
		}

		public BatchDto(int batchId) {
			super();
			BatchId = batchId;
		}

		public BatchDto() {
			super();
		}
		

	}
