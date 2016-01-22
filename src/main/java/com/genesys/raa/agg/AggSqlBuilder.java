package com.genesys.raa.agg;


public class AggSqlBuilder {
	
	private Aggregate aggregate;
	private String sql = "";
	
	private AggSqlBuilder(Aggregate aggregate) {
		this.aggregate = aggregate;
	}
	
	public static AggSqlBuilder buildQueryFor(Aggregate aggregate) {
		return new AggSqlBuilder(aggregate);
	}
	
	public AggSqlBuilder createTable(TimeScaleUnit timeScale) {
		// TODO implement
		return this;
	}
	
	public AggSqlBuilder insertSelect(TimeScaleUnit timeScale) {
		
		String aggScaleTable = aggregate.getTableName(timeScale);
		String aggSubscaleTable = aggregate.getTableName(TimeScaleUnit.getSubscaleUnit(timeScale));
		
		if(aggSubscaleTable == null) {
			
			sql += "INSERT INTO " + aggScaleTable + " " + aggregate.getSelectSql();
			
		} else {
			
			sql += "INSERT INTO" + aggScaleTable 
					+ "SELECT dt.intervalId, " 
						+ aggregate.getGroupByColumns() // group columns without intervalId
						+ "SUM(" + aggregate.getSumColumns() + ")"
					+ "FROM TIME_LINE dt "
					+ "JOIN " + aggSubscaleTable + " sub ON (dt.parentIntervalId = sub.intervalId)" 
					+ "WHERE dt.intervalId = ?"
					+ "GROUP BY " + aggregate.getGroupByColumns();
			
		}
		
		sql += ";\n";
		
		return this;
	}
	public AggSqlBuilder dropIndexes(TimeScaleUnit timeScale) {
		return this;
	};
	public AggSqlBuilder createIndexes(TimeScaleUnit timeScale) {
		return this;
	}
	public String sql() {
		return sql;
	}
	public String toStrifng() { 
		return sql();
	}; 
}
