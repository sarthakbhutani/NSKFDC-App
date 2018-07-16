package com.nskfdc.scgj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.GenerateMinutesOfSelectionConfig;
import com.nskfdc.scgj.dto.GenerateMinutesOfSelectionDto;


@Repository
public class GenerateMinutesOfSelectionDao extends AbstractTransactionalDao  {
	
	private static final GenerateMinutesOfSelectionRowmapper generateMinutesOfSelectionRowMapper = new GenerateMinutesOfSelectionRowmapper();

	@Autowired
	private GenerateMinutesOfSelectionConfig generateminutesofselectionconfig;
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateMinutesOfSelectionDao.class);
	public Collection<GenerateMinutesOfSelectionDto> generateMinutesOfSelection(/*String jobRole,String trainingPartnerName,String sectorSkillCouncil,String centreCity*/) {
		
		
		LOGGER.debug("Request received from service");
		LOGGER.debug("Generate minutes Of Selection details ");
		
		try {
			
			LOGGER.debug("In try block of Generate Minutes Of Selection  Dao");
//			Map<String, Object> parameters = new HashMap<>();
//			parameters.put("jobRole",jobRole);
//			parameters.put("trainingPartnerName", trainingPartnerName);
//			parameters.put("sectorSkillCouncil", sectorSkillCouncil);
//			parameters.put("centreCity", centreCity);
			
			
			return getJdbcTemplate().query(generateminutesofselectionconfig.getShowMinutesOfSelectionMeetingDetails()  , generateMinutesOfSelectionRowMapper);
			
		}catch(Exception e) {
			
			LOGGER.debug("In catch block of Generate  Minutes Of Selection  Dao"+e);
			return null;
		}
	}	
	
/* Declaring inner class minutes Of Selection Committee Meeting Rowmapper */
private static class GenerateMinutesOfSelectionRowmapper implements RowMapper<GenerateMinutesOfSelectionDto>{
	
	@Override
	public GenerateMinutesOfSelectionDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		String jobRole = rs.getString("jobRole");
		String trainingPartnerName = rs.getString("trainingPartnerName");
		String sectorSkillCouncil=rs.getString("sectorSkillCouncil");
		String centreCity=rs.getString("centreCity");


		return new GenerateMinutesOfSelectionDto(jobRole,trainingPartnerName,sectorSkillCouncil,centreCity);

		
	}

}

}
