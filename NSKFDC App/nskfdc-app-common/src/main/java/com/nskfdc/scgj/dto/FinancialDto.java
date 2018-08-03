package com.nskfdc.scgj.dto;


	import com.nskfdc.scgj.common.BaseDto;

	public class FinancialDto extends BaseDto{

		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String financialYear;

		
		public String getFinancialDto() {
			return financialYear;
		}
		public String getFinancialYear() {
			return financialYear;
		}
		public void setFinancialYear(String financialYear) {
			this.financialYear = financialYear;
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


