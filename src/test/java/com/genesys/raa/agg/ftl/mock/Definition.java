package com.genesys.raa.agg.ftl.mock;

import com.genesys.raa.agg.definition.ColumnMetaData;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vkhaluti on 08-Feb-2016.
 */
@Data
public class Definition {
    String name;
    String sql;
    List<ColumnMetaData> columns;

    /*
    ${aggDef.groupColumnNames[]} <#-- list of "group by" aggregate columns -->
    ${aggDef.sumColumnNames[]} <#-- list of "sum" aggregate columns -->
    ${aggDef.countColumnNames[]} <#-- list of "count" aggregate columns -->
    ${aggDef.keyColumnName} <#-- group keycolumn name (e.g. DATE_TIME_KEY-->
    ${aggDef.indexColumnNames[]} <#-- list of "indexed" aggregate columns -->
    */

    public Definition() throws IOException {
        sql = new String(Files.readAllBytes(new File("src/test/resources/AGENT.sql").toPath()));
        name = "AGENT";
        columns = new ArrayList<ColumnMetaData>();

    }


}
