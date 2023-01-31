/**
 * TCSS 305 - Assignment 5
 */
package view;

import java.awt.Shape;
import java.awt.geom.Path2D;

/**
 * This tool uses a Path2D to simulate a pencil tool.
 * @author Paul Schmidt
 * @version 5b
 */
public class PencilTool extends AbstractDrawingTool {
    /**
     * The current path being drawn.
     */
    private Path2D myCurrentPath;
    /**
     * Initiate the Initial Path.
     */
    public PencilTool() {
        super();
        myCurrentPath = new Path2D.Double();
    }

    @Override
    public Shape createShape(final int theStartX, final int theStartY, 
            final int theEndX, final int theEndY) {
        if (theStartX < 0 || theStartY < 0) {
            myCurrentPath.reset();
        }
        //If the Path doesn't have a start point yet, move to theStartX and theStartY.
        if (myCurrentPath.getCurrentPoint() == null) {
            myCurrentPath = new Path2D.Double();
            myCurrentPath.moveTo(theStartX, theStartY);
        }
        myCurrentPath.lineTo(theEndX, theEndY);
        return (Shape) myCurrentPath.clone();
    }
    /**
     * Resets the Path2D to initial values. This is required for drawing independent Paths.
     */
    public void resetTool() {
        myCurrentPath.reset();
    }

}
