/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */

package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Car;
import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;
import org.junit.Test;

/**
 * Unit tests for class Truck.
 * 
 * @author Paul Schmidt
 * @version 1.0
 */
public class TruckTest {

    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 100;
    
    /** Test method for Truck constructor. */
    @Test
    public void testTruckConstructor() {
        final Truck t = new Truck(10, 11, Direction.NORTH);
        
        assertEquals("Truck x coordinate not initialized correctly!", 10, t.getX());
        assertEquals("Truck y coordinate not initialized correctly!", 11, t.getY());
        assertEquals("Truck direction not initialized correctly!",
                     Direction.NORTH, t.getDirection());
        assertEquals("Truck death time not initialized correctly!", 0, t.getDeathTime());
        assertTrue("Truck isAlive() fails initially!", t.isAlive());
    }
    
    /** Test method for Truck setters. */
    @Test
    public void testTruckSetters() {
        final Truck t = new Truck(10, 11, Direction.NORTH);
        
        t.setX(12);
        assertEquals("Truck setX failed!", 12, t.getX());
        t.setY(13);
        assertEquals("Truck setY failed!", 13, t.getY());
        t.setDirection(Direction.SOUTH);
        assertEquals("Truck setDirection failed!", Direction.SOUTH, t.getDirection());
    }

    /**
     * Test method for Truck canPass() method.
     */
    @Test
    public void testCanPass() {
        
        // Trucks can move to STREET, CROSSWALKS, or LIGHT
        // so we need to test all of those conditions
        
        // Trucks should NOT choose to move to other terrain types
        // so we need to test that Truck never move to other terrain types
        
        // Truck should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also
        
        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.LIGHT);
                
        final Truck testTruck = new Truck(0, 0, Direction.NORTH);
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.STREET 
                        || destinationTerrain == Terrain.LIGHT) {
                
                    // trucks can pass STREET and LIGHT under any light condition
                    assertTrue("Truck should be able to pass STREET"
                               + ", with light " + currentLightCondition,
                               testTruck.canPass(destinationTerrain, currentLightCondition));
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                           // trucks can pass CROSSWALK
                           // if the light is YELLOW or GREEN but not RED

                    if (currentLightCondition == Light.RED) {
                        assertFalse("Trucks should NOT be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            testTruck.canPass(destinationTerrain,
                                          currentLightCondition));
                    } else { // light is green or yellow
                        assertTrue("Trucks should be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            testTruck.canPass(destinationTerrain,
                                          currentLightCondition));
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {
 
                    assertFalse("Trucks should NOT be able to pass " + destinationTerrain
                        + ", with light " + currentLightCondition,
                        testTruck.canPass(destinationTerrain, currentLightCondition));
                }
            } 
        }
    }

    /**
     * Test method for Truck chooseDirection() method.
     */
    @Test
    public void testChooseDirectionSurroundedByValidTerrain() {
        for (final Terrain t : Terrain.values()) {
            if (t == Terrain.LIGHT || t == Terrain.CROSSWALK || t == Terrain.STREET) {
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, t);
                
                boolean seenWest = false;
                boolean seenNorth = false;
                boolean seenEast = false;
                boolean seenSouth = false;
                
                final Truck testTruck = new Truck(0, 0, Direction.NORTH);
                
                for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
                    final Direction d = testTruck.chooseDirection(neighbors);
                    
                    if (d == Direction.WEST) {
                        seenWest = true;
                    } else if (d == Direction.NORTH) {
                        seenNorth = true;
                    } else if (d == Direction.EAST) {
                        seenEast = true;
                    } else if (d == Direction.SOUTH) { // this should NOT be chosen
                        seenSouth = true;
                    }
                }
         
                assertTrue("Truck chooseDirection() fails to select randomly "
                           + "among all possible valid choices!",
                           seenWest && seenNorth && seenEast);
                    
                assertFalse("Truck chooseDirection() reversed direction when not necessary!",
                            seenSouth);
            }
        }
    }
    /**
     * Test method for Truck chooseDirection() method.
     */
    @Test
    public void testChooseDirectionOnStreetMustReverse() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.LIGHT && t != Terrain.CROSSWALK && t != Terrain.STREET) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.STREET);
                
                final Truck testTruck = new Truck(0, 0, Direction.NORTH);
                
                // the Truck must reverse and go SOUTH
                assertEquals("Truck chooseDirection() failed "
                                + "when reverse was the only valid choice!",
                             Direction.SOUTH, testTruck.chooseDirection(neighbors));
            }
                
        }
    }
    /**
     * Test method for Collide.
     */
    @Test
    public void truckCollideWithTruckTest() {
        final Truck testTruck1 = new Truck(0, 0, Direction.NORTH);
        final Truck testTruck2 = new Truck(0, 0, Direction.NORTH);
        testTruck1.collide(testTruck2);
        assertEquals(testTruck1.isAlive(), true);
        assertEquals(testTruck2.isAlive(), true);
    }
    /**
     * Test method for Collide.
     */
    @Test
    public void truckCollideWithCarTest() {
        final Truck testTruck = new Truck(0, 0, Direction.NORTH);
        final Car testCar = new Car(0, 0, Direction.NORTH);
        testTruck.collide(testCar);
        testCar.collide(testTruck);
        assertEquals(testTruck.isAlive(), true);
        assertEquals(testCar.isAlive(), false);
        testTruck.collide(testCar);
        testCar.collide(testTruck);
        assertEquals(testTruck.isAlive(), true);
        assertEquals(testCar.isAlive(), false);
    }
    /**
     * Test method to make sure the Image of the vehicle is changed
     * when it is alive vs dead.
     */
    @Test
    public void truckCollideWithCarImageTest() {
        final Truck testTruck = new Truck(0, 0, Direction.NORTH);
        final Car testCar = new Car(0, 0, Direction.NORTH);
        assertEquals(testCar.getImageFileName(), "car.gif");
        testTruck.collide(testCar);
        testCar.collide(testTruck);
        assertEquals(testCar.getImageFileName(), "car_dead.gif");
        for (int i = 0; i < 10; i++) {
            assertEquals(testCar.isAlive(), false);
            System.out.println(testCar.isAlive() + " " + i);
            testCar.poke();
        }
        assertEquals(testCar.isAlive(), true);
    }
    /**
     * Test toString().
     */
    @Test
    public void testToString() {
        final Truck testTruck = new Truck(0, 0, Direction.NORTH);
        assertEquals(testTruck.toString(), "truck");
    }
    /**
     * Test reset().
     */
    @Test
    public void testReset() {
        final Truck testTruck = new Truck(0, 0, Direction.NORTH);
        testTruck.setX(5);
        testTruck.setY(5);
        testTruck.setDirection(Direction.EAST);
        testTruck.reset();
        assertEquals(testTruck.getX(), 0);
        assertEquals(testTruck.getY(), 0);
        assertEquals(testTruck.getDirection(), Direction.NORTH);
    }
}
