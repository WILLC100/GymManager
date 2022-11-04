package com.example.softmeth_project3;
/**
 Represents a fitness class with name, instructor, time, and members.
 Includes constructors, member search, add, and drop functionalities.
 @author William Chen
 @author Kalrav Pandit
 */
public class FitnessClass {
    private String className;
    private String instructor;
    private Time time;
    private String location;
    private Member[] membersCheckedIn, guestsCheckedIn;
    private static final int NOT_FOUND = -1;
    private static final int GROWTH_FACTOR = 4;

    private int totalMembers;
    /**
     Default constructor for a fitness class; initializes null values.
     */
    public FitnessClass() {
        className = null;
        instructor = null;
        time = null;
        membersCheckedIn = null;
    }
    /**
     Constructor for a fitness class based on a constant class session.
     @param c the class name
     @param i the class instructor
     @param t the class time
     @param l the class location
     */
    public FitnessClass(String c, String i, String t, String l) {
        className = c;
        instructor = i;
        time = Time.valueOf(t.toUpperCase());
        location = l;
        membersCheckedIn = new Member[GROWTH_FACTOR];
        guestsCheckedIn = new Member[GROWTH_FACTOR];
    }

    /**
     Checks in a member into a fitness class if they are not checked in.
     @param member The member to check in
     @return The result of the check in operation; returns false if checked in
     */
    public boolean checkIn(Member member) {
        int members = 0;
        for (int i = 0; i < membersCheckedIn.length; i++) {
            if (membersCheckedIn[i] != null) {
                if (membersCheckedIn[i].getFirstName().equalsIgnoreCase(member.getFirstName())
                        && membersCheckedIn[i].getLastName().equalsIgnoreCase
                        (member.getLastName())
                        && (membersCheckedIn[i].getDob().compareTo(member.getDob()) == 0)) {
                    return false;
                }
                members++;
            }
        }
        if (membersCheckedIn.length > members) {
            grow();
        }
        membersCheckedIn[members] = member;
        totalMembers = members + 1;
        return true;
    }
    /**
     Checks in a guest into a fitness class if the family/premium member can have additional guests.
     @param member The member the guest is checking in under
     @return The result of the check in operation; returns false if member cannot have more guests
     */
    public boolean checkInGuest(Family member) {
        int members = 0;
        for (int i = 0; i < guestsCheckedIn.length; i++) {
            if (guestsCheckedIn[i] != null) {
                members++;
            }
        }
        if (member instanceof Premium && ((Premium) member).getGuests() >= 3) {
            return false;
        }
        else if (!(member instanceof Premium) && ((Family) member).getGuests() >= 1) {
            return false;
        }
        if (guestsCheckedIn.length > members) {
            grow();
        }
        guestsCheckedIn[members] = member;
        member.addGuest();
        return true;
    }
    /**
     Drops a member from fitness class if they are already checked into the class.
     @param member The member to drop
     @return The result of the drop operation; returns false if not checked in
     */
    public boolean drop(Member member) {
        if (findMember(member) == NOT_FOUND) return false;
        int memberPtr = 0;
        for (int i = 0; i < membersCheckedIn.length; i++) {
            if (membersCheckedIn[i] != null &&
                    (membersCheckedIn[i].getFirstName().equalsIgnoreCase
                            (member.getFirstName())
                    && membersCheckedIn[i].getLastName().equalsIgnoreCase
                            (member.getLastName())
                    && (membersCheckedIn[i].getDob().compareTo
                            (member.getDob()) == 0))) {
                membersCheckedIn[i] = new Member();
                memberPtr = i;
                break;
            }
        }
        for (int j = memberPtr; j < membersCheckedIn.length - 1; j++) {
            membersCheckedIn[j] = membersCheckedIn[j+1];
        }
        membersCheckedIn[membersCheckedIn.length - 1] = new Member();
        totalMembers--;
        return true;
    }
    /**
     Drops a guest from a fitness class if the guest is there.
     @param member The member the guest was checking in under
     @return The result of the drop operation; returns false if member was not found
     */
    public boolean dropGuest(Family member) {
            if (findGuest(member) == NOT_FOUND) return false;
            int memberPtr = 0;
            for (int i = 0; i < guestsCheckedIn.length; i++) {
                if (guestsCheckedIn[i] != null &&
                        (guestsCheckedIn[i].getFirstName().equalsIgnoreCase
                                (member.getFirstName())
                                && guestsCheckedIn[i].getLastName().equalsIgnoreCase
                                (member.getLastName())
                                && (guestsCheckedIn[i].getDob().compareTo
                                (member.getDob()) == 0))) {
                    guestsCheckedIn[i] = new Member();
                    memberPtr = i;
                    break;
                }
            }
            for (int j = memberPtr; j < guestsCheckedIn.length - 1; j++) {
                guestsCheckedIn[j] = guestsCheckedIn[j+1];
            }
            guestsCheckedIn[guestsCheckedIn.length - 1] = new Member();
            member.removeGuest();
            return true;
    }
    /**
     Searches the checked in list of a fitness class for a specific member.
     @param member The member to search for
     @return The index of the member in membersCheckedIn, or -1 if not found
     */
    public int findMember(Member member) {
        for (int i = 0; i < totalMembers; i++) {
            if (membersCheckedIn[i] != null) {
                String fname = membersCheckedIn[i].getFirstName();
                String lname = membersCheckedIn[i].getLastName();
                Date dob = membersCheckedIn[i].getDob();
                if (fname.equalsIgnoreCase(member.getFirstName())
                        && lname.equalsIgnoreCase(member.getLastName())
                        && (dob.compareTo(member.getDob()) == 0)) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }
    /**
     Searches the checked in list of a fitness class for a specific guest.
     @param member The member to search for
     @return The index of the member in membersCheckedIn, or -1 if not found
     */
    public int findGuest(Member member) {
        for (int i = 0; i < guestsCheckedIn.length; i++) {
            if (guestsCheckedIn[i] != null) {
                String fname = guestsCheckedIn[i].getFirstName();
                String lname = guestsCheckedIn[i].getLastName();
                Date dob = guestsCheckedIn[i].getDob();
                if (fname.equalsIgnoreCase(member.getFirstName())
                        && lname.equalsIgnoreCase(member.getLastName())
                        && (dob.compareTo(member.getDob()) == 0)) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }
    /**
     Expands the membersCheckedIn array by 4 when called.
     */
    private void grow() {
        Member[] tmp = membersCheckedIn;
        membersCheckedIn = new Member[tmp.length + GROWTH_FACTOR];
        for (int i = 0; i < tmp.length; i++) {
            membersCheckedIn[i] = tmp[i];
        }
    }
    /**
     Gets the class session name.
     @return The class name
     */
    public String getClassName() {
        return className;
    }
    /**
     Gets the class session instructor.
     @return The class instructor.
     */
    public String getInstructor() {
        return instructor;
    }
    /**
     Gets the formatted (hh:mm) time of a given time.
     @return The formatted time.
     */
    public String getFormattedTime() {
        return time.toHourAndMinute();
    }
    /**
     Gets the list of members checked into a specific class
     @return The list of members checked in.
     */
    public Member[] getMembersCheckedIn() {
        return membersCheckedIn;
    }
    public Member[] getGuestsCheckedIn() {
        return guestsCheckedIn;
    }

    /**
     Accesses the count of members checked into a class.
     @return The number of members checked into a class.
     */
    public int countCheckedInMembers() {
        return totalMembers;
    }
    public String getLocation() {
        return location;
    }
    /**
     Getter method for time.
     @return the time of the class, as a Time object
     */
    /**
     Get the maximum number of guests contained.
     @return the length of the guests
     */
    public int getGuestSize() {
        int guests = 0;
        for (Member m: guestsCheckedIn) {
            if (m != null) {
                guests++;
            }
        }
        return guests;
    }
    /**
     Represents the fitness class as a formatted string.
     @return the fitness class as a formatted string
     */
    public String toString() {
        return (getClassName().toUpperCase() + " - " +
                getInstructor().toUpperCase() + ", " + getFormattedTime()
                + ", " + getLocation().toUpperCase());
    }
}
