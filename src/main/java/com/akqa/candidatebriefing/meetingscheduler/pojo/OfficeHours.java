package com.akqa.candidatebriefing.meetingscheduler.pojo;

import java.text.MessageFormat;
import java.time.LocalTime;

/**
 *
 */
public class OfficeHours {
	private final LocalTime start;
	private final LocalTime end;

	/**
	 *
	 * @param startValue
	 * @param endValue
	 */
	public OfficeHours(Integer[] startValue, Integer[] endValue){
		this.start = LocalTime.of(startValue[0], startValue[1]);
		this.end = LocalTime.of(endValue[0], endValue[1]);
	}

	/**
	 *
	 * @return
	 */
	public LocalTime getStart() {
		return start;
	}

	/**
	 *
	 * @return
	 */
	public LocalTime getEnd() {
		return end;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return MessageFormat.format("OfficeHours'{'start=''{0}'', end=''{1}'''}'", start, end);
	}
}
