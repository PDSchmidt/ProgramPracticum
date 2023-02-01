/*
 * TCSS 305 Autumn 2022
 * Assignment 4
 */
package gui;

import filters.EdgeDetectFilter;
import filters.EdgeHighlightFilter;
import filters.Filter;
import filters.FlipHorizontalFilter;
import filters.FlipVerticalFilter;
import filters.GrayscaleFilter;
import filters.SharpenFilter;
import filters.SoftenFilter;
import image.PixelImage;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This program creates and displays a java GUI application. With it, you can open a an image
 * and apply some filters to it. You can then save the image, open a new image, or close the 
 * current image.
 * @author Paul Schmidt
 * @version 1.0
 */
public class GUI {
    /**
     * The parent component of the program.
     */
    private JFrame myFrame;
    /**
     * A list of buttons that apply different Filters to the image.
     */
    private List<JButton> myFilterButtonArray;
    /**
     * Saves the current Image.
     */
    private JButton mySaveButton;
    /**
     * Closes the current Image.
     */
    private JButton myCloseButton;
    /**
     * Holds a reference to the current Image displayed in the GUI.
     */
    private PixelImage myImage;
    /**
     * The Label that holds the Image in which we set the Icon.
     */
    private JLabel myImageLabel;
    /**
     * A FileChooser to be used by the program to either open/load a file or save a new one.
     */
    private JFileChooser myChooser;
    /**
     * Empty Constructor.
     */
    public GUI() {
        //Purposely left empty
    }
    /**
     * Start method that instantiates the GUI and all elements. It calls various private
     * helper methods in order to reduce the amount of work in the start() method.
     */
    public void start() {
        myChooser = new JFileChooser(".");
        myFrame = new JFrame("Assignment 4");
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFilterButtonArray = generateFilterButtons();
        myFrame.add(generateFilterPanel(), BorderLayout.WEST);
        myFrame.add(generateSouthPanel(), BorderLayout.SOUTH);
        myFrame.add(generateImageLabel(), BorderLayout.CENTER);
        setButtonsEnabled(false);
        myFrame.pack();
        myFrame.setVisible(true);
    }
    /**
     * Method added to the Open and Close buttons' action listeners in order to update 
     * the image displayed in the image panel.
     * @param thePixelImage the new PixelImage to display. If we pass a null reference, the 
     * previous PixelImage will be removed and the panel will be empty.
     */
    private void updateImageIcon(final PixelImage thePixelImage) {
        final ImageIcon aIcon;
        if (thePixelImage == null) {
            aIcon = null;
        } else {
            aIcon = new ImageIcon(thePixelImage);
        }
        myImageLabel.setIcon(aIcon);
        myImageLabel.repaint();
    }
    /**
     * Displays an error message if the wrong filetype is chosen to load.
     */
    private void displayErrorMessage() {
        JOptionPane.showMessageDialog(myFrame, "The selected file did not contain an image!",
                "Error!", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Either enables or disables the all but the Open button based on the parameter passed in.
     * @param theStatus true enables the buttons, false disables them.
     */
    private void setButtonsEnabled(final boolean theStatus) {
        for (int i = 0; i < myFilterButtonArray.size(); i++) {
            myFilterButtonArray.get(i).setEnabled(theStatus);
        }
        mySaveButton.setEnabled(theStatus);
        myCloseButton.setEnabled(theStatus);
    }
    /**
     * Generates a list of Filter objects and stores them in a List. generateFilterButtons() 
     * uses this list to generate the Filter Buttons.
     * NOTE: In order to add more Filter buttons to the GUI, simply add a new filter object to
     * the list in this method and the generateFilterButtons() will take care of the rest.
     * @return a List of filter objects.
     */
    private List<Filter> generateFilters() {
        final List<Filter> filters = new ArrayList<Filter>();
        filters.add(new EdgeDetectFilter());
        filters.add(new EdgeHighlightFilter());
        filters.add(new FlipHorizontalFilter());
        filters.add(new FlipVerticalFilter());
        filters.add(new GrayscaleFilter());
        filters.add(new SharpenFilter());
        filters.add(new SoftenFilter());
        return filters;
    }
    /**
     * Uses a List of Filter objects to create a List of FilterButtons.
     * @return a list of Filter Buttons to add to the GUI.
     */
    private List<JButton> generateFilterButtons() {
        final List<JButton> buttons = new ArrayList<>();
        final List<Filter> filters = generateFilters();
        for (int i = 0; i < filters.size(); i++) {
            buttons.add(createFilterButton(filters.get(i)));
        }
        return buttons;
    }
    /**
     * Uses a Filter object to construct a button, label it, and attach the appropriate 
     * listener.
     * @param theFilter used to generate the button.
     * @return the new filter button.
     */
    private JButton createFilterButton(final Filter theFilter) {
        final JButton filterButton = new JButton(theFilter.getDescription());
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                theFilter.filter(myImage);
                myImageLabel.repaint();
            }
            });
        return filterButton;
    }
    /**
     * Creates the Label used to house the Image in the GUI.
     * @return the Label.
     */
    private JLabel generateImageLabel() {
        myImageLabel = new JLabel();
        myImageLabel.setHorizontalAlignment(JLabel.CENTER);
        myImageLabel.setVerticalAlignment(JLabel.CENTER);
        return myImageLabel;
    }
    /**
     * Creates the Open button, it's action listener, attaches the two, and returns a reference
     * to it.
     * @return the open button.
     */
    private JButton createOpenButton() {
        final JButton openButton = new JButton("Open...");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final int result = myChooser.showOpenDialog(myFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        myImage = PixelImage.load(myChooser.getSelectedFile());
                        updateImageIcon(myImage);
                        myFrame.pack();
                        setButtonsEnabled(true);
                    } catch (final IOException e) {
                        displayErrorMessage();
                    }
                }
            }
        });
        return openButton;
    }
    /**
     * Creates the Save button, it's action listener, attaches the two, and returns a reference
     * to it.
     * @return the save button.
     */
    private JButton createSaveButton() {
        mySaveButton = new JButton("Save As...");
        mySaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final int result = myChooser.showSaveDialog(myFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        myImage.save(myChooser.getSelectedFile());
                    } catch (final IOException anException) {
                        anException.printStackTrace();
                    }
                }
            }
        });
        return mySaveButton;
    }
    /**
     * Creates the Close button, it's action listener, attaches the two, and returns a 
     * reference to it.
     * @return the close button.
     */
    private JButton createCloseButton() {
        myCloseButton = new JButton("Close Image");
        myCloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                updateImageIcon(null);
                setButtonsEnabled(false);
                myFrame.pack();
            }
        });
        return myCloseButton;
    }
    /**
     * Creates the filter panel by using the List of filter buttons.
     * @return the filter button panel.
     */
    private JPanel generateFilterPanel() {
        final JPanel filterPanel = new JPanel(new GridLayout(myFilterButtonArray.size(), 1));
        for (int i = 0; i < myFilterButtonArray.size(); i++) {
            filterPanel.add(myFilterButtonArray.get(i));
        }
        return filterPanel;
    }
    /**
     * Creates the panel that contains the open, save, and close buttons, then adds the 
     * buttons to the panel.
     * @return a reference to the panel to be added to the Southern portion of the 
     * BorderLayout.
     */
    private JPanel generateSouthPanel() {
        final JPanel southPanel = new JPanel();
        southPanel.add(createOpenButton());
        southPanel.add(createSaveButton());
        southPanel.add(createCloseButton());
        return southPanel;
    }
}