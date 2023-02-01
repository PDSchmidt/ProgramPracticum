/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.Map;

/**
 * This class represents a Bicycle vehicle and implements functionality specific
 * to the Bicycle.
 * @author Paul Schmidt
 * @version 1.0
 */
public final class Bicycle extends AbstractVehicle {
    /**
     * How long this Vehicle stays dead when colliding with the correct Vehicles.
     */
    private static final int DEFAULT_DEATH_VALUE = 30;
    /**
     * The Image File for the default state of the Bicycle.
     */
    private static final String DEFAULT_IMAGE = "bicycle.gif";
    /**
     * The Image File for the dead state of the Bicycle.
     */
    private static final String DEAD_IMAGE = "bicycle_dead.gif";

    /**
     * Constructor for the Bicycle class.
     * @param theX the initial X value of the Vehicle
     * @param theY the initial Y value of the Vehicle
     * @param theDirection the initial Direction the Vehicle is facing
     */
    public Bicycle(final int theX, final int theY, final Direction theDirection) {
        //Pass to the Super Constructor the initial X, Y, and Direction while
        //also passing along the information relevant to this type of Vehicle
        super(theX, theY, theDirection, DEFAULT_IMAGE, DEAD_IMAGE, DEFAULT_DEATH_VALUE);
    }
    /**
     * canPass() implementation for the Bicycle. If the Terrain is a WALL or GRASS, can't pass.
     * The Bicycle also cannot pass a LIGHT or CROSSWALK if the Light is NOT GREEN.
     * Otherwise, it can pass.
     * @param theTerrain The Terrain value to check
     * @param theLight The Light value to check
     * @return Whether this Bicycle can pass the given Terrain with the given Light value
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = true;
        if (theTerrain == Terrain.GRASS || theTerrain == Terrain.WALL) {
            result = false;
        }
        if ((theTerrain == Terrain.LIGHT || theTerrain == Terrain.CROSSWALK)
                && theLight != Light.GREEN) {
            result = false;
        }
        return result;
    }

    /**
     * Implementation of the chooseDirection() method for the Bicycle. The Bicycle always
     * prefers to go Straight if it can or TRAILS. If there are no TRAILS (besides reverse),
     * it will prefer left on valid Terrain, otherwise right on Valid Terrain. If there are no
     * valid Terrain ahead, left, or right, then it will reverse Directions.
     * @param theNeighbors A map of the Terrain around the Vehicle
     * @return the Direction the Bicycle wants to go
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction forwardDirection = getDirection();
        Direction result = forwardDirection.reverse();
        final Terrain terrainForward = theNeighbors.get(forwardDirection);
        if (terrainForward == Terrain.TRAIL) {
            result = forwardDirection;
        } else if (theNeighbors.get(forwardDirection.right()) == Terrain.TRAIL) {
            result = forwardDirection.right();
        } else if (theNeighbors.get(forwardDirection.left()) == Terrain.TRAIL) {
            result = forwardDirection.left();
        } else if (terrainForward == Terrain.STREET
                ||  terrainForward == Terrain.CROSSWALK
                ||  terrainForward == Terrain.LIGHT) {
            result = forwardDirection;
        } else {
            final Terrain terrainRight = theNeighbors.get(forwardDirection.right());
            if (terrainRight == Terrain.STREET
                ||  terrainRight == Terrain.CROSSWALK
                ||  terrainRight == Terrain.LIGHT) {
                result = forwardDirection.right();
            } else {
                final Terrain terrainLeft = theNeighbors.get(forwardDirection.left());
                if (terrainLeft == Terrain.STREET
                        ||  terrainLeft == Terrain.CROSSWALK
                        ||  terrainLeft == Terrain.LIGHT) {
                    result = forwardDirection.left();
                }
            }
        }
        return result;
    }

}
