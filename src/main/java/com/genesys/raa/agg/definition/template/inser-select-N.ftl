<#--
INPUT:
 int LEVEL_NUM - number of level for wich we want to generate inser-select statement
-->
INSERT INTO ${group.code}_${groupLevels[LEVEL_NUM].code}_${definition.name}
SELECT
    gi.${groupLevels[LEVEL_NUM].joinColumn} as ${definition.groupKeyColumn}
    <#list definition.columns as column>
        , <#if column.groupType == "GROUP">
            agg.${column.name} as ${column.name}
        <#elseif column.groupType == "SUM" || column.groupType == "COUNT">
            SUM( agg.${column.name} ) as ${column.name}
        </#if>
    </#list>
FROM
    ${groupLevels[LEVEL_NUM].tableName} gi
JOIN ${group.code}_${groupLevels[LEVEL_NUM - 1].code}_${definition.name} agg
    ON (gi.${groupLevels[LEVEL_NUM].joinColumn} = agg.${groupLevels[LEVEL_NUM - 1].joinColumn})
WHERE
    gi.${groupLevels[LEVEL_NUM].joinColumn} = ?
GROUP BY
    gi.${groupLevels[LEVEL_NUM].joinColumn}
    <#list definition.columns as column>
        , <#if column.groupType == "GROUP">
            agg.${column.name}
        </#if>
    </#list>