/*
 * TCSS 305 Autumn 2022
 * Assignment 1
 */
package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.awt.geom.Point2D;
import model.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



/**
 * This JUnit program performs unit testing for the Circle class.
 * 
 * @author Paul Schmidt
 * @version 1.0
 */
public class CircleTest {
    /**
     * Acceptable tolerance to use when comparing floats or doubles.
     */
    private static final double TOLERANCE = .000001;

    /**
     * Default Circle instance to be initialized in setUp method.
     */
    private Circle myCircle;
    
    /**
     * A default Point2D object to be initialized in setUp method.
     */
    private Point2D myPoint;
    
    /**
     * A default Color object to be initialized in setUp method.
     */
    private Color myColor;

    /**
     * SetUp method that initializes a default Circle, Point2D (to be used as a center), 
     *  and a default Color value for each test.
     */
    @BeforeEach
    public void setUp() {
        myCircle = new Circle();
        myPoint = new Point2D.Double(0.0, 0.0);
        myColor = Color.BLACK;
    }

    /**
     * Test method for overloaded Constructor with all valid input parameters.
     */
    @Test
    public void testCircleDoublePoint2DColor() {
        /**
         * Uses unique parameters to create a new Circle object that is different from 
         * the default myCircle created in the setUp method.
         */
        final Point2D testPoint = new Point2D.Double(2.0, 2.0);
        final Color testColor = Color.BLUE;
        final Circle testCircle = new Circle(2.0, testPoint, testColor);
        /**
         * Test that the new Circle was constructed properly.
         */
        assertEquals(2.0, testCircle.getRadius(), TOLERANCE);
        assertEquals(testPoint, testCircle.getCenter());
        assertEquals(testColor, testCircle.getColor());
    }

    /**
     * Test method for overloaded Constructor with an invalid radius parameter.
     */
    @Test
    public void testCircleNegPoint2DColor() {
        assertThrows(IllegalArgumentException.class, () -> new Circle(-1.5, myPoint, myColor));
    }

    /**
     * Test method for overloaded Constructor with a null Point2D parameter.
     */
    @Test
    public void testCircleDoubleNullColor() {
        assertThrows(NullPointerException.class, () -> new Circle(1.0, null, myColor));
    }

    /**
     * Test method for overloaded Constructor with a null Color parameter.
     */
    @Test
    public void testCircleDoublePoint2DNull() {
        assertThrows(NullPointerException.class, () -> new Circle(1.0, myPoint, null));
    }

    /**
     * Test method for default constructor.
     */
    @Test
    public void testCircle() {
        assertEquals(1.0, myCircle.getRadius(), TOLERANCE);
        assertEquals(Color.BLACK, myCircle.getColor());
        assertEquals(new Point2D.Double(0.0, 0.0), myCircle.getCenter());
    }

    /**
     * Test method for {@link model.Circle#setRadius(double)}.
     */
    @Test
    public void testSetRadius() {
        myCircle.setRadius(1.5);
        assertEquals(1.5, myCircle.getRadius(), TOLERANCE);
    }
    
    /**
     * Test method for the case that the setRadius method is passed an Illegal Parameter.
     */
    @Test
    public void testSetRadiusZeroOrLess() {
        assertThrows(IllegalArgumentException.class, () -> myCircle.setRadius(0));
    }

    /**
     * Test method for {@link model.Circle#setCenter(java.awt.geom.Point2D)}.
     */
    @Test
    public void testSetCenter() {
        myCircle.setCenter(new Point2D.Double(1.0, 1.0));
        assertEquals(new Point2D.Double(1.0, 1.0), myCircle.getCenter());
        
    }
    
    /**
     * Test method for the case that the setCenter method is passed a null parameter.
     */
    @Test
    public void testSetCenterNull() {
        assertThrows(NullPointerException.class, () -> myCircle.setCenter(null));
        
    }

    /**
     * Test method for {@link model.Circle#setColor(java.awt.Color)}.
     */
    @Test
    public void testSetColor() {
        assertEquals(Color.BLACK, myCircle.getColor());
    }
    
    /**
     * Test method for the case that the setColor method is passed a null parameter.
     */
    @Test
    public void testSetColorNull() {
        assertThrows(NullPointerException.class, () -> myCircle.setColor(null));
    }

    /**
     * Test method for calculating the Diameter of a Circle.
     */
    @Test
    public void testCalculateDiameter() {
        assertEquals(2.0, myCircle.calculateDiameter(), TOLERANCE);
    }

    /**
     * Test method for calculating the Circumference of a Circle.
     */
    @Test
    public void testCalculateCircumference() {
        assertEquals(2 * Math.PI * 1.0, myCircle.calculateCircumference(), TOLERANCE);
    }

    /**
     * Test method for calculating the Area of a Circle.
     */
    @Test
    public void testCalculateArea() {
        assertEquals(Math.PI * (1.0 * 1.0), myCircle.calculateArea(), TOLERANCE);
    }

    /**
     * Test method for the overridden toString method.
     */
    @Test
    public void testToString() {
        assertEquals("Circle [radius=1.00, "
                + "center=Point2D.Double[0.0, 0.0], "
                + "color=java.awt.Color[r=0,g=0,b=0]]", 
                myCircle.toString());
        
    }

}
