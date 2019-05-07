// 
// Authors: David Will and Swopnil N. Shrestha
// Purpose: Develop a Simple whack a mole game with Java Awt and Swing Libraries
// Instructor: Dr. Alan K Zaring
// Class: CS252 Object Oriented Programming with Java (Spring 2019)
// Date: 2019/18/04
// Phase: 03
// 

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

import java.util.Scanner;
import java.util.Random;

public class Holes {
        public static void main(String[] args)
        {

            // 
            // Get user input from System.in using the Scanner object and verify with
            // predeterminded conditions.
            // 

            Scanner input = new Scanner(System.in);

            // 
            // userInputRows is the desired number of rows of holes, entered by the user
            // 

            int userInputRows = -1;

            // 
            // userInputColumns is the desired number of rows of columns, entered by the user
            // 

            int userInputColumns = -1;

            // 
            // rowInputIsFulfilled is true if an integer is available for input and the input integer
            // is between 3 and 9, inclusive
            // 

            boolean rowInputIsFulfilled = false;

            // 
            // Invariants: Until the input has an integer which is within the range of 3-9 inclusive,
            //  rowInputIsFulfilled will be false
            // 

            while (!rowInputIsFulfilled)
            {

                System.out.print("Enter number of rows (between 3 and 9, inclusive): ");
                
                if (input.hasNext() && !input.hasNextInt()){

                    System.err.println("*** Error: Input must be an integer");
                    input.next();

                }

                else if (input.hasNextInt()) {

                    userInputRows = input.nextInt();

                    if (userInputRows < 3 || userInputRows > 9){

                        System.err.println("*** Error: Input must be between 3 and 9, inclusive");

                    }

                    else {
    
                        rowInputIsFulfilled = true;

                    }

                }
                
            }

            //
            // columnInputIsFulfilled is true if an integer is available for input and the input integer
            // is between 3 and 9, inclusive
            //
            boolean columnInputIsFulfilled = false;

            //
            // Invariants: Until the input has an integer which is within the range of 3-9 inclusive,
            //  columnInputIsFulfilled will be false
            //

            while (!columnInputIsFulfilled)
            {

                System.out.print("Enter number of columns (between 3 and 9, inclusive): ");

                if (input.hasNext() && !input.hasNextInt()){

                    System.err.println("*** Error: Input must be an integer");
                    input.next();

                }

                else if (input.hasNextInt()) {

                    userInputColumns = input.nextInt();

                    if (userInputColumns < 3 || userInputColumns > 9){

                        System.err.println("*** Error: Input must be between 3 and 9, inclusive");
    
                    }
    
                    else {
    
                        columnInputIsFulfilled = true;
    
                    }

                }
                
            }

            final int OUR_NUMBER_OF_ROWS = userInputRows;
            final int OUR_NUMBER_OF_COLUMNS = userInputColumns;
            
            // 
            // Run the program frame that displays a score and a userInputRows by userInputColumns
            // grid of holes (Ellipses), of which one hole is pseudo-randomly chosen to be colored
            // in red while all the other holes are colored in black. Clicking on the red hole adds
            // 5 points to the user
            // 
            
            EventQueue.invokeLater(
                new Runnable()
                {

                    public void run() {

                        ProgramFrame frame = new ProgramFrame(OUR_NUMBER_OF_ROWS, OUR_NUMBER_OF_COLUMNS);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);

                    }

                }
            );

        }
    
    }


class ProgramFrame extends JFrame
{

    // 
    // Private Fields
    // 

    private final String OUR_TITLE = "Holes";
    private final int OUR_INITIAL_HOLE_DIAMETER = 200;
    private JPanel myMainPanel;

    // 
    // Private Accessors
    // 

    private JPanel mainPanel(){
        return myMainPanel;
    }

    // 
    // Private Mutators
    // 

    private void setMainPanel(JPanel other){
        myMainPanel = other;
    }

    // 
    // Public Constructors
    // 

    public ProgramFrame(int initialNumberOfRows, int initialNumberOfColumns){

        setSize(
            initialNumberOfColumns * OUR_INITIAL_HOLE_DIAMETER,
            getInsets().top + initialNumberOfRows * OUR_INITIAL_HOLE_DIAMETER
            );
        setTitle(OUR_TITLE);

        // 
        // Set Main Panel to Hold all Components
        // 
    
        setMainPanel(new JPanel());
        mainPanel().setLayout(new BoxLayout(mainPanel(), BoxLayout.Y_AXIS));
    
        // 
        // Create the Model
        // 

        HolesModel model =
                    new HolesModel(initialNumberOfRows, initialNumberOfColumns);
    
        // 
        // Add Components to the Panel
        // 
        
        // mainPanel().add(new ScoreComponent(model));
        mainPanel().add(new HolesComponent(model));

        // 
        // Add the Panel to the Program Frame
        // 

        add(mainPanel());
    }

}

class HolesComponent extends JComponent implements HolesModelObserver
{

    //
    // Private Fields
    

        private HolesModel myModel;

    //
    // Private Accessors
    //

        private HolesModel model()
        {

            return myModel;

            }

    //
    // Private Mutators
    //

        private void setModel(HolesModel otherModel)
        {

            myModel = otherModel;

            }

    //
    // Public Ctors
    //

        public HolesComponent(HolesModel initialModel)
        {

            setModel(initialModel);
            model().attach(this);

            //
            // Click handler listenes to clicks on the holes
            //

                addMouseListener(new MouseClickHandler());

            //
            // Component listener listenes to screen resize
            //

                addComponentListener(new ResizeHandler());

            }

    //
    // Public Observation Methods
    //

        public void updateScore()
        {

            }

        public void updateRedHolePosition()
        {

            repaint();

            }

    //
    // Public Overrides
    //

        //
        // Interacts with the model to set the position and sizes of the holes
        //

            @Override
            public void paintComponent(Graphics canvas)
            {

                final Graphics2D canvas2D = ((Graphics2D) canvas);

                    final double holeWidth =
                        ((double) getWidth()) / model().numberOfColumns();
                    final double holeHeight =
                        ((double) getHeight()) / model().numberOfRows();


                    for (int row = 0; row < model().numberOfRows(); ++ row)
                        for (int column = 0; column < model().numberOfColumns(); ++ column) {


                                canvas2D.setPaint(
                                    row == model().redHoleRow()
                                            && column == model().redHoleColumn()
                                        ? Color.RED
                                        : Color.BLACK
                                    );

                                model().hole(row, column).setFrame(
                                    column * holeWidth,
                                    row * holeHeight,
                                    holeWidth,
                                    holeHeight
                                    );


                                canvas2D.fill(model().hole(row, column));

                            }

            }


    //
    // Private Inner Classes
    //


        // Acts as a controller for the model
        //

            private class ResizeHandler extends ComponentAdapter
            {

                @Override
                public void componentResized(ComponentEvent event)
                {

                    //
                    // Pseudo-randomly move the red hole
                    //

                        model().randomizeRedHolePosition();

                    }

                }

            private class MouseClickHandler extends MouseAdapter
            {

                @Override
                public void mouseClicked(MouseEvent event)
                {

                    if (model().hole(
                            model().redHoleRow(),
                            model().redHoleColumn()
                            ).contains(event.getPoint())) {

                        //
                        // Update the score with the additional points
                        //

                            model().setScore(model().score() + model().scoreIncrement());

                        //
                        // Pseudo-randomly move the red hole
                        //

                            model().randomizeRedHolePosition();

                        }

                    }

                }


    }
