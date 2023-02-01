/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.Locale;
import java.util.Random;

/**
 * This class implements most methods from the Vehicle interface in order to 
 * provide functionality to its subclasses that represent instantiable Vehicle objects.
 * @author Paul Schmidt
 * @version 1.0
 */
public abstract class AbstractVehicle implements Vehicle {
    /**
     * Static Random variable to be inherited and used by all vehicle subclasses.
     */
    protected static final Random MY_RANDOM = new Random();
    
    /**
     * Initial X-Coordinate of the Vehicle.
     */
    private final int myInitialX;
    /**
     * Initial Y-Coordinate of the Vehicle.
     */
    private final int myInitialY;
    /**
     * Initial Direction the Vehicle is facing.
     */
    private final Direction myInitialDirection;
    /**
     * Current Direction the Vehicle is facing.
     */
    private Direction myDirection;
    /**
     * Current X-Coordinate of the Vehicle.
     */
    private int myCurrentX;
    /**
     * Current Y-Coordinate of the Vehicle.
     */
    private int myCurrentY;
    /**
     * How many rounds this Vehicle stays dead.
     */
    private final int myDefaultDeathValue;
    /**
     * How many rounds this Vehicle has been dead.
     */
    private int myCurrentDeathValue;
    /**
     * Image file name for the Default state of the Vehicle.
     */
    private final String myDefaultImageFileName;
    /**
     * Image file name for the Dead state of the Vehicle.
     */
    private final String myDeadImageFileName;
    
    /**
     * Constructor for the AbstractVehicle Class.
     * @param theX the initial X of the Vehicle
     * @param theY the initial Y of the Vehicle
     * @param theDirection the initial Direction of the Vehicle
     * @param theDefaultImage the FileName for the Alive Image of the Vehicle
     * @param theDeadImage the FileNAme for the Dead Image of the Vehicle
     * @param theDeathValue the Death Value used to determine how long this Vehicle stays dead
     */
    protected AbstractVehicle(final int theX, final int theY, final Direction theDirection, 
            final String theDefaultImage, final String theDeadImage, final int theDeathValue) {
        myInitialX = theX;
        myCurrentX = myInitialX;
        
        myInitialY = theY;
        myCurrentY = myInitialY;
        
        myInitialDirection = theDirection;
        myDirection = myInitialDirection;
        
        myDefaultImageFileName = theDefaultImage;
        myDeadImageFileName = theDeadImage;
        
        myDefaultDeathValue = theDeathValue;
        myCurrentDeathValue = theDeathValue;
        
    }
    /**
     * This method uses a boolean value to determine whether this Vehicle should be considered
     * dead or alive.
     * true = vehicle should be dead.
     * false = vehicle should be alive and we should reset its current death counter to its
     * default value and face it in a random Direction.
     * @param theDeadState boolean value to pass in to determine whether this Vehicle should
     * be dead or alive.
     */
    protected final void makeDead(final boolean theDeadState) {
        if (theDeadState) {
            myCurrentDeathValue--;
        } else {
            myCurrentDeathValue = myDefaultDeathValue;
            myDirection = Direction.randomDirection();
        }
    }

    /**
     * Implemented behavior for what happens when a Vehicle collides with another.
     * If both are alive, compares their default death values to determine 
     * whether this Vehicle dies.
     */
    @Override
    public final void collide(final Vehicle theOther) {
        if (isAlive() && theOther.isAlive()
                && getDeathTime() > theOther.getDeathTime()) {
            makeDead(true);
        }
    }

    /**
     * @return myDefaultDeathValue of the Vehicle.
     */
    @Override
    public final int getDeathTime() {
        return myDefaultDeathValue;
    }

    /**
     * @return the default Image filepath for a Vehicle if it is alive, otherwise
     * returns the dead one.
     */
    @Override
    public final String getImageFileName() {
        String result = "";
        if (isAlive()) {
            result = myDefaultImageFileName;
        } else {
            result = myDeadImageFileName;
        }
        return result;
    }

    /**
     * @return the Direction the Vehicle is facing.
     */
    @Override
    public final Direction getDirection() {
        return myDirection;
    }

    /**
     * @return the current X coordinate of the Vehicle.
     */
    @Override
    public final int getX() {
        return myCurrentX;
    }

    /**
     * @return the current Y coordinate of the Vehicle.
     */
    @Override
    public final int getY() {
        return myCurrentY;
    }

    /**
     * @return whether this Vehicle is alive or not by comparing its current
     * death value with its default value.
     */
    @Override
    public final boolean isAlive() {
        return myCurrentDeathValue == myDefaultDeathValue;
    }

    /**
     * If the vehicle still has remaining turns it needs to stay dead, decrement that
     * time by 1. Otherwise, reset the Vehicle to its "alive" state.
     */
    @Override
    public final void poke() {
        if (myCurrentDeathValue > 0) {
            myCurrentDeathValue--;
        } else {
            myCurrentDeathValue = myDefaultDeathValue;
            makeDead(false);
        }
    }

    /**
     * Resets the current Vehicles x and y coordinates, its direction, and its alive state
     * to their initial values.
     */
    @Override
    public final void reset() {
        myCurrentX = myInitialX;
        myCurrentY = myInitialY;
        myDirection = myInitialDirection;
        myCurrentDeathValue = myDefaultDeathValue;
        
    }

    /**
     * Sets the Vehicles current Direction to the desired Direction.
     */
    @Override
    public final void setDirection(final Direction theDir) {
        myDirection = theDir;
    }

    /**
     * Sets the Vehicles current X coordinate to the desired value.
     */
    @Override
    public final void setX(final int theX) {
        myCurrentX = theX;
    }

    /**
     * Sets the Vehicles current Y coordinate to the desired value.
     */
    @Override
    public final void setY(final int theY) {
        myCurrentY = theY;
    }
    /**
     * @return a String representation of this Vehicle by returning it's class.
     */
    @Override
    public final String toString() {
        return this.getClass().getSimpleName().toLowerCase(Locale.US);
    }

}
