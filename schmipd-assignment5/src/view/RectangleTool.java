/**
 * TCSS 305 - Assignment 5
 */
package view;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * Tool that creates rectangle shapes.
 * @author Paul Schmidt
 * @version 5b
 */
public class RectangleTool extends AbstractDrawingTool {

    @Override
    public Shape createShape(final int theStartX, final int theStartY, 
            final int theEndX, final int theEndY) {
        int x = theStartX;
        int y = theStartY;
        //This code allows for the rectangle to be drawn "backwards"
        if (theStartX > theEndX) {
            x = theEndX;
        }
        if (theStartY > theEndY) {
            y = theEndY;
        }
        return new Rectangle2D.Double(x, y, Math.abs(theEndX - theStartX), 
                Math.abs(theEndY - theStartY));
    }
    /**
     * Intentionally left blank.
     */
    protected void resetTool() {
    }
}
