package com.genesys.raa.agg.model;

import java.sql.PreparedStatement;
import java.util.List;

public class Aggregate extends Definition {

	public Aggregate() throws Exception {
		super(null, null, null);
	}

	public Aggregate(String aggregateName, String selectSql,
					 List<String> groupColumns) throws Exception {
		super(aggregateName, selectSql, groupColumns);
		// TODO Auto-generated constructor stub
	}

	PreparedStatement preparedStatement;

	/**
	 * @return the preparedStatement
	 */
	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public void setColumns(String[] columns) {
		// TODO Auto-generated method stub
		
	}
	
}
