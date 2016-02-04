package com.genesys.raa.agg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;



@SpringBootApplication
@ImportResource("classpath:spring-agg.xml")
public class StandaloneAggregatorMain {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(StandaloneAggregatorMain.class, args);

//		System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//		String[] beanNames = ctx.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			System.out.println(beanName);
//		}
	}

}

/*
public class StandaloneAggregatorMain extends DefaultAggregatorManager {
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-agg.xml");

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
		
		tenantContainer.unplugAggregate("name");
		tenantContainer.stop();
	}
}
*/
