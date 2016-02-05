package com.genesys.raa.agg.persistence;

import com.genesys.raa.agg.model.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vkhaluti on 2/1/2016.
 */
@Repository
public interface JobPersistence extends CrudRepository<Job, Long> {
    
}
