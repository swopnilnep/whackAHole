// Authors: David Will and Swopnil N. Shrestha
// Purpose: Develop a Simple whack a mole game with Java Awt and Swing Libraries
// Instructor: Dr. Alan K Zaring
// Class: CS252 Object Oriented Programming with Java (Spring 2019)
// Date: 2019/18/04

 package phase1;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.JButton;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Phase1 {

//    Pre: What needs to be true
//    Post: What changes
//    Loop invariants: x
    
    public static void main(String[] args) {

//        
//        Get user input from System.in using the Scanner object and verify with
//        predeterminded conditions. Begin the EventQueue to generate the GUI
//        
        // Use the scanner class to get the number of rows and columns from the user
        // using System.in, and verify that these are integers within the desired range (between 3 and 9) 

        Scanner myScanner = new Scanner(System.in);

        System.out.print("Enter the number of rows (between 3 and 9, inclusive): ");
        
        
        // Invariant
        //      myScanner does not have an integer
        while (!myScanner.hasNextInt()){
            System.err.println("Non-integer entered. Please enter a valid integer");
            myScanner.next();
            System.out.print("Enter the number of rows (between 3 and 9, inclusive): ");
        }
        
        int userRows = myScanner.nextInt();

        // Invariant
        //      myScanner is out of the expected range
        while (userRows > 9 | userRows < 3) {
            System.out.print("Please enter a valid number of rows: ");
            userRows = myScanner.nextInt();
        }

        int userInputRows = userRows;

        System.out.print("Enter the number of columns (between 3 and 9, inclusive): ");
        
        // Invariant
        //      myScanner does not have an integer
        while (!myScanner.hasNextInt()){
            System.err.println("Non-integer entered. Please enter a valid integer");
            myScanner.next();
            System.out.print("Enter the number of rows (between 3 and 9, inclusive): ");
        }
        
        int userColumns = myScanner.nextInt();

        // Invariant
        //      myScanner is out of the expected range
        while (userColumns > 9 | userColumns < 3) {
            System.out.print("Please enter a valid number of columns: ");
            userColumns = myScanner.nextInt();
        }

        int userInputColumns = userColumns;

        // Begin main runnable
        // Create and set up the main program frame
        
        EventQueue.invokeLater(
            new Runnable() {

                @Override
                public void run() {
                    
                    ProgramFrame frame = new ProgramFrame(userInputRows, userInputColumns);

                    frame.setTitle("Holes");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }

            }
        );
        
        
    }

}

// This is a class for the program frame, which only includes a constructor for
// the class object. We add a component, entitled "HolesComponent" to the frame.
class ProgramFrame extends JFrame {

    public ProgramFrame(int inputRows, int inputColumns) {

        JPanel testPanel = new JPanel();
        
        testPanel.setLayout(new BorderLayout());
       
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new BoxLayout(newPanel, Y_AXIS));
        newPanel.setBackground(Color.RED);
  
        JLabel scoreLabel = new JLabel("Score: ");
        JLabel levelLabel = new JLabel("Level: ");
        JLabel livesLabel = new JLabel("Lives Remaining:                               ");
        JButton pauseButton = new JButton("Pause");
        JButton highScoreButton = new JButton("High Scores");
        newPanel.add(scoreLabel);
        newPanel.add(levelLabel);
        newPanel.add(livesLabel);
        newPanel.add(pauseButton);
        newPanel.add(highScoreButton);
        testPanel.add(newPanel, "Center");

        
        add(testPanel, "East");
        
        add(new HolesComponent(inputRows, inputColumns));
        
        pack();

    }
}
 
 class sideBarPanel extends JPanel {
     public sideBarPanel(){
         
     }
     
 }


class HolesComponent extends JComponent {

    // Instance Fields
    private final int OUR_INITIAL_DIAMETER = 200;
    private int userRows;
    private int userColumns;

    final private int OUR_DEFAULT_WIDTH;
    final private int OUR_DEFAULT_HEIGHT;

    private Ellipse2D myRedEllipse;
    private Ellipse2D myCurrentEllipse;

    private int myScore = 0;

    private ArrayList < Ellipse2D > myEllipses = new ArrayList < > ();
    
    //defaultSound = new URL ("/home/willda07/Documents/whackahole");

// Private Accessors
    
    // pre: This instance is in a valid state
    // post: returns field myEllipses, nothing is changed
    private ArrayList < Ellipse2D > ellipses() {
        return myEllipses;
    }
    
    // pre: This instance is in a valid state
    // post: Returns the field myRedEllipse, nothing is changed
    private Ellipse2D redEllipse() {

        return myRedEllipse;
    }
    
    // pre: This instance is in a valid state
    // post: returns field myScore, nothing is changed
    private int score() {

        return myScore;

    }
    
    // pre: This instance is in a valid state and the user has clicked on a red ellipse
    // post: adds 5 to the myScore field, then returns myScore
    private void addToScore() {

        myScore += 5;

    }
    
    // pre: This instance is in a valid state
    // post: Returns the field myCurrentEllipse, nothing is changed
    private Ellipse2D currentEllipse() {

        return myCurrentEllipse;

    }

// Private Mutators

    // pre: This instance is in a valid state
    // post: Changes the myEllipses field to a different array containing Ellipse2D objects and doesn't return anything
    private void setEllipses(ArrayList < Ellipse2D > other) {

        myEllipses = other;

    }

    // pre: This instance is in a valid state
    // post: Sets the field myCurrentEllipse to different Ellipse2D object, which is passed in as a parameter
    //       This method doesn't return anything.
    private void setCurrentEllipse(Ellipse2D other) {

        myCurrentEllipse = other;

    }

    // pre: This instance is in a valid state
    // post: Sets the field myCurrentEllipse to an Ellipse2D object that is passed in as a parameter
    //       This method does not return anything
    private void setRedEllipse(Ellipse2D other) {

        myRedEllipse = other;

    }
    
    // pre: This instance is in a valid state
    // post: Field myEllipses has been added to via a loop based on fields userRows and userColumns
    //       This function returns nothing
    private void initializeEllipses() {

        Ellipse2D testEllipse;

        for (int initialX = 0; initialX < OUR_DEFAULT_WIDTH; initialX += OUR_INITIAL_DIAMETER) {
            for (int initialY = 0; initialY < OUR_DEFAULT_HEIGHT; initialY += OUR_INITIAL_DIAMETER) {
                testEllipse = new Ellipse2D.Double(initialX, initialY, OUR_INITIAL_DIAMETER, OUR_INITIAL_DIAMETER);
                ellipses().add(testEllipse);

            }
        }
    }
    
    // pre: This instance is in a valid state
    // post: Field myRedEllipse is changed to a random ellipse in myEllipses by accessing a random index of myEllipses
    //       This function returns nothing
    private void setRedEllipse() {

        Random getRandomIndex = new Random();
        int redEllipseIndex = getRandomIndex.nextInt(ellipses().size());
        HolesComponent.this.setRedEllipse(ellipses().get(redEllipseIndex));

    }
    
    
    // pre: The instance is in a valid state
    // post: The dimensions of each ellipse are modified based on the amount that the
    //       window has been resized by the user--this amount is passed in as parameters newEllipseHeight and newEllipseWidth. 
    //       Additionally, the field myEllipses has been updated to include the new Ellipse2D objects 
    //       because they have different heights and widths.
    private void resetEllipsesOnResize(int newEllipseHeight, double newEllipseWidth) {

    ArrayList < Ellipse2D > newEllipseArrayList = new ArrayList < Ellipse2D > ();

    Ellipse2D redoneEllipse = new Ellipse2D.Double(0, 0, 0, 0);

    int initialX = 0;
    int initialY = 0;

    for (int numColumns = 0; numColumns < userColumns; ++numColumns) {
        initialY = 0;
        for (int numRows = 0; numRows < userRows; ++numRows) {
            redoneEllipse = new Ellipse2D.Double(initialX, initialY, newEllipseWidth, newEllipseHeight);
            newEllipseArrayList.add(redoneEllipse);
            initialY += newEllipseHeight;
        }

        initialX += newEllipseWidth;
    }

    setEllipses(newEllipseArrayList);
    repaint();
    }
    
    
    // pre: This instance is in a valid state
    // post: This function returns an Ellipse2D object based on the location of a
    //       clickpoint, which is passed in as a formal parameter
    private Ellipse2D findEllipseContainingPoint(Point2D clickPoint) {

    int ellipseNumber = 0;
    Boolean pointInEllipse = false;
    Ellipse2D thisEllipse = null;

    for (; ellipseNumber < ellipses().size(); ++ellipseNumber) {

        thisEllipse = ellipses().get(ellipseNumber);

        if (thisEllipse.contains(clickPoint))
            pointInEllipse = true;

        if (pointInEllipse) break;
    }

    return pointInEllipse ? thisEllipse : null;

    }
    
    // Pre: The instance is in a vaild state and soundName is a vaild file
    // Post: The soundfile entered by the user has been played
    //       We use a try/catch block to make sure that the program doesn't crash on
    //       an invalid filename.
    private void playSound(String soundName){
        try{
    
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            
        }
        
        catch(Exception ex){
            System.out.println("Error playing sound.");
        }
    }
    
//    private void timeHandle(){
//        long runningTimeNano = System.nanoTime();
//        double runningTimeSec = runningTimeNano / 1000000000.0;
//        
//        while (true){
//            if (runningTimeSec % 5 == 0){
//                setRedEllipse();
//            }
//        }
//    }
        

// Overridden methods
    // pre: The instance is in a valid state
    // post: updates the canvas to match any changes when a function modifies myEllipses
    //       Iterates through the myEllipses list and paints each ellipse red if it is
    //       myRedEllipse, or black otherwise.
    @Override
    public void paintComponent(Graphics canvas) {

        // Invariant
        //      For each iterator, ellipses() contains an Ellipse2D object
        //      that is painted either red or black
        for (Ellipse2D ellipseOnCanvas: ellipses()) {
            if (ellipseOnCanvas == redEllipse()) {
                ((Graphics2D) canvas).setColor(Color.red);
                ((Graphics2D) canvas).fill(ellipseOnCanvas);
            } else {

                ((Graphics2D) canvas).setColor(Color.black);
                ((Graphics2D) canvas).fill(ellipseOnCanvas);
            }

            canvas.setFont(new Font("TimesRoman", Font.PLAIN, 80));
            canvas.setColor(Color.green);
            canvas.drawString("Score: " + score(), 0, 100);

        }

    }
    // pre: This instance is in a valid state
    // post: calls InitializeEllipses to draw the window with the dimensions that 
    //       the user entered, and changes the dimensions of the screen to match
    @Override
    public Dimension getPreferredSize() {
        initializeEllipses();
        return new Dimension(OUR_DEFAULT_WIDTH, OUR_DEFAULT_HEIGHT);

    }

// Constructors
    // pre: None
    // post: takes in the user's input for rows and columns
    //       field userRows equals the user's row input
    //       field userColumns equals the user's column input
    //       Changes the width and height parameters to correctly fit the number of ellipses
    //       Sets myCurrentEllipse to null and myEllipses to an empty ArrayList
    //       Adds a MouseListener and ComponentListener
    public HolesComponent(int inputRows, int inputColumns) {

        userRows = inputRows;
        userColumns = inputColumns;
        OUR_DEFAULT_WIDTH = userColumns * (OUR_INITIAL_DIAMETER);
        OUR_DEFAULT_HEIGHT = userRows * (OUR_INITIAL_DIAMETER);

        setEllipses(new ArrayList < Ellipse2D > ());
        setCurrentEllipse(null);

        addMouseListener(new MouseHandler());
        addComponentListener(new windowComponentListener());

    }

    // This class handles the window resizes and calls the appropriate function
    // to make sure that the ellipses stay proportional to the window
    private class windowComponentListener implements ComponentListener {

        @Override
        public void componentHidden(ComponentEvent event) {
            ;
        }

        @Override
        public void componentMoved(ComponentEvent event) {
            ;
        }

        @Override
        public void componentResized(ComponentEvent event) {
            //double sidebarWidth = event.getComponent().getWidth() * 0.2;
            
            int newHeight = event.getComponent().getHeight() / userRows;
            double newWidth = (event.getComponent().getWidth()) / userColumns;
            resetEllipsesOnResize(newHeight, newWidth);
            setRedEllipse();

        }

        @Override
        public void componentShown(ComponentEvent e) {
            ;

        }
    }

    // This class monitors the users clicks and changes the red ellipse if the user
    // successfully clicks on myRedEllipse
    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent event) {

            setCurrentEllipse(findEllipseContainingPoint(event.getPoint()));

            if (currentEllipse() == redEllipse()) {
                playSound("correctSound.wav");
                addToScore();
                setRedEllipse();

            }
            
            else{
                playSound("wrongSound.wav");
            }

            repaint();

        }

    }
}