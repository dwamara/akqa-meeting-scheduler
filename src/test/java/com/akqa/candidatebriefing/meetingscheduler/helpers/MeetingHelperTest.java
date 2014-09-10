package com.akqa.candidatebriefing.meetingscheduler.helpers;

import com.akqa.candidatebriefing.meetingscheduler.pojo.MeetingRequest;
import com.akqa.candidatebriefing.meetingscheduler.pojo.OfficeHours;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

/**
 * Helper Tester.
 *
 * @author Daniel Wamara
 * @version 1.0
 * @since <pre>Jun 6, 2014</pre>
 */
public class MeetingHelperTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	/**
	 * Method: isMeetingOutsideOfficeHours(MeetingRequest meetingRequest, OfficeHours officeHours)
	 * This test the case when the both the start and end of a MeetingRequest lands during the OfficeHours.
	 */
	@Test
	public void testIsMeetingOutsideOfficeHoursWithMeetingInOfficeHours() throws Exception {
		final OfficeHours officeHours = new OfficeHours(new Integer[]{9,0}, new Integer[]{17,0});
		final MeetingRequest meetingRequest = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 09:00 2");
		Assert.assertFalse("Meeting should have been during office hours", MeetingHelper.INSTANCE.isMeetingOutsideOfficeHours(meetingRequest, officeHours));
	}

	/**
	 * Method: isMeetingOutsideOfficeHours(MeetingRequest meetingRequest, OfficeHours officeHours)
	 * This test the case when the start of a MeetingRequest lands outside the OfficeHours.
	 */
	@Test
	public void testIsMeetingOutsideOfficeHoursWithMeetingStartTimeOutsideOfOfficeHours() throws Exception {
		final OfficeHours officeHours = new OfficeHours(new Integer[]{9,0}, new Integer[]{17,0});
		final MeetingRequest meetingRequest = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 08:00 2");
		Assert.assertTrue("Meeting should have been outsie office hours", MeetingHelper.INSTANCE.isMeetingOutsideOfficeHours(meetingRequest, officeHours));
	}

	/**
	 * Method: isMeetingOutsideOfficeHours(MeetingRequest meetingRequest, OfficeHours officeHours)
	 * This test the case when the end of a MeetingRequest lands outside the OfficeHours.
	 */
	@Test
	public void testIsMeetingOutsideOfficeHoursWithMeetingEndTimeOutsideOfOfficeHours() throws Exception {
		final OfficeHours officeHours = new OfficeHours(new Integer[]{9,0}, new Integer[]{17,0});
		final MeetingRequest meetingRequest = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 18:00 2");
		Assert.assertTrue("Meeting should have been outsie office hours", MeetingHelper.INSTANCE.isMeetingOutsideOfficeHours(meetingRequest, officeHours));
	}

	/**
	 * Method: determineOfficeHours(final String officeHoursString)
	 * This method tests the creation of an OfficeHours instance
	 */
	@Test
	public void testDetermineOfficeHours() throws Exception {
		final OfficeHours officeHours = MeetingHelper.INSTANCE.determineOfficeHours("0900 1700");
		Assert.assertNotNull(officeHours);
		Assert.assertEquals(LocalTime.of(9, 0), officeHours.getStart());
		Assert.assertEquals(LocalTime.of(17, 0), officeHours.getEnd());
	}

	/**
	 * Method: isNewMeetingOverlappingPreviousMeeting(MeetingRequest newMeetingRequest, MeetingRequest previousMeetingRequest)
	 * This method tests overlapping of meetings when both are at the same time. There is overlapping.
	 */
	@Test
	public void testIsNewMeetingOverlappingPreviousMeetingWith2MeetingAtTheSameTime() throws Exception {
		final MeetingRequest meetingRequest1 = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 08:00 2");
		final MeetingRequest meetingRequest2 = new MeetingRequest("2011-03-18 10:17:06 EMP002", "2011-03-21 08:00 2");

		Assert.assertTrue("Meetings should have been overlapping", MeetingHelper.INSTANCE.isNewMeetingOverlappingPreviousMeeting(meetingRequest1, meetingRequest2));
	}

	/**
	 * Method: isNewMeetingOverlappingPreviousMeeting(MeetingRequest newMeetingRequest, MeetingRequest previousMeetingRequest)
	 * This method tests overlapping of meetings when one starts and ends in another one. There is overlapping.
	 */
	@Test
	public void testIsNewMeetingOverlappingPreviousMeetingWithOneMeetingInsideAnotherOne() throws Exception {
		final MeetingRequest meetingRequest1 = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 08:00 5");
		final MeetingRequest meetingRequest2 = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 09:00 1");

		Assert.assertTrue("Meetings should have been overlapping", MeetingHelper.INSTANCE.isNewMeetingOverlappingPreviousMeeting(meetingRequest1, meetingRequest2));
	}

	/**
	 * Method: isNewMeetingOverlappingPreviousMeeting(MeetingRequest newMeetingRequest, MeetingRequest previousMeetingRequest)
	 * This method tests overlapping of meetings when one starts inside another one. There is overlapping.
	 */
	@Test
	public void testIsNewMeetingOverlappingPreviousMeetingWithOneMeetingStartingInAnotherOne() throws Exception {
		final MeetingRequest meetingRequest1 = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 08:00 5");
		final MeetingRequest meetingRequest2 = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 09:00 5");

		Assert.assertTrue("Meetings should have been overlapping", MeetingHelper.INSTANCE.isNewMeetingOverlappingPreviousMeeting(meetingRequest1, meetingRequest2));
	}

	/**
	 * Method: isNewMeetingOverlappingPreviousMeeting(MeetingRequest newMeetingRequest, MeetingRequest previousMeetingRequest)
	 * This method tests overlapping of meetings when one end inside another one. There is overlapping.
	 */
	@Test
	public void testIsNewMeetingOverlappingPreviousMeetingWithOneMeetingEndingInAnotherOne() throws Exception {
		final MeetingRequest meetingRequest1 = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 10:00 2");
		final MeetingRequest meetingRequest2 = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 09:00 2");

		Assert.assertTrue("Meetings should have been overlapping", MeetingHelper.INSTANCE.isNewMeetingOverlappingPreviousMeeting(meetingRequest1, meetingRequest2));
	}

	/**
	 * Method: isNewMeetingOverlappingPreviousMeeting(MeetingRequest newMeetingRequest, MeetingRequest previousMeetingRequest)
	 * This method tests overlapping of meetings when one starts after another one. There is no overlapping.
	 */
	@Test
	public void testIsNewMeetingOverlappingPreviousMeetingWithOneMeetingAfterAnotherOne() throws Exception {
		final MeetingRequest meetingRequest1 = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 08:00 2");
		final MeetingRequest meetingRequest2 = new MeetingRequest("2011-03-17 10:17:06 EMP001", "2011-03-21 10:00 2");

		Assert.assertFalse("Meetings should not have been overlapping", MeetingHelper.INSTANCE.isNewMeetingOverlappingPreviousMeeting(meetingRequest1, meetingRequest2));
	}
}
