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
    public Integer updateTargets(String nsdcRegNumber, int targets)
    {
        try {

            LOGGER.debug("Request received from service to update targets of training partner with NSDC Reg. Number : " + nsdcRegNumber);
            
            LOGGER.debug("Checking existence of user with NSDC Registration Number : " + nsdcRegNumber);
            
            Integer existenceStatus = checkExistenceStatus(nsdcRegNumber);
            LOGGER.debug("The status of existence of user is : " + existenceStatus);
            
            
            if(existenceStatus == 1)
            {
            	LOGGER.debug("User with NSDC Reg. Number :" + nsdcRegNumber + "exists");
            	  LOGGER.debug("The new target to be updated is : " + targets);
                  LOGGER.debug("Creating hashmap of objects");
                  Map<String, Object> parameters = new HashMap<>();
                  LOGGER.debug("Inserting values into the hashmap");
                  parameters.put("nsdcRegNumber", nsdcRegNumber);
                  parameters.put("updatedTargets", targets);
                  return getJdbcTemplate().update(updateTargetsConfig.getUpdateTargets(), parameters);

            }
            
            else
            {
            	LOGGER.debug("User with NSDC Reg. Number does not exist in the database");
            	return existenceStatus;
            }
            
          
        }

        catch(Exception e )
        {
            LOGGER.error("An exception occured while updating targets for training partner with NSDC Registration Number : " + nsdcRegNumber);
            LOGGER.error("The exception is : " + e);
            LOGGER.error("Returning -23");
            return -23;
        }

    }

    private Integer checkExistenceStatus(String nsdcRegNumber) {
		
    	LOGGER.debug("In method checkExistenceStatus to check existence of user with NSDC Reg. Number : " + nsdcRegNumber);
    	
    	Map<String,Object>existenceParam = new HashMap<>();
    	LOGGER.debug("Hashmap of object created");
    	existenceParam.put("nsdcRegNumber", nsdcRegNumber);
    	try
    	{
    		LOGGER.debug("Inserting params into JDBC Template to check the existence of user");
    		return getJdbcTemplate().queryForObject(updateTargetsConfig.getCheckUserExistence(),existenceParam,Integer.class);
    	}
    	catch(Exception e)
    	{
    		LOGGER.error("An exception occured while checking the existence of user with nsdcRegNumber : " +nsdcRegNumber);
    		return -30;
    	}
	
	}

	public Collection<UpdateTargetsDto> updateTargetDetails(String nsdcRegNumber) {
        LOGGER.debug("Request received from service to get details of updated target for training partner");
        try
        {
            LOGGER.debug("Creating hashmap of objects");
            Map<String,Object> param = new HashMap<>();
            param.put("nsdcRegNumber",nsdcRegNumber);
            return getJdbcTemplate().query(updateTargetsConfig.getUpdatedTargetDetails(), param,updateTargetRowMapper);
        }
        catch(Exception e)
        {
        	LOGGER.error("An Exception occured while fetching the details for the updated targets " + e);
            return null;
        }

    }

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
