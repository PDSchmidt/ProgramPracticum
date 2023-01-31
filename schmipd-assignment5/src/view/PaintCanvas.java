/**
 * TCSS 305 - Assignment 5
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Custom JPanel class that acts as a drawable surface.
 * @author Paul Schmidt
 * @version 5c
 */
public final class PaintCanvas extends JPanel {
    /**
     * Serialization added for warning resolution.
     */
    private static final long serialVersionUID = -7024930362139111456L;
    /**
     * The Default Color to use when drawing.
     */
    private static final Color INITIAL_COLOR = new Color(51, 0, 111);
    /**
     * The Default Fill Color to use when drawing.
     */
    private static final Color INITIAL_FILL_COLOR = new Color(232, 211, 162);
    /**
     * Initial Thickness to be used to draw.
     */
    private static final int INITIAL_THICKNESS = 3;
    /**
     * The LineTool to use.
     */
    private static final AbstractDrawingTool LINE_TOOL = new LineTool();
    /**
     * The RectangleTool to use.
     */
    private static final AbstractDrawingTool RECTANGLE_TOOL = new RectangleTool();
    /**
     * The EllipseTool to use.
     */
    private static final AbstractDrawingTool ELLIPSE_TOOL = new EllipseTool();
    /**
     * The PencilTool to use.
     */
    private static final AbstractDrawingTool PENCIL_TOOL = new PencilTool();
    /**
     * The Current DrawingTool being used.
     */
    private AbstractDrawingTool myCurrentTool;
    /**
     * The current Color used to draw.
     */
    private Color myColor;
    /**
     * The current Fill Color used to draw when fill is enabled.
     */
    private Color myFillColor;
    /**
     * The current Thickness used to draw.
     */
    private int myThickness;
    /**
     * The current Stroke used to draw.
     */
    private Stroke myStroke;
    /**
     * The initial X value used to draw the Shape.
     */
    private int myInitialX;
    /**
     * The initial Y value used to draw the Shape.
     */
    private int myInitialY;
    /**
     * The ending X value used to draw the Shape.
     */
    private int myEndX;
    /**
     * The ending Y value used to draw the Shape.
     */
    private int myEndY;
    /**
     * The List of CanvasShapes that have been drawn on the current PaintCanvas so far.
     */
    private final List<CanvasShape> myShapes;
    /**
     * Boolean value used to determine when the current Shape should be committed to the list
     * of CanvasShapes.
     */
    private boolean myIsFinalPoint;
    /**
     * Boolean value used to determine whether we should be drawing a new shape right now.
     */
    private boolean myPaintShape;
    /**
     * Boolean value used to determine whether we should be drawing filled shapes.
     */
    private boolean myFillSelected;
    
    
    /**
     * Initiates initial values of instance variables as well as the Canvas Itself.
     */
    public PaintCanvas() {
        super();
        myCurrentTool = LINE_TOOL;
        initializeValues();
        setStroke(INITIAL_THICKNESS);
        myShapes = new ArrayList<>();
        setBackground(Color.WHITE);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }
    /**
     * Returns value of myIsFinalPoint.
     * @return if this is the final point of the shape and we should draw it.
     */
    private boolean isFinalPoint() {
        return myIsFinalPoint;
    }
    /***
     * Sets myInitialX value.
     * @param theX value to set myInitialX
     */
    protected void setInitialX(final int theX) {
        myInitialX = theX;
    }
    /***
     * Sets myInitialY value.
     * @param theY value to set myInitialY
     */
    protected void setInitialY(final int theY) {
        myInitialY = theY;
    }
    /***
     * Sets myEndX value.
     * @param theX value to set myEndX
     */
    protected void setEndX(final int theX) {
        myEndX = theX;
    }
    /***
     * Sets myEndY value.
     * @param theY value to set myEndY
     */
    protected void setEndY(final int theY) {
        myEndY = theY;
    }
    /**
     * Sets the current Stroke of the Canvas. Sets a different style if the current tool is 
     * the Pencil.
     * @param theThickness of the Stroke.
     */
    protected void setStroke(final int theThickness) {
        myThickness = theThickness;
        if (myCurrentTool.equals(PENCIL_TOOL)) {
            myStroke = new BasicStroke(myThickness, 
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        } else {
            myStroke = new BasicStroke(myThickness);
        }

    }
    /**
     * Initializes default values for fields.
     */
    private void initializeValues() {
        resetValues();
        myFillColor = INITIAL_FILL_COLOR;
        myColor = INITIAL_COLOR;
        myPaintShape = false;
        myIsFinalPoint = false;
        myFillSelected = false;
    }
    /**
     * Resets the initial values of the X and y coordinates.
     */
    protected void resetValues() {
        myInitialX = -1;
        myInitialY = -1;
        myEndX = -1;
        myEndY = -1;
    }
    /**
     * Sets whether this is the final point of the Shape or not.
     * @param theIsFinal boolean value of the final point.
     */
    protected void setIsFinalPoint(final boolean theIsFinal) {
        myIsFinalPoint = theIsFinal;
    }
    /**
     * Sets whether we should be painting a shape based on the current values.
     * @param theValue the boolean value to set.
     */
    protected void setmyPaintShape(final boolean theValue) {
        myPaintShape = theValue;
    }
    /**
     * Clears out the List of CanvasShapes we've drawn on the PaintCanvas so far. Also resets
     * the pencil tool in order to clear the path coordinates.
     */
    protected void clearCanvas() {
        PENCIL_TOOL.resetTool();
        myShapes.clear();
    }
    /**
     * Sets the current tool to the Line Tool.
     */
    protected void setLineTool() {
        myCurrentTool = LINE_TOOL;
        setStroke(myThickness);
    }
    /**
     * Sets the current tool to the Rectangle Tool.
     */
    protected void setRectangleTool() {
        myCurrentTool = RECTANGLE_TOOL;
        setStroke(myThickness);
    }
    /**
     * Sets the current tool to the Ellipse Tool.
     */
    protected void setEllipseTool() {
        myCurrentTool = ELLIPSE_TOOL;
        setStroke(myThickness);
    }
    /**
     * Sets the current tool to the Pencil Tool.
     */
    protected void setPencilTool() {
        PENCIL_TOOL.resetTool();
        myCurrentTool = PENCIL_TOOL;
        setStroke(myThickness);
    }
    /**
     * Resets the Pencil Tool.
     */
    protected void resetPencilTool() {
        PENCIL_TOOL.resetTool();
    }
    /**
     * Returns the current Color being used.
     * @return the Color.
     */
    protected Color getColor() {
        return myColor;
    }
    /**
     * Sets the Color being used.
     * @param theColor the Color.
     */
    protected void setColor(final Color theColor) {
        myColor = theColor;
    }
    /**
     * Set the Fill Color being used.
     * @param theColor to use for the Fill Color
     */
    protected void setFillColor(final Color theColor) {
        myFillColor = theColor;
    }
    /**
     * Getter for myFillColor.
     * @return the Fill Color to return.
     */
    protected Color getFillColor() {
        return myFillColor;
    }
    /**
     * Sets whether the Fill Selected Checkbox is filled or not.
     * @param theFillSelected the state of the FillCheckBox.
     */
    protected void setFillSelected(final boolean theFillSelected) {
        myFillSelected = theFillSelected;
    }
    /**
     * Overrides the paintComponent method in the repaint() method of the PaintCanvas.
     * Always redraws all of the CanvasShapes that have been drawn on the PaintCanvas so far.
     * Then, if the PaintCanvas should be drawing a shape, begins to draw a shape based on the 
     * DrawingTool, Color, and Stroke being used.
     * Finally, if the mouse has been released on the component, the current shape being drawn 
     * will be saved to the list of CanvasShapes along with the current Color and Stroke being 
     * used to draw it.
     * @param theGraphics the Graphics object used to draw on the Canvas.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      
                RenderingHints.VALUE_ANTIALIAS_ON);
        final int numShapes = myShapes.size();
        for (int i = 0; i < numShapes; i++) {
            myShapes.get(i).drawShape(g2d);
        }
        if (myPaintShape) {
            final Shape currentShape = myCurrentTool.createShape(myInitialX, myInitialY,
                            myEndX, myEndY);
            g2d.setColor(myColor);
            g2d.setStroke(myStroke);
            final CanvasShape finalShape = new CanvasShape(currentShape, myColor, myFillColor, 
                    myStroke, myFillSelected);
            if (myThickness > 0 && myInitialX > -1 && myInitialY > -1) {
                if (isFinalPoint()) {
                    PENCIL_TOOL.resetTool();
                    myShapes.add(finalShape);
                    finalShape.drawShape(g2d);
                    setIsFinalPoint(false);
                    setmyPaintShape(false);
                } else {
                    finalShape.drawShape(g2d);
                }
            }
        }
    }
}
