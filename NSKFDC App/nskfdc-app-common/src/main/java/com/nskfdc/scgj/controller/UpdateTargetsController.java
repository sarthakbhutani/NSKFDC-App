package com.nskfdc.scgj.controller;

import com.nskfdc.scgj.dto.UpdateTargetsDto;
import com.nskfdc.scgj.service.UpdateTargetsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UpdateTargetsController {

    @Autowired
    private UpdateTargetsService updateTargetsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTargetsController.class);

    @RequestMapping("/updateTargets")
    public Integer updateTargets(@RequestParam ("nsdcRegNumber") String nsdcRegNumber, @RequestParam("targets") int targets)
    {

        LOGGER.debug("Request received from front end to update targets of training partner with nsdc Reg Number : " + nsdcRegNumber);
        return updateTargetsService.updateTargets(nsdcRegNumber,targets);
    }

    @RequestMapping("getUpdatedTargets")
    public Collection<UpdateTargetsDto> updateTargetDetails(@RequestParam("nsdcRegNumber") String nsdcRegNumber)
    {
     LOGGER.debug("Request received from front end to get the updated targets of training partner with nsdc reg number: " + nsdcRegNumber);
     return updateTargetsService.updateTargetDetails(nsdcRegNumber);
    }


}
