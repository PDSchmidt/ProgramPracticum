/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents a Human vehicle and implements functionality specific
 * to the Human.
 * @author Paul Schmidt
 * @version 1.0
 */
public final class Human extends AbstractVehicle {
    /**
     * How long this Vehicle stays dead when colliding with the correct Vehicles.
     */
    private static final int DEFAULT_DEATH_VALUE = 40;
    /**
     * The Image File for the default state of the Human.
     */
    private static final String DEFAULT_IMAGE = "human.gif";
    /**
     * The Image File for the dead state of the Human.
     */
    private static final String DEAD_IMAGE = "human_dead.gif";

    /**
     * The constructor for the Human class.
     * @param theX the initial X value of the Vehicle
     * @param theY the initial Y value of the Vehicle
     * @param theDirection the initial Direction the Vehicle is facing
     */
    public Human(final int theX, final int theY, final Direction theDirection) {
        //Pass to the Super Constructor the initial X, Y, and Direction while
        //also passing along the information relevant to this type of Vehicle
        super(theX, theY, theDirection, DEFAULT_IMAGE, DEAD_IMAGE, DEFAULT_DEATH_VALUE);
    }
    /**
     * canPass() implementation for the Human. If the Terrain is NOT GRASS or NOT CROSSWALK,
     * then the Human cannot pass.
     * The Human also cannot pass a CROSSWALK if the Light is GREEN.
     * Otherwise, it can pass.
     * @param theTerrain The Terrain value to check
     * @param theLight The Light value to check
     * @return Whether this Human can pass the given Terrain with the given Light value
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = true;
        if (theTerrain != Terrain.CROSSWALK && theTerrain != Terrain.GRASS) {
            result = false;
        }
        if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN) {
            result = false;
        }
        return result;
    }
    /**
     * Implementation of the chooseDirection() method for the Human. The Human moves
     * pseudo-randomly. If there is a CROSSWALK next to the Human then the Human prefers 
     * to face the CROSSWALK(unless it just came from a CROSSWALK). Otherwise, it will
     * randomly choose from available GRASS Terrain next to it. If there is no GRASS, then
     * it will reverse.
     * @param theNeighbors A map of the Terrain around the Vehicle
     * @return the Direction the Human wants to go
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final ArrayList<Direction> validDirections = new ArrayList<Direction>();
        final Direction reverse = getDirection().reverse();
        Direction chosenDirection = reverse;
        boolean foundCrosswalk = false;
        for (final Direction lookingDirection : theNeighbors.keySet()) {
            if (lookingDirection != reverse) {
                final Terrain terrainInDir = theNeighbors.get(lookingDirection);
                if (terrainInDir == Terrain.CROSSWALK) {
                    chosenDirection = lookingDirection;
                    foundCrosswalk = true;
                    break;
                } else {
                    if (terrainInDir == Terrain.GRASS) {
                        validDirections.add(lookingDirection);
                    }
                }
            }
        }
        if (!foundCrosswalk && !validDirections.isEmpty()) {
            final int rand = MY_RANDOM.nextInt(validDirections.size());
            chosenDirection = validDirections.get(rand);
        }
        return chosenDirection;
    }

}
