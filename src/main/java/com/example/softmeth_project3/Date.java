package com.example.softmeth_project3;

import java.util.Calendar;
/**
 Represents the month, day, and year values of a date.
 Includes methods to check if date is valid, format date, and getters and setters.
 @author William Chen
 @author Kalrav Pandit
 */
public class Date implements Comparable<Date> {
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    private static final int SPLIT_MONTH_VALUE = 0;
    private static final int SPLIT_DAY_VALUE = 1;
    private static final int ZERO_INDEX_OFFSET = 1;

    private static final int SPLIT_YEAR_VALUE = 2;
    private static final int JANUARY = 1, FEBRUARY = 2, APRIL = 4,
            JUNE = 6, SEPT = 9,  NOV = 11, DEC = 12;
    private static final int THIRTY_DAYS = 30, THIRTY_ONE_DAYS = 31;
    private static final int LEAP_YEAR_FEB = 29, NO_LEAP_FEB = 28;

    private int year;
    private int month;
    private int day;
    /**
     Default constructor.
     Initializes a new Date based on the current day, month, and year.
     */
    public Date() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + ZERO_INDEX_OFFSET;
        day = cal.get(Calendar.DAY_OF_MONTH);
    }
    /**
     Creates a new Date based on the current date, with months added on (up to a year).
     @param growthFactor the number of months to add
     */
    public Date(int growthFactor) {
        super();
        Calendar cal = Calendar.getInstance();
        if (growthFactor == DEC) {
            cal.add(Calendar.YEAR, ZERO_INDEX_OFFSET);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + ZERO_INDEX_OFFSET;
            day = cal.get(Calendar.DAY_OF_MONTH);
            return;
        }
        cal.add(Calendar.MONTH, growthFactor);
        month = cal.get(Calendar.MONTH) + ZERO_INDEX_OFFSET;
        day = cal.get(Calendar.DAY_OF_MONTH);
        year = cal.get(Calendar.YEAR);
    }
    /**
     Creates a new Date based on a string formatted as "MM/DD/YYYY".
     @param date a String formatted as "MM/DD/YYYY"
     */
    public Date(String date) {
        String[] split = date.split("/");
        year = Integer.parseInt(split[SPLIT_YEAR_VALUE]);
        month = Integer.parseInt(split[SPLIT_MONTH_VALUE]);
        day = Integer.parseInt(split[SPLIT_DAY_VALUE]);
    }
    /**
     Formats the year in MM/DD/YYYY format.
     @return the year formatted as a String in MM/DD/YYYY format.
     */
    public String formattedDate() {
        return this.month + "/" + this.day + "/" + this.year;
    }
    /** 
     Compares this date against another date passed through parameters.
     Compared by year, month, and day in that order.
     @param date the object to be compared
     @return a value greater than 0 if this date is greater,
     less than 0 if this date is smaller, or 0 if dates are the same
     */
    @Override
    public int compareTo(Date date) {
        // compare by year
        if (this.year != date.year) {
            return this.year - date.year;
        }
        else if (this.month != date.month) {
            return this.month - date.month;
        }
        else if (this.day != date.day) {
            return this.day - date.day;
        }
        return 0;
    }

    /**
     Checks if date is valid (has proper days and is a calendar month).
     @return the result; true if valid and false if invalid
     */
    public boolean isValid() {
        if (year < 1900) return false;
        boolean isLeap = false;
        if (this.month < JANUARY || this.month > DEC || this.day <= 0) return false;
       // Special Case: February
        else if (this.month == FEBRUARY) {
            if (this.year % QUADRENNIAL == 0) {
                isLeap = true;
                if (this.year % CENTENNIAL == 0 && this.year % QUATERCENTENNIAL != 0) isLeap = false;
            }
            if (isLeap && this.day > LEAP_YEAR_FEB ||
                    (!isLeap && this.day > NO_LEAP_FEB)) {
                return false;
            }
        }
        else {
            if (this.month == APRIL || this.month == JUNE ||
                    this.month == SEPT || this.month == NOV) {
                if (this.day > THIRTY_DAYS) {
                    return false;
                }
            }
            else if (this.day > THIRTY_ONE_DAYS) {
                return false;
            }
        }
        return true;
    }
    /**
     Sets the year value to a new year based on user input.
     @param newYear the year to change the existing Date's year to.
     */
    public void setYear(int newYear) {
        this.year = newYear;
    }
    /** Getter for year value.
     * @return the year value as an integer. */
    public int getYear() {
        return this.year;
    }
}