package com.akqa.candidatebriefing.meetingscheduler.helpers;

import com.akqa.candidatebriefing.meetingscheduler.pojo.MeetingRequest;
import com.akqa.candidatebriefing.meetingscheduler.pojo.OfficeHours;

import java.util.StringTokenizer;

/**
 * This singleton helper class encapsulates methods helpful to treat data pertaining to MeetingRequest and OfficeHours.
 * @author Daniel Wamara
 */
public enum MeetingHelper {
	/** An instance of this class, it will uniquely exists */
	INSTANCE;

	/** The blank character */
	public static final String BLANK = " ";

	/**
	 * The method checks if a MeetingRequest is outside OfficeHours, so it could be ignored
	 * @param meetingRequest the MeetingRequest to check
	 * @param officeHours the given OfficeHours during which every meeting should occur
	 * @return true if the MeetingRequest is outside OfficeHours, false otherwise
	 */
	public static boolean isMeetingOutsideOfficeHours(MeetingRequest meetingRequest, OfficeHours officeHours) {
		return meetingRequest.getStartTime().isBefore(officeHours.getStart()) || meetingRequest.getEndTime().isAfter(officeHours.getEnd());
	}

	/**
	 * This method creates an OfficeHours class with the given String read from the input
	 * @param officeHoursString the String passed from the input
	 * @return a new instance of OfficeHours
	 */
	public static OfficeHours determineOfficeHours(final String officeHoursString) {
		// TODO Validate that officeHours is exactly in the format we want to avoid parsing problems

		final StringTokenizer stringTokenizer = new StringTokenizer(officeHoursString, BLANK);
		final OfficeHours officeHours = new OfficeHours(TimeHelper.INSTANCE.extractHoursAndMinutesOfTime(stringTokenizer.nextToken()),
				TimeHelper.INSTANCE.extractHoursAndMinutesOfTime(stringTokenizer.nextToken()));
		return officeHours;
	}

	/**
	 * This method checks if a MeetingRequest overlaps with another one
	 * @param newMeetingRequest
	 * @param previousMeetingRequest
	 * @return
	 */
	public static boolean isNewMeetingOverlappingPreviousMeeting(MeetingRequest newMeetingRequest, MeetingRequest previousMeetingRequest) {
		return (newMeetingRequest.getStartTime().isBefore(previousMeetingRequest.getEndTime()) && previousMeetingRequest.getStartTime().isBefore(newMeetingRequest.getEndTime()))
			||
			((newMeetingRequest.getStartTime() == previousMeetingRequest.getStartTime() && newMeetingRequest.getEndTime() == previousMeetingRequest.getEndTime()));
	}
}
