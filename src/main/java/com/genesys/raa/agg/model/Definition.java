package com.genesys.raa.agg.model;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.genesys.raa.agg.TimeScaleUnit;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.davidmoten.rx.jdbc.Database;

import javax.persistence.Entity;

@Entity
public class Definition {
	
	private String aggregateName;
	private String selectSql;
	private List<String> columns = new ArrayList<String>(); 
	private List<String> groupColumns; 
	private List<String> sumColumns = new ArrayList<String>();
	private List<String> sqlParameters = new ArrayList<String>();
	
	private int parametersCount;
	private int queryCost;
	
	public Definition(String aggregateName, String selectSql, List<String> groupColumns) throws Exception {
		this.aggregateName = aggregateName;
		this.selectSql = selectSql;
		this.groupColumns = groupColumns;
		
		//Database database = configuration.getDatabase();
		Database database = null;
		Connection connection = database.getConnectionProvider().get();
		
		PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
		ResultSetMetaData metaData = preparedStatement.getMetaData();
		if(!metaData.getColumnName(1).equals("intervalId")) {
			throw new Exception("Can't find 'intervalId' column in selectSql. ColumnMetaData 'intervalId' must be the first column in selectSql");
		}
		
		ParameterMetaData parameterMetaData = preparedStatement.getParameterMetaData();
		for(int i = 1; i <= metaData.getColumnCount(); i++) {
			String columnName = metaData.getColumnLabel(i);
			columns.add(columnName);
			if(!groupColumns.contains(columnName)) {
				sumColumns.add(columnName);
			}
		}
		
		for(int i = 1; i <= parameterMetaData.getParameterCount(); i++) {
			String parameterName = parameterMetaData.getParameterClassName(i);
			sqlParameters.add(parameterName);
		}
		if(!sqlParameters.contains("INTERVAL_ID")) {
			throw new Exception("Can't find 'INTERVAL_ID' parameter in selectSql. SelectSql has to have an input parameter 'INTERVAL_ID'");
		}
	}
	
	/**
	 * @param scaleUnit
	 * @return name of the table in which an aggregated data is stored for this time scale  
	 */
	/*public String getTableName(TimeScaleUnit scaleUnit ) {
		 
		String tablePrefix = configuration.getDatabaseTablePrefix();
		String tableName = tablePrefix + "_" + aggregateName + "_" + scaleUnit.toString();
		return tableName;
	}*/
	
	/**
	 * @return the name
	 */
	public String getName() {
		return aggregateName;
	}
	/**
	 * @return the sql
	 */
	public String getSelectSql() {
		return selectSql;
	}
	/**
	 * @return the columns
	 */
	public List<String> getColumns() {
		return columns;
	}
	/**
	 * @return the groupColumns
	 */
	public List<String> getGroupByColumns() {
		return groupColumns;
	}
	/**
	 * @return the sumColumns
	 */
	public List<String> getSumColumns() {
		return sumColumns;
	}
	/**
	 * @return the parametersCount
	 */
	public int getParametersCount() {
		return parametersCount;
	}
	/**
	 * @return the queryCost
	 */
	public int getQueryCost() {
		return queryCost;
	}
	public List<String> getIndexColumns() {
		// TODO Auto-generated method stub
		return null;
	}

}
