package com.genesys.raa.agg;

import com.genesys.raa.agg.model.Aggregate;
import com.genesys.raa.agg.model.Definition;
import com.genesys.raa.agg.model.Tenant;
import com.genesys.raa.agg.service.AggService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


public class StandaloneAggregatorMain extends DefaultAggregatorManager {
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("agg-spring.xml");

		Configuration configuration = (Configuration) context.getBean(Configuration.class);
//		TenantContainer tenantContainer = new TenantContainer(new Tenant());

		long tenantId = 0;
		AggFactory aggFactory =  context.getBean(AggFactory.class);
		TenantContainer tenantContainer = aggFactory.getEngine(tenantId);
		tenantContainer.getTenant();
		tenantContainer.stop();
		AggService aggService = aggFactory.getService();
		
		List<Definition> aggDefinitions = aggFactory.getAggDefinitions();

		for (Definition aggDefinition : aggDefinitions) {
			Aggregate aggregate = aggService.deployAggregate(
					aggDefinition.getName(),
					aggDefinition.getSelectSql(),
					aggDefinition.getGroupByColumns(),
					aggDefinition.getIndexColumns()
			);
			tenantContainer.plugAggregate(aggregate);
		}
		
		tenantContainer.start();
		
		tenantContainer.unplugAggregate("aggregateName");
		tenantContainer.stop();
	}
}
