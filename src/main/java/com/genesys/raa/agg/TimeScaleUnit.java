package com.genesys.raa.agg;

public enum TimeScaleUnit {
	WHOLE_TIME,
	YEAR,
	MONTH,
	QUATER,
	DAY, WEEK,
	HOUR,
	MINUTE_30,
	MINUTE_20,
	MINUTE_15,
	MINUTE_10,
	MINUTE_5,
	MINUTE_3,
	MINUTE_1,
	SECOND_30,
	SECOND_20,
	SECOND_15,
	SECOND_10,
	SECOND_5,
	SECOND_1;
	
	public static TimeScaleUnit getSubscaleUnit(TimeScaleUnit scaleUnit) {
		switch (scaleUnit) {
		case WHOLE_TIME:
			return YEAR;
		case YEAR:
			return MONTH;
		case MONTH:
			return DAY;
		case DAY:
			return HOUR;
		case HOUR:
			return MINUTE_15;
		default:
			return null;
		}
	}
}
