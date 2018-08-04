package com.nskfdc.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.common.Privilege;
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
	
	@Privilege(value= {"scgj"})
	@RequestMapping("/getNumberOfCandidatesTrained")
	public Integer getNumberOfCandidatesTrained(){
		LOGGER.debug("In DASHBOARD Controller");
		LOGGER.debug("To get NUMBER OF CANDIDATES TRAINED");
		
		try {
			
			LOGGER.debug("TRYING -- Get Number of Candidates Trained");
			LOGGER.debug("Sending request to Dashboard service");
			LOGGER.debug("Method - getNumberOfCandidatesTrained");
			return dashboardService.getNumberOfCandidatesTrained();
			
			}
		catch(Exception e) {
			LOGGER.error("CATCHING -- EXCEPTION in DASHBOARD Controller");
			LOGGER.error("Catch while getNumberOfCandidatesTrained");
			LOGGER.error("An exception occurred is" + e);
			return null;
			
			}
	}
	
	
	
	
	/**
	 * @author Shivanshu Garg
	 * @return Number of Training Partners
	 * @description Method to get Number of Training Partners
	 */
	@Privilege(value= {"scgj"})
	@RequestMapping("/getNumberOfTrainingPartners")
	public Integer getNumberOfTrainingPartners(){
			LOGGER.debug("In DASHBOARD Controller");
			LOGGER.debug("To get NUMBER OF TRAINING PARTNERS");
		try {
			
			LOGGER.debug("TRYING -- Get Number of Training Partners");
			LOGGER.debug("Sending request to Dashboard service");
			LOGGER.debug("Method - getNumberOfTrainingPartners");
			return dashboardService.getNumberOfTrainingPartners();
		}catch(Exception e) {
			
			LOGGER.error("CATCHING -- EXCEPTION in DASHBOARD Controller");
			LOGGER.error("Catch while getNumberOfTrainingPartners");
			LOGGER.error("An exception occurred is" + e);
			return null;
			
		}
	}
	
	
	
	
	
	/**
	 *@author Shivanshu Garg 
	 * @return Number of Upcoming assessments
	 * @description Method to find Number of Upcoming Assessments 
	 */
	@Privilege(value= {"scgj"})
	@RequestMapping("/getNumberOfUpcomingAssessments")
	public Integer getNumberOfUpcomingAssessments(){
		
		LOGGER.debug("In DASHBOARD Controller");
		LOGGER.debug("To get NUMBER OF UPCOMING ASSESSMENTS");
		
		try {
			LOGGER.debug("TRYING -- Get Number of Upcoming Assessments");
			LOGGER.debug("Sending request to Dashboard service");
			LOGGER.debug("Method - getNumberOfUpcomingAssessments");
			return dashboardService.getNumberOfUpcomingAssessments();
			
		}
		catch(Exception e) {
			
			LOGGER.error("CATCHING -- EXCEPTION in DASHBOARD Controller");
			LOGGER.error("Catch while in getNumberOfUpcomingAssessments");
			LOGGER.error("An exception occurred is" + e);
			return null;
			
		}
	}

	/**
	 * @author Shivanshu Garg
	 * @return Number of Ongoing Trainings
	 * @desciption Method to get Number of Ongoing Trainings
	 */
	@Privilege(value= {"scgj"})
	@RequestMapping("/getNumberOfOngoingTrainings")
	public Integer getNumberOfOngoingTrainings(){
		
		LOGGER.debug("In DASHBOARD Controller");
		LOGGER.debug("To get NUMBER OF ONGOING TRAININGS");
		
		try {
			LOGGER.debug("TRYING -- Get Number of Ongoing Trainings");
			LOGGER.debug("Sending request to Dashboard service");
			LOGGER.debug("Method - getNumberOfOngoingTrainings");			
			return dashboardService.getNumberOfOngoingTrainings();
			
		}catch(Exception e) {
			
			LOGGER.error("CATCHING -- EXCEPTION in DASHBOARD Controller");
			LOGGER.error("Catch while in getNumberOfOngoingTrainings");
			LOGGER.error("An exception occurred is" + e);
			return null;
			
		}
	}
	
	
	
	
	

	/**
	 * @author Shivanshu Garg
	 * @return Total number of candidates trained in last 6 months
	 * 
	 */
	@Privilege(value= {"scgj"})
	@RequestMapping("/getTotalNumberOfCandidatesTrainedInLast6Months")
	public Collection<CandidatesTrainedInLast6MonthsDto> getTotalNumberOfCandidatesTrainedInLast6Months(){
		
		LOGGER.debug("In DASHBOARD Controller");
		LOGGER.debug("For BAR CHART- 2");
		LOGGER.debug("In controller method - getTotalNumberOfCandidatesTrainedInLast6Months");
		
		
		try {
			LOGGER.debug("TRYING -- Get Total number of Candidates trained in last 6 months");
			LOGGER.debug("Sending request to Dashboard service");
			LOGGER.debug("Method - getShowTotalNumberOfCandidatesTrainedInLast6Months");
			return dashboardService.getShowTotalNumberOfCandidatesTrainedInLast6Months();
			}
		
		catch(Exception e) {
			LOGGER.error("CATCHING -- Exception in Dasboard Controller");
			LOGGER.error("In getTotalNumberOfCandidatesTrainedInLast6Months");
			LOGGER.error("An Exception occured is "+e);
			return null;
			
			}
	}

	
	
	
	/**
	 * @author Aman
	 * @description method to get details of states
	 */
	@Privilege(value= {"scgj"})
	@RequestMapping("/getStateDetails")
	public Collection<StateDetailsDto> getdetails(){
		LOGGER.debug("In DASHBOARD Controller");
		LOGGER.debug("For BAR CHART- 1");
		LOGGER.debug("To Get Top 5 states with maximum Training Centers");
		LOGGER.debug("In controller method - getdetails");

		
		try {
			LOGGER.debug("TRYING -- Get TOP 5 states with maximum Training Centers");
			LOGGER.debug("Sending request to Dashboard service");
			LOGGER.debug("Method - getShowStateDetails");
			return dashboardService.getShowStateDetails();
			
		}catch(Exception e) {
			LOGGER.error("CATCHING Exception in Dashboard Controller");
			LOGGER.error("In Method getdetails");
			LOGGER.error("An exception occurred is "+e);
			return null;
			
		}
	}

	
	
	/**
	 * @author Aman
	 * @description method to get details of Map
	 */
	@Privilege(value= {"scgj"})
	@RequestMapping("/getshowStateDetailsForMapChart")
	public Collection<StateDetailsDto> getshowStateDetailsForMapChart(){
		LOGGER.debug("In DASHBOARD Controller");
		LOGGER.debug("For MAP CHART - Get Number of Training Centers in each state");
		LOGGER.debug("In method - getshowStateDetailsForMapChart");

		
		try {
			LOGGER.debug("TRYING -- Get Number of Training Centers in each state");
			LOGGER.debug("Sending request to DASHBOARD service");
			LOGGER.debug("Method- getshowStateDetailsForMapChart");
			return dashboardService.getshowStateDetailsForMapChart();
			
		}catch(Exception e) {
			LOGGER.error("CATCHING -- Exception in Dashboard Controller");
			LOGGER.error("In method - getshowStateDetailsForMapChart");
			LOGGER.error("An exception occurred is"+e);
			return null;
			
		}
	}

	
	}





