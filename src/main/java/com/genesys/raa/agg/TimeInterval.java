package com.genesys.raa.agg;

import java.util.Date;

public class TimeInterval {

	long intervalId;
	
	/**
	 * Size of the interval
	 */
	TimeScaleUnit scaleUnit;
	
	/**
	 * A number of the interval in bigger time cycle
	 */
	int intervalNum;
	
	Date startPoint;
	Date stopPoint;
	
	
	public long getIntervalId() {
		return intervalId;
	}
	public void setIntervalId(long intervalId) {
		this.intervalId = intervalId;
	}
	public TimeScaleUnit getScaleUnit() {
		return scaleUnit;
	}
	public void setScaleUnit(TimeScaleUnit scaleUnit) {
		this.scaleUnit = scaleUnit;
	}
	public int getIntervalNum() {
		return intervalNum;
	}
	public void setIntervalNum(int intervalNum) {
		this.intervalNum = intervalNum;
	}
	public Date getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Date startPoint) {
		this.startPoint = startPoint;
	}
	public Date getStopPoint() {
		return stopPoint;
	}
	public void setStopPoint(Date stopPoint) {
		this.stopPoint = stopPoint;
	}
	
}
