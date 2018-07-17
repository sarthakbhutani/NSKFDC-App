package com.nskfdc.scgj.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.DashboardConfig;
import com.nskfdc.scgj.dto.CandidatesTrainedInLast6MonthsDto;
import com.nskfdc.scgj.dto.StateDetailsDto;


@Repository
public class DashboardDao extends AbstractTransactionalDao{
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DashboardDao.class);
	private static final BarchartRowmapper rm = new BarchartRowmapper();
	private static final BarchartRowmapper1 rm1 = new BarchartRowmapper1();
	private static final MapchartRowmapper rm2 = new MapchartRowmapper();
	
	

	@Autowired
	private DashboardConfig dashboardConfig;
	
	/**
	 * @author Shivanshu Garg
	 * @description Method to fetch  number of Candidates Trained. 
	 * @return Number of candidates trained
	 */
	
	/**
	 *@author Shivanshu Garg
	 * @description Hashmap to map empty parameters
	 * to fulfill queryForObject Syntax
	 */
	
	public Integer getNumberOfCandidatesTrained(){
		
		LOGGER.debug("Request received from Service");
		LOGGER.debug("In NumberOfCandidatesTrained Dao, to get Number of CandidatesTrained");
		
		
		try {
			
			LOGGER.debug("In try block");
			LOGGER.debug("Execute query to get NumberOfCandidatesTrained");
			Map<String,Object> parameters = new HashMap<> ();
			return getJdbcTemplate().queryForObject(dashboardConfig.getShowNumberOfCandidatesTrained(),parameters,Integer.class);
			
		} 
		catch(DataAccessException de)
		{
			LOGGER.error("A data access exception has occured: " + de);
			LOGGER.error("Returning zero");
			return null;
		}
		
		catch(Exception e)
		{
			LOGGER.error("An Exception occured: " + e);
			LOGGER.error("Returning 0");
			return null;
		}
		
	}

	/**
	 * @author Shivanshu Garg
	 * @return number of ongoing trainings
	 * @description Method to find number of ongoing trainings
	 */

	/**
	 * @description Hashmap to map empty parameters
	 * to fulfill queryForObject Syntax
	 */
	
	public Integer getNumberOfOngoingTrainings(){
		
		LOGGER.debug("Request received from Service");
		LOGGER.debug("In get Number Of Ongoing Trainings Dao, to get Number of OngoingTrainings");
		
		
		try {
			
			LOGGER.debug("In try block");
			LOGGER.debug("Execute query to get Number Of OngoingTrainings");
			Map<String,Object> parameters = new HashMap<> ();
			return getJdbcTemplate().queryForObject(dashboardConfig.getShowNumberOfOngoingTrainings(),parameters,Integer.class);
			
		} 
		catch(DataAccessException de)
		{
			LOGGER.error("A data access exception has occured: " + de);
			LOGGER.error("Returning 0");
			return null;
		}
		
		catch(Exception e)
		{
			LOGGER.error("An exception occured: " + e);
			return null;
		}
		
		
	}
/**
 * @author Shivanshu Garg
 * @return Number of Training partners
 * @description Method to find number of training partners
 */
	
	/**
	 * @description Hashmap to map empty parameters
	 * to fulfill queryForObject Syntax
	 */
	
public Integer getNumberOfTrainingPartners(){
	
	LOGGER.debug("Request received from Service");
	LOGGER.debug("In Number Of Training Partners Dao, to get Number Of TrainingPartners");
	
	
	try {    
		
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get Number Of Training Partners");
		Map<String,Object> parameters = new HashMap<>();
		return getJdbcTemplate().queryForObject(dashboardConfig.getShowNumberOfTrainingPartners(),parameters, Integer.class);
		
	} 
	catch(DataAccessException de)
	{
		LOGGER.error("A data access exception has occured: " + de);
		LOGGER.error("Returning 0");
		return null;
	}
	
	catch(Exception e)
	{
		LOGGER.error("An exception occured: " + e);
		return null;
	}
	
	
}
	/**
	 * @author Shivanshu Garg
	 * @return Number of Upcoming assessments
	 * @description Method to find number of upcoming assessments
	 */

	/**
	 * @description Hashmap to map empty parameters
	 * to fulfill queryForObject Syntax
	 */

public Integer getNumberOfUpcomingAssessments(){
	
	LOGGER.debug("Request received from Service");
	LOGGER.debug("In Number Of Upcoming Assessments Dao, to get Number Of UpcomingAssessments");
	
	
	try {
		
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get  Number Of Upcoming Assessments");
		Map<String,Object> parameters = new HashMap<> ();
		return getJdbcTemplate().queryForObject(dashboardConfig.getShowNumberOfUpcomingAssessments(),parameters,Integer.class);
		
	} 
	catch(DataAccessException de)
	{
		LOGGER.error("A data access exception has occured: " + de);
		LOGGER.error("Returning 0");
		return  null;
	}
	catch (Exception e) {
		
		LOGGER.debug("In Catch Block");
		LOGGER.error("An exception occured while getting the Number Of Upcoming Assessments" + e);
		return null;
		
	}
	
}


/**
 * @author Shivanshu Garg
 * @return total number of candidates trained in last 6 months
 */

public Collection<CandidatesTrainedInLast6MonthsDto> getTotalNumberOfCandidatesTrainedInLast6Months(){
	
	LOGGER.debug("Request received from Service");
	LOGGER.debug("In DashboardDao, to get total number of Candidates Trained in last 6 months ");
	
	
	try {
		
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get total number of Candidates Trained in last 6 months");
		Map<String,Object> parameters= new HashMap<>();
		return getJdbcTemplate().query(dashboardConfig.getShowTotalNumberOfCandidatesTrainedInLast6Months(),parameters,rm1);
				
		
	} 
	catch(DataAccessException de)
	{
		LOGGER.error("A data access exception has occured: " + de);
		LOGGER.debug("Returning 0");
		return null;
	}
	catch (Exception e) {
		
		LOGGER.debug("In Catch Block");
		LOGGER.debug("An error occured while getting the total number of candidates trained in last 6 months" + e);
		return null;
		
	}
	
}




	 /**
	  * @author Aman
	  * @description method to find details of states
	  */

public Collection<StateDetailsDto> getShowStateDetails(){
	
	LOGGER.debug("Request received from Service");
	LOGGER.debug("In DashboardDao, to get StateChart Detail");
	
	
	try {
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get Statechart details");
		Map<String,Object> parameters= new HashMap<>();
		return getJdbcTemplate().query(dashboardConfig.getShowStateDetails(),parameters,rm);
		
	} catch (Exception e) {
		LOGGER.debug("In Catch Block");
		LOGGER.debug("An error occured while getting the state chart details" + e);
		return null;
		
	}
	
}


private static class BarchartRowmapper implements RowMapper<StateDetailsDto>{
	
	@Override
	public StateDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		String states=rs.getString("centreState");
		int centers=rs.getInt("centers");
		return new StateDetailsDto(states,centers);
		
	}
	
}

private static class BarchartRowmapper1 implements RowMapper<CandidatesTrainedInLast6MonthsDto>{
	
	@Override
	public CandidatesTrainedInLast6MonthsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		
		
		String month = rs.getString("month");
		int count = rs.getInt("count");
		return new CandidatesTrainedInLast6MonthsDto(month,count);
		
	}
	
}
/**
 * @author Aman
 * @description method to find details of Map states
 */

public Collection<StateDetailsDto> getshowStateDetailsForMapChart(){

LOGGER.debug("Request received from Service");
LOGGER.debug("In DashboardDao, to get  Map StateChart Detail");


try {
	LOGGER.debug("In try block");
	LOGGER.debug("Execute query to get Map Statechart details");
	Map<String,Object> parameters= new HashMap<>();
	return getJdbcTemplate().query(dashboardConfig.getshowStateDetailsForMapChart(),parameters,rm2);
	
} catch (Exception e) {
	LOGGER.debug("In Catch Block");
	LOGGER.debug("An error occured while getting the Map state chart details" + e);
	return null;
	
}

}


private static class MapchartRowmapper implements RowMapper<StateDetailsDto>{

@Override
public StateDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	
	String states=rs.getString("centreState");
	int centers=rs.getInt("centers");
	return new StateDetailsDto(states,centers);
	
}

}

}


