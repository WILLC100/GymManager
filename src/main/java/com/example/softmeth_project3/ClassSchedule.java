package com.example.softmeth_project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 Stores a schedule of fitness classes as inputted by the user through parameters in constructor.
 Dynamically sizes the classes list as new classes are added.
 Prints participants and fitness classes.

 @author William Chen
 @author Kalrav Pandit
 */
public class ClassSchedule {
    private static final int INDEX_ONE = 1, INDEX_TWO = 2;
    private static final int INDEX_THREE = 3, INDEX_FOUR = 4;
    private static final int CLASS_INTERVAL = 5;
    private FitnessClass [] classes;
    private int numClasses;
    /**
     Default constructor. Initializes a size of classes and a class list.
     */
    public ClassSchedule() {
        numClasses = 0;
        classes = new FitnessClass[CLASS_INTERVAL];
    }
    /**
     Adds a fitness class to the class schedule and rejects if already found.
     @param fc the fitness class to add to the schedule
     */
    public boolean add(FitnessClass fc) {
        for (int i = 0; i < classes.length; i++) {
            if (classes[i] != null) {
                if (classes[i].equals(fc)) {
                    return false;
                }
            }
        }
        classes[numClasses] = fc;
        numClasses++;
        if (numClasses >= CLASS_INTERVAL) {
            grow();
        }
        return true;
    }
    /**
     Expands the classes array by 5, the growth factor, when called.
     */
    private void grow() {
        FitnessClass[] tmp = classes;
        classes = new FitnessClass[tmp.length + CLASS_INTERVAL];
        for (int i = 0; i < tmp.length; i++) {
            classes[i] = tmp[i];
        }
    }
    /**
     Prints the participants of a fitness class and guests if any.
     @param fitnessClass the class to print out participants for
     */
    public String printParticipants(FitnessClass fitnessClass) {
        String res = "";
        if (fitnessClass.countCheckedInMembers() != 0) {
         for (int i = 0; i < fitnessClass.countCheckedInMembers(); i++) {
            if (fitnessClass.getMembersCheckedIn()[i] != null) {
               res += ("\t\t" +
                        fitnessClass.getMembersCheckedIn()[i].toString() + "\n");
            }
         }
    }
        if (fitnessClass.getGuestSize() > 0) {
            res += ("\t- Guests -\n");
            for (int i = 0; i < fitnessClass.getGuestSize(); i++) {
             if (fitnessClass.getGuestsCheckedIn()[i] != null) {
                res += "\t\t" + fitnessClass.getGuestsCheckedIn()[i].toString() + "\n";
             }
            }
        }
        return res;
    }
    /**
    Prints all fitness classes and participants if any.
    */
    public String printFitnessClasses() {
        if (classes[0] == null) {
            return ("Fitness class schedule is empty.");
        }
        String res = "-list of classes-\n";
        for (FitnessClass fitnessClass: classes) {
            if (fitnessClass != null) {
                res += (fitnessClass.getClassName().toUpperCase() + " - " +
                    fitnessClass.getInstructor().toUpperCase() + ", " + fitnessClass.getFormattedTime()
                    + ", " + fitnessClass.getLocation().toUpperCase() + "\n");
                printParticipants(fitnessClass);
            }
        }
        return res;
    }

    /**
     Loads the fitness classes from the corresponding text file into the class schedule.
     @return whether this was successful based on if the file was found
     */
    public String loadClasses() {
        File classList = new File("./classSchedule.txt");
        Scanner input = null;
        try {
            input = new Scanner(classList);
        } catch (FileNotFoundException e) {
            return "FAILURE";
        }
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] splitLine = line.split("\\s+");
            if (splitLine.length < INDEX_FOUR) {
                break;
            }
            String className = splitLine[0];
            String instructor = splitLine[INDEX_ONE];
            String timeframe = splitLine[INDEX_TWO];
            String location = splitLine[INDEX_THREE];
            FitnessClass fc = new FitnessClass(className.toUpperCase(), instructor, timeframe, location);
            add(fc);
        }
        input.close();
        return "SUCCESS";
    }
    /**
     Getter method for the list of all fitness classes.
     @return the list of classes
     */
    public FitnessClass[] getAllClasses() {
        return classes;
    }
    /**
     Getter method for the number of classes.
     @return the number of classes
     */
    public int getNumClasses() {
        return numClasses;
    }
}
