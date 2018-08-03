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
import org.springframework.dao.EmptyResultDataAccessException;
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
		
		LOGGER.debug("Request received from Service - getNumberOfCandidatesTrained");
		LOGGER.debug("In DASHBOARD DAO");
		LOGGER.debug("To get Number of Candidates Trained");
		
		
		try {
			
			LOGGER.debug("TRYING -- getNumberOfCandidatesTrained");
			Map<String,Object> parameters = new HashMap<> ();
			LOGGER.debug("EXECUTING query to get NumberOfCandidatesTrained");
			return getJdbcTemplate().queryForObject(dashboardConfig.getShowNumberOfCandidatesTrained(),parameters,Integer.class);
			
		} 
		catch(EmptyResultDataAccessException re)
		{
			LOGGER.error("CATCHING -- EmptyResultDataAccessException Handled in Dashboard DAO");
			LOGGER.error("In method - getNumberOfCandidatesTrained" + re);
			return 0;
		}
		catch(DataAccessException de)
		{
			LOGGER.error("CATCHING -- DataAccessException Handled in Dashboard DAO");
			LOGGER.error("In method - getNumberOfCandidatesTrained" + de);
			return 0;
		}
		
		catch(Exception e)
		{
			LOGGER.error("CATCHING -- Exception Handled in Dashboard DAO");
			LOGGER.error("In method - getNumberOfCandidatesTrained");
			LOGGER.error("An exception occurred is "+e);
			return -1;
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
		
		LOGGER.debug("Request received from Service - getNumberOfOngoingTrainings");
		LOGGER.debug("In DASHBOARD Dao, To get Number of OngoingTrainings");
		
		
		try {
			
			LOGGER.debug("TRYING -- getNumberOfOngoingTrainings");
			LOGGER.debug("EXECUTING  query to get Number Of Ongoing Trainings");
			Map<String,Object> parameters = new HashMap<> ();
			return getJdbcTemplate().queryForObject(dashboardConfig.getShowNumberOfOngoingTrainings(),parameters,Integer.class);
			
		} 
		catch(EmptyResultDataAccessException re)
		{
			LOGGER.error("CATCHING -- EmptyResultDataAccessException Handled in Dashboard DAO");
			LOGGER.error("In method - getNumberOfOngoingTrainings" + re);
			return 0;
		}
		catch(DataAccessException de)
		{
			LOGGER.error("CATCHING -- DataAccessException Handled in Dashboard DAO");
			LOGGER.error("In method - getNumberOfOngoingTrainings" + de);
			return -1;
		}
	
		catch(Exception e)
		{
			LOGGER.error("CATCHING -- Exception Handled in Dashboard DAO");
			LOGGER.error("In method - getNumberOfOngoingTrainings");
			LOGGER.error("An exception occurred is "+e);
			return -2;
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
	
	LOGGER.debug("Request received from Service - getNumberOfTrainingPartners");
	LOGGER.debug("In DASHBOARD Dao");
	LOGGER.debug(" To get total Number Of TrainingPartners");
	
	try {    
		
		LOGGER.debug("TRYING -- getNumberOfTrainingPartners");
		LOGGER.debug("Execute query -- Get Total Number of Training Partners");
		Map<String,Object> parameters = new HashMap<>();
		return getJdbcTemplate().queryForObject(dashboardConfig.getShowNumberOfTrainingPartners(),parameters, Integer.class);
	}
	catch(EmptyResultDataAccessException re)
	{
		LOGGER.error("CATCHING -- EmptyResultDataAccessException Handled in Dashboard DAO");
		LOGGER.error("In method - getNumberOfTrainingPartners" + re);
		return 0;
	}
	catch(DataAccessException de)
	{
		LOGGER.error("CATCHING -- DataAccessException Handled in Dashboard DAO");
		LOGGER.error("In method - getNumberOfTrainingPartners" + de);
		return -1;
	}

	catch(Exception e)
	{
		LOGGER.error("CATCHING -- Exception Handled in Dashboard DAO");
		LOGGER.error("In method - getNumberOfTrainingPartners");
		LOGGER.error("An exception occurred is "+e);
		return -2;
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
	
	LOGGER.debug("Request received from Service - getNumberOfUpcomingAssessments");
	LOGGER.debug("In DASHBOARD Dao");
	LOGGER.debug("To get Total number Of Upcoming Assessments");
	
	
	try {
		
		LOGGER.debug("TRYING -- getNumberOfUpcomingAssessments ");
		LOGGER.debug("Execute query to get Number Of Upcoming Assessments");
		Map<String,Object> parameters = new HashMap<> ();
		return getJdbcTemplate().queryForObject(dashboardConfig.getShowNumberOfUpcomingAssessments(),parameters,Integer.class);
	} 
	catch(EmptyResultDataAccessException re)
	{
		LOGGER.error("CATCHING -- EmptyResultDataAccessException Handled in Dashboard DAO");
		LOGGER.error("In method - getNumberOfUpcomingAssessments" + re);
		return 0;
	}
	catch(DataAccessException de)
	{
		LOGGER.error("CATCHING -- DataAccessException Handled in Dashboard DAO");
		LOGGER.error("In method - getNumberOfUpcomingAssessments" + de);
		return -1;
	}
	catch(Exception e)
	{
		LOGGER.error("CATCHING -- Exception Handled in Dashboard DAO");
		LOGGER.error("In method - getNumberOfUpcomingAssessments");
		LOGGER.error("An exception occurred is "+e);
		return -2;
	}
	
}


/**
 * @author Shivanshu Garg
 * @return total number of candidates trained in last 6 months
 */

public Collection<CandidatesTrainedInLast6MonthsDto> getTotalNumberOfCandidatesTrainedInLast6Months(){
	
	LOGGER.debug("Request received from Service - getShowTotalNumberOfCandidatesTrainedInLast6Months");
	LOGGER.debug("In DASHBOARD DAO");
	LOGGER.debug("To get total number of Candidates Trained in last 6 months ");
	
	
	try {
		
		LOGGER.debug("TRYING -- getTotalNumberOfCandidatesTrainedInLast6Months");
		LOGGER.debug("Execute query to get total number of Candidates Trained in last 6 months");
		Map<String,Object> parameters= new HashMap<>();
		return getJdbcTemplate().query(dashboardConfig.getShowTotalNumberOfCandidatesTrainedInLast6Months(),parameters,rm1);
				
		
	} 
	catch(EmptyResultDataAccessException de)
	{
		LOGGER.error("CATCHING -- EmptyResultDataAccessException Handled in Dashboard DAO");
		LOGGER.error("In method - getTotalNumberOfCandidatesTrainedInLast6Months" + de);
		return null;
	}
	catch(DataAccessException de)
	{
		LOGGER.error("CATCHING -- DataAccessException Handled in Dashboard DAO");
		LOGGER.error("In method - getTotalNumberOfCandidatesTrainedInLast6Months" + de);
		return null;
	}

	catch(Exception e)
	{
		LOGGER.error("CATCHING -- Exception Handled in Dashboard DAO");
		LOGGER.error("In method - getTotalNumberOfCandidatesTrainedInLast6Months");
		LOGGER.error("An exception occurred is "+e);
		return null;
	}
	
}




	 /**
	  * @author Aman
	  * @description method to find details of states
	  */

public Collection<StateDetailsDto> getShowStateDetails(){
	
	LOGGER.debug("Request received from Service - getShowStateDetails");
	LOGGER.debug("In DASHBOARD DAO");
	LOGGER.debug("To get Top 5 states with maximum Training centers");
	
	
	try {
		LOGGER.debug("TRYING -- getShowStateDetails");
		LOGGER.debug("Execute query to get Top 5 states for BAR chart - 1");
		Map<String,Object> parameters= new HashMap<>();
		return getJdbcTemplate().query(dashboardConfig.getShowStateDetails(),parameters,rm);
		
	} catch (Exception e) {
		LOGGER.error("CATCHING -- Exception Handled in Dashboard DAO");
		LOGGER.error("In method - getTotalNumberOfCandidatesTrainedInLast6Months");
		LOGGER.error("An exception occurred is "+e);
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

	LOGGER.debug("Request received from Service - getshowStateDetailsForMapChart");
	LOGGER.debug("In DASHBOARD DAO");
	LOGGER.debug("To get number of Training centers in each state");
	
	
	try {
		LOGGER.debug("TRYING -- getshowStateDetailsForMapChart");
		LOGGER.debug("Execute query to get training centers in state in MAP CHART ");
		Map<String,Object> parameters= new HashMap<>();
		return getJdbcTemplate().query(dashboardConfig.getshowStateDetailsForMapChart(),parameters,rm2);
		
	} catch (Exception e) {
		LOGGER.error("CATCHING -- Exception Handled in Dashboard DAO");
		LOGGER.error("In method - getshowStateDetailsForMapChart");
		LOGGER.error("An exception occurred is "+e);
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


