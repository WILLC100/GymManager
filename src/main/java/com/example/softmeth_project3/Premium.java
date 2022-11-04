package com.example.softmeth_project3;
/**
 Represents a premium membership with membership fee.
 @author William Chen
 @author Kalrav Pandit
 */
public class Premium extends Family {
    private static final double SUBSCRIPTION_MONTHLY_FEE = 59.99;
    private static final int PAY_CYCLE = 11;
    /**
     Premium constructor given a set of parameters.
     @param firstName the member's first name
     @param lastName the member's last name
     @param dateOfBirth the member's date of birth as a Date object
     @param expireDate the member's expiration date as a Date object
     @param loc the member's gym location as a String
     */
    public Premium(String firstName, String lastName, Date dateOfBirth, Date expireDate, String loc) {
        super(firstName, lastName, dateOfBirth, expireDate, loc);
    }
    @Override
    public double membershipFee() {
        return SUBSCRIPTION_MONTHLY_FEE * PAY_CYCLE;
    }




}
