/**
 * TCSS 305 - Assignment 5
 */
package view;

import java.awt.Shape;


/**
 * Interface for tools that will take in four coordinate parameters and create a shape to draw
 * based on their internal implementation.
 * @author Paul Schmidt
 * @version 5b
 */
public interface DrawingToolInterface {
    /**
     * The method to overwrite.
     * @param theStartX The starting X coordinate
     * @param theStartY The starting Y coordinate
     * @param theEndX The ending X coordinate
     * @param theEndY The ending Y coordinate
     * @return The Shape object to be drawn
     */
    Shape createShape(int theStartX, int theStartY, int theEndX, int theEndY);
}
