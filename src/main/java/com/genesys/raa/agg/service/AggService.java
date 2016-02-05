package com.genesys.raa.agg.service;

import com.genesys.raa.agg.model.Aggregate;
import com.genesys.raa.agg.model.Definition;
import com.genesys.raa.agg.model.Job;
import org.springframework.stereotype.Service;

import java.util.List;

/*import com.genesys.raa.agg.AggSqlBuilder;
import com.genesys.raa.agg.AggSynchronizer;
import com.genesys.raa.agg.TimeScaleUnit;*/

@Service
public class AggService {
	
	//Database database = configuration.getDatabase();

	public void aggregateInterval(String aggregateName, long intervalId) {
		
	}
	
	public void undeployAggregate(String aggregateName){
		
	}
	public void clearAggregate(String aggregateName){
		
	}
	public void addJob(Job job) {
		
	} 
	public Job getJob(long jobId){
		return null;
		
	}
	public boolean updateJob(Job job){
		return false;
		
	}
	public Job acquireJob(long jobId) {
		return null;
		
	}
	
	
	/**
	 * Mark this com.genesys.raa.agg.job as completed
	 * @param jobId
	 * @return
	 */
	public boolean releaseJob(long jobId){
		/* 
		 * TODO
		 *  - insert parent interval com.genesys.raa.agg.job if it is a last interval in bigger cycle
		 *  - delete this com.genesys.raa.agg.job
		 *  - insert com.genesys.raa.agg.job history
		 */
		
		return false;
		
	}
	public Job acquireNextJobFor(String aggName){
		return null;
		
	}
	public Definition addDefinition(Definition definition){
		return definition;
		
	}
	public Definition updateDefinition(Definition definition) {
		return definition;
		
	}
	public boolean deleteDefinition(Definition definition) {
		return false;
		
	}
	
	/*public Aggregate deployAggregate(Definition definition) {
		return deployAggregate(definition.getName(), definition.getSelectSql(), definition.getGroupByColumns(), definition.getIndexColumns());
	}
*/
	public Aggregate deployAggregate(
			String aggregateName,
			String aggregateDefinitionSql, 
			List<String> aggregateGroupbyColumns,
			List<String> aggregateIndexColumns) throws Exception
	{
		Aggregate aggregate = new Aggregate();

 		/*if(isAggregateExists(aggregateName)) {
			AggSynchronizer synchronizer = null;
			synchronizer.synchronizeTables();
			synchronizer.synchronizeIndexes();
		} else {
			String sql = AggSqlBuilder.buildQueryFor(aggregate)
				.createTable(TimeScaleUnit.MINUTE_15)
				.createTable(TimeScaleUnit.DAY)
				.createTable(TimeScaleUnit.MONTH)
				.createTable(TimeScaleUnit.HOUR)
				.createTable(TimeScaleUnit.WEEK)
				.createTable(TimeScaleUnit.YEAR)
				.sql();
		}*/
		/*String[] columns = null;
		aggregate.setColumns(columns);*/
		return aggregate;
	}
	
	public boolean isAggregateExists(String aggregateName) {
		/*if(database.select("select count(*) from AGGREGATE where name = ?")
				.parameter(aggregateName)
				.getAs(Integer.class).toSingle().equals(1) ) {
			return true;
		}*/
		return false;
	}
}
