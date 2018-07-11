
	package com.nskfdc.scgj.dto;

	import com.nskfdc.scgj.common.BaseDto;

	public class BatchDto extends BaseDto{
		public BatchDto(String batchId) {
			super();
			this.batchId = batchId;
		}

		private String batchId;

		public String getBatchId() {
			return batchId;
		}

		public void setBatchId(String batchId) {
			this.batchId = batchId;
		}

		public BatchDto() {
			super();
			// TODO Auto-generated constructor stub
		}


	}
