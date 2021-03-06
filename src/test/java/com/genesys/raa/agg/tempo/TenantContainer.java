package com.genesys.raa.agg.tempo;

import com.genesys.raa.agg.model.Aggregate;
import com.genesys.raa.agg.model.Tenant;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@FieldDefaults(makeFinal=true, level = AccessLevel.PRIVATE)
@Getter
public class TenantContainer {

    Tenant tenant;

	public void plugAggregate(Aggregate aggregate) {
		// TODO 
		// new JobScheduler(aggregate)
		// new AggJobExecutor(aggregate)
	}

	public void start() {
		
	}

	public void unplugAggregate(String aggregateName) {
		
	}

	public void stop() {

	}

}
