package com.genesys.raa.agg.definition;

import com.genesys.raa.agg.InvalidColumnMetaDataCombinationException;
import com.genesys.raa.agg.model.Definition;
import com.genesys.raa.agg.service.AggService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by SPIDER on 03.02.2016.
 */
@Component
public class Deployer {


    @Setter
    @Autowired
    private AggService aggService;

    void deployDefinition(Definition definition) throws InvalidColumnMetaDataCombinationException {
        /*
        Create/upgrade corresponding aggregate tables fom all group levels
         */
    }
}
