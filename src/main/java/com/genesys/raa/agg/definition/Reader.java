package com.genesys.raa.agg.definition;

import com.genesys.raa.agg.model.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SPIDER on 03.02.2016.
 */
@Component
public class Reader {

    @Autowired
    Deployer deployer;

    public List<Definition> readDefinitions() {
        /*
        Read *.sql files from specific folder, parse it with PreparedStatement
        and create definitions (Definition entity class)
        and populate to database to DEFINITION table
         */
        List<Definition> definitions = new ArrayList<>();

        /*

         */

        if(true /* get config properties */) {
            for (Definition definition : definitions) {
                deployer.deployDefinition(definition);
            }
        }

        return definitions;
    }

}
