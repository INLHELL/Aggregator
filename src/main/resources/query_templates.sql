/* ***********************

${GROUP_COLUMNS} Set<String>
${SUM_COLUMNS} Set<String>
${JOIN_TABLES}
${AGG_SCHEMA} String
${SRC_SCHEMA} String (It's GIM database)
${GROUP.CODE} String
${GROUP_LEVEL.CODE} String
${AGG_NAME} String
${AGG_TABLE_NAME[GROUP_LEVEL.INDEX]} String
${GROUP_LEVEL.INDEX} Integer

*/
/* ***********************
	AGGREGATE DEFINITION TEMPLATE
	${AGG_SELECT}
*/
	SELECT
		gi.ID AS $IGROUP_ITEM_ID, /* $I<COLUMN_NAME> means that an index will be created for this column */
		${GROUP_COLUMNS}, /* e.g. TBL_NAME.COL_NAME AS $GDIMENTION_COL_NAME. $G<NAME> means that it's a group column */
		${SUM_COLUMNS}, /* e.g. SUM(<condition>) as $SMETRIC_SUM_COL. $S<NAME> means that it's a sum column */
		${COUNT_COLUMNS} /* e.g. COUNT(<condition>) as $CMETRIC_COUNT_COL. $C<NAME> means that it's a count column */
	FROM
		GROUP_ITEM gi
	${JOIN_TABLES}
	WHERE
		gi.ID BETWEEN :FROM_ID TO :TO_ID
	GROUP BY
		gi.ID,
		${GROUP_COLUMNS}
	;

/* ***********************
	AGGREGATE TABLE NAMING TEMPLATE
	${AGG_TABLE_NAME[GROUP_LEVEL.INDEX]}
*/
	${AGG_SCHEMA}.${GROUP.CODE}_${GROUP_LEVEL.CODE}_${AGG_NAME}

/* ***********************
	CREATE TABLE TEMPLATE
	${AGG_CREATE_TABLE}
*/
	CREATE TABLE ${AGG_SCHEMA}.${AGG_TABLE_NAME} AS ${AGG_SELECT}

/* ***********************
	POPULATE AGGREGATE TABLES
	${AGG_INSERT}
*/
-- GROUP_LEVEL.INDEX == 0
	INSERT INTO ${AGG_SCHEMA}.${AGG_TABLE_NAME[0]} ${AGG_SELECT}

-- GROUP_LEVEL.INDEX > 0 = X
	INSERT INTO ${AGG_TABLE_NAME[X]}
		( GROUP_ITEM_ID, ${GROUP_COLUMNS}, ${SUM_COLUMNS} )
	SELECT
		gi.PARENT_ID AS GROUP_ITEM_ID,
		${GROUP_COLUMNS},
		${SUM_COLUMNS}
	FROM
		GROUP_ITEM gi
	JOIN
		${SRC_SCHEMA}.${AGG_TABLE_NAME[X-1]} agg ON (
			agg.GROUP_ITEM_ID = gi.ID AND
			gi.PARENT_ID BETWEEN :FROM_ID TO :TO_ID
		)
	GROUP BY gi.PARENT_ID, ${GROUP_COLUMNS}

------------------------------------------

/* ***********************
	EXAMPLE:
	Aggregate: ${AGG_NAME} = CAMPAIGN
	Definition File Name: CAMPAIGN.sql
	Group: DATE_TIME
*/
