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
            LOGGER.debug("The new target to be updated is : " + targets);
            LOGGER.debug("Creating hashmap of objects");
            Map<String, Object> parameters = new HashMap<>();
            LOGGER.debug("Inserting values into the hashmap");
            parameters.put("nsdcRegNumber", nsdcRegNumber);
            parameters.put("updatedTargets", targets);

            return getJdbcTemplate().update(updateTargetsConfig.getUpdateTargets(), parameters);

        }

        catch(Exception e )
        {
            LOGGER.error("An exception occured while updating targets for training partner with NSDC Registration Number : " + nsdcRegNumber);
            LOGGER.error("The exception is : " + e);
            LOGGER.error("Returning -23");
            return -23;
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
