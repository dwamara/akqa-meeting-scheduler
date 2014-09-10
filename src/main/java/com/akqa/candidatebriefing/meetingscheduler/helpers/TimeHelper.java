package com.akqa.candidatebriefing.meetingscheduler.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

/**
 * This singleton helper class encapsulates methods helpful to treat data pertaining to time.
 * @author Daniel Wamara
 */
public enum TimeHelper {
	INSTANCE;

	/**
	 *
	 * @param timeToken
	 * @return
	 */
	public static Integer[] extractHoursAndMinutesOfTime(final String timeToken) {
		return extractHoursAndMinutesOfTime(timeToken, null);
	}

	/**
	 *
	 * @param timeToken
	 * @param delimiter
	 * @return
	 */
	public static Integer[] extractHoursAndMinutesOfTime(final String timeToken, final String delimiter) {
		// TODO Validate that timeToken is exactly in the format we want to avoid parsing problems

		Integer hour = null;
		Integer minutes = null;

		if (delimiter != null && delimiter.trim().length() > 0) {
			StringTokenizer st = new StringTokenizer(timeToken, delimiter);
			hour = Integer.valueOf(st.nextToken());
			minutes = Integer.valueOf(st.nextToken());
		} else {
			hour = Integer.valueOf(timeToken.substring(0, 2));
			minutes = Integer.valueOf(timeToken.substring(2));
		}

		return new Integer[]{hour, minutes};
	}

	/**
	 *
	 * @param submissionDateAsString
	 * @param submissionTimeAsString
	 * @return
	 */
	public static LocalDateTime getSubmissionTime(final String submissionDateAsString, final String submissionTimeAsString) {
		final LocalDate date = LocalDate.parse(submissionDateAsString, DateTimeFormatter.ISO_LOCAL_DATE);
		final LocalTime time = LocalTime.parse(submissionTimeAsString, DateTimeFormatter.ISO_LOCAL_TIME);
		return LocalDateTime.of(date, time);
	}

	/**
	 *
	 * @param meetingDateAsString
	 * @return
	 */
	public static LocalDate getMeetingDate(final String meetingDateAsString) {
		return LocalDate.parse(meetingDateAsString, DateTimeFormatter.ISO_LOCAL_DATE);
	}

	/**
	 *
	 * @param s
	 * @return
	 */
	public static LocalTime getMeetingStartTime(final String s) {
		Integer[] hourAndMinutes = extractHoursAndMinutesOfTime(s, ":");
		return LocalTime.of(hourAndMinutes[0], hourAndMinutes[1]);
	}
}
