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
		
		LOGGER.debug("Request received from Control to get Number Of Candidates Trained");
		LOGGER.debug("In CandidatesTrained Service, to get Number Of CandidatesTrained ");
		
		try {
			
			LOGGER.debug("In try block to get Number Of Candidates Trained");
			return dashboardDao.getNumberOfCandidatesTrained();
		}
		catch (Exception e) {
		
			LOGGER.error("An error occurred while getting the Number Of CandidatesTrained"+ e);
			return null;
		}
	}

	
	 /**
	 * @author Shivanshu Garg
	 * @return Number of Ongoing Trainings
	 * @description Method to find number of Ongoing Training
	 */

	public Integer getNumberOfOngoingTrainings(){
		
		LOGGER.debug("Request received from Control to get number of Ongoing Trainings");
		LOGGER.debug("In OngoingTrainings Service, to get number of OngoingTrainings ");
	
		try {
			LOGGER.debug("In try block to get Number of OngoingTrainings ");
			return dashboardDao.getNumberOfOngoingTrainings();
		}
		catch (Exception e) {
		
			LOGGER.error("An error occurred while getting the Number Of OngoingTrainings"+ e);
			return null;
			}
	}

	/**
	 * @author Shivanshu Garg
	 * @return Number of Training partners
	 * @description Method to find Number of Training Partners
	 */

	public Integer getNumberOfTrainingPartners(){
	
	LOGGER.debug("Request received from Control to get number of Training Partners  ");
	LOGGER.debug("In TrainingPartners Service, to get number of TrainingPartners ");
	
	try {
		LOGGER.debug("In try block to get number of TrainingPartners");
		return dashboardDao.getNumberOfTrainingPartners();
	}
	catch (Exception e) {
	
		LOGGER.error("An error occurred while getting the Number Of TrainingPartners"+ e);
		return null;
	}
}

	/**
	 *@author Shivanshu Garg 
	 * @return Number of Upcoming Assessments
	 * @descriptiopn Method to find Number of Upcoming Assessments
	 */

	public Integer getNumberOfUpcomingAssessments(){
	
	LOGGER.debug("Request received from Control to get Number of Upcoming assessments");
	LOGGER.debug("In UpcomingAssessments Service, to get the number of upcoming assessments");
	
	try {
		LOGGER.debug("In try block to get Number of UpcomingAssessments ");
		return dashboardDao.getNumberOfUpcomingAssessments();
	}
	catch (Exception e) {
	
		LOGGER.error("An error occurred while getting the Number Of UpcomingAssessments"+ e);
		return null;
	}
}

	/**
	 * @author Shivanshu Garg
	 * @return total number of candidates trained in last 6 momths
	 */
	public Collection<CandidatesTrainedInLast6MonthsDto> getShowTotalNumberOfCandidatesTrainedInLast6Months(){
	LOGGER.debug("Request received from Control");
	LOGGER.debug("In Dashboard Service, to get total Number of Candidates Trained in last 6 months");
	
	
	try {
		LOGGER.debug("In try block to get total Number of Candidates Trained in last 6 months");
		return dashboardDao.getTotalNumberOfCandidatesTrainedInLast6Months();
		} 
	
	catch (Exception e) {
	
		LOGGER.debug("An error occurred while getting the Number of Candidates Trained chart"+ e);
		return null;
						}
	}
	

	/**@author Aman
	 * @description method to get details of states
	 * 
	 */
	public Collection<StateDetailsDto> getShowStateDetails(){
		LOGGER.debug("Request received from Control");
		LOGGER.debug("In Dashboard Service, to get state details");
		
		
		try {
			LOGGER.debug("In try block to get state details");
			return dashboardDao.getShowStateDetails();
		} catch (Exception e) {
		
			LOGGER.debug("An error occurred while getting the state details"+ e);
			return null;
		}
	}
	
	/**@author Aman
	 * @description method to get details of Map of states
	 * 
	 */
	public Collection<StateDetailsDto> getshowStateDetailsForMapChart(){
		LOGGER.debug("Request received from Control");
		LOGGER.debug("In Dashboard Service, to get Map state details");
		
		
		try {
			LOGGER.debug("In try block to get Map state details");
			return dashboardDao.getshowStateDetailsForMapChart();
		} catch (Exception e) {
		
			LOGGER.debug("An error occurred while getting the Map state details"+ e);
			return null;
		}
	}
}

	
	





