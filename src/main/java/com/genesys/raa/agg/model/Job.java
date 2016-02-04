package com.genesys.raa.agg.model;


//import com.genesys.raa.agg.TimeInterval;

//@Entity
public class Job {
	
	long jobId;
	String aggregateName;
//	TimeInterval interval;
	String lastExecMessage;
	
	
	/**
	 * @return the lastExecMessage
	 */
	public String getLastExecMessage() {
		return lastExecMessage;
	}
	/**
	 * @param lastExecMessage the lastExecMessage to set
	 */
	public void setLastExecMessage(String lastExecMessage) {
		this.lastExecMessage = lastExecMessage;
	}
	/**
	 * @return the jobId
	 */
	public long getJobId() {
		return jobId;
	}

	/**
	 * @return the interval
	 */
	/*public TimeInterval getInterval() {
		return interval;
	}
	*/

}
