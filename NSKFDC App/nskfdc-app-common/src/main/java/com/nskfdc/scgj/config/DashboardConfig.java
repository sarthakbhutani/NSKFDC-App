package com.nskfdc.scgj.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="DashboardStatistics",locations="classpath:sql/dashboard.yml")
public class DashboardConfig {
	private String showNumberOfCandidatesTrained;
	
	public String getShowNumberOfCandidatesTrained() {
		return showNumberOfCandidatesTrained;
	}

	public void setShowNumberOfCandidatesTrained(String showNumberOfCandidatesTrained) {
		this.showNumberOfCandidatesTrained = showNumberOfCandidatesTrained;
	}

		/**
		*@author Shivanshu Garg
		*@return Number of Ongoing Trainings
		*@description Method to find Number of Ongoing Trainings 
		*/
	
		private String showNumberOfOngoingTrainings;
		
		public String getShowNumberOfOngoingTrainings() {
			return showNumberOfOngoingTrainings;
		}

		public void setShowNumberOfOngoingTrainings(String showNumberOfOngoingTrainings) {
			this.showNumberOfOngoingTrainings = showNumberOfOngoingTrainings;
		}

	
		/**
		* @author Shivanshu Garg
		* @return Number of Training Partners
		* @description Method to find Number of Training partners 
		*/
	

	private String showNumberOfTrainingPartners;
		
	public String getShowNumberOfTrainingPartners() {
			return showNumberOfTrainingPartners;
	}

	public void setShowNumberOfTrainingPartners(String showNumberOfTrainingPartners) {
		this.showNumberOfTrainingPartners = showNumberOfTrainingPartners;
	}

		
		/**
		* @author Shivanshu Garg
		* @return Number of Upcoming Assessments
		* @description Method to find Number of Upcoming Assessments
		*/
	
	private String showNumberOfUpcomingAssessments;
			
	public String getShowNumberOfUpcomingAssessments() {
		return showNumberOfUpcomingAssessments;
	}

	public void setShowNumberOfUpcomingAssessments(String showNumberOfUpcomingAssessments) {
		this.showNumberOfUpcomingAssessments = showNumberOfUpcomingAssessments;
	}


		/**
		* @author Shivanshu Garg
		* @return Total number of candidates trained in last 6 months
		*/
	private String showTotalNumberOfCandidatesTrainedInLast6Months;

		public String getShowTotalNumberOfCandidatesTrainedInLast6Months() {
			return showTotalNumberOfCandidatesTrainedInLast6Months;
	}

	public void setShowTotalNumberOfCandidatesTrainedInLast6Months(
		String showTotalNumberOfCandidatesTrainedInLast6Months) {
		this.showTotalNumberOfCandidatesTrainedInLast6Months = showTotalNumberOfCandidatesTrainedInLast6Months;
	}

		 

		
	/**
	 * @author Aman
	 * @return State details
	 * @description method to return details of state
	 */
private String showStateDetails;

public String getShowStateDetails() {
	
	return showStateDetails;
}

public void setShowStateDetails(String showStateDetails) {
	this.showStateDetails = showStateDetails;
}


/**
 * @author Aman
 * @return Map details
 * @description method to return details of Map chart
 */
private String showStateDetailsForMapChart;

public String getshowStateDetailsForMapChart() {

return showStateDetailsForMapChart;
}

public void setshowStateDetailsForMapChart(String showStateDetailsForMapChart) {
this.showStateDetailsForMapChart = showStateDetailsForMapChart;
}


}
