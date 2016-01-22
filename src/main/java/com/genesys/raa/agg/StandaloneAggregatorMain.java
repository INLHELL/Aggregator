package com.genesys.raa.agg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


public class StandaloneAggregatorMain extends com.genesys.raa.agg.DefaultAggregatorManager {
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("agg-spring.xml");
		com.genesys.raa.agg.AggConfiguration configuration = (com.genesys.raa.agg.AggConfiguration) context.getBean("configuration");
		long tenantId = 0;
		com.genesys.raa.agg.AggFactory aggFactory = new com.genesys.raa.agg.AggFactory(configuration);
		com.genesys.raa.agg.AggEngine aggEngine = aggFactory.getEngine(tenantId);
		com.genesys.raa.agg.AggService aggService = aggFactory.getService();
		
		List<com.genesys.raa.agg.AggDefinition> aggDefinitions = aggFactory.getAggDefinitions();
		
		for (int i = 0; i < aggDefinitions.size(); i++) {
			com.genesys.raa.agg.AggDefinition aggDefinition = aggDefinitions.get(i);
			com.genesys.raa.agg.Aggregate aggregate = aggService.deployAggregate(
					aggDefinition.getName(), 
					aggDefinition.getSelectSql(), 
					aggDefinition.getGroupByColumns(), 
					aggDefinition.getIndexColumns()
			);
			aggEngine.plugAggregate(aggregate);
		}
		
		aggEngine.start();
		
		aggEngine.unplugAggregate("aggregateName");
		aggEngine.stop();
	}
}
