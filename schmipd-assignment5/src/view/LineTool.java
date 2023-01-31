/**
 * TCSS 305 - Assignment 5
 */
package view;

import java.awt.Shape;
import java.awt.geom.Line2D;

/**
 * This tool creates Line Shapes.
 * @author Paul Schmidt
 * @version 5b
 */
public class LineTool extends AbstractDrawingTool {

    @Override
    public Shape createShape(final int theStartX, final int theStartY, 
            final int theEndX, final int theEndY) {
        return new Line2D.Double(theStartX, theStartY, theEndX, theEndY);
    }
    /**
     * This method left intentionally blank.
     */
    protected void resetTool() {
    }
}
