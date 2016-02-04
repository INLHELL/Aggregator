package com.genesys.raa.agg.tempo;

public interface AggSynchronizer {
	public boolean synchronizeTables();
	public boolean synchronizeIndexes();

}
