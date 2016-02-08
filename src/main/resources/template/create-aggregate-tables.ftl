/*
    CREATE SQL FOR AGGREGATE: ${definition.name}
*/
<#list groupLevels as groupLevel>

CREATE TABLE ${group.code}_${groupLevel.code}_${definition.name}
AS
    <#if groupLevel.num == 0>
        ${definition.sql}
    <#else>
        SELECT *
        FROM ${group.code}_${groupLevels[groupLevel?index - 1].code}_${definition.name}
        <#--
            E.g. FROM DT_SUBHR_AGENT, where:
                ${group.code} = DT
                ${groupLevels[groupLevel?index - 1].code}  = SUBHR
                ${definition.name} = AGENT
         -->
    </#if>
;

</#list>
