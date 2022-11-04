package com.example.softmeth_project3;
/**
 Represents a member with the Family membership, extending the standard membership.
 Initializes the member, counts guests, and contains the membership fee.
 @author William Chen
 @author Kalrav Pandit
 */
public class Family extends Member {
    private static final double ONE_TIME_FEE = 29.99;
    private static final double MONTHLY_FEE = 59.99;
    private static final int PAY_CYCLE = 3;
    private int guests;
    /**
     Creates a new Family member and initializes zero guests.
     @param firstName the member's first name
     @param lastName the member's last name
     @param dateOfBirth the member's date of birth as a Date object
     @param expireDate the member's expiration date as a Date object
     @param loc the member's gym location as a String
     */
    public Family(String firstName, String lastName, Date dateOfBirth, Date expireDate, String loc) {
        super(firstName, lastName, dateOfBirth, expireDate, loc);
        guests = 0;
    }
    /**
     Increases the guest count by 1 to represent adding a guest.
     */
    public void addGuest() {
        guests++;
    }
    /**
     Decreases the guest count by 1 to represent removing a guest.
     */
    public void removeGuest() {
        guests--;
    }
    /**
     Obtains the number of guests.
     @return the number of guests this member has
     */
    public int getGuests() {
        return guests;
    }
    /**
     Obtains the membership fee of this new membership.
     @return the membership fee due for this year
     */
    @Override
    public double membershipFee() {
        return ONE_TIME_FEE + MONTHLY_FEE * PAY_CYCLE;
    }




}
