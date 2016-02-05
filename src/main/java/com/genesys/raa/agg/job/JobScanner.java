package com.genesys.raa.agg.job;

import com.genesys.raa.agg.model.Job;
import com.genesys.raa.agg.persistence.JobPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by SPIDER on 03.02.2016.
 */
@Component
public class JobScanner {

    @Autowired
    JobPersistence jobPersistence;

    @Scheduled(cron = "*/10 * * * * *")
    public void test() {
        System.out.println(this);
        Job job = new Job();
        job.setAggregateId(0);
        job.setGroupItemId(0);
        jobPersistence.save(job);
    }
}
