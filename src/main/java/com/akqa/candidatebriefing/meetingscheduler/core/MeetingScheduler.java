package com.akqa.candidatebriefing.meetingscheduler.core;

import com.akqa.candidatebriefing.meetingscheduler.helpers.MeetingHelper;
import com.akqa.candidatebriefing.meetingscheduler.pojo.MeetingRequest;
import com.akqa.candidatebriefing.meetingscheduler.pojo.OfficeHours;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This is the core class called to process the scheduling
 */
public class MeetingScheduler {
	private final Map<LocalDate, Set<MeetingRequest>> meetingsMap = new TreeMap<LocalDate, Set<MeetingRequest>>();
	private String meetingMapAsString;

	/**
	 * This method processes the scheduling
	 * @param meetingsBatch the input to be processed
	 */
	public  void schedule(final String meetingsBatch) {
		final Scanner scanner = new Scanner(meetingsBatch);
		scanner.useDelimiter(System.getProperty("line.separator"));

		// TODO must take in account empty lines, just in case

		boolean firstLine = true;

		OfficeHours officeHours = null;
		final Set<MeetingRequest> meetingRequestsSet = new TreeSet<MeetingRequest>();
		while(scanner.hasNext()){
			if (firstLine) {
				String officeHoursString = scanner.next();
				officeHours = MeetingHelper.INSTANCE.determineOfficeHours(officeHoursString);
				firstLine = false;
			} else {
				MeetingRequest meetingRequest = new MeetingRequest(scanner.next(), scanner.next());
				addMeetingToSet(meetingRequest, officeHours, meetingRequestsSet);
			}
		}
		scanner.close();

		cleanOverlappingMeetings(meetingsMap, meetingRequestsSet);

		meetingMapAsString = createScheduling(meetingsMap);

		dumpToConsole(meetingMapAsString);
		dumpToFile(meetingMapAsString);
	}

	/**
	 * This method dumps the result of the processing to a file in a temporary folder
	 * @param meetingsScheduling the result of the processing
	 */
	private void dumpToFile(final String meetingsScheduling) {
		try {
			Files.write(Paths.get(System.getProperty("java.io.tmpdir") + File.separator + "scheduling_" + System.currentTimeMillis() + ".txt"), meetingsScheduling.getBytes());
		} catch (IOException ioExc) {
			System.out.println("Dumping file failed: " + ioExc.getMessage());
		}
	}

	/**
	 * This method dumps the result of the processing to the console
	 * @param meetingsScheduling the result of the processing
	 */
	private void dumpToConsole(final String meetingsScheduling) {
		System.out.println(meetingsScheduling);
	}

	/**
	 * This method create a String representation of the result of the processing.
	 * @param meetingsMapValue
	 */
	private  String createScheduling(final Map<LocalDate, Set<MeetingRequest>> meetingsMapValue) {
		final StringBuilder sb = new StringBuilder();
		for (Map.Entry<LocalDate, Set<MeetingRequest>> meetingEntry : meetingsMapValue.entrySet()) {
			final LocalDate meetingDate = meetingEntry.getKey();

			sb.append(meetingDate).append("\n");

			final Set<MeetingRequest> meetings = meetingEntry.getValue();
			for (MeetingRequest meeting : meetings) {
				sb.append(meeting.getStartTime()).append(" ");
				sb.append(meeting.getEndTime()).append(" ");
				sb.append(meeting.getEmployeeId()).append("\n");
			}

		}
		return sb.toString();
	}

	/**
	 * This method add the MeetingRequest to the set by eliminating any overlapping meetings
	 * @param meetingsMap
	 * @param meetingRequestsSet
	 */
	private  void cleanOverlappingMeetings(final Map<LocalDate, Set<MeetingRequest>> meetingsMap, final Set<MeetingRequest> meetingRequestsSet) {
		for (MeetingRequest meetingRequestInSet : meetingRequestsSet) {
			if (!meetingsMap.containsKey(meetingRequestInSet.getMeetingDate())) {
				meetingsMap.put(meetingRequestInSet.getMeetingDate(), new TreeSet<MeetingRequest>());
			}

			if (meetingsMap.get(meetingRequestInSet.getMeetingDate()).isEmpty()) {
				meetingsMap.get(meetingRequestInSet.getMeetingDate()).add(meetingRequestInSet);
			} else {
				MeetingRequest lastMeetingRequestInSet = (MeetingRequest)((TreeSet)meetingsMap.get(meetingRequestInSet.getMeetingDate())).last();
				if (!MeetingHelper.INSTANCE.isNewMeetingOverlappingPreviousMeeting(meetingRequestInSet, lastMeetingRequestInSet)) {
					meetingsMap.get(meetingRequestInSet.getMeetingDate()).add(meetingRequestInSet);
				}
			}
		}
	}

	/**
	 * We add the MeetingRequest to the Set that will contain all the MeetingRequest. We don't need to care about MeetingRequest falling outside
	 * of the office hours, so they are here put aside.
	 * @param meetingRequest the MeetingRequest to add to the Set
	 * @param officeHours the evaluated office hours
	 * @param meetingsSet the Set containing the MeetingRequest worth evaluating
	 */
	private  void addMeetingToSet(final MeetingRequest meetingRequest, final OfficeHours officeHours, final Set<MeetingRequest> meetingsSet) {
		if (!MeetingHelper.INSTANCE.isMeetingOutsideOfficeHours(meetingRequest, officeHours)) {
			meetingsSet.add(meetingRequest);
		}
	}

	/**
	 * This method returns the map containing the MeetingRequest
	 * @return the map set during the process
	 */
	public Map<LocalDate, Set<MeetingRequest>> getMeetingsMap() {
		return meetingsMap;
	}

	/**
	 * This method returns a String representation of the processing
	 * @return
	 */
	public String getMeetingMapAsString() {
		return meetingMapAsString;
	}
}
