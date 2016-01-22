package com.genesys.raa.agg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class AggFactory {
	@Autowired
	AggConfiguration configuration;
	
	private static final int DEFAULT_TENANT_ID = 0;
	
	public AggFactory(AggConfiguration configuration) {
		this.configuration = configuration;
	}

	public AggEngine getEngine() {
		return getEngine(DEFAULT_TENANT_ID);
	}
	
	public List<AggDefinition> getAggDefinitions() throws Exception {
		Map<String, String> definitions = configuration.getAggregateDefinitions();
		List<AggDefinition> defs = new ArrayList<AggDefinition>();
		for ( String aggName : definitions.keySet()) {
			AggDefinition  definition  = new AggDefinition(
					aggName, 
					definitions.get(aggName), null);
			defs.add(definition);
		}
		return defs;
	}

	public AggEngine getEngine(long tenantId) {
		// TODO Auto-generated method stub
		return null;
	}

	public AggService getService() {
		// TODO Auto-generated method stub
		return null;
	}
}
