package edu.umaine.cs.cos515.max;

import javax.swing.JFrame;

import edu.umaine.cs.appletConsole.ConsoleApplet;

/**
 * Runs the <code>MaximaToJava</code> converter as an applet or as a stand
 * alone windowed application.
 * 
 * @author Mark Royer
 * 
 */
public class MaximaToJavaConsole extends ConsoleApplet {

    /**
     * Required for serializability
     */
    private static final long serialVersionUID = -1447111213688478898L;

    @Override
    public void init() {
        super.init();

        this.setTitle("Maxima to Java");

        this.setMainProgram(new MaximaToJava());

    }

    /**
     * @param args
     *            Not used
     */
    public static void main(String[] args) {
        MaximaToJavaConsole ma = new MaximaToJavaConsole();

        JFrame frame = new JFrame("Console Application");
        frame.getContentPane().add(ma);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        ma.init();
    }

}
