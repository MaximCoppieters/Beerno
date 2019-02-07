package cz.mendelu.busItWeek.library.beacons;

import cz.mendelu.busItWeek.library.BeaconTask;

/**
 * The abstract class for defining a single beacon to be detected.
 * The beacon is represented by two numbers:
 *  - major - an unsigned short integer, i.e., an integer ranging from 1 to 65535
 *  - minor - also an unsigned short integer, like the major number.
 */
public abstract class BeaconDefinition {

    // the minor number of the beacon
    protected int minorNumber;
    // the major number of the beacon
    protected int majorNumber;

    protected boolean codeExecuted = false;

    /**
     * The initialization  of the beacon.
     * @param task the BeaconTask containing info about beacon
     * @throws IllegalArgumentException
     */
    public BeaconDefinition(BeaconTask task) throws IllegalArgumentException{

        if (task.getMinor() < 1 || task.getMinor() > 65535){
            throw new IllegalArgumentException("Minor number of of range!");
        }

        if (task.getMajor() < 1 || task.getMajor() > 65535){
            throw new IllegalArgumentException("Major number of of range!");
        }

        this.minorNumber = task.getMinor();
        this.majorNumber = task.getMajor();
    }

    /**
     * Returns the beacon minor number
     * @return beacon minor number
     */
    public int getMinorNumber() {
        return minorNumber;
    }

    /**
     * Returns the beacon major number
     * @return beacon major number
     */
    public int getMajorNumber() {
        return majorNumber;
    }

    /**
     * This method is exeuted when the beacon is found by the beacon manager
     */
    public abstract void execute();
}