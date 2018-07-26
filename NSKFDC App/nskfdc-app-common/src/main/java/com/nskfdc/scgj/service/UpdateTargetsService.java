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
        LOGGER.debug("Received request from controller to update targets of training partner with nsdcRegNumber:  " + nsdcRegNumber);
        LOGGER.debug("Sending request to dao to update the targets of training partner");
        return updateTargetsDao.updateTargets(nsdcRegNumber,targets);
    }

    public Collection<UpdateTargetsDto> updateTargetDetails(String nsdcRegNumber) {

        LOGGER.debug("Request received from controller to get details of updated targets");
        return updateTargetsDao.updateTargetDetails(nsdcRegNumber);
    }
}
