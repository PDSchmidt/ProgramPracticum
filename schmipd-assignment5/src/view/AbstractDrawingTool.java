/**
 * TCSS 305 - Assignment 5
 */
package view;

import java.awt.Shape;

/**
 * The Abstract Class for Tools that are used to Draw Shapes on the PaintCanvas.
 * @author Paul Schmidt
 * @version 5b
 */
public abstract class AbstractDrawingTool implements DrawingToolInterface {
    @Override
    public abstract Shape createShape(int theStartX, int theStartY, int theEndX, int theEndY);
    /**
     * If a tool has parameters that need to be reset during certain situations, 
     * override this method.
     */
    protected abstract void resetTool();
}
