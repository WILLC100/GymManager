package com.example.softmeth_project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert.AlertType;

/**
 Reads from standard input and tests all classes and actions.
 Allows adding, removing, dropping classes, and checking into classes.
 Allows printing schedules and members based on sort criteria.
 @author William Chen
 @author Kalrav Pandit
 */
public class GymManagerController {

    private static final int INDEX_ONE = 1, INDEX_TWO = 2;
    private static final int INDEX_THREE = 3, INDEX_FOUR = 4;
    private static final int INDEX_FIVE = 5;
    private static final int NOT_FOUND = -1;
    private static final int MAJORITY_AGE = 18;
    private static final int INDEX_SIX = 6;
    private static final int QUARTERLY = 3, YEARLY = 12;

    @FXML
    private TextField addFirstName;
    @FXML
    private TextField removeFirstName;
    @FXML
    private TextField classFirstName;
    @FXML
    private TextField addLastName;
    @FXML
    private TextField classLastName;
    @FXML
    private TextField removeLastName;
    @FXML
    private TextField addLocation;
    @FXML
    private DatePicker addDOB;
    @FXML
    private DatePicker classDOB;
    @FXML
    private DatePicker removeDOB;
    @FXML
    private RadioButton standard;
    @FXML
    private RadioButton premium;
    @FXML
    private RadioButton family;
    @FXML
    private TextField classInstructor;
    @FXML
    private TextField classLocation;
    @FXML
    private TextField className;
    @FXML
    private TextArea messageArea;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button classCheckIn;
    @FXML
    private Button classDrop;
    @FXML
    private ToggleGroup tg;
    @FXML
    private ToggleGroup classToggle;
    private MemberDatabase members = new MemberDatabase();
    private ClassSchedule scheduleClasses = new ClassSchedule();
    /**
     Runs the input session and reads from standard input until the Q command is entered.
     */
    @FXML
    public void handleAddMember(ActionEvent event) {
        String result;
        String[] split = new String[5];
        try {
            if ((addFirstName.getText().isEmpty() || addLastName.getText().isEmpty())
            || addLocation.getText().isEmpty()) {
                messageArea.appendText("Input invalid. Check for empty text fields!\n");
                return;
            }
            split[INDEX_ONE] = addFirstName.getText();
            split[INDEX_TWO] = addLastName.getText();
            split[INDEX_FOUR]  = addLocation.getText();
            LocalDate dob = addDOB.getConverter().fromString(addDOB.getEditor().getText());
            String[] dobSplit = dob.toString().split("-");
            split[INDEX_THREE]  = dobSplit[INDEX_ONE] + "/" + dobSplit[INDEX_TWO] + "/" + dobSplit[0];
            RadioButton selectedRadioButton = (RadioButton) tg.getSelectedToggle();
            String membershipType = selectedRadioButton.getText();
            if (membershipType.equals("Standard")) {
                split[0] = "A";
            }
            else if (membershipType.equals("Family")) {
                split[0] = "AF";
            }
            else if (membershipType.equals("Premium")) {
                split[0] = "AP";
            }
        }
        catch (NumberFormatException e) {
            messageArea.appendText("Number format invalid!\n");
            return;
        }
        catch (NullPointerException e) {
            messageArea.appendText("Input invalid. Check for empty fields!\n");
            return;
        }
        catch (Exception e) {
            messageArea.appendText("Input invalid. Check for empty fields!\n");
            return;
        }
        result = runAddCommand(split, members);
        messageArea.appendText(result + "\n");
    }
    @FXML
    public void handleRemoveMember(ActionEvent event) {
        String result;
        String[] split;
        try {
            if ((removeFirstName.getText().isEmpty() || removeLastName.getText().isEmpty())) {
                result = "Input invalid. Check for empty text fields!\n";
                messageArea.appendText(result);
                return;
            }
            split = new String[INDEX_FOUR];
            split[0] = "R";
            split[INDEX_ONE] = removeFirstName.getText();
            split[INDEX_TWO] = removeLastName.getText();
            LocalDate dob = removeDOB.getConverter().fromString(removeDOB.getEditor().getText());
            String[] dobSplit = dob.toString().split("-");
            split[INDEX_THREE]  = dobSplit[INDEX_ONE] + "/" + dobSplit[INDEX_TWO] + "/" + dobSplit[0];
        }
        catch (NumberFormatException e) {
            messageArea.appendText("Number format invalid!\n");
            return;
        }
        catch (NullPointerException e) {
            result = "Input invalid. Check for empty fields!\n";
            messageArea.appendText(result);
            return;
        }
        catch (Exception e) {
            result = "Input invalid. Check for empty fields!\n";
            messageArea.appendText(result);
            return;
        }
        result = runRemoveCommand(split, members);
        messageArea.appendText(result + "\n");
    }
    @FXML
    public void handleClassCheckIn(ActionEvent event) {
        String result;
        String[] split;
        try {
            split = new String[7];
            split[INDEX_ONE] = className.getText();
            split[INDEX_TWO] = classInstructor.getText();
            split[INDEX_THREE] = classLocation.getText();
            split[INDEX_FOUR] = classFirstName.getText();
            split[INDEX_FIVE] = classLastName.getText();
            LocalDate dob = classDOB.getConverter().fromString(classDOB.getEditor().getText());
            String[] dobSplit = dob.toString().split("-");
            split[INDEX_SIX]  = dobSplit[INDEX_ONE] + "/" + dobSplit[INDEX_TWO] + "/" + dobSplit[0];
            RadioButton selectedRadioButton = (RadioButton) classToggle.getSelectedToggle();
            String action = selectedRadioButton.getText();
            if (action.equals("Self")) {
                split[0] = "C";
            }
            else if (action.equals("Guest")) {
                split[0] = "CG";
            }
            for (String s : split) {
                if (s.isEmpty()) {
                    messageArea.appendText("Input invalid. Check for empty fields!\n");
                    return;
                }
            }
        }
        catch (NumberFormatException e) {
            messageArea.appendText("Number format invalid!\n");
            return;
        }
        catch (Exception e) {
            result = "Input invalid! Check for empty fields!\n";
            messageArea.appendText(result);
            return;
        }
        result = checkIntoClass(split, members, scheduleClasses);
        messageArea.appendText(result + "\n");
    }
    @FXML
    public void handleClassDrop(ActionEvent event) {
        String result;
        String[] split;
        try {
            split = new String[7];
            split[INDEX_ONE] = className.getText();
            split[INDEX_TWO] = classInstructor.getText();
            split[INDEX_THREE] = classLocation.getText();
            split[INDEX_FOUR] = classFirstName.getText();
            split[INDEX_FIVE] = classLastName.getText();
            LocalDate dob = classDOB.getConverter().fromString(classDOB.getEditor().getText());
            String[] dobSplit = dob.toString().split("-");
            split[INDEX_SIX]  = dobSplit[INDEX_ONE] + "/" + dobSplit[INDEX_TWO] + "/" + dobSplit[0];
            RadioButton selectedRadioButton = (RadioButton) classToggle.getSelectedToggle();
            String action = selectedRadioButton.getText();
            if (action.equals("Self")) {
                split[0] = "C";
            }
            else if (action.equals("Guest")) {
                split[0] = "CG";
            }
            for (String s : split) {
                if (s.isEmpty()) {
                    messageArea.appendText("Input invalid. Check for empty fields!\n");
                    return;
                }
            }
        }
        catch (NumberFormatException e) {
            messageArea.appendText("Number format invalid!\n");
            return;
        }
        catch (Exception e) {
            result = "Input invalid! Check for empty fields!\n";
            messageArea.appendText(result);
            return;
        }
        result = dropClass(split, members, scheduleClasses.getAllClasses());
        messageArea.appendText(result + "\n");
    }
    /**
     Adds a member to the member database given a string from standard input.
     Rejects if:
     - date is not valid,
     - date of birth is today or in the future,
     - member is < 18 years of age,
     - member is already in the database,
     - or location does not exist.
     @param split The split input line read from standard input.
     */
    private String runAddCommand(String[] split, MemberDatabase mb) {
        String firstName = split[INDEX_ONE], location = split[INDEX_FOUR];
        String lastName = split[INDEX_TWO];
        Date dob = new Date(split[INDEX_THREE]), current = new Date();
        Date eighteenYearsAgo = new Date();
        eighteenYearsAgo.setYear(eighteenYearsAgo.getYear() - MAJORITY_AGE);
        if (!dob.isValid()) {
            return ("DOB" + " " + dob.formattedDate() + ": " +
                    "invalid calendar date!");
        }
        else if (dob.compareTo(current) > 0) {
            return ("DOB" + " " + dob.formattedDate() + ": " +
                    "cannot be today or a future date!");
        }
        else if (dob.compareTo(eighteenYearsAgo) > 0) {
            return ("DOB" + " " + dob.formattedDate() + ": " +
                    "must be 18 or older to join!");
        }
        else if (Location.find(location) == NOT_FOUND) {
            return (location + ": invalid location!");
        }
        Date expiry = new Date(QUARTERLY);
        Member member = (new Member(firstName, lastName, dob, expiry, location));
        if (mb.checkForMember(member) == NOT_FOUND) {
            if (split[0].equals("AF")) {
                mb.add(new Family(firstName, lastName, dob, new Date(QUARTERLY), location));
            }
            else if (split[0].equals("AP")) {
                mb.add(new Premium(firstName, lastName, dob, new Date(YEARLY), location));
            }
            else {
                mb.add(member);
            }
                return (firstName + " " + lastName + " added.");
        }
        return (firstName + " " + lastName + " is already in the database.");
    }
    /**
     Removes a member from the member database given an input string.
     Rejects if member does not exist in the database.
     @param split The split input line read from standard input.
     */
    private String runRemoveCommand(String[] split, MemberDatabase mb) {
        String fname = split[INDEX_ONE], lname = split[INDEX_TWO];
        Date dob = new Date(split[INDEX_THREE]);
        boolean res = mb.remove(new Member(fname, lname, dob,
                null, "NOT_SPECIFIED"));
        if (res) {
            return (fname + " " + lname + " removed.");
        }
        else {
            return (fname + " " + lname
                    + " is not in the database.");
        }
    }

    /**
     Checks if a class session exists in the array of class sessions.
     Determines if instructor exists, location is valid, and if a class by an instructor exists.
     @param name the name of the class to search for.
     @param loc the location of the class to search for.
     @param instructor the instructor of the class to search for.
     @param classSessions the array of class sessions to search for the given class.
     @return the index of the class session in the array, or -1 if not found.
     */
    private int checkForClass(String name, String loc, String instructor, FitnessClass[] classSessions) {
        instructor = instructor.toUpperCase();
        int nameExists = 0, locationExists = 0, instructorExists = 0;
        for (int i = 0; i < classSessions.length; i++) {
            if (classSessions[i] != null) {
                if (classSessions[i].getClassName().equalsIgnoreCase(name)
                        && classSessions[i].getLocation().equalsIgnoreCase(loc)
                        && classSessions[i].getInstructor().equalsIgnoreCase(instructor)) {
                    return i;
                }
                if (classSessions[i].getLocation().equalsIgnoreCase(loc)) {
                    locationExists++;
                }
                if (classSessions[i].getInstructor().equalsIgnoreCase(instructor)) {
                    instructorExists++;
                }
                if (classSessions[i].getClassName().equalsIgnoreCase(name)) {
                    nameExists++;
                }
            }
        }
        if (instructorExists == 0) {
            messageArea.appendText(instructor.toUpperCase() + " - instructor does not exist.");
        }
        else if (locationExists == 0) {
            messageArea.appendText(loc + " - invalid location.");
        }
        else if (instructorExists != 0 && nameExists != 0) {
            messageArea.appendText(name.toUpperCase() + " by "
                    + instructor.toUpperCase() + " does not exist at " + loc + ".");
        }
        else {
            messageArea.appendText(name + " - " + " class does not exist.");
        }
        return NOT_FOUND;
    }


    /**
     Drops a member from a class, given information of member and class from standard input.
     Rejects if member did not check in, member is not in the database, or birth date is invalid.
     @param split The split input line read from standard input.
     @param mb The member database to reference and find a member
     @param classSessions the class session database
     */
    private String dropClass(String[] split, MemberDatabase mb, FitnessClass[] classSessions) {
        if (split[0].equals("DG")) {
            return dropGuest(split, mb, classSessions);
        }
        String classSession = split[INDEX_ONE], location = split[INDEX_THREE],
                instructor = split[INDEX_TWO], dob = split[INDEX_SIX], fname = split[INDEX_FOUR], lname = split[INDEX_FIVE];
        Member testMember = new Member(fname, lname, new Date(dob), null, "NOT_SPECIFIED");
        int classIndex = checkForClass(classSession, location, instructor, classSessions);
        if (classIndex == NOT_FOUND) {
            return "Class does not exist.";
        }
        else if (!new Date(dob).isValid()) {
            return ("DOB" + " " + dob + ": invalid calendar date!");
        }
        int foundMember = mb.checkForMember(testMember);
        if (foundMember == NOT_FOUND) {
            return (fname + " " + lname + " " + dob +
                    " is not in the database.");
        }
        Member toDrop = mb.getList()[foundMember];
        boolean res = classSessions[classIndex].drop(toDrop);
        if (res) {
            return (fname + " " + lname + " done with the class.");
        }
        else {
            return (fname + " " + lname + " did not check in.");
        }
    }
    /**
     Drops guest from a fitness class, given the member that the guest was under.
     Rejects if birthdate is invalid, member is not in database, member did not check in a guest,
     or member cannot have guests.
     @param split The split input line read from standard input.
     @param mb The member database to reference and find a member
     @param classSessions the class session database
     */
    private String dropGuest(String[] split, MemberDatabase mb, FitnessClass[] classSessions) {
        String classSession = split[INDEX_ONE], location = split[INDEX_THREE],
                instructor = split[INDEX_TWO], dob = split[INDEX_SIX], fname = split[INDEX_FOUR], lname = split[INDEX_FIVE];
        Family testMember = new Family(fname, lname, new Date(dob),
                null, "NOT_SPECIFIED");
        int classIndex = checkForClass(classSession, location, instructor, classSessions);
        if (classIndex == NOT_FOUND) {
            return "";
        }
        else if (!new Date(dob).isValid()) {
            return ("DOB" + " " + dob + ": invalid calendar date!");
        }
        int foundMember = mb.checkForMember(testMember);
        if (foundMember == NOT_FOUND) {
            return (fname + " " + lname + " " + dob +
                    " is not in the database.");
        }
        Family toDrop = null;
        if (mb.getList()[foundMember] instanceof Family) {
            toDrop = (Family) mb.getList()[foundMember];
        }
        else {
            return ("Standard member - no guests.");
        }
        boolean res = classSessions[classIndex].dropGuest(toDrop);
        if (res) {
            return (fname + " " + lname + " Guest done with the class.");
        }
        else {
            return (fname + " " + lname + " did not check in.");
        }
    }
    /**
     Checks a member into a fitness class, given information of the member and class based on standard input.
     Rejects if:
     - date is not valid,
     - member is not in database,
     - class does not exist,
     - membership is expired,
     - member checking into the wrong location,
     - class conflict exists,
     - or already checked in.
     @param split The split input line of information read from standard input.
     @param mb The member database to reference and find a member
     @param scheduleClasses the class session schedule
     */
    private String checkIntoClass(String[] split, MemberDatabase mb, ClassSchedule scheduleClasses) {
        if (split[0].equals("CG")) {
            return checkInGuest(split, scheduleClasses, mb);
        }
        String classSession = split[INDEX_ONE], location = split[INDEX_THREE], instructor = split[INDEX_TWO],
                dob = split[INDEX_SIX], fname = split[INDEX_FOUR], lname = split[INDEX_FIVE];
        FitnessClass[] classSessions = scheduleClasses.getAllClasses();
        Member testMember = new Member(fname, lname, new Date(dob), null, "NOT_SPECIFIED");
        if (!new Date(dob).isValid()) {
            return ("DOB" + " " + dob + ": invalid calendar date!");
        }
        else if (mb.checkForMember(testMember) == NOT_FOUND) {
            return (fname + " " + lname + " " + dob + " is not in the database.");
        }
        else if (checkForClass(classSession, location, instructor, classSessions) == NOT_FOUND) {
            return "";
        }
        else if (mb.getExpiryOfMember(testMember) < 0) {
            return (fname + " " + lname + " " + dob + " membership expired.");
        }
        else if (!(mb.getList()[mb.checkForMember(testMember)] instanceof Family) &&
                !mb.getList()[mb.checkForMember(testMember)].getLocation().name().equals(location.toUpperCase())) {
            return (fname + " " + lname + " checking in "
                    + Location.valueOf(location.toUpperCase()).formattedLocation() +
                    ": standard membership location restriction.");
        }
        else {
            int index = checkForClass(classSession, location, instructor, classSessions);
            for (int i = 0; i < scheduleClasses.getNumClasses(); i++) {
                if (index != i && classSessions[i].getFormattedTime().equals(classSessions[index].getFormattedTime())
                        && classSessions[i].findMember(testMember) != NOT_FOUND) {
                            return ("Time conflict - " + classSessions[index].toString()
                                    + ", " + Location.valueOf(location.toUpperCase()).zipAndCounty());
                }
            }
            return findAndCheckMember(split, scheduleClasses, mb);
        }
    }
    /**
     Prints a member from a list of members or classes given a situation read from standard input.
     @param situation The requested order of members or classes
     @param mb The member database to reference and find a member
     */
    private String printList(String situation, MemberDatabase mb) {
        if (mb.isEmpty()) {
            return ("Member database is empty!\n");
        }
        String res = "";
        switch (situation) {
            case "P":
                res += ("\n-list of members-\n");
                res += mb.print();
            break;
            case "PC":
                res += ("\n-list of members sorted by county and zipcode-\n");
                res += mb.printByCounty();
            break;
            case "PN":
                res += ("\n-list of members sorted by last name, and first name-\n");
                res += mb.printByName();
            break;
            case "PD":
                res += ("\n-list of members sorted by membership expiration date-\n");
                res += mb.printByExpirationDate();
            break;
            case "PF":
                res += ("\n-list of members with membership fees-\n");
                res += mb.printByFees();
                break;
        }
        res += ("-end of list- \n");
        return res;
    }
    @FXML
    public void handlePrint(ActionEvent event) {
        messageArea.appendText(members.print() + "\n");
    }
    @FXML
    public void handlePrintByDates(ActionEvent event) {
        messageArea.appendText(members.printByExpirationDate() + "\n");
    }
    @FXML
    public void handlePrintByFee(ActionEvent event) {
        messageArea.appendText(members.printByFees() + "\n");
    }
    @FXML
    public void handlePrintByName(ActionEvent event) {
        messageArea.appendText(members.printByName() + "\n");
    }
    @FXML
    public void handlePrintByCounty(ActionEvent event) {
        messageArea.appendText(members.printByCounty() + "\n");
    }
    @FXML
    public void handleFitnessClasses(ActionEvent event) {
        messageArea.appendText(scheduleClasses.printFitnessClasses() + "\n");
    }
    @FXML
    public void handleLoadFitnessClasses(ActionEvent event) {
        String res;
        try {
            res = scheduleClasses.loadClasses();
        }
        catch (Exception e) {
            messageArea.appendText("Input file invalid!\n");
            return;
        }
        if (res.equals("FAILURE")) {
            messageArea.appendText("Input file invalid!\n");
            return;
        }
        messageArea.appendText("-fitness classes loaded-\n");
        messageArea.appendText(scheduleClasses.printFitnessClasses() + "\n");
    }
    @FXML
    public void handleLoadMembers(ActionEvent event) {
        String res;
        try {
            res = members.loadMembers();
        }
        catch (Exception e) {
            messageArea.appendText("Input file invalid!\n");
            return;
        }
        if (res.equals("FAILURE")) {
            messageArea.appendText("Input file invalid!\n");
            return;
        }
        messageArea.appendText("-member list loaded-\n");
        messageArea.appendText(members.print() + "\n");
    }

        /**
         Checks in a guest given the member they are under and the class they want to check in to.
         Rejects if:
         - member cannot have guests due to too many guests,
         - member is a standard member and cannot have guests, or
         - guest is checking in at a different location than membership,
         @param split The split input line of information read from standard input.
         @param mb The member database to reference and find a member
         @param scheduleClasses the class session schedule
         */
    private String checkInGuest(String[] split, ClassSchedule scheduleClasses, MemberDatabase mb) {
        String classSession = split[INDEX_ONE], instructor = split[INDEX_TWO],
                location = split[INDEX_THREE], dob = split[INDEX_SIX],
                fname = split[INDEX_FOUR], lname = split[INDEX_FIVE];
        FitnessClass[] classSessions = scheduleClasses.getAllClasses();
        Member testMember = new Member(fname, lname, new Date(dob), null, "NOT_SPECIFIED");
        FitnessClass classToCheck = classSessions[checkForClass(classSession, location, instructor, classSessions)];
        int toAdd = mb.checkForMember(testMember);
        boolean res = false;
        Member toCheckIn = mb.getList()[toAdd];
        double fee = toCheckIn.membershipFee();
        if (toCheckIn instanceof Premium) {
            if (!toCheckIn.getLocation().name().equalsIgnoreCase(location)) {
                return (fname + " " + lname + " Guest checking in " + Location.valueOf(location.toUpperCase()).formattedLocation()
                        + " - guest location restriction.");
            }
            res = classToCheck.checkInGuest((Premium) toCheckIn);

        }
        else if (toCheckIn instanceof Family) {
            if (!toCheckIn.getLocation().name().equalsIgnoreCase(location)) {
                return (fname + " " + lname + " Guest checking in " + Location.valueOf(location.toUpperCase()).formattedLocation() +
                        " - guest location restriction.");
            }
            res = classToCheck.checkInGuest((Family) toCheckIn);
        }
        else {
            return ("Standard membership - guest check-in is not allowed.");
        }
        if (!res) {
            return (fname + " " + lname + " ran out of guest pass.");
        }
        else {
            return (fname + " " + lname + " (Guest) checked in " + classToCheck.toString() + ".");
        }
    }
    /**
     Generates a test member to check in a member provided conditions are valid.
     @param split the split-up string of user input to generate test member
     @param scheduleClasses the schedule of fitness classes
     @param mb The member database to reference and find a member
     */
    private String findAndCheckMember(String[] split, ClassSchedule scheduleClasses, MemberDatabase mb) {
        String classSession = split[INDEX_ONE], instructor = split[INDEX_TWO],
                location = split[INDEX_THREE], dob = split[INDEX_SIX],
                fname = split[INDEX_FOUR], lname = split[INDEX_FIVE];
        FitnessClass[] classSessions = scheduleClasses.getAllClasses();
        Member testMember = new Member(fname, lname, new Date(dob),
                null, "NOT_SPECIFIED");
        FitnessClass classToCheck = classSessions[checkForClass(classSession,
                location, instructor, classSessions)];
        int toAdd = mb.checkForMember(testMember);
        Member toCheckIn = mb.getList()[toAdd];
        boolean res = classToCheck.checkIn(toCheckIn);
        if (res) {
            return (fname + " " + lname + " checked in" +
                    " " + classToCheck.toString() + ".");
        }
        else {
            return (fname + " " + lname + " already checked in.");
        }
    }
}
