/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents a Truck vehicle and implements functionality specific
 * to the Truck.
 * @author Paul Schmidt
 * @version 1.0
 */
public final class Truck extends AbstractVehicle {
    /**
     * How long this Vehicle stays dead when colliding with the correct Vehicles.
     */
    private static final int DEFAULT_DEATH_VALUE = 0;
    /**
     * The Image File for the default state of the Truck.
     */
    private static final String DEFAULT_IMAGE = "truck.gif";
    /**
     * The Image File for the dead state of the Truck.
     */
    private static final String DEAD_IMAGE = "truck_dead.gif";

    /**
     * Constructor for the Truck class.
     * @param theX the initial X value of the Vehicle
     * @param theY the initial Y value of the Vehicle
     * @param theDirection the initial Direction the Vehicle is facing
     */
    public Truck(final int theX, final int theY, final Direction theDirection) {
        //Pass to the Super Constructor the initial X, Y, and Direction while
        //also passing along the information relevant to this type of Vehicle
        super(theX, theY, theDirection, DEFAULT_IMAGE, DEAD_IMAGE, DEFAULT_DEATH_VALUE);
    }
    /**
     * canPass() implementation for the Truck. If the Terrain is NOT STREET, NOT CROSSWALK,
     * and NOT LIGHT, then the Truck cannot pass.
     * The Truck also cannot pass a CROSSWALK if the Light is RED.
     * Otherwise, it can pass.
     * @param theTerrain The Terrain value to check
     * @param theLight The Light value to check
     * @return Whether this Truck can pass the given Terrain with the given Light value
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = true;
        if (theTerrain != Terrain.CROSSWALK && theTerrain != Terrain.STREET
                && theTerrain != Terrain.LIGHT) {
            result = false;
        }
        if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
            result = false;
        }
        return result;
    }

    /**
     * Implementation of the chooseDirection() method for the Truck. The Truck moves
     * pseudo-randomly. It looks in each Direction that is NOT reverse, and if the Terrain
     * in that Direction is a STREET, CROSSWALK, or LIGHT, then it adds that Direction to a 
     * List. It the list is NOT empty after it has seen those Directions, then it will randomly
     * choose to move in a Direction from among them. If it cannot move in any of those 
     * Directions, then it will choose to reverse.
     * @param theNeighbors A map of the Terrain around the Vehicle
     * @return the Direction the Truck wants to go
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final ArrayList<Direction> validDirections = new ArrayList<Direction>();
        final Direction chosenDirection;
        final Direction reverse = getDirection().reverse();
        for (final Direction lookingDirection : theNeighbors.keySet()) {
            if (lookingDirection != reverse) {
                final Terrain terrainInDir = theNeighbors.get(lookingDirection);
                if (terrainInDir == Terrain.CROSSWALK) {
                    validDirections.add(lookingDirection);
                } else if (terrainInDir == Terrain.STREET) {
                    validDirections.add(lookingDirection);
                } else if (terrainInDir == Terrain.LIGHT) {
                    validDirections.add(lookingDirection);
                } else {
                    continue;
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
