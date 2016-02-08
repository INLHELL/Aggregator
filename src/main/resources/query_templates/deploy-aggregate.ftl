<#--
INPUT:
    ${AGGREGATE_SELECT}
    ${AGG_LEVEL_TABLE_NAMES}
-->

<#list AGG_LEVEL_TABLE_NAMES as TABLE_NAME>

CREATE TABLE ${AGG_LEVEL_TABLE_NAMES[groupLevel.pos]}
AS
    <#if groupLevel.pos == 0>
    ${aggDef.sql}
    <#else>
    SELECT *
    FROM ${AGG_LEVEL_TABLE_NAMES[groupLevel.pos-1]}
    </#if>
;

</#list>

<#--
Group group;
List<GroupLevel> groupLevels; // ordered list by a "pos"-field
Definition aggDef;
-->
${group.code} <#-- major prefix to aggregate table (e.g. DT, that means date_time) -->
${groupLevel.pos} <#-- number of a group level (order position) -->
${groupLevel.code} <#-- prefix to aggregate table -->

${aggDef.name} <#-- aggregate name -->
${aggDef.sql} <#-- aggregate select-sql -->
${aggDef.columns} <#-- list of all aggregate columns -->
${aggDef.groupColumnNames[]} <#-- list of "group by" aggregate columns -->
${aggDef.sumColumnNames[]} <#-- list of "sum" aggregate columns -->
${aggDef.countColumnNames[]} <#-- list of "count" aggregate columns -->
${aggDef.keyColumnName} <#-- group key column name (e.g. DATE_TIME_KEY -->
${aggDef.indexColumnNames[]} <#-- list of "indexed" aggregate columns -->
${AGG_LEVEL_TABLE_NAMES[]} <#-- LEVEL_POS => TABLE_NAME-->
${AGG_LEVEL_TABLE_NAME = ${group.code}_${groupLevel.code}_${aggDef.name}}

<#list groupLevels as groupLevel>

    CREATE TABLE ${AGG_LEVEL_TABLE_NAMES[groupLevel.pos]}
    AS
    <#if groupLevel.pos == 0>
        ${aggDef.sql}
    <#else>
        SELECT *
        FROM ${AGG_LEVEL_TABLE_NAMES[groupLevel.pos-1]}
    </#if>
    ;

</#list>