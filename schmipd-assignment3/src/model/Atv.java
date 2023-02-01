/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents an ATV vehicle and implements functionality specific
 * to the ATV.
 * @author Paul Schmidt
 * @version 1.0
 */
public final class Atv extends AbstractVehicle {
    /**
     * How long this Vehicle stays dead when colliding with the correct Vehicles.
     */
    private static final int DEFAULT_DEATH_VALUE = 20;
    /**
     * The Image File for the default state of the ATV.
     */
    private static final String DEFAULT_IMAGE = "atv.gif";
    /**
     * The Image File for the dead state of the ATV.
     */
    private static final String DEAD_IMAGE = "atv_dead.gif";

    /**
     * Constructor for the Atv Class.
     * @param theX the initial X value of the Vehicle
     * @param theY the initial Y value of the Vehicle
     * @param theDirection the initial Direction the Vehicle is facing
     */
    public Atv(final int theX, final int theY, final Direction theDirection) {
        //Pass to the Super Constructor the initial X, Y, and Direction while
        //also passing along the information relevant to this type of Vehicle
        super(theX, theY, theDirection, DEFAULT_IMAGE, DEAD_IMAGE, DEFAULT_DEATH_VALUE);
    }
    /**
     * canPass() implementation for the ATV. If the Terrain is a WALL, can't pass
     * Otherwise, the ATV moves as it pleases regardless of and Light value.
     * @param theTerrain The Terrain value to check
     * @param theLight The Light value to check
     * @return Whether this ATV can pass the given Terrain with the given Light value
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = true;
        if (theTerrain == Terrain.WALL) {
            result = false;
        }
        return result;
    }
    /**
     * Implementation of the chooseDirection() method for the ATV. Checks around the Vehicle
     * in all Directions that are not where the ATV just came from and adds valid moves to an
     * ArrayList. Then, if the List is not empty, randomly pick one of those Directions to move
     * in. Otherwise, choose to reverse Directions.
     * @param theNeighbors A map of the Terrain around the Vehicle
     * @return the Direction the ATV wants to go
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final ArrayList<Direction> validDirections = new ArrayList<Direction>();
        final Direction chosenDirection;
        final Direction reverse = getDirection().reverse();
        for (final Direction lookingDirection : theNeighbors.keySet()) {
            if (lookingDirection != reverse) {
                final Terrain terrainInDir = theNeighbors.get(lookingDirection);
                if (terrainInDir != Terrain.WALL) {
                    validDirections.add(lookingDirection);
                }
            }
        }
        if (validDirections.isEmpty()) {
            chosenDirection = reverse;
        } else {
            final int rand = MY_RANDOM.nextInt(validDirections.size());
            chosenDirection = validDirections.get(rand);
        }
        return chosenDirection;
    }

}
