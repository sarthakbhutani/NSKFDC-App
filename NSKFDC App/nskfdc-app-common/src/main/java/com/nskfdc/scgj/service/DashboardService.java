package com.nskfdc.scgj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.DashboardDao;
import com.nskfdc.scgj.dto.CandidatesTrainedInLast6MonthsDto;
import com.nskfdc.scgj.dto.StateDetailsDto;

@Service
public class DashboardService {
private static final Logger LOGGER = LoggerFactory.getLogger(DashboardService.class);
	

	@Autowired
	private DashboardDao dashboardDao;
	
	
	
	/**
	 *@author Shivanshu Garg 
	 * @return number of candidates trained
	 */
	public Integer getNumberOfCandidatesTrained(){
		
		LOGGER.debug("Request received from getNumberOfCandidatesTrained");
		LOGGER.debug("IN DASHBOARD Service");
		LOGGER.debug("To get Number of Candidates Trained - getNumberOfCandidatesTrained");
		
		try {
			LOGGER.debug("TRYING -- Get Number of Candidates Trained");
			LOGGER.debug("Sending request to Dashboard DAO");
			LOGGER.debug("Method - getNumberOfCandidatesTrained");
			return dashboardDao.getNumberOfCandidatesTrained();
		}
		catch (Exception e) {
			LOGGER.error("CATCHING -- Exception in DASHBOARD Service");
			LOGGER.error("In method - getNumberOfCandidatesTrained");
			LOGGER.error("An exception occurred is "+e);
			return null;
		}
	}

	
	
	 /**
	 * @author Shivanshu Garg
	 * @return Number of Ongoing Trainings
	 * @description Method to find number of Ongoing Training
	 */

	public Integer getNumberOfOngoingTrainings(){
		
		LOGGER.debug("Request received from getNumberOfOngoingTrainings");
		LOGGER.debug("IN DASHBOARD Service");
		LOGGER.debug("To get Number of Ongoing Trainings - getNumberOfOngoingTrainings");
		
		try {
			LOGGER.debug("TRYING -- Get Number of Ongoing Trainings");
			LOGGER.debug("Sending request to Dashboard DAO");
			LOGGER.debug("Method - getNumberOfOngoingTrainings");
			return dashboardDao.getNumberOfOngoingTrainings();
		}
		catch (Exception e) {
			LOGGER.error("CATCHING -- Exception in DASHBOARD Service");
			LOGGER.error("In method - getNumberOfOngoingTrainings");
			LOGGER.error("An exception occurred is "+e);		
			return null;
			}
	}

	/**
	 * @author Shivanshu Garg
	 * @return Number of Training partners
	 * @description Method to find Number of Training Partners
	 */

	public Integer getNumberOfTrainingPartners(){
	
		
		LOGGER.debug("Request received from getNumberOfTrainingPartners");
		LOGGER.debug("IN DASHBOARD Service");
		LOGGER.debug("To get Number of Training Partners - getNumberOfTrainingPartners");
		
		try {
			LOGGER.debug("TRYING -- Get Number of Training Partners");
			LOGGER.debug("Sending request to Dashboard DAO");
			LOGGER.debug("Method - getNumberOfTrainingPartners");
			return dashboardDao.getNumberOfTrainingPartners();
		}
		catch (Exception e) {
			LOGGER.error("CATCHING -- Exception in Dashboard Service");
			LOGGER.error("In method - getNumberOfTrainingPartners");
			LOGGER.error("An exception Occurred is "+ e);
			return null;
		}
}

	/**
	 *@author Shivanshu Garg 
	 * @return Number of Upcoming Assessments
	 * @descriptiopn Method to find Number of Upcoming Assessments
	 */

	public Integer getNumberOfUpcomingAssessments(){
	
		LOGGER.debug("Request received from getNumberOfUpcomingAssessments");
		LOGGER.debug("IN DASHBOARD Service");
		LOGGER.debug("To get Number of Upcoming Assessments - getNumberOfUpcomingAssessments");
		
		try {
			LOGGER.debug("TRYING -- Get Number of Upcoming Assessments");
			LOGGER.debug("Sending request to Dashboard DAO");
			LOGGER.debug("Method - getNumberOfUpcomingAssessments");
		return dashboardDao.getNumberOfUpcomingAssessments();
		}
		catch (Exception e) {
			LOGGER.error("CATCHING -- Exception handled in Dashboard Service");
			LOGGER.error("In method - getNumberOfUpcomingAssessments");
			LOGGER.error("An exception occurred is "+ e);
			return null;
		}
}

	/**
	 * @author Shivanshu Garg
	 * @return total number of candidates trained in last 6 momths
	 */
	public Collection<CandidatesTrainedInLast6MonthsDto> getShowTotalNumberOfCandidatesTrainedInLast6Months(){
		LOGGER.debug("Request received from getTotalNumberOfCandidatesTrainedInLast6Months");
		LOGGER.debug("IN DASHBOARD Service");
		LOGGER.debug("To get BAR CHART- 2");
		LOGGER.debug("In getTotalNumberOfCandidatesTrainedInLast6Months");
		
		try {
			LOGGER.debug("TRYING -- Get Total Number of Candidates trained in Last 6 months");
			LOGGER.debug("Sending request to Dashboard DAO");
			LOGGER.debug("Method - getTotalNumberOfCandidatesTrainedInLast6Months");
			return dashboardDao.getTotalNumberOfCandidatesTrainedInLast6Months();
		} 
	
	catch (Exception e) {
			LOGGER.error("CATCHING -- Exception handled in Dashboard Service");
			LOGGER.error("In method - getShowTotalNumberOfCandidatesTrainedInLast6Months");
			LOGGER.error("An exception occurred is "+ e);
			return null;
		}
	}
	

	/**@author Aman
	 * @description method to get details of states
	 * 
	 */
	public Collection<StateDetailsDto> getShowStateDetails(){
		LOGGER.debug("Request received from getdetails");
		LOGGER.debug("In Dashboard Service");
		LOGGER.debug("For BAR CHART - 1");
		LOGGER.debug("In getShowStateDetails");
		
		
		try {
			LOGGER.debug("TRYING -- To get Top 5 states with Maximum Training Centers");
			LOGGER.debug("Sending Request to DASHBOARD DAO");
			LOGGER.debug("Method - getShowStateDetails");
			return dashboardDao.getShowStateDetails();
		} catch (Exception e) {
			LOGGER.error("CATCHING -- Exception handled in Dashboard Service");
			LOGGER.error("In method - getShowStateDetails");
			LOGGER.error("An error occurred is "+e);
			return null;
		}
	}
	
	/**@author Aman
	 * @description method to get details of Map of states
	 * 
	 */
	public Collection<StateDetailsDto> getshowStateDetailsForMapChart(){
		LOGGER.debug("Request received from getshowStateDetailsForMapChart");
		LOGGER.debug("In Dashboard Service");
		LOGGER.debug("For MAP CHART ");
		LOGGER.debug("In getshowStateDetailsForMapChart");
		
		
		try {
			LOGGER.debug("TRYING -- To get number of Training Center in Each state");
			LOGGER.debug("Sending Request to DASHBOARD DAO");
			LOGGER.debug("Method - getshowStateDetailsForMapChart");
			return dashboardDao.getshowStateDetailsForMapChart();
		} catch (Exception e) {
		
			LOGGER.error("CATCHING -- Exception handled in Dashboard Service");
			LOGGER.error("In method - getshowStateDetailsForMapChart");
			LOGGER.error("An error occurred is "+e);
			return null;
		}
	}
}

	
	





