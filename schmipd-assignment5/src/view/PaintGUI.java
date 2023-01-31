/*
 * TCSS 305 - Assignment 5
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Presents the GUI for the PowerPaint application.
 * 
 * @author Paul Schmidt
 * @version 5c
 */
public final class PaintGUI {
    
    // Constants
    /**
     * Toolkit used to set the Size of the Application.
     */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    /**
     * Dimension used to set the Size of the Application.
     */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    /**
     * Width of the Screen.
     */
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    /**
     * Height of the Screen.
     */
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
    /**
     * The Scale for setting the Size of the Application.
     */
    private static final int SCALE = 3;
    /**
     * Set the Size of the Application.
     */
    private static final Dimension APP_SIZE = new Dimension(SCREEN_WIDTH / SCALE, 
            SCREEN_HEIGHT / SCALE);
    /**
     * Image Icon to use for the Application.
     */
    private static final String IMAGE_ICON = "files/w_small.png";
    
    // instance fields
    /**
     * The ClearButton that clears the Canvas.
     */
    private JMenuItem myClearButton;
    /**
     * The FillButton in the Options Menu.
     */
    private JMenuItem myFillButton;
    /**
     * The JFrame of the Application.
     */
    private final JFrame myFrame;
    /**
     * The Menu Bar for the Application.
     */
    private final JMenuBar myMenu;
    /**
     * The Canvas to be painted on.
     */
    private final PaintCanvas myCanvas;
    /**
     * A List of Actions associated with creating the Tools to add to the ToolBars.
     */
    private List<Action> myToolActions;
    /**
     * Creates the default PaintGUI Application.
     */
    public PaintGUI() {
        super();
        myFrame = new JFrame("TCSS 305 Paint – Autumn 2022");
        myMenu = new JMenuBar();
        myCanvas = new PaintCanvas();
        createCanvasListener();
        createTools();
        start();
    }

    /**
     * Performs all tasks necessary to display the UI.
     */
    protected void start() {
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(APP_SIZE);
        final ImageIcon anImage = new ImageIcon(IMAGE_ICON);
        myFrame.setIconImage(anImage.getImage());
        myFrame.setLocation(SCREEN_WIDTH / 2 - myFrame.getWidth() / 2, 
                SCREEN_HEIGHT / 2 - myFrame.getHeight() / 2);
        myFrame.add(myCanvas, BorderLayout.CENTER);
        createMenuBar();
        createToolBar();
        myFrame.setJMenuBar(myMenu);
        myFrame.setVisible(true);
    }
    /**
     * Creates an array of Actions that are used to create the buttons that represent them.
     */
    private void createTools() {
        myToolActions = new ArrayList<>();
        myToolActions.add(new LineAction());
        myToolActions.add(new RectangleAction());
        myToolActions.add(new EllipseAction());
        myToolActions.add(new PencilAction());
    }
    /**
     * Creates the Listener that's used for the PaintCanvas.
     */
    private void createCanvasListener() {
        final CanvasMouseListener mouseListener = new CanvasMouseListener();
        myCanvas.addMouseListener(mouseListener);
        myCanvas.addMouseMotionListener(mouseListener);
    }
    /**
     * Creates the MenuBar.
     */
    private void createMenuBar() {
        createOptionsMenu();
        createToolMenu();
        createHelpMenu();
    }
    /**
     * Creates the Options portion of the JMenuBar.
     */
    private void createOptionsMenu() {
        final JMenu optionMenu = new JMenu("Options");
        final JMenu thicknessMenu = new JMenu("Thicknesss");
        final ThicknessSlider thicknessSlider = new ThicknessSlider();
        thicknessMenu.add(thicknessSlider);
        final JMenuItem colorItem = new JMenuItem("Color...");
        colorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final Color chosenColor = JColorChooser.showDialog(myFrame, 
                        "Color", myCanvas.getColor());
                if (chosenColor != null) {
                    myCanvas.setColor(chosenColor);
                }
            }
        });
        optionMenu.add(thicknessMenu);
        optionMenu.addSeparator();
        optionMenu.add(colorItem);
        final JMenuItem fillColorItem = new JMenuItem("Fill Color...");
        fillColorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final Color chosenColor = JColorChooser.showDialog(myFrame, 
                        "Fill Color", myCanvas.getFillColor());
                if (chosenColor != null) {
                    myCanvas.setFillColor(chosenColor);
                }
            }
        });
        optionMenu.add(fillColorItem);
        optionMenu.addSeparator();
        myFillButton = new FillButton();
        optionMenu.add(myFillButton);
        optionMenu.addSeparator();
        myClearButton = new ClearButton();
        optionMenu.add(myClearButton);
        myMenu.add(optionMenu);
    }
    /**
     * Creates the Tool JMenu portion of the MenuBar.
     */
    private void createToolMenu() {
        final JMenu toolMenu = new JMenu("Tools");
        final ButtonGroup radioGroup = new ButtonGroup();
        for (int i = 0; i < myToolActions.size(); i++) {
            final JRadioButtonMenuItem button = new JRadioButtonMenuItem(myToolActions.get(i));
            radioGroup.add(button);
            toolMenu.add(button);
            if (i == 0) {
                button.setSelected(true);
            }
        }
        myMenu.add(toolMenu);
    }
    /**
     * Creates the Help Portion of the JMenuBar.
     */
    private void createHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        final ImageIcon anImage = new ImageIcon(IMAGE_ICON);
        final JMenuItem aboutMenuItem = new JMenuItem("About...");
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(myFrame, "Paul Schmidt\nAutumn 2022", 
                        "TCSS 305 Paint", JOptionPane.PLAIN_MESSAGE, anImage);
            }
        });
        helpMenu.add(aboutMenuItem);
        myMenu.add(helpMenu);
    }
    /**
     * Creates the ToolBar and attaches it to the JFrame.
     */
    private void createToolBar() {
        final JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        final ButtonGroup toolBarGroup = new ButtonGroup();
        for (int i = 0; i < myToolActions.size(); i++) {
            final JToggleButton button = new JToggleButton(myToolActions.get(i));
            toolBarGroup.add(button);
            toolBar.add(button);
            if (i == 0) {
                button.setSelected(true);
            }
        }
        myFrame.add(toolBar, BorderLayout.SOUTH);
    }
    
    //Inner Classes
    
    /**
     * A Custom ClearButton that extends JMenuItem. Initial state is disabled, but enables if
     * something is drawn on the canvas. Then can be pressed to clear the canvas of drawings
     * and then becomes Disabled again.
     * @author Paul Schmidt
     * @version 5b
     */
    private final class ClearButton extends JMenuItem {
        /**
         * Serialization added in accordance with Warnings.
         */
        private static final long serialVersionUID = 6121019107057239645L;
        /**
         * Creates the ClearButton and the appropriate ActionListener.
         */
        private ClearButton() {
            super("Clear");
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                    myCanvas.clearCanvas();
                    myCanvas.setIsFinalPoint(false);
                    myCanvas.setmyPaintShape(false);
                    myCanvas.repaint();
                    setEnabled(false);
                }
            });
            setEnabled(false);
        }
    }
    /**
     * A custom Checkbox Fill Button that provides information to the PaintCanvas about
     * whether we should be drawing filled objects.
     * @author Paul Schmidt
     * @version 5c
     */
    private final class FillButton extends JCheckBoxMenuItem {
        /**
         * 
         */
        private static final long serialVersionUID = -1241007373738562057L;

        private FillButton() {
            super("Fill");
            setState(false);
            setEnabled(false);
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                        myCanvas.setFillSelected(
                                ((JCheckBoxMenuItem) myFillButton).getState());
                }
            });
        }
    }
    /**
     * Custom JSlider Class that sets the thickness of the Shape being drawn in the Canvas.
     * @author Paul Schmidt
     * @version 5b
     */
    private final class ThicknessSlider extends JSlider {
        /**
         * Serialization added in accordance with Warnings.
         */
        private static final long serialVersionUID = -7991257308376313919L;
        /**
         * Minimum value of the Slider.
         */
        private static final int MIN_VALUE = 0;
        /**
         * Maximum value of the Slider.
         */
        private static final int MAX_VALUE = 15;
        /**
         * Initial Value of the Slider.
         */
        private static final int INITIAL_VALUE = 3;
        /**
         * Big tick value of the Slider.
         */
        private static final int BIG_TICK_VALUE = 5;
        /**
         * Small tick value of the Slider.
         */
        private static final int SMALL_TICK_VALUE = 1;
        /**
         * Creates the ThicknessSlider and adds a ChangeListener.
         */
        private ThicknessSlider() {
            super(SwingConstants.HORIZONTAL, MIN_VALUE, MAX_VALUE, INITIAL_VALUE);
            setMajorTickSpacing(BIG_TICK_VALUE);
            setMinorTickSpacing(SMALL_TICK_VALUE);
            setPaintLabels(true);
            setPaintTicks(true);
            addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(final ChangeEvent theEvent) {
                    //It seems the PaintCanvas is repainted when the MenuBar is interacted
                    //with, so we need to tell the Canvas that we don't want to be drawing
                    //any new shapes.
                    myCanvas.setmyPaintShape(false);
                    //Sets the stroke of the graphics tool based on the value of the slider.
                    myCanvas.setStroke(getValue());
                }
            });
        }
    }
    /**
     * Custom MouseAdapter for the PaintCanvas that begins to draw on the canvas when the mouse
     * is dragged and released. Note that it doesn't start drawing when the Mouse is initially
     * pressed. Doing so leads to a brief visual bug based on the previous X and Y values of 
     * the PaintCanvas.
     * @author Paul Schmidt
     * @version 5b
     */
    private final class CanvasMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(final MouseEvent theEvent) {
            myCanvas.resetPencilTool();
            if (theEvent.getX() > -1 && theEvent.getY() > -1) {
                myCanvas.setInitialX(theEvent.getX());
                myCanvas.setInitialY(theEvent.getY());
                myCanvas.setIsFinalPoint(false);
                myCanvas.setmyPaintShape(false);
            }
        }
        @Override
        public void mouseDragged(final MouseEvent theEvent) {
            myCanvas.setEndX(theEvent.getX());
            myCanvas.setEndY(theEvent.getY());
            myCanvas.setIsFinalPoint(false);
            myCanvas.setmyPaintShape(true);
            myCanvas.repaint();
        }
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            myCanvas.setEndX(theEvent.getX());
            myCanvas.setEndY(theEvent.getY());
            myCanvas.setIsFinalPoint(true);
            myCanvas.repaint();
            if (!myClearButton.isEnabled()) {
                myClearButton.setEnabled(true);
            }
        }
    }
    /**
     * Custom Action class used to create the LineTool.
     * @author Paul Schmidt
     * @version 5c
     */
    private final class LineAction extends AbstractAction {
        /**
         * Serialization added in accordance with Warnings.
         */
        private static final long serialVersionUID = 8784287871134954542L;
        private LineAction() {
            super("Line", new ImageIcon("files/line_bw.gif"));
            putValue(Action.SELECTED_KEY, true);
        }
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCanvas.setLineTool();
            myFillButton.setEnabled(false);
            ((JCheckBoxMenuItem) myFillButton).setState(false);
            myCanvas.setFillSelected(false);
            myCanvas.setmyPaintShape(false);
        }
    }
    /**
     * Custom Action class used to create the RectangleTool.
     * @author Paul Schmidt
     * @version 5c
     */
    private final class RectangleAction extends AbstractAction {
        /**
         * Serialization added in accordance with Warnings.
         */
        private static final long serialVersionUID = -670173950868954794L;
        private RectangleAction() {
            super("Rectangle", new ImageIcon("files/rectangle_bw.gif"));
            putValue(Action.SELECTED_KEY, true);
        }
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCanvas.setRectangleTool();
            myFillButton.setEnabled(true);
            myCanvas.setmyPaintShape(false);
        }
    }
    /**
     * Custom Action class used to create the EllipseTool.
     * @author Paul Schmidt
     * @version 5c
     */
    private final class EllipseAction extends AbstractAction {
        /**
         * Serialization added in accordance with Warnings.
         */
        private static final long serialVersionUID = 340126006231228286L;
        private EllipseAction() {
            super("Ellipse", new ImageIcon("files/ellipse_bw.gif"));
            putValue(Action.SELECTED_KEY, true);
        }
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCanvas.setEllipseTool();
            myFillButton.setEnabled(true);
            myCanvas.setmyPaintShape(false);
        }
        
    }
    /**
     * Custom Action class used to create the PencilTool.
     * @author Paul Schmidt
     * @version 5c
     */
    private final class PencilAction extends AbstractAction {
        /**
         * Serialization added in accordance with Warnings.
         */
        private static final long serialVersionUID = -1280801635600222650L;
        private PencilAction() {
            super("Pencil", new ImageIcon("files/pencil_bw.gif"));
            putValue(Action.SELECTED_KEY, true);
        }
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCanvas.setPencilTool();
            myFillButton.setEnabled(false);
            ((JCheckBoxMenuItem) myFillButton).setState(false);
            myCanvas.setFillSelected(false);
            myCanvas.setmyPaintShape(false);
        }
    }
}