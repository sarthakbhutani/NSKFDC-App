package com.nskfdc.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import com.nskfdc.scgj.dto.CandidatesTrainedInLast6MonthsDto;
import com.nskfdc.scgj.dto.StateDetailsDto;
import com.nskfdc.scgj.service.DashboardService;


	@RestController
	public class DashboardController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	private DashboardService dashboardService;
	
	/**
	 * @author Shivanshu Garg
	 * @return total Number of candidates trained
	 * @description Method to Get Number of candidates trained 
	 */
	@RequestMapping("/getNumberOfCandidatesTrained")
	public Integer getNumberOfCandidatesTrained(){
		
		LOGGER.debug("Request received from frontend to get Number of Candidates Trained");
		LOGGER.debug("In get Number of CandidatesTrained Controller");
		
		try {
			
			LOGGER.debug("In try block to get Number of CandidatesTrained");
			LOGGER.debug("Sending request to service");
			return dashboardService.getNumberOfCandidatesTrained();
			
			}
		catch(Exception e) {
			
			LOGGER.error("An exception occurred while getting the Number of CandidatesTrained" + e);
			return null;
			
							}
	}
	
	/**
	 * @author Shivanshu Garg
	 * @return Number of Training Partners
	 * @description Method to get Number of Training Partners
	 */
	@RequestMapping("/getNumberOfTrainingPartners")
	public Integer getNumberOfTrainingPartners(){
		
		LOGGER.debug("Request received from frontend to get Number of Training partners");
		LOGGER.debug("In get Number of TrainingPartners Controller to get Number of training Partners");
		
		try {
			
			LOGGER.debug("In try block to get Number of TrainingPartners");
			LOGGER.debug("Sending request to service");
			return dashboardService.getNumberOfTrainingPartners();
			
		}catch(Exception e) {
			
			LOGGER.error("An exception occurred while getting the Number of TrainingPartners" + e);
			return null;
			
		}
	}
	
	
	/**
	 *@author Shivanshu Garg 
	 * @return Number of Upcoming assessments
	 * @description Method to find Number of Upcoming Assessments 
	 */
	
	@RequestMapping("/getNumberOfUpcomingAssessments")
	public Integer getNumberOfUpcomingAssessments(){
		
		LOGGER.debug("Request received from frontend to get number of Upcoming assessments");
		LOGGER.debug("In get Number of UpcomingAssessments Controller");
		
		try {
			
			LOGGER.debug("In try block to get Number of UpcomingAssessments ");
			LOGGER.debug("Sending request to service");
			return dashboardService.getNumberOfUpcomingAssessments();
			
		}
		catch(Exception e) {
			
			LOGGER.error("An exception occurred while getting the Number of UpcomingAssessments" + e);
			return null;
			
		}
	}

	/**
	 * @author Shivanshu Garg
	 * @return Number of Ongoing Trainings
	 * @desciption Method to get Number of Ongoing Trainings
	 */
	
	@RequestMapping("/getNumberOfOngoingTrainings")
	public Integer getNumberOfOngoingTrainings(){
		
		LOGGER.debug("Request received from frontend to get Number of Ongoing Trainings");
		LOGGER.debug("In get Number of OngoingTrainings Controller");
		
		try {
			
			LOGGER.debug("In try block to get Number of OngoingTrainings");
			LOGGER.debug("Sending request to service");
			return dashboardService.getNumberOfOngoingTrainings();
			
		}catch(Exception e) {
			
			LOGGER.error("An exception occurred while getting the Number Of OngoingTrainings" + e);
			return null;
			
		}
	}

	/**
	 * @author Shivanshu Garg
	 * @return Total number of candidates trained in last 6 months
	 * 
	 */
	@RequestMapping("/getTotalNumberOfCandidatesTrainedInLast6Months")
	public Collection<CandidatesTrainedInLast6MonthsDto> getTotalNumberOfCandidatesTrainedInLast6Months(){
		LOGGER.debug("Request received from frontend to get Total Number Of Candidates Trained In Last 6 Months");
		LOGGER.debug("In total number of Candidates Trained in last 6 months Controller");

		
		try {
			LOGGER.debug("In try block to get Number of Candidates Trained in last 6 months");
			LOGGER.debug("Sending request to service");
			return dashboardService.getShowTotalNumberOfCandidatesTrainedInLast6Months();
			}
		
		catch(Exception e) {
			return null;
			
							}
	}

	
	/**
	 * @author Aman
	 * @description method to get details of states
	 */
	
	@RequestMapping("/getStateDetails")
	public Collection<StateDetailsDto> getdetails(){
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In State Controller");

		
		try {
			LOGGER.debug("In try block to get State details");
			LOGGER.debug("Sending request to service");
			return dashboardService.getShowStateDetails();
			
		}catch(Exception e) {
			return null;
			
		}
	}

	/**
	 * @author Aman
	 * @description method to get details of Map
	 */
	
	@RequestMapping("/getshowStateDetailsForMapChart")
	public Collection<StateDetailsDto> getshowStateDetailsForMapChart(){
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In Map State Controller");

		
		try {
			LOGGER.debug("In try block to get Map State details");
			LOGGER.debug("Sending request to service");
			return dashboardService.getshowStateDetailsForMapChart();
			
		}catch(Exception e) {
			return null;
			
		}
	}

	
	}





