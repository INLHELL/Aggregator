package com.genesys.raa.agg.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Access(AccessType.FIELD)
public class Job {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GEN")
	@SequenceGenerator(name="SEQ_GEN", sequenceName="AGG_JOB_SEQ")
	private long id;

	@Column(nullable = false)
	private long aggregateId;

	@Column(nullable = false)
	private long groupItemId;

	private Date createDate;
	private Date updateDate;
	private Date acquireDate;
	private boolean acquired;

	@Column(length = 500)
	private String lastExecMessage;


	/*@Column(nullable = false, length = 50, unique = true)
	private String name;
	String aggregateName;
//	TimeInterval interval;
	String lastExecMessage;
	
	
	*//**
	 * @return the lastExecMessage
	 *//*
	public String getLastExecMessage() {
		return lastExecMessage;
	}
	*//**
	 * @param lastExecMessage the lastExecMessage to set
	 *//*
	public void setLastExecMessage(String lastExecMessage) {
		this.lastExecMessage = lastExecMessage;
	}
	*//**
	 * @return the jobId
	 *//*
	public long getJobId() {
		return jobId;
	}

	*//**
	 * @return the interval
	 *//*
	*//*public TimeInterval getInterval() {
		return interval;
	}
	*//*
*/
}
