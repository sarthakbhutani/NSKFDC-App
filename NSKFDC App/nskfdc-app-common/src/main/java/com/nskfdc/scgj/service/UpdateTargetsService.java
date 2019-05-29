package com.nskfdc.scgj.service;

import com.nskfdc.scgj.dao.UpdateTargetsDao;
import com.nskfdc.scgj.dto.UpdateTargetsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UpdateTargetsService {

    @Autowired
     private UpdateTargetsDao updateTargetsDao;

    private static final Logger LOGGER= LoggerFactory.getLogger(UpdateTargetsService.class);

    public Integer updateTargets(String nsdcRegNumber, int targets)
    {
        LOGGER.debug("Received request from Controller to UpdateTargetsService");
        LOGGER.debug("In updateTargets - to update targets of training partner for entered NSDC Reg Number ");
        LOGGER.debug("Sending request to updateTargetsDao - updateTargets");
        return updateTargetsDao.updateTargets(nsdcRegNumber,targets);
    }

    public Collection<UpdateTargetsDto> updateTargetDetails(String nsdcRegNumber) {

        LOGGER.debug("Request received from controller to UpdateTargetsService");
        LOGGER.debug("In updateTargetDetails - To get the details of targets corresponding to NSDC Reg Number");
        LOGGER.debug("Sending request to UpdateTargetsDao - updateTargetDetails");
        return updateTargetsDao.updateTargetDetails(nsdcRegNumber);
    }
}
