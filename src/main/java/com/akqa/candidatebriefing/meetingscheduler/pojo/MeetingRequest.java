package com.akqa.candidatebriefing.meetingscheduler.pojo;

import com.akqa.candidatebriefing.meetingscheduler.helpers.MeetingHelper;
import com.akqa.candidatebriefing.meetingscheduler.helpers.TimeHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.StringTokenizer;

/**
 *
 */
public class MeetingRequest implements Comparable<MeetingRequest> {
	private  LocalDateTime submissionTime;
	private  String employeeId;

	private  LocalDate meetingDate;
	private  LocalTime startTime;
	private  LocalTime endTime;

	/**
	 *
	 * @param requestingData
	 * @param meetingTime
	 * @return
	 */
	public MeetingRequest(final String requestingData, final String meetingTime) {
		// TODO for improvement, use regular expression
		// TODO for improvement, it should be checked that a request is not submitted for a meeting in the past

		StringTokenizer st = new StringTokenizer(requestingData, MeetingHelper.BLANK);

		this.submissionTime = TimeHelper.INSTANCE.getSubmissionTime(st.nextToken(), st.nextToken());
		this.employeeId = st.nextToken();

		st = new StringTokenizer(meetingTime, MeetingHelper.BLANK);
		this.meetingDate = TimeHelper.INSTANCE.getMeetingDate(st.nextToken());
		this.startTime = TimeHelper.INSTANCE.getMeetingStartTime(st.nextToken());
		this.endTime = this.startTime.plusHours(Long.parseLong(st.nextToken()));
	}


	/**
	 *
	 * @return
	 */
	public LocalDateTime getSubmissionTime() {
		return submissionTime;
	}

	/**
	 *
	 * @return
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 *
	 * @return
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/**
	 *
	 * @return
	 */
	public String getEmployeeId() {
		return employeeId;
	}


	/**
	 *
	 * @return
	 */
	public LocalDate getMeetingDate() {
		return meetingDate;
	}

	/**
	 * Compares this object with the specified object for order.  Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the
	 * specified object.
	 * <p/>
	 * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) == -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This implies that <tt>x.compareTo(y)</tt> must throw
	 * an exception iff <tt>y.compareTo(x)</tt> throws an exception.)
	 * <p/>
	 * <p>The implementor must also ensure that the relation is transitive: <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies <tt>x.compareTo(z)&gt;0</tt>.
	 * <p/>
	 * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt> implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for all <tt>z</tt>.
	 * <p/>
	 * <p>It is strongly recommended, but <i>not</i> strictly required that <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any class that implements the
	 * <tt>Comparable</tt> interface and violates this condition should clearly indicate this fact.  The recommended language is "Note: this class has a natural ordering that is
	 * inconsistent with equals."
	 * <p/>
	 * <p>In the foregoing description, the notation <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical <i>signum</i> function, which is defined to return one of
	 * <tt>-1</tt>, <tt>0</tt>, or <tt>1</tt> according to whether the value of <i>expression</i> is negative, zero or positive.
	 *
	 * @param o the object to be compared.
	 *
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
	 *
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it from being compared to this object.
	 */
	@Override
	public int compareTo(final MeetingRequest that) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		if (this == that) {
			return EQUAL;
		}

		if (this.getSubmissionTime().isBefore(that.getSubmissionTime())) {
			return (this.getStartTime().isBefore(that.getStartTime()))? AFTER : BEFORE;
		}

		if (this.getSubmissionTime().isAfter(that.getSubmissionTime())) {
			return (this.getStartTime().isBefore(that.getStartTime()))? BEFORE : AFTER;
		}

		return EQUAL;
	}
}
