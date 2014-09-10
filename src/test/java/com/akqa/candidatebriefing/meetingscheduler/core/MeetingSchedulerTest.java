package com.akqa.candidatebriefing.meetingscheduler.core;

import com.akqa.candidatebriefing.meetingscheduler.pojo.MeetingRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/** 
* MeetingScheduler Tester. 
* 
* @author Daniel Wamara
* @since <pre>Jun 6, 2014</pre>
* @version 1.0 
*/ 
public class MeetingSchedulerTest {

	@Before
	public void before() throws Exception {
	}

	/**
	 * Requirement: No part of a meeting may fall outside office hours.
	 */
	@Test
	public void shouldNotBookMeetingRoomOutsideOfficeHours() {
		//given
		final String request = InputStringCreator.INSTANCE.createRequestWithMeetingOutsideOfficeHours();

		//when
		MeetingScheduler meetingScheduler = new MeetingScheduler();
		meetingScheduler.schedule(request);
		Map<LocalDate, Set<MeetingRequest>> meetingMap = meetingScheduler.getMeetingsMap();

		//then
		Assert.assertTrue(meetingMap.isEmpty());
	}

	/**
	 * Requirement: Meetings may not overlap.
	 */
	@Test
	public void shouldNotHaveOverlappingMeetings() {
		//given
		final String request = InputStringCreator.INSTANCE.createRequestWithOverlappingMeetings();

		//when
		MeetingScheduler meetingScheduler = new MeetingScheduler();
		meetingScheduler.schedule(request);

		//then
		Assert.assertEquals(InputStringCreator.INSTANCE.createResponseForOverlappingMeettings(), meetingScheduler.getMeetingMapAsString());
	}

	/**
	 * Requirement: Bookings must be processed in the chronological order in which they were submitted.
	 */
	@Test
	public void shouldProcessMeetingsInChronologicalOrderOfSubmission() {
		//given
		final String request = InputStringCreator.INSTANCE.createRequestFromDocumentSample();

		//when
		MeetingScheduler meetingScheduler = new MeetingScheduler();
		meetingScheduler.schedule(request);

		//then
		Assert.assertEquals(InputStringCreator.INSTANCE.createResponseFromDocumentSample(), meetingScheduler.getMeetingMapAsString());
	}

	/**
	 * Requirement: The ordering of booking submissions in the supplied input is not guaranteed.
	 */
	@Test
	public void shouldProcessMeetingsWithNoGuaranteeOfSubmissionOrderingInInput() {
		//given
		final String request = InputStringCreator.INSTANCE.createRequestFromDocumentSample();

		//when
		MeetingScheduler meetingScheduler = new MeetingScheduler();
		meetingScheduler.schedule(request);

		//then
		Assert.assertEquals(InputStringCreator.INSTANCE.createResponseFromDocumentSample(), meetingScheduler.getMeetingMapAsString());
	}

	/**
	 * Requirement: Should be conform to the sample in the document
	 */
	@Test
	public void shouldGroupMeetingsChronologically() {
		//given
		final String request = InputStringCreator.INSTANCE.createRequestFromDocumentSample();

		//when
		MeetingScheduler meetingScheduler = new MeetingScheduler();
		meetingScheduler.schedule(request);

		//then
		Assert.assertEquals(InputStringCreator.INSTANCE.createResponseFromDocumentSample(), meetingScheduler.getMeetingMapAsString());
	}
}
