/*
 * TCSS 305 - Assignment 4
 */

package gui;

import java.awt.EventQueue;

/**
 * Runs the program by instantiating and starting the GUI.
 * 
 * @author TCSS 305 instructors
 * @version 1.3
 */
public final class Driver {

    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private Driver() {
        throw new IllegalStateException();
    }

    /**
     * The main method, invokes the GUI. Command line arguments are ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final GUI gui = new GUI();
                gui.start();
            }
        });
    }
}
