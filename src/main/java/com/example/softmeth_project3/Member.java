package com.example.softmeth_project3;
/**
 Stores data and management methods for a gym member.
 Includes constructors and member search, fee, equality, and comparison methods.
 @author William Chen
 @author Kalrav Pandit
 */
public class Member implements Comparable<Member>{
    private String fname;
    private String lname;
    private Date dob;
    private Date expire;
    private Location location;
    private static final double ONE_TIME_FEE = 29.99;
    private static final double SUBSCRIPTION_MONTHLY_FEE = 39.99;
    private static final int PAY_CYCLE = 3;
    /**
     Default constructor for a member; initializes null values.
     */
    public Member() {
        fname = null;
        lname = null;
        dob = null;
        expire = null;
        location = null;
    }
    /**
     Member constructor given a set of parameters.
     @param firstName the member's first name
     @param lastName the member's last name
     @param dateOfBirth the member's date of birth as a Date object
     @param expireDate the member's expiration date as a Date object
     @param loc the member's gym location as a String
     */
    public Member(String firstName, String lastName, Date dateOfBirth, Date expireDate, String loc) {
        fname = firstName;
        lname = lastName;
        dob = dateOfBirth;
        expire = expireDate;
        location = Location.valueOf(loc.toUpperCase());
    }
    /**
     Obtains the membership fee for a standard member.
     @return the membership fee for member
     */
    public double membershipFee() {
        return ONE_TIME_FEE + SUBSCRIPTION_MONTHLY_FEE * PAY_CYCLE;
    }
    /**
     Gets member's first name.
     @return first name of the member
     */
    public String getFirstName() {
        return fname;
    }
    /**
     Gets member's last name.
     @return last name of the member
     */
    public String getLastName() {
        return lname;
    }
    /**
     Gets member's date of birth
     @return date of birth, formatted as a Date
     */
    public Date getDob() {
        return dob;
    }
    /**
     Gets member's gym expiration date
     @return the member's expiration date, formatted as a Date
     */
    public Date getExpire() {
        return expire;
    }
    /**
     Gets member's location
     @return the member's location, formatted as a Location
     */
    public Location getLocation() {
        return this.location;
    }
    /**
     Outputs member details, including name, date of birth, expiration, and location
     @return a single line outputting member information
     */
    @Override
    public String toString() {
        String res;
        if (getExpire().compareTo(new Date()) < 0 ) {
            res = fname + " " + lname + ", " + "DOB: " + dob.formattedDate() + ", Membership expired "
                    + expire.formattedDate() + ", Location: " + location.formattedLocation();
        }
        else {
            res = fname + " " + lname + ", " + "DOB: " + dob.formattedDate() + ", Membership expires "
                    + expire.formattedDate() + ", Location: " + location.formattedLocation();
        }
        if (this instanceof Premium) {
            res += ", (Premium) Guest-pass remaining: ";
            Premium pm = (Premium) this;
            res += 3 - pm.getGuests();

        }
        else if (this instanceof Family) {
            res += ", (Family) Guest-pass remaining: ";
            Family pm = (Family) this;
            res += 1 - pm.getGuests();
        }
        return res;
    }
    /**
     Checks if date of birth, first name, and last name are equal to another member to see if they are equal to each other
     @param obj the member to be compared
     @return true if the same, and false if not the same member
     */
    @Override
    public boolean equals(Object obj) {
        Member memberToCompare = (Member) obj;
        if (memberToCompare.fname.equals(this.fname)
                && memberToCompare.lname.equals(this.lname)
                && memberToCompare.dob == dob) {
            return true;
        }
        return false;
    }
    /**
     Compares this member against another member (last and first name) passed through parameters.
     @param member the member to be compared
     @return a value greater than 0 if member has a later name in the alphabet, less than 0 if member has an earlier name, or 0 if names are the same
     */
    @Override
    public int compareTo(Member member) {
        int lnCompare = this.lname.compareToIgnoreCase(member.lname);
        if (lnCompare == 0) return this.fname.compareToIgnoreCase(member.fname);
        return lnCompare;
    }

}