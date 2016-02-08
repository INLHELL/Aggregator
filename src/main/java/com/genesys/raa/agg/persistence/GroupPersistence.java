package com.genesys.raa.agg.persistence;

import com.genesys.raa.agg.model.Group;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by vkhaluti on 05-Feb-2016.
 */
public interface GroupPersistence extends CrudRepository<Group, Long> {
}
