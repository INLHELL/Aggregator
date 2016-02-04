package com.genesys.raa.agg.persistence;

import com.genesys.raa.agg.model.Definition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by SPIDER on 03.02.2016.
 */
@Repository
public interface DefinitionPersistence extends CrudRepository<Definition, Long> {

    public Definition findByName(String name);
    public List<Definition> findByNameLike(String name);

}
