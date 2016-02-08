INSERT INTO ${AGG_TABLES[AGG_LEVEL]}
    (
        ${GROUP_ITEM_ID_COLUMN_NAME}
        <#list columns as column>
            , ${column.name}
        </#list>
    )
SELECT
    gi.${PARENT_ID} AS ${GROUP_ITEM_ID_COLUMN_NAME}
    <#list columns as column>
        ,
        <#if column.groupType == "GROUP">
            agg.${column.name} as ${column.name}
        <#elseif column.groupType == "SUM">
            SUM( agg.${column.name} ) as ${column.name}
        </#if>
    </#list>
FROM
    ${GROUP_ITEM_TABLE_NAME} gi
JOIN
    ${AGG_TABLES[AGG_LEVEL - 1]} agg ON (
        agg.${GROUP_ITEM_ID_COLUMN_NAME} = gi.${GROUP_ITEM_ID_COLUMN_NAME}
    )
WHERE
    gi.${PARENT_ID} = ?
GROUP BY gi.${PARENT_ID}
    <#list columns as column>
        ,
        <#if column.groupType == "GROUP">
            agg.${column.name}
        </#if>
    </#list>
