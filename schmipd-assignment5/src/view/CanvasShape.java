/**
 * TCSS 305 - Assignment 5
 */
package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

/**
 * This class stores information about a Shape, the color it was drawn with, and the stroke
 * used to draw it. This way the Shape can be redrawn when the PaintCanvas is updated.
 * @author Paul Schmidt
 * @version 5c
 */
public class CanvasShape {
    /**
     * The Shape to store.
     */
    protected final Shape myShape;
    /**
     * The Color value of the Shape.
     */
    private final Color myColor;
    /**
     * The Color of the Filled Shape.
     */
    private final Color myFillColor;
    /**
     * The Stroke used to paint the Shape.
     */
    private final Stroke myStroke;
    /**
     * Whether this CanvasShape should be drawn filled or not.
     */
    private final Boolean myIsFilled;
    /**
     * Constructor for creating the CanvasShape.
     * @param theShape The Shape to store.
     * @param theColor The Color to store.
     * @param theStroke The Stroke to store.
     */
    public CanvasShape(final Shape theShape,
             final Color theColor, final Color theFillColor, 
             final Stroke theStroke, final boolean theFill) {
        myShape = theShape;
        myColor = theColor;
        myFillColor = theFillColor;
        myStroke = theStroke;
        myIsFilled = theFill;
    }
    /**
     * Uses the Graphics2D object to draw this CanvasShape. If this Shape was drawn using the
     * Fill option, draw it Filled first using the Fill Color first, then draw the regular
     * Shape on top of that.
     * @param theGraphics The graphics object to use.
     */
    public void drawShape(final Graphics2D theGraphics) {
        theGraphics.setStroke(myStroke);
        if (myIsFilled) {
            theGraphics.setColor(myFillColor);
            theGraphics.fill(myShape);
        }
        theGraphics.setColor(myColor);
        theGraphics.draw(myShape);
    }
}
