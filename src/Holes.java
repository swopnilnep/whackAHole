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
import java.io.File;
import javax.swing.*;

import java.util.Scanner;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
        BorderLayout borderLayout = new BorderLayout();
        mainPanel().setLayout(borderLayout);
        

        // 
        // Create the Model
        // 

        HolesModel model =
                    new HolesModel(initialNumberOfRows, initialNumberOfColumns);
    
        // 
        // Add Components to the Panel
        // 

        JPanel holesPanel = new JPanel();
        holesPanel.setLayout(new BoxLayout(holesPanel, BoxLayout.Y_AXIS));
        holesPanel.add(new HolesComponent(model));
        
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.add(new ScoreComponent(model));
        sidebarPanel.add(new LevelsComponent(model));
        sidebarPanel.add(new LivesComponent(model));
        
        mainPanel().add(holesPanel, BorderLayout.CENTER);
        mainPanel().add(sidebarPanel, BorderLayout.EAST);
        

        // 
        // Add the Panel to the Program Frame
        // 

        add(mainPanel());
    }

}

class ScoreComponent extends JLabel implements HolesModelObserver
{
    //
    // Private Fields
    //

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
        
        public ScoreComponent(HolesModel initialModel)
        {
            setModel(initialModel);
            model().attach(this);
            
            setFont(new Font("Times New Roman", Font.BOLD, 30));
            setForeground(Color.BLACK);
            
            setText("Score: " + model().score());
        }
        
    //
    // Public Observation Methods
    //
        @Override
        public void updateScore()
        {
            setText("Score: " + model().score());
            
            repaint();
        }
        
        @Override
        public void updateRedHolePosition()
        {

            }
        
        @Override
        public void updateSoundStatus()
        {
            
        }

        @Override
        public void updateLevel()
        {
            
        }
        
        @Override
        public void updateLivesRemaining()
        {
            
        }
    
}


class LivesComponent extends JLabel implements HolesModelObserver
{
    //
    // Private Fields
    //

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
        
        public LivesComponent(HolesModel initialModel)
        {
            setModel(initialModel);
            model().attach(this);
            
            setFont(new Font("Times New Roman", Font.BOLD, 30));
            setForeground(Color.BLACK);
            
            setText("Lives Remaining: " + model().livesRemaining());
        }
        
    //
    // Public Observation Methods
    //
        @Override
        public void updateScore()
        {
         
        }
        
        @Override
        public void updateRedHolePosition()
        {

            }
        
        @Override
        public void updateSoundStatus()
        {
            
        }
        
        @Override
        public void updateLivesRemaining()
        {
            setText("Lives Remaining: "+ model().livesRemaining());
            repaint();
        }
        
        @Override
        public void updateLevel()
        {
            
        }
    
}

class LevelsComponent extends JLabel implements HolesModelObserver
{
    //
    // Private Fields
    //

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
        
        public LevelsComponent(HolesModel initialModel)
        {
            setModel(initialModel);
            model().attach(this);
            
            setFont(new Font("Times New Roman", Font.BOLD, 30));
            setForeground(Color.BLACK);
            
            setText("Level: " + model().level());
        }
        
    //
    // Public Observation Methods
    //
        @Override
        public void updateScore()
        {

        }
        
        @Override
        public void updateRedHolePosition()
        {

            }
        
        @Override
        public void updateSoundStatus()
        {
            
        }

        @Override
        public void updateLevel()
        {
            setText("Level: " + model().level());
            repaint();
        }
        
        @Override
        public void updateLivesRemaining()
        {
            
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
    // Methods
    //
        
       
        ActionListener taskPerformer = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent evt) {  
            model().randomizeRedHolePosition();
            repaint();
            }
        };
        int myDelay = 1000;
        Timer myTimer = new Timer(myDelay, taskPerformer);
        
        
        public void playSound(String soundName){
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
        @Override
        public void updateScore()
        {

            }
        @Override
        public void updateRedHolePosition()
        {

            repaint();

            }
        
        @Override
        public void updateSoundStatus()
        {
            
        }
        
        @Override
        public void updateLivesRemaining()
        {
            
        }

        @Override
        public void updateLevel()
        {
            playSound(model().wrongSound());
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
                myTimer.start();
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
                            myTimer.restart();
                            model().setScore(model().score() + model().scoreIncrement());
                            
                            if (!model().isMuted()){
                                playSound(model().correctSound());
                            }
                        //
                        // Pseudo-randomly move the red hole
                        //
                        
                            model().incrementCorrectClicks();
                            if (model().correctClicks() % 10 == 0){
                                model().incrementLevel();
                               
                            }

                            model().randomizeRedHolePosition();

                        }
                    
                    else{
                        if (!model().isMuted()){
                            playSound(model().wrongSound());
                            model().decrementLivesRemaining();
                        }
                    }

                    }

                }


    }
