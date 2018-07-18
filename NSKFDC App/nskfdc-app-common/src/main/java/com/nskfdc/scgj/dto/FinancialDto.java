package com.nskfdc.scgj.dto;


	import com.nskfdc.scgj.common.BaseDto;

	public class FinancialDto extends BaseDto{

		
		private String financialYear;

		
		public String getFinancialDto() {
			return financialYear;
		}
		public void setFinancialDto(String financialDto) {
			financialYear = financialDto;
		}
		public FinancialDto(String financialDto ){
			super();
			this.financialYear=financialDto;
		}
		public FinancialDto(){
			super();
		}
		
		
		
	}


