package com.genesys.raa.agg.rest;

import com.genesys.raa.agg.persistence.JobPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SPIDER on 03.02.2016.
 */

@RestController
public class AggRestController {

    @Autowired
    JobPersistence jobPersistence;

    @RequestMapping("/")
    String test() {
        //return jobPersistence.findOne(Long.valueOf(7)).toString();
        return jobPersistence.findAll().toString();
    }
}
