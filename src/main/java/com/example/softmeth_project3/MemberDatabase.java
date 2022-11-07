package com.example.softmeth_project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 Stores a growing list of members along with sort functionality based on criteria.
 Includes constructors, member search, add, and remove functionalities.
 @author William Chen
 @author Kalrav Pandit
 */
public class MemberDatabase {
    private Member [] mlist;
    private int size;
    private static final int NOT_FOUND = -1;
    private static final int INVALID = Integer.MIN_VALUE;
    private static final int GROWTH_FACTOR = 4;
    private static final int INDEX_ONE = 1, INDEX_TWO = 2;
    private static final int INDEX_THREE = 3, INDEX_FOUR = 4;
    /**
     Default constructor.
     Initializes a member list of four members with blank members in each entry.
     */
    public MemberDatabase() {
        mlist = new Member[GROWTH_FACTOR];
        for (Member newMember: mlist) {
            newMember = new Member();
        }
        size = 0;
    }
    /**
     Finds a member in the member database list; a found member has the same first name, last name, and date of birth.
     @param member the member to search for
     @return the index of the member in database, or -1 if not found
     */
    private int find(Member member) {
        for (int i = 0; i < mlist.length; i++) {
            if (mlist[i] != null) {
                String fname = mlist[i].getFirstName();
                String lname = mlist[i].getLastName();
                Date dob = mlist[i].getDob();
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
     Allows other methods to search for members in the database.
     @param member the member to search for
     @return the index of the member in database, or -1 if not found
     */
    public int checkForMember(Member member) {
        return find(member);
    }
    /**
     Gets the expiration status of a member.
     @param member the member to get expiry date for
     @return some value greater than 0 if expired or less if not; 0 if it expires today
     */
    public int getExpiryOfMember(Member member) {
        int res = find(member);
        if (res == NOT_FOUND) return INVALID;
        Date expireDate = mlist[res].getExpire();
        return expireDate.compareTo(new Date());
    }
    /**
     Expands the mlist array by 4 when called.
     */
    private void grow() {
         Member[] tmp = mlist;
         mlist = new Member[tmp.length + GROWTH_FACTOR];
         for (int i = 0; i < tmp.length; i++) {
             mlist[i] = tmp[i];
         }
    }
    /**
     Adds a member to the database if not already in the database.
     @param member the member to add
     @return true if successful or false if member is in database already
     */
    public boolean add(Member member) {
        for (int i = 0; i < mlist.length; i++) {
            if (mlist[i] != null) {
                if (mlist[i].equals(member)) {
                    return false;
                }
            }
        }
        if (mlist.length == size) {
            grow();
        }
        mlist[size] = member;
        size++;
        return true;
    }
    /**
     Removes a member to the database if already in the database.
     @param member the member to remove
     @return true if successful or false if member is not in database already
     */
    public boolean remove(Member member) {
        for (int i = 0; i < size; i++) {
            String fname = mlist[i].getFirstName();
            String lname = mlist[i].getLastName();
            Date dob = mlist[i].getDob();
            if (fname.equalsIgnoreCase(member.getFirstName())
                    && lname.equalsIgnoreCase(member.getLastName())
                    && (dob.compareTo(member.getDob()) == 0)) {
                mlist[i] = new Member();
                for (int j = i; j < mlist.length - 1; j++) {
                    mlist[j] = mlist[j+1];
                }
                mlist[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }
    /**
     Checks if this database is empty (contains no members).
     @return true if empty or false if not
     */
    public boolean isEmpty() {
        int nulls = 0;
        for (Member m: mlist) {
            if (m == null) nulls++;
        }
        return (nulls == mlist.length);
    }
    /**
     Sorts this list by names of members.
     @return the list of sorted members
     */
    private Member[] sortByName() {
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size; j++) {
                if (mlist[j].compareTo(mlist[min]) < 0) min = j;
            }
            Member tmp = mlist[min];
            mlist[min] = mlist[i];
            mlist[i] = tmp;
        }
        return mlist;
    }
    /**
     Sorts this list by expiration date.
     @return the list of sorted members
     */
    private Member[] sortByExpiry() {
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size; j++) {
                if (mlist[j].getExpire().compareTo(mlist[min].getExpire()) < 0) min = j;
            }
            Member tmp = mlist[min];
            mlist[min] = mlist[i];
            mlist[i] = tmp;
        }
        return mlist;
    }
    /**
     Sorts this list by county and zip code.
     @return the list of sorted members
     */
    private Member[] sortByLocation() {
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size; j++) {
                if (mlist[j].getLocation().compareByCounty(mlist[min].getLocation()) < 0) min = j;
            }
            Member tmp = mlist[min];
            mlist[min] = mlist[i];
            mlist[i] = tmp;
        }
        return mlist;
    }
    /**
     Prints the member list as is.
     */
    public String print() {
        if (size == 0) {
            return "Member database is empty!";
        }
        String res = "-list of members-\n";
        for (int i = 0; i < size; i++) {
            res += (mlist[i].toString() + "\n");
        }
        return res;
    }
    /**
     Prints the member list sorted by county and zipcode.
     */
    public String printByCounty() {
        if (size == 0) {
            return "Member database is empty!";
        }
        String res = "-list of members sorted by county and zipcode-\n";
        Member[] sorted = sortByLocation();
        for (int i = 0; i < size; i++) {
            res += (sorted[i].toString() + "\n");
        }
        return res;
    }
    /**
     Prints the member list sorted by expiration date.
     */
    public String printByExpirationDate() {
        if (size == 0) {
            return "Member database is empty!";
        }
        String res = "-list of members sorted by membership expiration date-\n";
        Member[] sorted = sortByExpiry();
        for (int i = 0; i < size; i++) {
            res += (sorted[i].toString() + "\n");
        }
        return res;
    }
    /**
     Prints the member list sorted by last name then first name.
     */
    public String printByName() {
        if (size == 0) {
            return "Member database is empty!";
        }
        String res = "-list of members sorted by last name, and first name-\n";
        Member[] sorted = sortByName();
        for (int i = 0; i < size; i++) {
            res += (sorted[i].toString() + "\n");
        }
        return res;
    }
    /**
     Prints the member list sorted by membership fees (least to most expensive).
     */
    public String printByFees() {
        if (size == 0) {
            return "Member database is empty!";
        }
        String res = "-list of members with membership fees-\n";
        for (int i = 0; i < size; i++) {
            res += (mlist[i].toString() + ", ");
            if (mlist[i] instanceof Premium) {
                Premium pm = (Premium) mlist[i];
                res += ("Membership fee: $" + pm.membershipFee() + "\n");

            }
            else if (mlist[i] instanceof Family) {
                Family pm = (Family) mlist[i];
                res += ("Membership fee: $" + pm.membershipFee() + "\n");
            }
            else {
                res += ("Membership fee: $" + mlist[i].membershipFee() + "\n");
            }
        }
        return res;
    }
    /**
     Loads the historical members from the corresponding text file into the member database.
     @return whether this was successful based on if the file was found
     */
    public String loadMembers() {
        File memberList = new File("./memberList.txt");
        Scanner input;
        try {
            input = new Scanner(memberList);
        }
        catch (FileNotFoundException e) {
            return "FAILURE";
        }
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] splitLine = line.split("\\s+");
            String fname = splitLine[0];
            String lname = splitLine[INDEX_ONE];
            String dob = splitLine[INDEX_TWO];
            String expiration = splitLine[INDEX_THREE];
            String location = splitLine[INDEX_FOUR];
            Member newMember = new Member(fname, lname, new Date(dob), new Date(expiration), location);
            add(newMember);
        }
        return "SUCCESS";
    }
    /**
     Returns the list of members.
     @return the member list
     */
    public Member[] getList() {
        return mlist;
    }
}