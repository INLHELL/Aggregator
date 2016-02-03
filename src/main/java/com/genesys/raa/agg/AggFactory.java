package com.genesys.raa.agg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.genesys.raa.agg.model.Definition;
import com.genesys.raa.agg.service.AggService;
import lombok.Data;

@Data
public class AggFactory {


	private static final int DEFAULT_TENANT_ID = 0;

	public TenantContainer getEngine() {
		return getEngine(DEFAULT_TENANT_ID);
	}
	
	/*public List<Definition> getAggDefinitions() throws Exception {
		Map<String, String> definitions = configuration.getAggregateDefinitions();
		List<Definition> defs = new ArrayList<Definition>();
		for ( String aggName : definitions.keySet()) {
			Definition  definition  = new Definition(
					aggName, 
					definitions.get(aggName), null);
			defs.add(definition);
		}
		return defs;
	}*/

	public TenantContainer getEngine(long tenantId) {
		// TODO Auto-generated method stub
		return null;
	}

	public AggService getService() {
		// TODO Auto-generated method stub
		return null;
	}
}
