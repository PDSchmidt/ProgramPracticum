/**
 * 
 */
package view;

/**
 * TCSS 305 - Assignment 5
 */
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * This tool creates Ellipse Shapes.
 * @author Paul Schmidt
 * @version 5b
 */
public class EllipseTool extends AbstractDrawingTool {

    @Override
    public Shape createShape(final int theStartX, final int theStartY, 
            final int theEndX, final int theEndY) {
        int x = theStartX;
        int y = theStartY;
        //This code allows the Ellipse to be drawn "backwards".
        if (theStartX > theEndX) {
            x = theEndX;
        }
        if (theStartY > theEndY) {
            y = theEndY;
        }
        return new Ellipse2D.Double(x, y, Math.abs(theEndX - theStartX), 
                Math.abs(theEndY - theStartY));
    }
    /**
     * Intentionally left blank.
     */
    protected void resetTool() {
    }
}
