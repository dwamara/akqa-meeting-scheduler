package com.akqa.candidatebriefing.meetingscheduler.core;

/**
 * This helper class creates String needed for the unit tests. Another mean could be to have the String represented directly in files that would be read.
 */
public enum InputStringCreator {
	INSTANCE;

	private final static String LINE_BREAK = System.getProperty("line.separator");

	public static String createRequestWithMeetingOutsideOfficeHours() {
		final StringBuilder stringBuilder =  new StringBuilder();
		stringBuilder.append("0900 1730").append(LINE_BREAK);
		stringBuilder.append("2011-03-15 17:29:12 EMP005").append(LINE_BREAK);
		stringBuilder.append("2011-03-21 16:00 3").append(LINE_BREAK);
		return stringBuilder.toString();
	}

	public static String createRequestFromDocumentSample() {
		final StringBuilder stringBuilder =  new StringBuilder();
		stringBuilder.append("0900 1730").append(LINE_BREAK);
		stringBuilder.append("2011-03-17 10:17:06 EMP001").append(LINE_BREAK);
		stringBuilder.append("2011-03-21 09:00 2").append(LINE_BREAK);
		stringBuilder.append("2011-03-16 12:34:56 EMP002").append(LINE_BREAK);
		stringBuilder.append("2011-03-21 09:00 2").append(LINE_BREAK);
		stringBuilder.append("2011-03-16 09:28:23 EMP003").append(LINE_BREAK);
		stringBuilder.append("2011-03-22 14:00 2").append(LINE_BREAK);
		stringBuilder.append("2011-03-17 11:23:45 EMP004").append(LINE_BREAK);
		stringBuilder.append("2011-03-22 16:00 1").append(LINE_BREAK);
		stringBuilder.append("2011-03-15 17:29:12 EMP005").append(LINE_BREAK);
		stringBuilder.append("2011-03-21 16:00 3").append(LINE_BREAK);
		return stringBuilder.toString();
	}

	public static String createResponseFromDocumentSample() {
		final StringBuilder stringBuilder =  new StringBuilder();
		stringBuilder.append("2011-03-21").append(LINE_BREAK);
		stringBuilder.append("09:00 11:00 EMP002").append(LINE_BREAK);
		stringBuilder.append("2011-03-22").append(LINE_BREAK);
		stringBuilder.append("14:00 16:00 EMP003").append(LINE_BREAK);
		stringBuilder.append("16:00 17:00 EMP004").append(LINE_BREAK);
		return stringBuilder.toString();
	}

	public String createRequestWithOverlappingMeetings() {
		final StringBuilder stringBuilder =  new StringBuilder();
		stringBuilder.append("0900 1730").append(LINE_BREAK);
		stringBuilder.append("2011-03-17 10:17:06 EMP001").append(LINE_BREAK);
		stringBuilder.append("2011-03-21 09:00 2").append(LINE_BREAK);
		stringBuilder.append("2011-03-16 12:34:56 EMP002").append(LINE_BREAK);
		stringBuilder.append("2011-03-21 10:00 1").append(LINE_BREAK);
		return stringBuilder.toString();
	}
	public static String createResponseForOverlappingMeettings() {
		final StringBuilder stringBuilder =  new StringBuilder();
		stringBuilder.append("2011-03-21").append(LINE_BREAK);
		stringBuilder.append("10:00 11:00 EMP002").append(LINE_BREAK);
		return stringBuilder.toString();
	}
}
