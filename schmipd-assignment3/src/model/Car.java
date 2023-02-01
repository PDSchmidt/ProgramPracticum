/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a Car vehicle and implements functionality specific
 * to the Car.
 * @author Paul Schmidt
 * @version 1.0
 */
public final class Car extends AbstractVehicle {
    /**
     * How long this Vehicle stays dead when colliding with the correct Vehicles.
     */
    private static final int DEFAULT_DEATH_VALUE = 10;
    /**
     * The Image File for the default state of the Car.
     */
    private static final String DEFAULT_IMAGE = "car.gif";
    /**
     * The Image File for the dead state of the Car.
     */
    private static final String DEAD_IMAGE = "car_dead.gif";

    /**
     * The constructor for the Car class.
     * @param theX the initial X value of the Vehicle
     * @param theY the initial Y value of the Vehicle
     * @param theDirection the initial Direction the Vehicle is facing
     */
    public Car(final int theX, final int theY, final Direction theDirection) {
        //Pass to the Super Constructor the initial X, Y, and Direction while
        //also passing along the information relevant to this type of Vehicle
        super(theX, theY, theDirection, DEFAULT_IMAGE, DEAD_IMAGE, DEFAULT_DEATH_VALUE);
    }
    /**
     * canPass() implementation for the Car. If the Terrain is NOT LIGHT, NOT CORSSWALK,
     * and NOT STREET, then the Car can't pass.
     * The Car also cannot pass a LIGHT or CROSSWALK if the Light is NOT GREEN.
     * Otherwise, it can pass.
     * @param theTerrain The Terrain value to check
     * @param theLight The Light value to check
     * @return Whether this Car can pass the given Terrain with the given Light value
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = true;
        if (theTerrain != Terrain.LIGHT && theTerrain != Terrain.CROSSWALK
                && theTerrain != Terrain.STREET) {
            result = false;
        }
        if ((theTerrain == Terrain.LIGHT || theTerrain == Terrain.CROSSWALK)
                && theLight != Light.GREEN) {
            result = false;
        }
        return result;
    }
    /**
     * Implementation of the chooseDirection() method for the Car. The Car always wants
     * to go Straight if it is a valid Terrain(STREET, CROSSWALK, or LIGHT). Otherwise, left
     * if valid Terrain. Otherwise right if valid Terrain. Otherwise, it will want to reverse.
     * @param theNeighbors A map of the Terrain around the Vehicle
     * @return the Direction the Car wants to go
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction straight = getDirection();
        final Direction chosenDirection;
        final Set<Terrain> validTerrain = new HashSet<Terrain>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.LIGHT);
        if (validTerrain.contains(theNeighbors.get(straight))) {
            chosenDirection = straight;
        } else if (validTerrain.contains(theNeighbors.get(straight.left()))) {
            chosenDirection = straight.left();
        } else if (validTerrain.contains(theNeighbors.get(straight.right()))) {
            chosenDirection = straight.right();
        } else {
            chosenDirection = straight.reverse();
        }
        return chosenDirection;
    }

}
