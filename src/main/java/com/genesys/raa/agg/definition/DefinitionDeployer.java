package com.genesys.raa.agg.definition;

import com.genesys.raa.agg.InvalidColumnMetaDataCombinationException;
import com.genesys.raa.agg.model.Definition;
import com.genesys.raa.agg.service.AggService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by SPIDER on 03.02.2016.
 */
@Component
public class DefinitionDeployer {

    @Setter
    @Autowired
    Connection connection;

    @Setter
    @Autowired
    AggService aggService;

    void deployDefinition(Definition definition) throws InvalidColumnMetaDataCombinationException {
        /*
        Create/upgrade corresponding aggregate tables fom all group levels
         */
    }
}
