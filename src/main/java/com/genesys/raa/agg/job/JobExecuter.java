package com.genesys.raa.agg.job;

import com.genesys.raa.agg.model.Aggregate;
import com.genesys.raa.agg.model.Job;
import com.genesys.raa.agg.service.AggService;
import com.genesys.raa.agg.AggSqlBuilder;
import com.genesys.raa.agg.TimeInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

@Component
public class JobExecuter {

	@Autowired
	AggService aggService;


	Aggregate aggregate;

	public JobExecuter() {}

	public JobExecuter(Aggregate aggregate) {
		this.aggregate = aggregate;
	}
	
	public void execute() throws Exception {
		
		/*Job job = aggService.acquireNextJobFor(aggregate.getName());
		
		TimeInterval interval = job.getInterval();
		
		boolean success = false;
		String failReason = "";
		
		// Insert-Select from FACT tables
		String insertSql = AggSqlBuilder.buildQueryFor(aggregate)
			.insertSelect(interval.getScaleUnit())
			.sql();
			 
		PreparedStatement preparedStatement = aggregate.getPreparedStatement();
			
		for(int i = 1; i <= aggregate.getParametersCount(); i++) {
			preparedStatement.setLong(i, interval.getIntervalId());
		}
		preparedStatement.execute();
		
		if(success) {
			aggService.releaseJob(job.getJobId());
		} else {
			job.setLastExecMessage(failReason);
			aggService.updateJob(job);
			throw new Exception();
		}*/
	}

}
