package com.driver;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Workspace extends Gmail{

    private ArrayList<Meeting> calendar; // Stores all the meetings

    public Workspace(String emailId) {
        // The inboxCapacity is equal to the maximum value an integer can store.
        super(emailId,Integer.MAX_VALUE);
        calendar = new ArrayList<>();
    }

    public void addMeeting(Meeting meeting){
        //add the meeting to calendar
        calendar.add(meeting);

    }

    public int findMaxMeetings(){
        // find the maximum number of meetings you can attend
        // 1. At a particular time, you can be present in at most one meeting
        // 2. If you want to attend a meeting, you must join it at its start time and leave at end time.
        // Example: If a meeting ends at 10:00 am, you cannot attend another meeting starting at 10:00 am
        //calendar.sort((a,b) -> a.getStartTime() - b.getStartTime());
        Collections.sort(calendar, (a,b) -> {
            if(a.getEndTime().compareTo(b.getEndTime()) >  0){
                return 1;
            }
            return -1;
        });

        /* testing purpose
        for(Meeting m : calendar){
            System.out.println(m.toString());
        }
        */

        int count = 1;


        // Initially select first meeting.
        //m.add(al.get(0).pos);

        // time_limit to check whether new
        // meeting can be conducted or not.
        LocalTime time_limit = calendar.get(0).getEndTime();

        // Check for all meeting whether it
        // can be selected or not.
        for (int i = 1; i < calendar.size(); i++) {
            if (calendar.get(i).getStartTime().compareTo(time_limit) > 0) {

                // Add selected meeting to arraylist
                //m.add(al.get(i).pos);

                count++;

                // Update time limit
                time_limit = calendar.get(i).getEndTime();
            }
        }
        return count;
    }


}
