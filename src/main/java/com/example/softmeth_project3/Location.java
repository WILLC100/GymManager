package com.example.softmeth_project3;
/**
 Represents a location with ZIP Codes and counties.
 Includes search functionality along with getters.
 @author William Chen
 @author Kalrav Pandit
 */
public enum Location {
    BRIDGEWATER ("08807", "SOMERSET"), EDISON ("08837", "MIDDLESEX"),
    FRANKLIN ("08873", "SOMERSET"), PISCATAWAY ("08854", "MIDDLESEX"),
    SOMERVILLE ("08876", "SOMERSET"), NOT_SPECIFIED ("99999", "NO_COUNTY");
    private final String zip;
    private static final int NOT_FOUND = -1;
    private static final int FOUND = 1;

    private final String county;
    /**
     * Constructor for a location based on zip code and county.
     * @param zipCode the ZIP Code of the location
     * @param countyName the county of the location
     */
    Location(String zipCode, String countyName) {
        this.zip = zipCode;
        this.county = countyName;
    }
    /**
     * Gets the zip code of this location.
     * @return the zip code, formatted as a String
     */
    public String getZip() {
        return zip;
    }
    /**
     * Gets the county of this location.
     * @return the county, formatted as a String
     */
    public String getCounty() {
        return county;
    }
    /**
     * Compares the county of this location against another location, and compares by ZIP Code if counties are the same.
     * @param loc2 the other location to compare
     * @return the result of the comparison; > 1 if greater, < 1 if smaller, and 0 if same
     */
    public int compareByCounty(Location loc2) {
        if (this.getCounty().compareTo(loc2.getCounty()) != 0) return this.getCounty().compareTo(loc2.getCounty());
        return (this.getZip().compareTo(loc2.getZip()));
    }
    /**
     * Formats the location in the form "NAME, ZIP, COUNTY"
     * @return the location formatted as "NAME, ZIP, COUNTY"
     */
    public String formattedLocation() {
        return this.name() + ", " + this.zip + ", " + this.county;
    }
    /**
     * Formats the location in the form "ZIP, COUNTY"
     * @return the location formatted as "ZIP, COUNTY"
     */
    public String zipAndCounty() {
        return this.zip + ", " + this.county;
    }
    /**
     * Finds the location (if it exists) in the set of values.
     * @return 1 if found successfully and -1 if not found
     */
    public static int find(String val) {
        if (val.equals("NOT_SPECIFIED")) return NOT_FOUND;
        for (Location l: Location.values()) {
            if (l.name().equalsIgnoreCase(val)) return FOUND;
        }
        return NOT_FOUND;
    }
}
