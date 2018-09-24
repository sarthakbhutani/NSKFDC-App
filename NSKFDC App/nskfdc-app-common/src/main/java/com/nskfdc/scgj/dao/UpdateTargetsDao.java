package com.nskfdc.scgj.dao;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.UpdateTargetsConfig;
import com.nskfdc.scgj.dto.GenerateCredentialSearchDto;
import com.nskfdc.scgj.dto.UpdateTargetsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UpdateTargetsDao extends AbstractTransactionalDao {

    @Autowired
    private UpdateTargetsConfig updateTargetsConfig;

    private static final Logger LOGGER= LoggerFactory.getLogger(UpdateTargetsDao.class);

    
    private static final UpdateTargetRowMapper updateTargetRowMapper = new UpdateTargetRowMapper();
    
    /**
     * Method to update targets of a training partner for a financial year
     * @param nsdcRegNumber
     * @param targets
     * @return Status of updating targets
     */
    public Integer updateTargets(String nsdcRegNumber, int targets)
    {
    	 LOGGER.debug("Request received from service to UpdateTargetsDao");
         LOGGER.debug("In updateTargets - To update targets of TP for entered NSDC Reg. Number");
         
        try {

           LOGGER.debug("TRYING -- To update the targets for a TP");
           LOGGER.debug("Checking existence of user for entered NSDC Registration Number : " + nsdcRegNumber);
           Integer existenceStatus = checkExistenceStatus(nsdcRegNumber);
           LOGGER.debug("The status of existence of user is : " + existenceStatus);
            
           if(existenceStatus == 1)
            {
        	   	LOGGER.debug("In IF -- When user EXISTS for entered NSDC Reg Number");
                LOGGER.debug("Creating hashmap of objects");
                Map<String, Object> parameters = new HashMap<>();
                LOGGER.debug("Inserting nsdcRegNumber, updatedTargets into parameters");
                parameters.put("nsdcRegNumber", nsdcRegNumber);
                parameters.put("updatedTargets", targets);
                LOGGER.debug("Executing query to update Targets for passed nsdcRegNumber");
                return getJdbcTemplate().update(updateTargetsConfig.getUpdateTargets(), parameters);

            }
            
            else
            {
            	LOGGER.debug("In ELSE -- When user with entered NSDC Reg Number does not exists");
            	LOGGER.debug("Returning existence status as 0");
            	return existenceStatus;
            }          
        }

        catch(Exception e )
        {
        	LOGGER.error("CATCHING -- Exception handled while updating targets");
            LOGGER.error("In UpdateTargetsDao - updateTargets");
            LOGGER.error("The Exception is : " + e);
            LOGGER.error("Returning status code -23");
            return -23;
        }

    }

    /**
     * Check existence of NSDC registration number
     * @param nsdcRegNumber
     * @return value of the status - 0,1,etc
     */
    private Integer checkExistenceStatus(String nsdcRegNumber) {
		
    	LOGGER.debug("Request received in method checkExistence for entered nsdcRegNumber");
    	LOGGER.debug("To check existence of user with NSDC Reg. Number : " + nsdcRegNumber);
    	
    	Map<String,Object>existenceParam = new HashMap<>();
    	LOGGER.debug("Inserting nsdcRegNumber into parameters");
    	existenceParam.put("nsdcRegNumber", nsdcRegNumber);
    	try
    	{
    		LOGGER.debug("TRYING -- checkExistenceStatus");
    		LOGGER.debug("Executing query to check existence of enetered NSDC Reg Number");
    		return getJdbcTemplate().queryForObject(updateTargetsConfig.getCheckUserExistence(),existenceParam,Integer.class);
    	}
    	catch(Exception e)
    	{
    		
    		LOGGER.error("CATCHING -- Exception handled while checking existence of nsdcRegNumber");
            LOGGER.error("In UpdateTargetsDao - checkExistenceStatus");
            LOGGER.error("The Exception is : " + e);
            LOGGER.error("Returning status code -30");
    		return -30;
    	}
	
	}

	/**
	 * MEthod to update target against nsdc regsitration number
	 * @param nsdcRegNumber
	 * @return collection of update targets
	 */
    public Collection<UpdateTargetsDto> updateTargetDetails(String nsdcRegNumber) {
        LOGGER.debug("Request received from service to UpdateTargetsDao");
        LOGGER.debug("To get Target Details for training partner");
        LOGGER.debug("Against entered NSDC Reg Number");
        try
        {
        	LOGGER.debug("TRYING -- updateTargetDetails");
            LOGGER.debug("Creating hashmap of objects");
            Map<String,Object> param = new HashMap<>();
            LOGGER.debug("Inserting entered nsdcRegNumber in parameter map");
            param.put("nsdcRegNumber",nsdcRegNumber);
            LOGGER.debug("Executing query to get target details againest enterded nsdcRegNumber");
            return getJdbcTemplate().query(updateTargetsConfig.getUpdatedTargetDetails(), param,updateTargetRowMapper);
        }
        catch(Exception e)
        {
        	LOGGER.error("CATCHING -- Exception handled while checking existence of nsdcRegNumber");
            LOGGER.error("In UpdateTargetsDao - checkExistenceStatus");
            LOGGER.error("The Exception is : " + e);
            LOGGER.error("Returning null");
            return null;
        }

    }

    /**
     * Row mapper class for update targets
     * @author Ruchi
     *
     */
    private static class UpdateTargetRowMapper implements RowMapper<UpdateTargetsDto> {

        @Override
        public UpdateTargetsDto mapRow(ResultSet rs, int rowNum) throws SQLException {


            String nsdcRegNumber = rs.getString("nsdcRegNumber");
            String trainingPartnerName = rs.getString("trainingPartnerName");
            int targets = rs.getInt("targets");
            Date targetApprovalDate = rs.getDate("targetApprovalDate");

            return new UpdateTargetsDto(nsdcRegNumber,trainingPartnerName,targets,targetApprovalDate);
        }
    }
}
