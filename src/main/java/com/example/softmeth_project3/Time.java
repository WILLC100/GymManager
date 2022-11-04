package com.example.softmeth_project3;

import java.text.DecimalFormat;
/**
 Stores constant time values with word names, hours, and minutes.
 Includes constructors, getters and setters for hours and minutes, and formatted time (hh:mm).
 @author William Chen
 @author Kalrav Pandit
 */
public enum Time {
    MORNING(9, 30), AFTERNOON(14, 0), EVENING(18, 30);
    private final int hour;
    private final int minute;
    /**
     Default constructor for a time, containing hour and minute.
     @param hr the hour in the time
     @param min the minutes in the time
     */
    Time(int hr, int min) {
        this.hour = hr;
        this.minute = min;
    }
    /**
     Formats time in HH:MM format.
     @return the time as a String in HH:MM format
     */
    public String toHourAndMinute() {
        return hour + ":" + new DecimalFormat("00").format(minute);
    }
}
